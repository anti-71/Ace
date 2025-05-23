package com.jeunesse.gui2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 自定义的登录界面：认JFrame做爸爸
public class LoginFrame extends JFrame implements ActionListener {
    public LoginFrame() {
        setTitle("登录界面");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init(); // 初始化这个窗口上的组件
    }

    private void init() {
        // 添加一个登录按钮
        JButton loginBtn = new JButton("登录");

        loginBtn.addActionListener(this); // 添加事件监听器

        JPanel panel = new JPanel();
        this.add(panel);

        panel.add(loginBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "我被点击了！");
    }
}
