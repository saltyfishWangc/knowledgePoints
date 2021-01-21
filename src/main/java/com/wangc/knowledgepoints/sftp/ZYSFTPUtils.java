package com.wangc.knowledgepoints.sftp;

import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Properties;
import java.util.Vector;

/**
 * 中原消金SFTP工具类
 * 通过密钥方式连接中原FTP服务器
 * 进行相关操作
 */
public class ZYSFTPUtils {

    public static ChannelSftp getChannel(String privKeyPath, String remoteUser, int port, String host) throws Exception{
        JSch jsch = new JSch();
        //密钥文件绝对路径
        Channel channel=null;
        try {
            jsch.addIdentity(privKeyPath);
            //用户名
            Session session = jsch.getSession(remoteUser, host, port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(60000);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return (ChannelSftp)channel;
    }

    public static void download(ChannelSftp sftp,String localPath,String remoteAbsPath,String localFileName) throws Exception{
        //check param
        if (StringUtils.isEmpty(localPath)
                || StringUtils.isEmpty(remoteAbsPath)
                || StringUtils.isEmpty(localFileName)) {
            throw new Exception("必须参数不能为空");
        }
        File localDir=new File(localPath);
        System.out.println("本地绝对路径：" + localDir.getAbsolutePath());
        if(!localDir.isDirectory()){
            throw new Exception(String.format("路径:[%s]不是一个目录", localPath));
        }
        try {
            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> vector=(Vector<ChannelSftp.LsEntry>)sftp.ls(remoteAbsPath);
            if(vector==null||vector.size()!=1){
                //target file not exist!
                throw new Exception(String.format("要下载的文件:[%s]不存在SFTP服务器上", localFileName));
            }
            //删除本地重名文件
            String fullFilePath=localPath.endsWith(File.separator)?localPath:(localPath+File.separator)+localFileName;
            System.out.println("下载文件到本地，路径：" + fullFilePath);
            File oldFile=new File(fullFilePath);
            if(oldFile.exists()){
                oldFile.delete();
            }
            sftp.get(remoteAbsPath, fullFilePath);
        } catch (SftpException e) {
            e.printStackTrace();
            throw new Exception(String.format("从SFTP服务器上下载文件异常:%s", e.toString()));
        }
    }

    public static void closeSession(ChannelSftp sftp){
        if(sftp!=null){
            try {
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                System.out.println("关闭SFTP session 异常");
            }
        }
    }
    
    /**
     * 删除本地文件
     * 
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        if (!file.isFile()) {
            return false;
        }

        return file.delete();
    }
}
