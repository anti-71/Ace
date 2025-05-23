package com.jeunesse.demo7tcp4;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerReaderRunnable implements Runnable {
    private Socket socket;
    public ServerReaderRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 给当前对应的浏览器管道响应一个网页数据回去
            OutputStream os =  socket.getOutputStream();
            // 通过字节输出流包装写出去数据给浏览器
            // 把字节输出流包装成打印流
            PrintStream ps = new PrintStream(os);
            // 写网页数据回去
            ps.println("HTTP/1.1 200 OK");
            ps.println("Content-Type:text/html;charset=utf-8");
            ps.println(); // 必须要换一行
            ps.println("<html>");
            ps.println("<head>");
            ps.println("<meta charset=utf-8>");
            ps.println("<title>");
            ps.println("jeunesse的网页");
            ps.println("</title>");
            ps.println("</head>");
            ps.println("<body>");
            ps.println("<h1 style='color:red';font-size=20px>看jeunesse的网页</h1>");
            ps.println("</body>");
            ps.println("</html>");
            ps.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端断开了");
        }
    }
}
