package com.jeunesse.demo4fileinputStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class FileInputStreamDemo2 {
    public static void main(String[] args) throws Exception {
        // 目标：掌握文件字节输入流读取文件中的字节数组到内存中来
        // 创建文件字节输入流管道与源文件接通
//        InputStream is = new FileInputStream(new File("File-IO\\src\\jeunesse02.txt"));
        InputStream is = new FileInputStream("File-IO\\src\\jeunesse03.txt"); // 简化写法

        // 开始读取文件中的字节并输出：每次读取多个字节
        // 定义一个字节数组用于每次读取字节
        byte[] buffer = new byte[3];
        // 定义一个变量记住每次读取了多少个字节
        int len;
        while ((len = is.read(buffer)) != -1) {
            // 把读取到的字节数组转换成字符串输出，读取多少倒出多少
            String str = new String(buffer, 0, len);
            System.out.print(str);
        }
        // 拓展：每次读取多个字节，性能得到提升，因为每次读取多个字节，可以减少硬盘和内存的交互次数，从而提升性能
        // 依然无法避免读取汉字输出乱码的问题：存在截断汉字字节的可能性！
    }
}
