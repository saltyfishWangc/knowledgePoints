package com.wangc.knowledgepoints.file;

import java.io.File;

/**
 * @author
 * @Description:
 * @date 2020/11/5 10:40
 */
public class FileTest {

    public static void main(String... args) {
        try {
            File loanStsOkFile = new File("C:\\Users\\dell\\Desktop\\1028刷脸问题需召回用户明细\\20201109\\" + "20201105\\", "ok.dat");
            if (!loanStsOkFile.exists()) {
                if (!loanStsOkFile.getParentFile().exists()) {
                    loanStsOkFile.getParentFile().mkdirs();
                }
            }
            loanStsOkFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
