package com.jeunesse.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 注册页面
 * 背景设置一张图片，左上角显示JTalk，输入框上方为标题“欢迎注册JTalk”
 * 第一个输入框为“输入昵称”，第二个输入框为“输入JTalk密码”，第三个输入框为“输入手机号码”并且左边为“+86”
 * 下面是注册按钮“立即注册”
 */
public class RegisterFrame extends JFrame {
    JTextField nicknameField;
    JPasswordField passwordField;
    JTextField phoneField;
    JButton registerBtn;

    public RegisterFrame() {
        //设置界面主题
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
        initComponent();

        setVisible(true);
        // 让注册按钮获取焦点
        registerBtn.requestFocus();
    }

    // 初始化组件
    private void initComponent() {
        // 输入框统一参数
        int fieldWidth = 300;
        int fieldHeight = 40;
        int xPos = 140;
        int verticalGap = 50;

        // 左上角Logo
        JLabel logoLabel = new JLabel("JTalk");
        logoLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBounds(30, 20, 100, 30);
        this.add(logoLabel);

        // 标题
        JLabel titleLabel = new JLabel("欢迎注册JTalk");
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        titleLabel.setBounds(90, 150, 400, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        // 昵称输入框
        nicknameField = new JTextField("输入昵称");
        nicknameField.setBounds(xPos, 210, fieldWidth, fieldHeight);
        nicknameField.setFont(new Font("宋体", Font.PLAIN, 16));
        nicknameField.setForeground(Color.GRAY);
        // 输入框边框
        nicknameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        nicknameField.addFocusListener(new FocusAdapter() {
            // 获取焦点时，清除提示信息
            @Override
            public void focusGained(FocusEvent e) {
                if (nicknameField.getText().equals("输入昵称")) {
                    nicknameField.setText("");
                    nicknameField.setForeground(Color.BLACK);
                }
            }
            // 失去焦点时，如果输入框为空，则显示输入提示
            @Override
            public void focusLost(FocusEvent e) {
                if (nicknameField.getText().isEmpty()) {
                    nicknameField.setForeground(Color.GRAY);
                    nicknameField.setText("输入昵称");
                }
            }
        });
        this.add(nicknameField);

        // 密码输入框
        passwordField = new JPasswordField("输入JTalk密码");
        passwordField.setBounds(xPos, 210 + verticalGap, fieldWidth, fieldHeight);
        passwordField.setEchoChar((char)0);
        passwordField.setFont(new Font("宋体", Font.PLAIN, 16));
        passwordField.setForeground(Color.GRAY);
        // 输入框边框
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        passwordField.addFocusListener(new FocusAdapter() {
            // 获取焦点时，清除提示信息，且输入内容改为*
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("输入JTalk密码")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('*');
                }
            }
            // 失去焦点时，如果输入框为空，则显示输入提示
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("输入JTalk密码");
                }
            }
        });
        this.add(passwordField);

        // 手机号输入表盘
        JPanel phonePanel = new JPanel(new BorderLayout());
        phonePanel.setBounds(xPos, 210 + verticalGap * 2, fieldWidth, fieldHeight);
        phonePanel.setOpaque(false);

        // 国家代码
        JLabel countryCode = new JLabel(" +86");
        countryCode.setFont(new Font("宋体", Font.PLAIN, 20));
        countryCode.setOpaque(true);
        countryCode.setBackground(Color.WHITE);
        countryCode.setPreferredSize(new Dimension(60, 30));
        phonePanel.add(countryCode, BorderLayout.WEST);

        // 手机号输入框
        phoneField = new JTextField("输入手机号码");
        phonePanel.add(phoneField, BorderLayout.CENTER);
        phoneField.setFont(new Font("宋体", Font.PLAIN, 16));
        phoneField.setForeground(Color.GRAY);
        // 输入框边框
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        phoneField.addFocusListener(new FocusAdapter() {
            // 获取焦点时，清除提示信息
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneField.getText().equals("输入手机号码")) {
                    phoneField.setText("");
                    phoneField.setForeground(Color.BLACK);
                }
            }
            // 失去焦点时，如果输入框为空，则显示输入提示
            @Override
            public void focusLost(FocusEvent e) {
                if (phoneField.getText().isEmpty()) {
                    phoneField.setForeground(Color.GRAY);
                    phoneField.setText("输入手机号码");
                }
            }
        });
        this.add(phonePanel);

        // 注册按钮
        registerBtn = new JButton("立即注册");
        registerBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
        registerBtn.setBounds(xPos, 210 + verticalGap * 4, fieldWidth, 45);
        registerBtn.setBackground(new Color(0, 121, 215));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        this.add(registerBtn);

        // 背景图片
        JLabel bgLabel = new JLabel(new ImageIcon("src/image/background2.png"));
        bgLabel.setBounds(0, 0, 600, 650);
        this.add(bgLabel);
    }

    // 初始化窗口
    private void initFrame() {
        // 窗口基础设置
        this.setSize(600,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.WHITE);
    }
}
