package com.jeunesse.demo2udp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServerDemo2 {
    public static void main(String[] args) throws Exception {
        System.out.println("===服务端启动了===");
        // 目标：完成UDP通信一发一收：服务端开发
        // 创建接收端对象，注册端口（接韭菜的人）
        DatagramSocket socket = new DatagramSocket(8080);

        // 2、创建一个数据包对象负责接收数据（韭菜盘子）
        byte[] buf = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // 3、接收数据，将数据对象封装到数据包对象的字节数组中去
        socket.receive(packet);

        // 4、看看数据是否收到了
        int len = packet.getLength(); // 获取当前收到的数据长度
        String data = new String(buf, 0, len);
        System.out.println("服务端收到了：" + data);

        // 获取对方的ip对象
        String ip = packet.getAddress().getHostAddress();
        int port = packet.getPort();
        System.out.println("对方IP：" + ip + " 对方端口：" + port);

        socket.close();
    }
}
