package com.jeunesse.demo10bufferedReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class BufferedReaderDemo1 {
    public static void main(String[] args) {
        // 目标：搞清楚缓冲字符输入流读取字符内容：性能提升了，多了按照行读取文本的能力
        try (
                // 1、创建文件字符输入流与源文件接通
                Reader fr = new FileReader("File-IO\\src\\jeunesse08.txt");
                // 2、创建缓冲字符输入流包装低级的字符输入流
                BufferedReader br = new BufferedReader(fr);
        ) {
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine());
//            System.out.println(br.readLine()); // null

            // 使用循环改进，来按照行读取数据
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
