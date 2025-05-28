package com.jeunesse.demo11buffedWrite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class BufferedWriterDemo1 {
    public static void main(String[] args) {
        // 目标：搞清楚缓冲字符输出流的使用：提升了字符输出流的写字符的性能，多了换行功能
        try (
                // 1、创建一个字符输出流对象，指定写出的目的地
//                Writer fw = new FileWriter("File-IO\\src\\jeunesse07-out.txt") // 覆盖管道
                Writer fw = new FileWriter("File-IO\\src\\jeunesse07-out.txt",true); // 追加数据
                // 2、创建一个缓冲字符输出流对象，把字符输出流对象作为构造参数传递给缓冲字符输出流对象
                BufferedWriter bw = new BufferedWriter(fw);
        ) {
            // 2、写一个字符出去
            bw.write('a');
            bw.write(98);
            bw.write('施');
            bw.newLine(); // 换行

            // 3、写一个字符出去
            bw.write("java");
            bw.write("我爱Java，虽然Java不是最好的编程语言，但是可以挣钱");
            bw.newLine(); // 换行

            // 4、写字符串的一部分出去
            bw.write("java", 0, 2);
            bw.newLine(); // 换行

            // 5、写一个字符数组出去
            char[] chs = "java".toCharArray();
            bw.write(chs);
            bw.newLine(); // 换行

            // 6、写一个字符数组的某部分出去
            bw.write(chs, 0, 2);
            bw.newLine(); // 换行

            // bw.flush(); // 刷新缓冲区，将缓冲区中的数据全部写出去
            // 刷新后，流可以继续使用
            // bw.close(); // 关闭资源，关闭包含了刷新！关闭后流不能继续使用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
