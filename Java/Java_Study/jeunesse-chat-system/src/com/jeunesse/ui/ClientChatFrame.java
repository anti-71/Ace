package com.jeunesse.ui;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientChatFrame extends JFrame {
    public JTextArea smsContent = new JTextArea(23, 50);
    private JTextArea smsSend = new JTextArea(4, 40);
    public JList<String> onLineUsers = new JList<>();
    private JButton sendBn = new JButton("发送");
    private Socket socket;

    public ClientChatFrame() {
        initView();
        this.setVisible(true);
    }

    public ClientChatFrame(String nickname, Socket socket) {
        this(); // 先调用上面无参数构造器，初始化界面信息
        // 初始化数据
        // 立马展示昵称到窗口
        this.setTitle(nickname);
        this.socket = socket;

        // 立即把客户端的这个Socket管道交给一个独立的线程专门负责读取客户端socket从服务端收到的在线人数更新数据或者群聊数据
        new ClientReaderThread(socket,this).start();
    }

    private void initView() {
        this.setSize(700, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口，退出程序
        this.setLocationRelativeTo(null); // 窗口居中

        // 设置窗口背景色
        this.getContentPane().setBackground(new Color(0xf0, 0xf0, 0xf0));

        // 设置字体
        Font font = new Font("SimKai", Font.PLAIN, 14);

        // 消息内容框
        smsContent.setFont(font);
        smsContent.setBackground(new Color(0xdd, 0xdd, 0xdd));
        smsContent.setEditable(false);

        // 发送消息框
        smsSend.setFont(font);
        smsSend.setWrapStyleWord(true);
        smsSend.setLineWrap(true);

        // 在线用户列表
        onLineUsers.setFont(font);
        onLineUsers.setFixedCellWidth(120);
        onLineUsers.setVisibleRowCount(13);

        // 创建底部面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(0xf0, 0xf0, 0xf0));

        // 消息输入框
        JScrollPane smsSendScrollPane = new JScrollPane(smsSend);
        smsSendScrollPane.setBorder(BorderFactory.createEmptyBorder());
        smsSendScrollPane.setPreferredSize(new Dimension(500, 50));

        // 发送按钮
        sendBn.setFont(font);
        sendBn.setBackground(Color.decode("#009688"));
        sendBn.setForeground(Color.WHITE);

        // 按钮面板
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        btns.setBackground(new Color(0xf0, 0xf0, 0xf0));
        btns.add(sendBn);

        // 给发送按钮绑定点击事件
        sendBn.addActionListener(e -> {
            // 获取输入框中的内容
            String msg = smsSend.getText();
            // 清空输入框
            smsSend.setText("");
            // 发送消息
            sendMsgToServer(msg);
        });

        // 添加组件
        bottomPanel.add(smsSendScrollPane, BorderLayout.CENTER);
        bottomPanel.add(btns, BorderLayout.EAST);

        // 用户列表面板
        JScrollPane userListScrollPane = new JScrollPane(onLineUsers);
        userListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        userListScrollPane.setPreferredSize(new Dimension(120, 500));

        // 中心消息面板
        JScrollPane smsContentScrollPane = new JScrollPane(smsContent);
        smsContentScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // 添加所有组件
        this.add(smsContentScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(userListScrollPane, BorderLayout.EAST);
    }

    // 发送消息给服务端
    private void sendMsgToServer(String msg) {
        try {
            // 1、从socket管道中获取一个特殊数据输出流
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            // 2、把消息发送给服务端
            dos.writeInt(2);
            dos.writeUTF(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新在线用户列表
    public void updateOnLineUsers(String[] onLineNames) {
        // 把这个线程读取到的在线用户名称展示到界面上
        onLineUsers.setListData(onLineNames);
    }

    // 更新群聊消息
    public void setMsgToWin(String msg) {
        // 更新群聊消息到界面上
        smsContent.append(msg);
    }
}