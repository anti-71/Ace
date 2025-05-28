package com.jeunesse.Client.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 私聊页面
 * 界面大小700 * 700，默认居中可拖动
 * 上方显示聊天对象的昵称和在线状态
 * 中间是聊天记录
 * 下方先有一栏“文本”、“表情”、”文件“的选择，然后是默认文本输入框，输入框右下角为一个”发送“按钮
 */
public class PrivateChatFrame extends JFrame {
    public static void main(String[] args) {
        new PrivateChatFrame();
    }
    public PrivateChatFrame() {
        initFrame();
        initComponent();

        this.setVisible(true);
    }

    // 初始化组件
    private void initComponent() {
        // ========== 顶部信息栏 ==========
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 700, 60);
        headerPanel.setBackground(Color.WHITE);

        // 在线状态指示
        JLabel statusIcon = new JLabel();
        statusIcon.setBounds(20, 25, 12, 12);
        statusIcon.setOpaque(true);
        statusIcon.setBackground(new Color(0, 200, 0)); // 绿色在线状态
        statusIcon.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // 好友昵称
        JLabel nicknameLabel = new JLabel("好友昵称");
        nicknameLabel.setBounds(40, 15, 200, 30);
        nicknameLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));

        headerPanel.add(statusIcon);
        headerPanel.add(nicknameLabel);
        this.add(headerPanel);

        // ========== 聊天记录区域 ==========
        JScrollPane chatScroll = new JScrollPane();
        chatScroll.setBounds(10, 70, 680, 350);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("宋体", Font.PLAIN, 14));
        chatArea.setText("[2023-10-20 09:00] 测试消息\n[2023-10-20 09:01] 这是一条历史消息");
        chatScroll.setViewportView(chatArea);
        this.add(chatScroll);

        // ========== 功能选项栏 ==========
        JPanel optionPanel = new JPanel(null);
        optionPanel.setBounds(10, 420, 680, 40);

        String[] options = {"文本", "表情", "文件"};
        for (int i = 0; i < options.length; i++) {
            JButton btn = new JButton(options[i]);
            btn.setBounds(10 + i * 100, 5, 80, 30);
            btn.setBackground(new Color(245, 245, 245));
            btn.setFocusPainted(false);
            optionPanel.add(btn);
        }
        this.add(optionPanel);

        // ========== 输入区域 ==========
        JTextArea inputArea = new JTextArea();
        inputArea.setBounds(10, 465, 580, 190);
        inputArea.setFont(new Font("宋体", Font.PLAIN, 18));
        inputArea.setBorder(null); // 移除默认边框
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        // 将换行改为shift + enter
        this.add(inputArea);

        // 发送按钮
        JButton sendBtn = new JButton("发送");
        sendBtn.setBounds(600, 615, 80, 40);
        sendBtn.setBackground(new Color(0, 120, 215));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
        sendBtn.setFocusPainted(false);
        // 绑定回车键
        inputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    sendBtn.doClick();
                }
            }
        });
        this.add(sendBtn);
    }

    // 初始化窗口
    private void initFrame() {
        // 窗口基础设置
        this.setTitle("JTalk - 私聊");
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
    }
}
