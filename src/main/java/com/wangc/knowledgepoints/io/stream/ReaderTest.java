package com.wangc.knowledgepoints.io.stream;

import java.io.*;

/**
 * @Description:
 * @date 2020/5/18 14:12
 */
public class ReaderTest {
    public static void main2(String... args) throws Exception {
        String line;
//        File zf = new File("F:\\settleClearToCoDetail-20000301.20200518");
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\settleClearToCoDetail-20000301.20200519"), "GBK"));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\dell\\Desktop\\data\\20200601\\settleClearToCoDetail-20000301.20200601"), "GBK"));
        while ((line = br.readLine()) != null) {
//            line=new String(line.getBytes("GBK"),"ISO8859_1");
//            line=new String(line.getBytes("UTF-8"),"UTF-8");
//            String payPostDate =line.substring(0,8);
            System.out.println("流水号：" + line.substring(0,64));
            System.out.println("借据号：" + line.substring(64,128));
            System.out.println("合同号：" + line.substring(128,192));
            System.out.println("证件类型：" + line.substring(192,194));
            System.out.println("证件号码：" + line.substring(194,214));
            System.out.println("客户姓名：" + line.substring(214,294));
            System.out.println("贷款期限：" + line.substring(294,306));
            System.out.println("贷款金额：" + line.substring(306,318));
            System.out.println("剩余本金：" + line.substring(318,330));
            System.out.println("当前期数：" + line.substring(330,342));
            System.out.println("还款期数：" + line.substring(342,346));
            System.out.println("还款日期：" + line.substring(346,354));
            System.out.println("还款方式：" + line.substring(354,356));
            System.out.println("还款总金额：" + line.substring(356,368));
            System.out.println("本金发生额：" + line.substring(368,380));
            System.out.println("利息发生额：" + line.substring(380,392));
            System.out.println("罚息发生额：" + line.substring(392,404));
            System.out.println("复利发生额：" + line.substring(404,416));
            System.out.println("数据日期：" + line.substring(416,424));
        }
    }


    public static void main(String... args) throws Exception {
        String line;
//        File zf = new File("F:\\settleClearToCoDetail-20000301.20200518");
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\settleClearToCoDetail-20000301.20200519"), "GBK"));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\dell\\Desktop\\data\\20200601\\insurFeeChargeToCoDetail-20000301.20200601"), "GBK"));
        while ((line = br.readLine()) != null) {
//            line=new String(line.getBytes("GBK"),"ISO8859_1");
//            line=new String(line.getBytes("UTF-8"),"UTF-8");
//            String payPostDate =line.substring(0,8);
            System.out.println("借据号：" + line.substring(0,64));
            System.out.println("合同号：" + line.substring(64,128));
            System.out.println("证件类型：" + line.substring(128,130));
            System.out.println("证件号码：" + line.substring(130,150));
            System.out.println("客户姓名：" + line.substring(150,230));
            System.out.println("贷款总期数：" + line.substring(230,234));
            System.out.println("贷款金额：" + line.substring(234,246));
            System.out.println("当前期数：" + line.substring(246,258));
            System.out.println("剩余本金：" + line.substring(258,270));
            System.out.println("扣款期数：" + line.substring(270,282));
            System.out.println("扣款金额：" + line.substring(282,294));
            System.out.println("扣款日期：" + line.substring(294,302));
            System.out.println("数据日期：" + line.substring(302,310));
        }
    }

    public static void main1(String... args) throws Exception  {
        FileOutputStream fout = new FileOutputStream("F:\\test.20200519");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String sourceStr = "你好    不好   还行 希望会好\r\nJJ202101280000000008JJ202101280000000008小明";
//        fout.write(sourceStr.getBytes("ISO8859-1"));
//        fout.write(sourceStr.getBytes("UTF-8"));
        fout.write(sourceStr.getBytes("GBK"));
//        fout.write(sourceStr.getBytes());
        fout.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\test.20200519"), "GBK"));
        String line;
        while ((line = br.readLine()) != null) {
//            line=new String(line.getBytes("GBK"),"ISO8859_1"); // 乱码
            line=new String(line.getBytes());
            System.out.println(line);
        }
    }
}
