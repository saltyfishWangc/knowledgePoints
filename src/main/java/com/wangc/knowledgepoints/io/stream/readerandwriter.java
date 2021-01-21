package com.wangc.knowledgepoints.io.stream;

import java.io.*;

/**
 * @Description:
 * @date 2020/5/11 18:01
 */
public class readerandwriter {
    public static void main(String... args) throws Exception {
        String str = "你好嘛  我不好";

        OutputStream out = new FileOutputStream("D:\\132.20200511");
        out.write(str.getBytes());
        out.flush();
        out.close();

        System.out.println("开始读");

        String line = "";
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\132.20200511")));
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }
        InputStream is = new FileInputStream("D:\\\\132.20200511");
        byte[] b = new byte[2014];
//        while ()
    }
}
