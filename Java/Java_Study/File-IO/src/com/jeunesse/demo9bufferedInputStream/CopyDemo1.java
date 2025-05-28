package com.jeunesse.demo6copy;

import java.io.*;

public class CopyDemo2 {
    public static void main(String[] args) {
        // 目标：掌握资源的新方式：try-with-resource
        // 源文件：D:\Note\Java\Assets\HTTP协议.png
        // 目标文件：D:\Ace\Java\Java_Study\File-IO\src\HTTP协议.png
        copyFile("D:\\Note\\Java\\Assets\\HTTP协议.png", "D:\\Ace\\Java\\Java_Study\\File-IO\\src\\HTTP协议.png");
    }

    private static void copyFile(String s, String s1) {
        // 1、创建字节输入流管道与源文件接通
        try (
                // 这里只能放置资源对象，用完后，最终会自动调用其cL0se方法关闭！！
                InputStream fis = new FileInputStream(s);
                OutputStream fos = new FileOutputStream(s1);
                ){
            // 2、读取一个字节数组，写入一个字节数组 1024 * 1024 + 3
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len); // 读取多少个字节，就写入多少个字节
            }
            System.out.println("复制完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
