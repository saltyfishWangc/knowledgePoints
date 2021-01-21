package com.wangc.knowledgepoints.base64;

import com.wangc.knowledgepoints.dfs.DfsDemo;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author
 * @Description:
 * @date 2020/12/29 14:54
 */
public class Base64TransImageUtil {

    public static void main(String... args) {
        // c008dbac-b284-4f18-a1af-c89c3521e9cc|45695088-9465-4544-b69a-3bcc280402d4
//        String fileId = "c008dbac-b284-4f18-a1af-c89c3521e9cc";
//        String fileId = "45695088-9465-4544-b69a-3bcc280402d4";
        // c416ab37-3501-4e0b-8e49-f6411da05d86|828de139-1917-4f68-8d31-1a01b7eebb5f
//        String fileId = "c416ab37-3501-4e0b-8e49-f6411da05d86";

//        String fileId_15w = "8aabf124-0443-4a28-bf4b-5fe32be1e015|2402861d-5a96-4a19-8f55-0ac2a6ab3173";
        String fileId_15w = "2402861d-5a96-4a19-8f55-0ac2a6ab3173";
//        String fileId_100w = "251707fb-821b-4a9c-89ee-dafc6f8a899c|b3166c64-18f9-4b95-b1df-d1dc536ce41b";
        String fileId_100w = "251707fb-821b-4a9c-89ee-dafc6f8a899c";

//        String fileId_800 = "251707fb-821b-4a9c-89ee-dafc6f8a899c|b3166c64-18f9-4b95-b1df-d1dc536ce41b";
        String fileId_800 = "251707fb-821b-4a9c-89ee-dafc6f8a899c";

//        int size = 600;
//        int height = 1024;
//        int width = 1536;
//        int compTimes = 5;
//        float scale = 0.9f;
        int size = 800;
        int height = 0;
        int width = 0;
        int compTimes = 5;
        float scale = 0.9f;

        String imgFilePath = "E:\\文档\\dxmimagedir\\100w-front.png";
//        String imgFilePath = "../resources/comp-imgae/100-front.png";
        try {
            GenerateImage(DfsDemo.compress(fileId_800, size, height, width, compTimes, scale), imgFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片转化成base64字符串
     *
     * @param imgPath
     * @return
     */
    public static String GetImageStr(String imgPath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = imgPath;// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        String encode = null; // 返回Base64编码过的字节数组字符串
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            // 读取图片字节数组
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encode(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return encode;
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgData     图片编码
     * @param imgFilePath 存放到本地路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean GenerateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }
}
