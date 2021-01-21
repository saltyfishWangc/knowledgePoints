package com.wangc.knowledgepoints.dfs;

import com.msjr.dfs.client.DFSClient;
import com.msjr.dfs.client.util.DFSBase64Util;
import com.msjr.dfs.client.util.DFSImageUtil;
import com.msjr.dfs.commons.DFSMessage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 * @Description:
 * @date 2020/12/29 17:15
 */
public class DfsDemo {

    public static String compress(String fileId, int size, int height, int width, int compTimes, float scale) {
        byte[] bytes = null;
        DFSMessage fmessage = null;


//        try (ByteArrayOutputStream frontByteArrayOutputStream = new ByteArrayOutputStream()) {
//            fmessage = DFSClient.instance().download(fileId,frontByteArrayOutputStream);
//            if(fmessage.isSuccess()){
//                bytes = frontByteArrayOutputStream.toByteArray();
//                return compressImage(bytes, size*1024, height, width, compTimes, scale);
//            } else {
//                return "";
//            }
//        } catch (Exception e) {
//            return "";
//        }
        ByteArrayOutputStream frontByteArrayOutputStream = null;
        try {
            frontByteArrayOutputStream = new ByteArrayOutputStream();
            fmessage = DFSClient.instance().download(fileId,frontByteArrayOutputStream);
            if(fmessage.isSuccess()){
                bytes = frontByteArrayOutputStream.toByteArray();
                return compressImage(bytes, size*1024, height, width, compTimes, scale);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            if (frontByteArrayOutputStream != null) {
                try {
                    frontByteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String... args) {

    }


    /**
     * 压缩图片
     *   压缩流程改成：先按尺寸压缩再按大小压缩
     * @param imageBytes
     * @return
     */
    private static String compressImage(byte[] imageBytes, int size, int height, int width, int compTimes, float scale) {

        if(imageBytes.length <= 0){
            return null;
        }

        if (height > 0 && width > 0 ) {
            int rawHeight = getImgHeight(imageBytes);
            int rawWidth = getImgWidth(imageBytes);
            int rawSize = imageBytes.length;

            if(rawSize <= size && rawHeight <= height && rawWidth <= width) {
                return  DFSBase64Util.encode(imageBytes);
            } else {
                //先按照尺寸压缩
                if(rawHeight > height || rawWidth > width){
                    imageBytes = DFSImageUtil.resizeImage(imageBytes,rawWidth > width ? width : rawWidth,rawHeight > height ? height : rawHeight);
                }
            }
        }
        //再按大小压缩
        int i = 1;
        while(imageBytes.length > size){
            System.out.println("需要压缩");
            if(i > compTimes){
                return DFSBase64Util.encode(imageBytes);
            }
            imageBytes = DFSImageUtil.compressImage(imageBytes, scale);
            i++;
        }

        return DFSBase64Util.encode(imageBytes);
    }



    /**
     * 获取图片宽度
     * @param imagebytes
     * @return 宽度
     */
    private static int getImgWidth(byte[] imagebytes) {
        BufferedImage src = null;
        int ret = -1;
        try {
            InputStream sourceImage = new ByteArrayInputStream(imagebytes);
            src = javax.imageio.ImageIO.read(sourceImage);
            ret = src.getWidth(null); // 得到源图宽
            sourceImage.close();
        } catch (Exception e) {
        }
        return ret;
    }

    /**
     * 获取图片高度
     * @param imagebytes
     * @return 高度
     */
    private static int getImgHeight(byte[] imagebytes) {
        BufferedImage src = null;
        int ret = -1;
        try {
            InputStream sourceImage = new ByteArrayInputStream(imagebytes);
            src = javax.imageio.ImageIO.read(sourceImage);
            ret = src.getHeight(null); // 得到源图高
            sourceImage.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
