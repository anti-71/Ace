package com.jeunesse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ServerReaderThread extends Thread{
    private Socket socket;
    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 接收的消息可能有很多种类型：1、登录消息（包含呢称） 2、群聊消息 3、私聊消息
            // 所以客户端必须声明协议发送消息
            // 比如客户端先发1，代表接下来是登录消息。
            // 比如客户端先发2，代表接下来是群聊消息。
            // 先从socket管道中接收客户端发送来的消息类型编号
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int type = dis.readInt(); // 1、2、3代表三种消息类型
                switch (type) {
                    case 1:
                        // 客户端发来了登录消息，接下来要接收呢称数据，再更新全部在线客户端的在线人数列表
                        String nickName = dis.readUTF();
                        // 把这个登录成功的客户端socket存入到在线集合
                        Server.onLineSockets.put(socket, nickName);
                        // 更新全部客户端的在线人数列表
                        updateClientonLineUserList();
                        break;
                    case 2:
                        // 客户端发来了群聊消息，接下来要接收群聊消息内容，再把群聊消息转发给全部在线客户端
                        String msg = dis.readUTF();
                        sendMsgToAll(msg);
                        break;
                    case 3:
                        // 客户端发来了私聊消息，接下来要接收私聊消息内容，再把私聊消息转发给指定的在线客户端
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("客户端下线了" + socket.getInetAddress().getHostAddress());
            Server.onLineSockets.remove(socket); // 把下线的客户端Socket从在线集合中移出
            updateClientonLineUserList(); // 下线了用户也需要更新全部客户端的在线人数列表
        }
    }

    // 给全部在线socket推送当前客户端发来的消息
    private void sendMsgToAll(String msg) {
        // 一定要拼装好这个消息，再发给全部在线socket
        StringBuilder sb = new StringBuilder();
        String name = Server.onLineSockets.get(socket);

        // 获取当前时间
        LocalDateTime  now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss EEE");
        String nowStr = dtf.format(now);

        String msgResult = sb.append(name).append(" ").append(nowStr).append("\r\n")
                .append(msg).append("\r\n").toString();
        // 推送给全部客户端socket
        for (Socket socket : Server.onLineSockets.keySet()) {
            try {
                // 3、把用户名称集合发送给每一个Socket管道
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(2); // 1 代表是客户端接下来是在线人数列表消息 2 代表是群聊消息
                dos.writeUTF(msgResult);
                dos.flush(); // 刷新数据！
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateClientonLineUserList() {
        // 更新全部客户端的在线人数列表
        // 拿到全部在线客户端的用户名称，把这些名称转发给全部在线s0ckt管道
        // 1、拿到全部在线客户端的用户名称
        Collection<String> onLineUsers = Server.onLineSockets.values();
        // 2、把这个集合中的所有用户都推送给全部客户端Socket管道
        for (Socket socket : Server.onLineSockets.keySet()) {
            try {
                // 3、把用户名称集合发送给每一个Socket管道
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(1); // 1 代表是客户端接下来是在线人数列表消息 2 代表是群聊消息
                dos.writeInt(onLineUsers.size()); // 告诉客户端，接下来要发多少个用户名称
                for (String user : onLineUsers) {
                    dos.writeUTF(user);
                }
                dos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
