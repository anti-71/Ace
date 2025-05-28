package com.jeunesse.demo6copy;

import com.jeunesse.demo5fileoutputStream.FileOutputStreamDemo1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDemo1 {
    public static void main(String[] args) {
        // 目标：使用字节流完成文件的复制操作
        // 源文件：D:\Note\Java\Assets\HTTP协议.png
        // 目标文件：D:\Ace\Java\Java_Study\File-IO\src\HTTP协议.png
        try {
            copyFile("D:\\Note\\Java\\Assets\\HTTP协议.png", "D:\\Ace\\Java\\Java_Study\\File-IO\\src\\HTTP协议.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(String s, String s1) throws Exception {
        // 1、创建字节输入流管道与源文件接通
        InputStream fis = new FileInputStream(s);
        OutputStream fos = new FileOutputStream(s1);
        // 2、读取一个字节数组，写入一个字节数组 1024 * 1024 + 3
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, len); // 读取多少个字节，就写入多少个字节
        }
        System.out.println("复制完成！");
        // 3、关闭流
        fos.close();
        fis.close();
    }
}
