package com.jeunesse.demo14commonsIO;

import org.apache.commons.io.FileUtils;
import java.io.File;

public class CommonsIODemo1 {
    public static void main(String[] args) throws Exception {
        // 目标：IO框架
        FileUtils.copyFile(new File("File-IO\\src\\csb_out.txt"),new File("File-IO\\src\\csb_out2.txt"));
        FileUtils.deleteDirectory(new File("File-IO\\src\\com\\jeunesse\\demo4fileinputStream-副本"));
    }
}
