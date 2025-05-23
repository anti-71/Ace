package com.jeunesse.gui;

import javax.swing.*;

public class JFrameDemo1 {
    public static void main(String[] args) {
        // 目标：快速入f门一下GUI Swing的编程
        // 1、创建一个窗口，有一个登录按钮
        JFrame jf = new JFrame("登录界面");

        JPanel panel = new JPanel();
        jf.add(panel);

        jf.setSize(400, 300); // 设置窗口大小
        jf.setLocationRelativeTo(null); // 居中显示
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗口时的默认操作：关闭窗口退出程序

        JButton jb = new JButton("登录");
        jb.setBounds(150, 100, 80, 30); // 设置按钮的位置和尺寸
        panel.add(jb);

        jf.setVisible(true); // 显示窗口
    }
}
