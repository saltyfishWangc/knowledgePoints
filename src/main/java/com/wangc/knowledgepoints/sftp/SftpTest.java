package com.wangc.knowledgepoints.sftp;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author
 * @Description:
 * @date 2020/6/30 16:08
 */
public class SftpTest {

    public static String BAT_FILE_PATH = "/upload/53/out";

    public static String ZIP_FILE_PATH = "/upload/53/images";

    public static String DATE = "2028-07-12";

    public static void main(String... args) {

        String signedFilesName = "zyxfA108_yyyymmdd.dat".replaceFirst("yyyymmdd", DATE.replaceAll("-", ""));

        String ZYXJ_FTP_HOST = "sftp-test.hnzycfc.com";
        String ZYXJ_FTP_PORT = "2222";
        String ZYXJ_FTP_USER = "msok";

//        String FILE_PATH = "/msok/upload/53/out";
//        String FILE_PATH = "/upload/53/out";


        ChannelSftp sftp = null;
        try {
            System.out.println("开始连接sftp");
            System.out.println("密钥地址：" + SftpTest.class.getClassLoader().getResource("msok").getPath());
            sftp = ZYSFTPUtils.getChannel(SftpTest.class.getClassLoader().getResource("msok").getPath(), ZYXJ_FTP_USER, Integer.valueOf(ZYXJ_FTP_PORT), ZYXJ_FTP_HOST);
            System.out.println("下载中原已签署同步协议，路径：【" + BAT_FILE_PATH + "/" + DATE.replaceAll("-", "") + "/" +  signedFilesName + "】");
            ZYSFTPUtils.download(sftp, "./", BAT_FILE_PATH + "/" + DATE.replaceAll("-", "") + "/" +  signedFilesName, signedFilesName);

            File repayFile = new File(signedFilesName);
            if (!repayFile.exists()) {
                System.out.println("ZyxjSignedAgreementsSyncOp, " + signedFilesName + "不存在");
                throw new Exception("中原消金文件下载失败");
            }

            System.out.println("开始读取bat文件内容，stp路径：【" + ZIP_FILE_PATH + "】，本地路径：【" + "./" + "】，文件名：【" + signedFilesName + "】");
            readBatFile(sftp, ZIP_FILE_PATH, "./", signedFilesName);
        } catch (Exception e) {
            System.out.println("异常");
            sftp.disconnect();
        }
    }

    /**
     * 读取文件内容
     * @param remotePath
     * @param localPath
     * @param readFileName
     * @throws Exception
     */
    public static void readBatFile(ChannelSftp sftp, String remotePath, String localPath, String readFileName) throws Exception {
        BufferedReader br = null;
        int total = 0;
        int infact = 0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(localPath + readFileName), "UTF-8"));
            String lineMsg;
            int lineNo = 1;
            int customers = 0;
            String sumDate = "";
            int sumCustomers = 0;
            JSONObject lineObj = null;

            // 签署协议字段
            String custName = "";       // 客户姓名
            String idNo = "";           // 证件号码
            String idTyp = "";          // 证件类型
            String submitLink = "";     // 提交环节 01 额度激活 02 提款环节 99 贷后环节
            String cooppfApplCde = "";  // 关联的业务编号（合作方业务流水号）
            String applSeq = "";        // 关联的业务编号（中原的业务流水号）
            String loanTyp = "";        // 产品编号/专案编号
            String fileName = "";       // 文件名
            String fileRemark = "";     // 备注
            while ((lineMsg = br.readLine()) != null) {
                lineMsg = new String(lineMsg.getBytes());
                System.out.println("第：" + lineNo + "行bat文件内容：【" + lineMsg + "】");
                lineObj = JSONObject.parseObject(lineMsg);
                if (lineNo == 1) {
                    // 数据统计日期
                    sumDate = lineObj.getString("sum_date");
                    // 记录条数
                    sumCustomers = lineObj.getIntValue("sum_bs");
                } else {
                    // 客户姓名
                    custName = lineObj.getString("cust_name");
                    // 证件号码
                    idNo = lineObj.getString("id_no");
                    // 证件类型
                    idTyp = lineObj.getString("id_typ");
                    // 提交环节
                    submitLink = lineObj.getString("submit_link");
                    // 关联的业务编号（合作方业务流水号）
                    cooppfApplCde = lineObj.getString("cooppf_appl_cde");
                    // 关联的业务编号（中原的业务流水号）
                    applSeq = lineObj.getString("appl_seq");
                    // 产品编号/专案编号
                    loanTyp = lineObj.getString("loan_typ");
                    // 文件名
                    fileName = lineObj.getString("file_name");
                    // 备注
                    fileRemark = lineObj.getString("file_remark");

                    if (StringUtils.isEmpty(fileName)) continue;
                    System.out.println("下载中原zip文件，路径：【" + ZIP_FILE_PATH + "/" + DATE.replaceAll("-", "") + "/" + fileName + "】");
                    ZYSFTPUtils.download(sftp, "./", ZIP_FILE_PATH + "/" + DATE.replaceAll("-", "") + "/" + fileName, fileName);
                    File ldFile = new File(fileName);
                    if (!ldFile.exists()) {
                        System.out.println("ZyxjSignedAgreementsSyncOp 下载zip文件失败");
                        throw new Exception("中原消金对账文件下载失败");
                    }
                    // 读取zip文件
                    readZipFile(idNo, submitLink, cooppfApplCde, applSeq, remotePath, localPath, fileName);
                }
                lineNo++;


            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取ftp稠州银行代偿文件异常！");
        } finally {
            br.close();
        }
    }

    /**
     * 读取zip文件内容
     * @param remotePath
     * @param localPath
     * @param fileName
     * @throws Exception
     */
    public static void readZipFile(String idNo, String submitLink, String cooppfApplCde, String applSeq, String remotePath, String localPath, String fileName) throws Exception {
        ZipInputStream zin = null;
        int total = 0;
        int infact = 0;
        try {
            Set<String> fileNameSet = new HashSet<String>();
            System.out.println("开始读取zip文件，路径：【" + localPath + fileName + "】");
            zin = new ZipInputStream(new FileInputStream(localPath + fileName));
            ZipEntry entry = null;
            while (((entry = zin.getNextEntry()) != null) && !entry.isDirectory()) {
                System.out.println("zip解压文件名：【" + entry.getName() + "】");
                System.out.println("创建本地文件：【" + localPath + entry.getName() + "】");
                File file = new File(localPath + entry.getName()); // 创建本地文件
                if (!file.exists()) {
                    file.mkdirs();
                    file.createNewFile();
                    System.out.println("文件名：【" + entry.getName() + "】");
                    fileNameSet.add(entry.getName());
                }
                System.out.println("绝对路径：【" + file.getAbsolutePath() + "】");
                zin.closeEntry();
            }
            zin.close();

            System.out.println(fileNameSet.toString());

            /**
             * filetype_fileName.pdf
             *  08 扣款协议
             *  09 授信协议
             *  10 个人信息查询和使用授权书
             *  11 个人信用报告查询授权书
             *  12 芝麻信用授权书
             *  13 借款协议
             *  21 结清证明
             *  99 其他
             */
            Boolean saveProtocolThirdSign = false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读取ftp中原已签署协议异常！");
        } finally {
            if (zin != null) {
                zin.close();
            }
        }
    }
}
