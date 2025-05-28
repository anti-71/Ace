package com.jeunesse.demo9bufferedInputStream;

import java.io.*;

public class CopyDemo1 {
    public static void main(String[] args) {
        // 目标：掌握缓冲字节流的使用
        // 源文件：D:\Note\Java\Assets\HTTP协议.png
        // 目标文件：D:\Ace\Java\Java_Study\File-IO\src\HTTP协议.png
        copyFile("D:\\Note\\Java\\Assets\\HTTP协议.png", "D:\\Ace\\Java\\Java_Study\\File-IO\\src\\HTTP协议.png");
    }

    private static void copyFile(String s, String s1) {
        // 1、创建字节输入流管道与源文件接通
        try (
                // 这里只能放置资源对象，用完后，最终会自动调用其close方法关闭！！
                InputStream fis = new FileInputStream(s);
                // 把低级的字节输入流包装成高级的缓冲字节输入流
                InputStream bis = new BufferedInputStream(fis);

                OutputStream fos = new FileOutputStream(s1);
                // 把低级的字节输出流包装成高级的缓冲字节输出流
                OutputStream bos = new BufferedOutputStream(fos);
                ){
            // 2、读取一个字节数组，写入一个字节数组 1024 * 1024 + 3
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len); // 读取多少个字节，就写入多少个字节
            }
            System.out.println("复制完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
