package com.wangc.knowledgepoints.msyd.loandatapush.xwzd;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author
 * @Description: 新网助贷数据推送工具类
 * @date 2020/11/5 17:46
 */
public class XwzdUtils {


    /** 还款计划文件名前缀*/
    final static String DISBURSEMENT_OUTPUT_FILE_NAME_PREFIX = "disbursement";

    /** 还款计划文件名后缀*/
    final static String DISBURSEMENT_OUTPUT_FILE_NAME_SUBFIX = "disbursement";

    public static void main(String... args) {
        try {
            XwzdUtils.createPushDataFile("src/main/resources/loandata/xwzd/20201104/in");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成推送文件
     *  生产上的数据放在类路径指定路径下，读取文件生成对应的推送文件
     */
    public static void createPushDataFile(String filePathDir) throws Exception {
        File srcFileDir = new File(filePathDir);
        String[] pathArr = srcFileDir.getParent().split("//");
        String date = pathArr[pathArr.length - 1];
        if (srcFileDir.isDirectory() && srcFileDir.listFiles() != null && srcFileDir.listFiles().length > 0) {
            File[] files = srcFileDir.listFiles();
            for (File file : files) {
                if (file.getName().startsWith("disbursement_")) {
                    // disbursement 读内容
                    XwzdUtils.readSrcData(file, "disbursement");
                } else if (file.getName().startsWith("loan_status_")) {
                    XwzdUtils.readSrcData(file, "loanstatus");
                } else {
                    System.out.println("不处理该文件: " + file.getName());
                }
            }
        } else {
            System.out.println("没有文件");
        }
    }

    public static void readSrcData(File file, String fileTyp) throws Exception {
        if ("disbursement".equals(fileTyp)) {
            XwzdUtils.readDisbursementSrcData(file);
        } else if ("loanstatus".equals(fileTyp)) {
            XwzdUtils.readLoanStatusSrcData(file);
        }
    }

    public static String readDisbursementSrcData(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String content = "";
        JSONObject obj = null;
        StringBuilder sBuilder = new StringBuilder();
        // 分隔符
        byte b2[] = {0x01};
        String b2Str = new String(b2);
        while ((content = reader.readLine()) != null) {
            obj = JSONObject.parseObject(content);
            sBuilder.append(obj.getString("appl_seq")).append(b2Str) // 我方订单号
                    .append(obj.getString("appl_seq")).append(b2Str) // 新网订单编号
                    .append(obj.getString("md_product_id")).append(b2Str) // 产品编号
                    .append(obj.getString("loan_amt")).append(b2Str) // 放款金额
                    .append("0").append(b2Str) // 提前收取费用
                    .append(obj.getString("dn_dt").replaceAll("-", "")).append(b2Str) // 放款日期
                    .append(obj.getString("loan_end_dt").replaceAll("-", "")).append(b2Str) // 贷款结束日期
                    .append(obj.getString("apply_tnr")).append(b2Str) // 总期数
                    .append(obj.getString("ps_perd_no")).append(b2Str) // 当前还款计划期数
                    .append(obj.getString("ps_due_dt").replaceAll("-", "")).append(b2Str) // 还款计划日期
                    .append(obj.getString("ps_prcp_amt")).append(b2Str) // 还款计划本金
                    .append(obj.getString("ps_norm_int_amt")).append(b2Str) // 还款计划利息
                    .append(obj.getString("ps_fee_amt")).append(b2Str) // 还款计划费用
                    .append("") // 其他
                    .append("\r\n");
        }
        return sBuilder.toString();
    }

    public static String readLoanStatusSrcData(File file) throws Exception {
        return "";
    }
}
