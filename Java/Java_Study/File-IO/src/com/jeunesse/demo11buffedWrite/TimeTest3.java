package com.jeunesse.demo11buffedWrite;

import java.io.*;

public class TimeTest3 {
    private static final String SRC_PATH = "File-IO/src/13-Stream流-常用终结方法.mp4";
    private static final String DEST_PATH = "File-IO/src/";

    public static void main(String[] args) {
        // 目标：缓冲流，低级流的性能分析
        // 使用低级的字节流按照一个一个字节的形式复制文件：非常的慢，简直让人无法忍受，直接淘汰，禁止使用！！
//        copyFile1();
        // 使用低级的字节流按照字节数组的形式复制文件：是比较慢的，还可以接受
        copyFile2();
        // 使用高级的缓冲字节流按照一个一个字节的形式复制文件：虽然是高级管道，但一个一个字节的复制还是太慢了，无法忍受，直接淘汰！
//        copyFile3();
        // 使用高级的缓冲字节流按照字节数组的形式复制文件：非常快！推荐使用！
        copyFile4();
    }

    // 使用高级的缓冲字节流按照字节数组的形式复制文件
    private static void copyFile4() {
        long start = System.currentTimeMillis();
        try(
                InputStream fis = new FileInputStream(SRC_PATH);
                OutputStream fos = new FileOutputStream(DEST_PATH + "4.mp4");
                BufferedInputStream bis = new BufferedInputStream(fis);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ) {
            byte[] buffer = new byte[1024 * 8]; // 1KB
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("高级缓冲字节流按照字节数组的形式复制文件耗时：" + (end - start) / 1000.0 + "s");
    }

    // 使用高级的缓冲字节流按照一个一个字节的形式复制文件
    private static void copyFile3() {
        long start = System.currentTimeMillis();
        try(
                InputStream fis = new FileInputStream(SRC_PATH);
                OutputStream fos = new FileOutputStream(DEST_PATH + "3.mp4");
                BufferedInputStream bis = new BufferedInputStream(fis);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ) {
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("高级缓冲字节流按照一个一个字节的形式复制文件耗时：" + (end - start) / 1000.0 + "s");
    }

    // 使用低级的字节流按照字节数组的形式复制文件
    private static void copyFile2() {
        long start = System.currentTimeMillis();
        try(
                InputStream fis = new FileInputStream(SRC_PATH);
                OutputStream fos = new FileOutputStream(DEST_PATH + "2.mp4");
                ) {
            byte[] buffer = new byte[1024 * 8]; // 1KB
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("低级字节流按照字节数组的形式复制文件耗时：" + (end - start) / 1000.0 + "s");
    }

    // 使用低级的字节流按照一个一个字节的形式复制文件
    public static void copyFile1() {
        // 拿系统当前时间
        long start = System.currentTimeMillis(); // 获取当前时间毫秒数，从1970-1-100:00:00开始走到此刻的。总毫秒值1s=1000ms
        try(
                InputStream fis = new FileInputStream(SRC_PATH);
                OutputStream fos = new FileOutputStream(DEST_PATH + "1.mp4");
                ) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis(); // 获取当前时间毫秒数，从1970-1-100:00:00开始走到此刻的。总毫秒值1s=1000ms
        System.out.println("低级字节流按照一个一个字节的形式复制文件耗时：" + (end - start) / 1000.0 + "s");
    }
}
