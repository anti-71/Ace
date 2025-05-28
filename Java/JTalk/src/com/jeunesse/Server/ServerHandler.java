package com.jeunesse.Server;

import java.net.Socket;

/**
 * 接收客户端的线程任务
 *
 */
public class ServerHandler implements Runnable {
    private final Socket clientSocket;

    public ServerHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
