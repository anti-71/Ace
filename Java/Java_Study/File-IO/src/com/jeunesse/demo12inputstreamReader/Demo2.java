package com.jeunesse.demo12inputstreamReader;

import java.io.*;

public class Demo2 {
    public static void main(String[] args) {
        // 目标：使用字符输入转换流InputStreamReader解决不同编码读取乱码的问题
        // 代码：UTF-8 文件UTF-8 读取不乱码
        // 代码：UTF-8 文件GBK 读取乱码
        try (
                // 先提取文件的原始字节流
                InputStream is = new FileInputStream("File-IO\\src\\jeunesse09.txt");
                // 指定字符集把原始字节流转换成字符输入流
                Reader isr = new InputStreamReader(is, "GBK");
                // 2、创建缓冲字符输入流包装低级的字符输入流
                BufferedReader br = new BufferedReader(isr);
        ) {
            // 定义一个字符串变量用于记住每次读取的一行数据
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            // 目前读取文本最优雅的方案：性能好，不乱码，可以按照行读取
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
