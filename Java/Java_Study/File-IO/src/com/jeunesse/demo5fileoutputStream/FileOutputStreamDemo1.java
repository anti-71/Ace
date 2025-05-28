package com.jeunesse.demo5fileoutputStream;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputStreamDemo1 {
    public static void main(String[] args) throws Exception{
        // 目标：学会使用文件字节输出流
        // 1、创建一个文件字节输出流管道，与目标文件接通
        OutputStream os = new FileOutputStream("File-IO\\src\\jeunesse05.txt",true);

        // 2、写入数据
        os.write(97);
        os.write('b');
//        os.write('徐'); // 会乱码
        os.write("\r\n".getBytes()); // 换行

        // 3、写一个字节数组出去
        byte[] bytes = "我爱你中国666".getBytes();
        os.write(bytes);
        os.write("\r\n".getBytes()); // 换行

        // 4、写一个字节数组的某一部分出去
        os.write(bytes, 0, 3);
        os.write("\r\n".getBytes()); // 换行

        os.close(); // 关闭管道 释放资源
    }
}
