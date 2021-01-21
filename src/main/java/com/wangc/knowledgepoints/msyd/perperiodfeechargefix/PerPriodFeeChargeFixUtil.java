package com.wangc.knowledgepoints.msyd.perperiodfeechargefix;

import com.wangc.knowledgepoints.build_sql.from_excel.ExcelDemo;
import com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto.Fee;
import com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto.FeeConfig;
import com.wangc.knowledgepoints.msyd.perperiodfeechargefix.dto.Shd;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 * @Description: 分期费用收取 结清日期修复
 * @date 2020/12/22 18:52
 */
public class PerPriodFeeChargeFixUtil {

    public static void main(String... args) {
        List<Fee> feeList = null;
        try {
            feeList = dealData();
//            System.out.println("一共更新：" + feeList.size() + "条数据");
//            for (Fee fee : feeList) {
//                System.out.println(fee);
//            }
            // 每个文件中脚本的条数
            int countPerFile = 0;

            // 遇到数据量大的时候，dba那边自己会将文件拆成4000条脚本一个文件，为减少dba操作，这里控制下每个文件的sql条数，生成多个文件
            int maxCountPerFile = 4000;

            // 数据变更内容说明
            String dataModContentSummary = "20201219之前已放款分期收取费用待结清日期数据变更";

            String dataModSaveFilePathPrefix = "E:\\文档\\项目文档\\数据变更\\cmis_dml(172.16.16.180)_" + dataModContentSummary + "_20201225";

            // 最终生成的数据变更文件存放地址 后缀
            String getDataModSaveFilePathSuffix = ".sql";

            // 文件数
            int countFile = (feeList.size() % maxCountPerFile) == 0 ? (feeList.size() / maxCountPerFile) : (feeList.size() / maxCountPerFile) + 1;

//            String sqlTemplate = "update glloans.lm_hold_fee_tx set HOLD_SETL_DT = '?' where SEQ_NO = '?' and LOAN_NO = '?' and FEE_CDE = '?' and TX_LOG_SEQ = '?' and HOLD_SETL_DT = '?';";
            String sqlTemplate = "update glloans.lm_hold_fee_tx set HOLD_SETL_DT = '?' where SEQ_NO = '?' and TX_LOG_SEQ = '?' and HOLD_SETL_DT = '?';";

            for (int i = 1; i <= countFile; i ++) {
                // 将脚本模板和数据组装成最终的脚本。指定数据源文件地址
//                String sql = buildSql(sqlTemplate, feeList.subList(maxCountPerFile * (i - 1) , (maxCountPerFile * i) <= feeList.size() ? (maxCountPerFile * i)  : feeList.size())
//                        , new String[]{"holdSetlDt", "seqNo", "loanNo", "feeCde", "txLogSeq", "valBeforeChg"});
                String sql = buildSql(sqlTemplate, feeList.subList(maxCountPerFile * (i - 1) , (maxCountPerFile * i) <= feeList.size() ? (maxCountPerFile * i)  : feeList.size())
                        , new String[]{"holdSetlDt", "seqNo", "txLogSeq", "valBeforeChg"});

                // 计算文件脚本条数
                countPerFile = ((maxCountPerFile * i) <= feeList.size() ? (maxCountPerFile * i) : feeList.size()) - maxCountPerFile * (i - 1);

                // 数据变更内容模板化
                String sqlContentTemplatePrefix = "set autocommit=0;\r\n" + "begin;\r\n" + "\r\n" + "-- 172.16.16.180/glloans\r\n"
                        + "-- 一共更新" + countPerFile + "条\r\n" + "-- " + dataModContentSummary + "\r\n";
                String sqlContentTemplateSuffix = "\r\ncommit;";
                sql = sqlContentTemplatePrefix + sql + sqlContentTemplateSuffix;

                // 生成最终的数据变更脚本文件。指定文件存放路径
                writeToFile(sql, (feeList.size() > maxCountPerFile) ? (dataModSaveFilePathPrefix + "_" + i + getDataModSaveFilePathSuffix) : (dataModSaveFilePathPrefix + getDataModSaveFilePathSuffix));

                System.out.println(((feeList.size() > maxCountPerFile) ? (dataModSaveFilePathPrefix + "_" + i + getDataModSaveFilePathSuffix) : (dataModSaveFilePathPrefix + getDataModSaveFilePathSuffix)) + " 脚本文件生成，一共：" + countPerFile + " 条脚本");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 写出脚本到指定的文件中
     * @param sql
     * @param filePath
     */
    public static void writeToFile(String sql, String filePath) {
        OutputStream out = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } else {
                // 存在则先删除再新建
                file.delete();
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            out.write(sql.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据脚本模板用数据填充生成数据变更内容
     * @param sqlTemplate
     * @param list
     * @return
     */
    public static String buildSql(String sqlTemplate, List<Fee> list, String... fields) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder("");
        int count = list.size();
        for (Fee fee : list) {
            String tempStr = sqlTemplate;
            Field f = null;
            for (String field : fields) {
                f = Fee.class.getDeclaredField(field);
                f.setAccessible(true);
                tempStr = tempStr.replaceFirst("\\?", String.valueOf(f.get(fee)));
            }
            sqlBuilder.append(tempStr).append("\r\n");
        }
        return sqlBuilder.toString();
    }

    public static List<Fee> dealData() throws Exception {
        String rootPath = "E:\\文档\\生产bug数据修复\\费用结清日期未对应\\";

        String feeConfigFilePath = "费用配置.xls";
        String feeDataFilePath = "分期收取费用数据.xls";
        String shdDataFilePath = "还款计划数据.xls";

        // 读取费用配置数据
        /**
         * FEE_CDE、FEE_STR_PERD_NO、FEE_SPAN
         */
        List<Map<String, String>> feeConfigDat = readExcel(rootPath + feeConfigFilePath);
        List<FeeConfig> feeConfigList = map2Bean(feeConfigDat, FeeConfig.class);

        // 读取分期收取费用数据
        /**
         * LOAN_NO、TX_LOG_SEQ、SEQ_NO、FEE_CDE、HOLD_SETL_DT
         */
        List<Map<String, String>> feeDat = readExcel(rootPath + feeDataFilePath);
        List<Fee> feeList = map2Bean(feeDat, Fee.class);
        feeList = feeList.stream().sorted(Comparator.comparing(Fee::getLoanNo, Comparator.naturalOrder())
                .thenComparing(Comparator.comparing(Fee::getSeqNo, Comparator.naturalOrder())))
                .collect(Collectors.toList());

        /**
         * 将分期收取费用数据按feeCde分成多个list存放map中
         */
        Map<String, List<Fee>> feeMapList = new HashMap<>();
        List<Fee> fees = null;
        for (Fee fee : feeList) {
            if (feeMapList.containsKey(fee.getFeeCde())) {
                feeMapList.get(fee.getFeeCde()).add(fee);
            } else {
                fees = new ArrayList<>();
                fees.add(fee);
                feeMapList.put(fee.getFeeCde(), fees);
            }
        }

        // 读取还款计划数据
        /**
         * LOAN_NO、PS_PERD_NO、PS_DUE_DT
         */
        List<Map<String, String>> shdDat = readExcel(rootPath + shdDataFilePath);
        List<Shd> shdList = map2Bean(shdDat, Shd.class);
        // 将还款计划列表按照loanNo,psPerdNo进行排序
        shdList = shdList.stream().sorted(Comparator.comparing(Shd::getLoanNo, Comparator.naturalOrder())
                .thenComparing(Comparator.comparing(Shd::getPsPerdNo, Comparator.naturalOrder())))
                .collect(Collectors.toList());

        /**
         * 1.对还款计划进行遍历
         * 2.对费用配置进行遍历
         * 3.取出对应费用配置的费用数据
         * 4.判断还款计划当前期数是否在费用配置的起始期数、起始期数 + 应缴期数 - 1范围内，
         * 若在则将费用的待结清日期变更为还款计划当期的应还日期，赋值费用变更之前的日期以
         * 作标识同时将该笔费用从集合中移除
         */
        List<Fee> newFeeList = new ArrayList<>();
        List<Fee> feeListDat = null;
        for (Shd shd : shdList) {
            for (FeeConfig feeConfig : feeConfigList) {
                if (feeConfig.getFeeStrPerdNo() <= shd.getPsPerdNo()
                        && shd.getPsPerdNo() < feeConfig.getFeeStrPerdNo() + feeConfig.getFeeSpan()) {
                    feeListDat = feeMapList.get(feeConfig.getFeeCde());
                    if (feeConfigDat.size() == 0) continue;
                    Iterator<Fee> feeIterator = feeListDat.iterator();
                    Fee fee = null;
                    boolean isMod = false; // 每一期还款计划都只会对应费用代码的一种借款期限配置，如果有一个匹配上了，该还款计划就不可用了
                    while (feeIterator.hasNext()) {
                        fee = feeIterator.next();
                        if (shd.getLoanNo().equals(fee.getLoanNo())) {
                            fee.setValBeforeChg(fee.getHoldSetlDt());
                            fee.setHoldSetlDt(shd.getPsDueDt());
                            fee.setPsPerdNo(shd.getPsPerdNo());
                            newFeeList.add(fee);
                            feeIterator.remove();
                            isMod = true;
                            break;
                        }
                    }
                    if (isMod) break;
                }
            }
        }
//        if (feeListDat.size() > 0) {
//            System.out.println("一共：" + feeListDat.size() + "未处理的数据");
//            for (Fee fee : feeListDat) {
//                System.out.println("未处理的数据为：【loanNo:" + fee.getLoanNo() + ", seq_no:" + fee.getSeqNo() + "】");
//            }
//        }
        for (String key : feeMapList.keySet()) {
            if (feeMapList.get(key).size() > 0) {
                for (Fee fee : feeMapList.get(key)) {
                System.out.println("未处理的数据为：【loanNo:" + fee.getLoanNo() + ", seq_no:" + fee.getSeqNo() + "】");
                }
            }
        }

        // 校验
        // 处理后的费用数据 newFeeList、还款计划 shdList、费用配置 feeConfigList
//        for (Fee fee : newFeeList) {
//            for (Shd shd : shdList) {
//                if (fee.getLoanNo().equals(shd.getLoanNo())) {
//                    for (FeeConfig feeConfig : feeConfigList) {
//                        if (fee.getFeeCde().equals(feeConfig.getFeeCde())) {
//                            if
//                        }
//                    }
//                }
//            }
//        }
        return newFeeList;
    }




    /**
     * map转换成对应实体类
     * @param mapList
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> map2Bean(List<Map<String, String>> mapList, Class<T> clazz) throws Exception {
        List<T> list = new ArrayList<T>();
        for (Map<String, String> map : mapList) {
            list.add(map2Bean(map, clazz));
        }
        return list;
    }

    public static <T> T map2Bean(Map<String, String> map, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        Method method = null;
        Field field = null;
        for (String key : map.keySet()) {
            field = clazz.getDeclaredField(strToJavaField(key));
            field.setAccessible(true);
            if ("int".equals(field.getType().getSimpleName())) {
                field.set(t, Integer.parseInt(map.get(key)));
            } else {
                field.set(t, map.get(key));
            }
        }
        return t;
    }

    /**
     * 将map中包含_格式的key转换成java成员变量 驼峰法
     *  eg:loan_no -> loanNo
     * @param str
     * @return
     */
    public static String strToJavaField(String str) {
        if (str.contains("_")) {
            String[] strArr = str.split("_");
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < strArr.length; index ++) {
                if (index == 0) {
                    sb.append(strArr[index].toLowerCase());
                } else {
                    char firstChar = strArr[index].charAt(0);
//                    if (Character.isLowerCase(firstChar)) {
//                        sb.append(Character.toUpperCase(firstChar)).append(strArr[index].substring(1, strArr[index].length() - 1).toLowerCase());
//                    } else {
//                        sb.append(strArr[index]);
//                    }
                    sb.append(Character.toUpperCase(firstChar)).append(strArr[index].substring(1, strArr[index].length()).toLowerCase());
                }
            }
            return sb.toString();
        } else {
            return str;
        }
    }


    /**
     * 读取excel
     * @param filePath
     * @return
     */
    public static List<Map<String, String>> readExcel(String filePath) {
        if (filePath.endsWith("xls")) {
            return readXLSExcel(filePath);
        } else if (filePath.endsWith("xlsx")) {
            return readXLSXExcel(filePath);
        }
        return null;
    }

    /**
     * 读取指定的excel文件
     * @param filePath
     * @return
     */
    public static List<Map<String, String>> readXLSExcel(String filePath) {
        // excel数据返回
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        // 每行数据
        Map<String, String> rowData = null;
        try {
            // 1.获取文件输入流
            InputStream inputStream = new FileInputStream(filePath);

            // 如果是加密了得文件，做密码校验处理
//            POIFSFileSystem pfs = new POIFSFileSystem(inputStream);
//            inputStream.close();
//            EncryptionInfo encInfo = new EncryptionInfo(pfs);
//            Decryptor decryptor = Decryptor.getInstance(encInfo);
//            decryptor.verifyPassword("!Q@W3e4r");
//            HSSFWorkbook workbook = new HSSFWorkbook(decryptor.getDataStream(pfs));

            // 2.获取Excel工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            // 3.得到Excel工作表对象
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            System.out.println("一共：" + sheetAt.getLastRowNum() + "行");
            // 4.循环读取表格数据

            // 用来处理数字类型的转字符串
            DecimalFormat format = new DecimalFormat("###########.#####");

            // 表头字段
            List<String> columnNames = null;
            for (Row row : sheetAt) {
//                System.out.println("当前行数：" + row.getRowNum());
                // 首行（即表头）不读取
                if (row.getRowNum() == 0) {
                    columnNames = new ArrayList<String>();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell = null;
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        if (CellType.STRING.compareTo(cell.getCellType()) == 0) {
                            columnNames.add(cell.getStringCellValue());
                        } else if (CellType.NUMERIC.compareTo(cell.getCellType()) == 0) {
                            columnNames.add(format.format(cell.getNumericCellValue()));
                        }
                    }
                    continue;
                }
                rowData = new HashMap<String, String>();
                // 读取当前行中单元格数据，索引从0开始
                int cellNum = row.getLastCellNum();
                for (int index = 0; index < cellNum; index ++) {
                    if (CellType.STRING.compareTo(row.getCell(index).getCellType()) == 0) {
                        rowData.put(columnNames.get(index).trim(), row.getCell(index).getStringCellValue().trim());
                    } else if (CellType.NUMERIC.compareTo(row.getCell(index).getCellType()) == 0) {
                        rowData.put(columnNames.get(index).trim(), format.format(row.getCell(index).getNumericCellValue()).trim());
                    }
                }
                result.add(rowData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取指定的excel文件
     * @param filePath
     * @return
     */
    public static List<Map<String, String>> readXLSXExcel(String filePath) {
        // excel数据返回
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        // 每行数据
        Map<String, String> rowData = null;
        try {
            // 1.获取文件输入流
            InputStream inputStream = new FileInputStream(filePath);

            // 如果是加密了得文件，做密码校验处理
//            POIFSFileSystem pfs = new POIFSFileSystem(inputStream);
//            inputStream.close();
//            EncryptionInfo encInfo = new EncryptionInfo(pfs);
//            Decryptor decryptor = Decryptor.getInstance(encInfo);
//            decryptor.verifyPassword("!Q@W3e4r");
//            HSSFWorkbook workbook = new HSSFWorkbook(decryptor.getDataStream(pfs));

            // 2.获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // 3.得到Excel工作表对象
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            System.out.println("一共：" + sheetAt.getLastRowNum() + "行");
            // 4.循环读取表格数据

            // 用来处理数字类型转字符串
            DecimalFormat format = new DecimalFormat("###########.#####");
            for (Row row : sheetAt) {
//                System.out.println("当前行数：" + row.getRowNum());
                // 首行（即表头）不读取
                if (row.getRowNum() == 0) {
                    continue;
                }
                rowData = new HashMap<String, String>();
                // 读取当前行中单元格数据，索引从0开始
                int cellNum = row.getLastCellNum();
                for (int index = 0; index < cellNum; index ++) {
                    if (CellType.STRING.compareTo(row.getCell(index).getCellType()) == 0) {
                        rowData.put(index + "", row.getCell(index).getStringCellValue());
                    } else if (CellType.NUMERIC.compareTo(row.getCell(index).getCellType()) == 0) {
                        rowData.put(index + "", format.format(row.getCell(index).getNumericCellValue()));
                    }
                }
                result.add(rowData);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
