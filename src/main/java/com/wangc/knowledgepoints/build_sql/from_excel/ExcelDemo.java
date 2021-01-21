package com.wangc.knowledgepoints.build_sql.from_excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.security.krb5.EncryptedData;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author
 * @Description: 读取excel文件组装sql
 * @date 2020/9/29 10:02
 */
public class ExcelDemo {

    // sql总条数
    private static int count = 0;

    // 遇到数据量大的时候，dba那边自己会将文件拆成4000条脚本一个文件，为减少dba操作，这里控制下每个文件的sql条数，生成多个文件
    private static int maxCountPerFile = 4000;

    // 数据变更内容说明
//    private static String dataModContentSummary = "借据已结清同步lc_appl状态数据变更";
    private static String dataModContentSummary = "20201031借款处理中拒绝数据变更";

    // 数据源文件地址
//    private static String dataSrcFilePath = "C:\\Users\\dell\\Desktop\\setlSync.xlsx";
//    private static String dataSrcFilePath = "C:\\Users\\dell\\Desktop\\根据数据中心给出的20201028刷脸通过数据的授信编号.xlsx";
    private static String dataSrcFilePath = "C:\\Users\\dell\\Desktop\\20201031借款处理中拒绝数据变更.xlsx";

    // 最终生成的数据变更文件存放地址 前缀
//    private static String dataModSaveFilePathPrefix = "E:\\文档\\项目文档\\数据变更\\setlSync\\cmis_dml(172.16.16.180)_" + dataModContentSummary + "_20200929";
    private static String dataModSaveFilePathPrefix = "E:\\文档\\项目文档\\数据变更\\cmis_dml(172.16.16.180)_" + dataModContentSummary + "_20201031";

    // 最终生成的数据变更文件存放地址 后缀
    private static String getDataModSaveFilePathSuffix = ".sql";

    // 数据变更脚本模板，需要的数据用?做通配符填充
//    private static String sqlTemplate = "update lc_appl set out_sts = '24', in_sts = '41', last_chg_usr= 'admin', last_chg_dt = NOW() where appl_cde = '?' and in_sts = '12' and out_sts = '06';";
//    private static String sqlTemplate = "update tb_credit_apply set face_status = '1' where credit_apply_seq = ? and face_status = '4';";
    private static String sqlTemplate = "update lc_appl set wf_appr_sts = '998', in_sts = '08', out_sts = '02' where appl_cde = '?' and wf_appr_sts = '000' and in_sts = '00' and out_sts = '01';";

    public static void main(String... args) {

        // 读取excel文件数据
        List<Map<String, String>> srcData = readExcel(dataSrcFilePath);

        // 每个文件中脚本的条数
        int countPerFile = 0;

        // 文件数
        int countFile = (srcData.size() % maxCountPerFile) == 0 ? (srcData.size() / maxCountPerFile) : (srcData.size() / maxCountPerFile) + 1;

        for (int i = 1; i <= countFile; i ++) {
            // 将脚本模板和数据组装成最终的脚本。指定数据源文件地址
            String sql = buildSql(sqlTemplate, srcData.subList(maxCountPerFile * (i - 1) , (maxCountPerFile * i) <= srcData.size() ? (maxCountPerFile * i)  : srcData.size()));

            // 计算文件脚本条数
            countPerFile = ((maxCountPerFile * i) <= srcData.size() ? (maxCountPerFile * i) : srcData.size()) - maxCountPerFile * (i - 1);

            // 数据变更内容模板化
            String sqlContentTemplatePrefix = "set autocommit=0;\r\n" + "begin;\r\n" + "\r\n" + "-- 172.16.16.180/cmis\r\n"
                    + "-- 一共更新" + countPerFile + "条\r\n" + "-- " + dataModContentSummary + "\r\n";
            String sqlContentTemplateSuffix = "\r\ncommit;";
            sql = sqlContentTemplatePrefix + sql + sqlContentTemplateSuffix;

            // 生成最终的数据变更脚本文件。指定文件存放路径
            writeToFile(sql, (srcData.size() > maxCountPerFile) ? (dataModSaveFilePathPrefix + "_" + i + getDataModSaveFilePathSuffix) : (dataModSaveFilePathPrefix + getDataModSaveFilePathSuffix));

            System.out.println(((srcData.size() > maxCountPerFile) ? (dataModSaveFilePathPrefix + "_" + i + getDataModSaveFilePathSuffix) : (dataModSaveFilePathPrefix + getDataModSaveFilePathSuffix)) + " 脚本文件生成，一共：" + countPerFile + " 条脚本");
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
    public static String buildSql(String sqlTemplate, List<Map<String, String>> list) {
        StringBuilder sqlBuilder = new StringBuilder("");
        count = list.size();
        for (Map<String, String> map : list) {
            String tempStr = sqlTemplate;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                tempStr = tempStr.replaceFirst("\\?", entry.getValue());
            }
            sqlBuilder.append(tempStr).append("\r\n");
        }
        return sqlBuilder.toString();
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
