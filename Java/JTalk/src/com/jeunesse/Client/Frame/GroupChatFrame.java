package com.jeunesse.Client.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * 群聊页面
 * 界面大小700 * 700，默认居中可拖动
 * 上方显示群聊名称和人数
 * 下面右边是群成员列表
 * 左边是先是聊天记录，下方先有一栏“文本”、“表情”、”文件“的选择
 * 然后是默认文本输入框，输入框右下角为一个”发送“按钮
 */
public class GroupChatFrame extends JFrame {
    public GroupChatFrame() {
        initFrame();
        initComponents();

        this.setVisible(true);
    }

    // 初始化窗口
    private void initFrame() {
        setTitle("JTalk - 群聊");
        setSize(700, 700);
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 绝对布局
        getContentPane().setBackground(new Color(240, 242, 245));
    }

    // 初始化组件
    private void initComponents() {
        // ========== 顶部信息栏 ==========
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(0, 0, 700, 60);
        headerPanel.setBackground(Color.WHITE);

        // 群聊信息
        JLabel groupNameLabel = new JLabel("技术交流群（128人）");
        groupNameLabel.setBounds(20, 15, 300, 30);
        groupNameLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        headerPanel.add(groupNameLabel);
        this.add(headerPanel);

        // ========== 主内容区域 ==========
        // 左边聊天记录
        JScrollPane chatScroll = new JScrollPane();
        chatScroll.setBounds(10, 70, 480, 350);
        chatScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("宋体", Font.PLAIN, 14));
        chatArea.setText("[系统] 欢迎加入本群聊\n[张三] 大家好！");
        chatScroll.setViewportView(chatArea);
        this.add(chatScroll);

        // 右边成员列表
        JScrollPane memberScroll = new JScrollPane();
        memberScroll.setBounds(500, 70, 190, 350);
        memberScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        DefaultListModel<String> memberModel = new DefaultListModel<>();
        memberModel.addElement("张三（管理员）");
        memberModel.addElement("李四");
        memberModel.addElement("王五 ●");
        JList<String> memberList = new JList<>(memberModel);
        memberList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if (value.toString().contains("●")) {
                    label.setForeground(new Color(0, 150, 0));
                }
                return label;
            }
        });
        memberScroll.setViewportView(memberList);
        this.add(memberScroll);

        // ========== 功能选项栏 ==========
        JPanel optionPanel = new JPanel(null);
        optionPanel.setBounds(10, 420, 680, 40);

        String[] options = {"文本", "表情", "文件"};
        for (int i = 0; i < options.length; i++) {
            JButton btn = new JButton(options[i]);
            btn.setBounds(10 + i*100, 5, 80, 30);
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
        this.add(inputArea);

        // 发送按钮
        JButton sendBtn = new JButton("发送");
        sendBtn.setBounds(600, 615, 80, 40);
        sendBtn.setBackground(new Color(0, 120, 215));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFont(new Font("微软雅黑", Font.BOLD, 20));
        sendBtn.setFocusPainted(false);
        // 绑定shift + enter
        inputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    sendBtn.doClick();
                }
            }
        });
        this.add(sendBtn);
    }

    public static void main(String[] args) {
        new GroupChatFrame();
    }
}