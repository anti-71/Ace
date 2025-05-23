package com.jeunesse.gui2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Test {
    public static void main(String[] args) {
        // 目标：认识GUI的事件处理机制
        JFrame jf = new JFrame("登录界面");

        JPanel panel = new JPanel();
        jf.add(panel);

        jf.setSize(400, 300); // 设置窗口大小
        jf.setLocationRelativeTo(null); // 居中显示
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗口时的默认操作：关闭窗口退出程序

        JButton jb = new JButton("登录");
        jb.setBounds(150, 100, 80, 30); // 设置按钮的位置和尺寸
        panel.add(jb);

        // 给按钮绑定点击事件监听器对象
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 一旦你点击jb按钮，底层触发这个方法执行
                // e 是事件对象，封装了事件相关信息
                JOptionPane.showMessageDialog(jf, "有人点击登录");
            }
        });

        // 需求：监听用户键盘上下左右四个按键的事件
        // 给jf窗口整体绑定按键事件
        jf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyword = e.getKeyCode(); // 拿事件源头的键帽编号
                // 判断按键编码是否是上、下、左、右
                if (keyword == KeyEvent.VK_UP) {
                    JOptionPane.showMessageDialog(jf, "用户点击了上");
                } else if (keyword == KeyEvent.VK_DOWN) {
                    JOptionPane.showMessageDialog(jf, "用户点击了下");
                } else if (keyword == KeyEvent.VK_LEFT) {
                    JOptionPane.showMessageDialog(jf, "用户点击了左");
                } else if (keyword == KeyEvent.VK_RIGHT) {
                    JOptionPane.showMessageDialog(jf, "用户点击了右");
                }
            }
        });

        jf.setVisible(true); // 显示窗口

        // 让窗口成为焦点
        jf.requestFocus();
    }
}
