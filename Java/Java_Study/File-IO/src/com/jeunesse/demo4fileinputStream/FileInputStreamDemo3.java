package com.jeunesse.demo4fileinputStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class FileInputStreamDemo3 {
    public static void main(String[] args) throws Exception {
        // 目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        // 1、创建文件字节输入流管道与源文件接通
//        InputStream is = new FileInputStream(new File("File-IO\\src\\jeunesse02.txt"));
        InputStream is = new FileInputStream("File-IO\\src\\jeunesse04.txt"); // 简化写法

        // 2、一次性读完文件的全部字节
        byte[] bytes = is.readAllBytes();

        System.out.println(new String(bytes));
    }
}
