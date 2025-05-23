package com.jeunesse.demo4tcp1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientDemo1 {
    public static void main(String[] args) {
        // 目标：实现TCP通信下一发一收：客户端开发
        System.out.println("客户端启动...");
        // 1、创建Socket管道对象，请求与服务端的Socket链接。可靠链接
        try {
            Socket socket = new Socket("127.0.0.1",9999);

            // 2、从socket通信管道中得到一个字节输出流
            OutputStream os = socket.getOutputStream();

            // 3、特殊数据流
            DataOutputStream dos =  new DataOutputStream(os);
            dos.writeInt(1);
            dos.writeUTF("我想你了，你在哪儿？");

            // 4、关闭资源
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
