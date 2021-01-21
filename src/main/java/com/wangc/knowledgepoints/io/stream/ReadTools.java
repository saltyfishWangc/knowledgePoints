package com.wangc.knowledgepoints.io.stream;

import java.io.*;
import java.util.*;

/**
 * @Description: 读取文件中的借据号拼装成查询脚本写出
 * @date 2020/5/27 15:53
 */
public class ReadTools {

    public static void main(String... args) throws Exception {
        collectInfo();
    }

    private static void collectInfo() throws Exception {
        FileInputStream finForLoanNoAndIir = null;
        FileInputStream finForApplSeqAndCde = null;
        BufferedReader br = null;
        FileOutputStream fou = null;
        BufferedWriter bou = null;
        FileOutputStream fouForUpdateSql = null;
        BufferedWriter bouForUpdateSql = null;
        try {
            long startTime = System.currentTimeMillis();
            finForLoanNoAndIir = new FileInputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\数据中心导出的借据号和综合年化利率.txt");
            br = new BufferedReader(new InputStreamReader(finForLoanNoAndIir));
            Map<String, String> loanNoAndIirSortMap = new LinkedHashMap<String, String>();
            String loanNoAndIirLine = "";
            while ((loanNoAndIirLine = br.readLine()) != null) {
                loanNoAndIirSortMap.put(loanNoAndIirLine.split("\t")[0], loanNoAndIirLine.split("\t")[1].substring(0, 4));
            }
            System.out.println("综合年化利率条数：" + loanNoAndIirSortMap.size());
            finForApplSeqAndCde = new FileInputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\数据中心导出的借据对应cmis.lc_appl的借款申请序列号文件.txt");
            br = new BufferedReader(new InputStreamReader(finForApplSeqAndCde));
            Map<String, String> applSeqAndCdeMap = new HashMap<String, String>();
            while ((loanNoAndIirLine = br.readLine()) != null) {
                applSeqAndCdeMap.put(loanNoAndIirLine.split("\t")[1], loanNoAndIirLine.split("\t")[0]);
            }
            System.out.println("借款序列号条数：" + applSeqAndCdeMap.size());

            long readEndTime = System.currentTimeMillis();
            System.out.println("完成读耗时：" + (readEndTime - startTime));

            StringBuilder outStr = new StringBuilder("");
            StringBuilder outStrForUpdateSql = new StringBuilder("");
            String updateSqlPartUpdateTemplate = "update lc_appl_ext set intergratedLoanRatePerYear = ";
            String updateSqlPartWhereTemplate = " where appl_seq = ";
            String updateSqlPartEndTemplate = " and intergratedLoanRatePerYear is null;";

            Set<String> filterApplSeqSet = new HashSet<String>();
            filterApplSeqSet.add("357330524236152832");
            filterApplSeqSet.add("357331198474719232");
            filterApplSeqSet.add("357331309955125248");
            filterApplSeqSet.add("357333672526876672");
            filterApplSeqSet.add("357336749162106880");
            filterApplSeqSet.add("357337596256321536");
            filterApplSeqSet.add("357337669430153216");
            filterApplSeqSet.add("357337725931618304");
            filterApplSeqSet.add("357338085484134400");
            filterApplSeqSet.add("357338799765716992");
            filterApplSeqSet.add("357340583976505344");
            filterApplSeqSet.add("357340781326893056");

            for (String loanNoAndIirSortKey : loanNoAndIirSortMap.keySet()) {
                for (String applSeqAndCdeKey : applSeqAndCdeMap.keySet()) {
                    if (loanNoAndIirSortKey.equals(applSeqAndCdeKey)) {
                        outStr.append(applSeqAndCdeMap.get(applSeqAndCdeKey)).append("\t").append(applSeqAndCdeKey).append("\t")
                                .append(loanNoAndIirSortKey).append("\t").append(loanNoAndIirSortMap.get(loanNoAndIirSortKey)).append("\r\n");
                        if (!filterApplSeqSet.contains(applSeqAndCdeKey)) {
                            outStrForUpdateSql.append(updateSqlPartUpdateTemplate).append(loanNoAndIirSortMap.get(loanNoAndIirSortKey))
                                    .append(updateSqlPartWhereTemplate).append(applSeqAndCdeMap.get(applSeqAndCdeKey))
                                    .append(updateSqlPartEndTemplate).append("\r\n");
                        }
                    }
                }

            }
            fou = new FileOutputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\collectInfo.txt");
            bou = new BufferedWriter(new OutputStreamWriter(fou));
            long writeStartTime = System.currentTimeMillis();
            System.out.println("开始写：" + writeStartTime);
            bou.write(outStr.toString());
            long writeEndTime = System.currentTimeMillis();
            System.out.println("完成写耗时：" + (writeEndTime -writeStartTime));


            fouForUpdateSql = new FileOutputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\update.sql");
            bouForUpdateSql = new BufferedWriter(new OutputStreamWriter(fouForUpdateSql));
            bouForUpdateSql.write(outStrForUpdateSql.toString());

        } finally {

            if (finForLoanNoAndIir != null) {
                finForLoanNoAndIir.close();
            }
            if (finForApplSeqAndCde != null) {
                finForApplSeqAndCde.close();
            }
            if (bou != null) {
                bou.close();
            }
            if (fou != null) {
                bou.close();
            }
            if (bouForUpdateSql != null) {
                bouForUpdateSql.close();
            }
            if (fouForUpdateSql != null) {
                fouForUpdateSql.close();
            }
        }
    }

    private static void readBuildQquerySqlAndOutput() throws Exception {
        FileInputStream fin = new FileInputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\loan_no.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));
        FileOutputStream fou = new FileOutputStream("E:\\文档\\项目文档\\稠州贷后数据上报\\querySql.sql");
        String line = null;
//        StringBuilder sqlBuilder = new StringBuilder("");
        StringBuilder querySqlTemplate = new StringBuilder("select appl_seq, loan_no from lpb_appl_dn where loan_no in (\r\n");
        String result = "";
        int num = 0;
        while ((line = br.readLine()) != null) {
            num ++;
            if (num % 20000 == 0) {
                querySqlTemplate.append("'").append(line).append("'").append(");").append("\r\n");
                // 写出去
                fou.write(querySqlTemplate.toString().getBytes());
                querySqlTemplate = new StringBuilder("select appl_seq, loan_no from lpb_appl_dn where loan_no in (\r\n");
            } else {
                querySqlTemplate.append("'").append(line).append("'").append(",").append("\r\n");
            }
        }
        if (querySqlTemplate.toString().endsWith(",\r\n")) {
            result = querySqlTemplate.toString();
            result = result.substring(0, result.lastIndexOf(",\r\n")) + ");";
            fou.write(result.getBytes());
        }
        if (fin != null) {
            fin.close();
        }
        if (fou != null) {
            fou.close();
        }
    }
}
