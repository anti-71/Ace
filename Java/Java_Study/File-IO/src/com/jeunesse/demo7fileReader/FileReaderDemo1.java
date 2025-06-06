package com.jeunesse.demo7fileReader;

import java.io.FileReader;
import java.io.Reader;

public class FileReaderDemo1 {
    public static void main(String[] args) {
        // 目标：掌握文件字符输入流读取字符内容到程序中来
        try (
                // 1、创建文件字符输入流与源文件接通
                Reader fr = new FileReader("File-IO\\src\\jeunesse06.txt")
        ) {
            // 2、定义一个字符数组，每次读多个字符
            char[] buffer = new char[3];
            int len; // 用于记录每次读取了多少个字符
            while ((len = fr.read(buffer)) != -1) {
                // 3、将读取的字符数组转换成字符串并输出
                System.out.print(new String(buffer, 0, len));
            }
            // 拓展：文件字符输入流每次读取多个字符，性能较好，而且读取中文
            // 是按照字符读取，不会出现乱码！这是一种读取中文很好的方案
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
