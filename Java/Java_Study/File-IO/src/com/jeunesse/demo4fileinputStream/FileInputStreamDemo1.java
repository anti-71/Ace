package com.jeunesse.demo4fileinputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileInputStreamDemo1 {
    public static void main(String[] args) throws Exception {
        // 目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        // 创建文件字节输入流管道与源文件接通
//        InputStream is = new FileInputStream(new File("File-IO\\src\\jeunesse02.txt"));
        InputStream is = new FileInputStream("File-IO\\src\\jeunesse03.txt"); // 简化写法

        // 开始读取文件中的字节并输出：每次读取一个字节
        // 定义一个变量记住每次读取的一个字节
        int b;
        while ((b = is.read()) != -1) {
            System.out.print((char) b);
        }
        // 每次读取一个字节的问题：性能较差，读取汉字输出一定会乱码！
    }
}
