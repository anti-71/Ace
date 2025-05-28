package com.jeunesse.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 登录页面
 * 上半部分放置一张图片，中间显示JTalk
 * 下半部分中间两行分别输入账号密码（输入框未输入时显示账号或密码，密码输入时显示*）
 * 右边分别显示注册账号和找回密码（蓝色字体）
 * 最下面一个登录按钮，与输入框等宽
 */
public class EntryFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public EntryFrame() {
        //设置界面主题
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initEdgeFrame();
        initFrame();
        initComponents();

        this.setVisible(true);
    }

    // 初始化边框
    private void initEdgeFrame() {
        // 取消默认窗口装饰（标题栏、边框等）
        this.setUndecorated(true);

        // 自定义关闭按钮（替代系统标题栏关闭）
        JButton closeBtn = new JButton("×");
        closeBtn.setBounds(385, 5, 40, 40);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setFont(new Font("Arial", Font.BOLD, 20));
        closeBtn.addActionListener(e -> System.exit(0));
        this.add(closeBtn);
    }

    // 初始化组件
    private void initComponents() {
        // 中间标题
        JLabel titleLabel = new JLabel("J Talk");
        titleLabel.setForeground(new Color(0, 153, 255));
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        titleLabel.setBounds(135, 90, 425, 40);
        this.add(titleLabel);

        // 上半部分图片
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(new ImageIcon("src/image/background1.jpg"));
        bgLabel.setBounds(0, 0, 425, 185);
        this.add(bgLabel);

        // 账号框默认显示"账号"，获得焦点清空
        usernameField = new JTextField("账号");
        usernameField.setForeground(Color.GRAY);
        usernameField.setBounds(100, 200, 200, 30);
        // 焦点事件处理
        usernameField.addFocusListener(new FocusAdapter() {
            // 焦点获取时，如果输入框为“账号”，则清空，可以输入
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("账号")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            // 焦点丢失时，如果输入框为空，则显示输入提示
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().trim().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("账号");
                }
            }
        });
        this.add(usernameField);

        // 密码框特殊处理：默认显示明文"密码"，输入时转为密文*
        passwordField = new JPasswordField("密码");
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // 初始显示明文
        passwordField.setBounds(100, 230, 200, 30);
        passwordField.addFocusListener(new FocusAdapter() {
            // 焦点获取时，如果输入框为"密码"，则清空，可以输入*
            @Override
            public void focusGained(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                if (new String(pf.getPassword()).equals("密码")) {
                    pf.setText("");
                    pf.setForeground(Color.BLACK);
                    pf.setEchoChar('*'); // 设置为密码模式
                }
            }

            // 焦点丢失时，如果输入框为空，则显示输入提示
            @Override
            public void focusLost(FocusEvent e) {
                JPasswordField pf = (JPasswordField) e.getSource();
                if (new String(pf.getPassword()).trim().isEmpty()) {
                    pf.setEchoChar((char) 0); // 设置为明文模式
                    pf.setText("密码");
                    pf.setForeground(Color.GRAY);
                }
            }
        });
        this.add(passwordField);

        // 右侧功能链接
        JLabel registerLabel = new JLabel("注册账号");
        registerLabel.setForeground(new Color(0, 102, 204));
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        registerLabel.setBounds(320, 203, 80, 20);
        this.add(registerLabel);

        JLabel findPwdLabel = new JLabel("找回密码");
        findPwdLabel.setForeground(new Color(0, 102, 204));
        findPwdLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        findPwdLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        findPwdLabel.setBounds(320, 237, 80, 20);
        this.add(findPwdLabel);

        // 登录按钮
        JButton loginBtn = new JButton("登 录");
        loginBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
        loginBtn.setBounds(100, 270, 200, 35);
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        this.add(loginBtn);
    }

    // 初始化窗口
    private void initFrame() {
        // 窗口基本设置
        this.setSize(425, 325);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // 禁止改变窗口大小
        this.setLocationRelativeTo(null); // 居中显示
        this.setLayout(null); // 绝对布局
        this.getContentPane().setBackground(Color.WHITE);
    }
}
