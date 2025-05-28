package com.jeunesse.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * 密码找回页面
 * 背景设置一张图片，左上角显示JTalk，输入框上方为标题"找回JTalk密码"
 * 输入框依次为：输入账号、输入绑定手机号、输入新密码
 * 下面是确认按钮"确认修改"
 */
public class FindpwdFrame extends JFrame {
    JTextField accountField;
    JTextField phoneField;
    JPasswordField newPasswordField;
    JButton confirmBtn;

    public static void main(String[] args) {
        new FindpwdFrame();
    }

    public FindpwdFrame() {
        // 设置界面主题
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
        initComponent();

        setVisible(true);
        confirmBtn.requestFocus();
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
        JLabel titleLabel = new JLabel("找回JTalk密码");
        titleLabel.setForeground(new Color(0, 180, 240));
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        titleLabel.setBounds(90, 150, 400, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);

        // 账号输入框
        accountField = new JTextField("输入账号");
        accountField.setBounds(xPos, 210, fieldWidth, fieldHeight);
        accountField.setFont(new Font("宋体", Font.PLAIN, 16));
        accountField.setForeground(Color.GRAY);
        accountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        accountField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (accountField.getText().equals("输入账号")) {
                    accountField.setText("");
                    accountField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (accountField.getText().isEmpty()) {
                    accountField.setForeground(Color.GRAY);
                    accountField.setText("输入账号");
                }
            }
        });
        this.add(accountField);

        // 手机号输入框
        phoneField = new JTextField("输入绑定手机号");
        phoneField.setBounds(xPos, 210 + verticalGap, fieldWidth, fieldHeight);
        phoneField.setFont(new Font("宋体", Font.PLAIN, 16));
        phoneField.setForeground(Color.GRAY);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        phoneField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneField.getText().equals("输入绑定手机号")) {
                    phoneField.setText("");
                    phoneField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (phoneField.getText().isEmpty()) {
                    phoneField.setForeground(Color.GRAY);
                    phoneField.setText("输入绑定手机号");
                }
            }
        });
        this.add(phoneField);

        // 新密码输入框
        newPasswordField = new JPasswordField("输入新密码");
        newPasswordField.setBounds(xPos, 210 + verticalGap * 2, fieldWidth, fieldHeight);
        newPasswordField.setEchoChar((char) 0);
        newPasswordField.setFont(new Font("宋体", Font.PLAIN, 16));
        newPasswordField.setForeground(Color.GRAY);
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        newPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(newPasswordField.getPassword()).equals("输入新密码")) {
                    newPasswordField.setText("");
                    newPasswordField.setForeground(Color.BLACK);
                    newPasswordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (newPasswordField.getPassword().length == 0) {
                    newPasswordField.setEchoChar((char) 0);
                    newPasswordField.setForeground(Color.GRAY);
                    newPasswordField.setText("输入新密码");
                }
            }
        });
        this.add(newPasswordField);

        // 确认按钮
        confirmBtn = new JButton("确认修改");
        confirmBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
        confirmBtn.setBounds(xPos, 210 + verticalGap * 4, fieldWidth, 45);
        confirmBtn.setBackground(new Color(0, 121, 215));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        this.add(confirmBtn);

        // 背景图片
        JLabel bgLabel = new JLabel(new ImageIcon("src/image/background3.png"));
        bgLabel.setBounds(0, 0, 600, 650);
        this.add(bgLabel);
    }

    // 初始化窗口
    private void initFrame() {
        this.setSize(600, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.WHITE);
    }
}
