package com.jeunesse.gui2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test2 {
    public static void main(String[] args) {
        // 第一种写法：提供实现类：创建实现类对象代表事件监听器对象
        JFrame jf = new JFrame("登录界面");

        JPanel panel = new JPanel();
        jf.add(panel);

        jf.setSize(400, 300); // 设置窗口大小
        jf.setLocationRelativeTo(null); // 居中显示
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗口时的默认操作：关闭窗口退出程序

        JButton jb = new JButton("登录");
        jb.setBounds(150, 100, 80, 30); // 设置按钮的位置和尺寸
        panel.add(jb);

        jb.addActionListener(new MyActionListener(jf));

        jf.setVisible(true);
    }
}

// 实现类
class MyActionListener implements ActionListener {
    private JFrame jf;

    public MyActionListener(JFrame jf) {
        this.jf = jf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(jf, "登录成功！");
    }
}
