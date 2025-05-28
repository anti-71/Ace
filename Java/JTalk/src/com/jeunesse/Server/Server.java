package com.jeunesse.Server;

import com.jeunesse.pojo.Constant;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 服务端主线程
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("启动服务端系统.....");
        // 创建线程池
        ExecutorService pool = new ThreadPoolExecutor(24,Constant.MAX_POOL_SIZE,60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(Constant.QUEUE_CAPACITY),
                new ThreadPoolExecutor.AbortPolicy());
        try {
            // 1、注册端口
            ServerSocket serverSocket = new ServerSocket(Constant.PORT);
            // 2、主线程负责接受客户端的连接请求
            while (true) {
                // 3、调用accept方法，获取到客户端的Socket对象
                System.out.println("等待客户端的连接.....");

                Socket socket = serverSocket.accept();
                // 把这个管道交给线程池来处理：以便支持很多客户端可以同时进来通信
                pool.execute(new ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            pool.shutdown();
        }
    }
}
