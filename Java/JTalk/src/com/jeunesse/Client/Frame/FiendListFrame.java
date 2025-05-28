package com.jeunesse.Client.Frame;

import javax.swing.*;
import java.awt.*;

/**
 * 好友列表页面
 * 界面默认显示在屏幕左侧
 * 上方1/6的部分为个人信息（昵称、id和手机号）
 * 接下来一个添加好友的按钮
 * 然后三个选择（好友、分组、群聊）
 * 再下方空白，待添加选择的栏目信息
 */
public class FiendListFrame extends JFrame {
    public static void main(String[] args) {
        new FiendListFrame();
    }
    public FiendListFrame() {
        initFrame();
        initComponent();

        this.setVisible(true);
    }

    // 初始化组件
    private void initComponent() {
        // 个人信息区域
        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(0, 0, 260, 80);
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // 个人信息组件
        addInfoItem(infoPanel, "昵称：", Color.BLACK);
        addInfoItem(infoPanel, "ID：", new Color(100, 100, 100));
        addInfoItem(infoPanel, "手机：", new Color(150, 150, 150));
        this.add(infoPanel);

        // 添加好友按钮
        JButton addBtn = new JButton("添加好友");
        addBtn.setBounds(0, 85, 260, 35);
        addBtn.setBackground(new Color(0, 120, 215));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addBtn.setFocusPainted(false);
        this.add(addBtn);

        // 选项卡按钮
        JPanel tabPanel = new JPanel(new GridLayout(1, 3));
        tabPanel.setBounds(0, 120,260, 30);
        tabPanel.setBackground(Color.WHITE);

        JToggleButton[] tabs = {
                new JToggleButton("好友"),
                new JToggleButton("分组"),
                new JToggleButton("群聊")
        };

        ButtonGroup tabGroup = new ButtonGroup();
        for (JToggleButton tab : tabs) {
            tab.setBackground(Color.WHITE);
            tab.setForeground(Color.DARK_GRAY);
            tab.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            tab.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(5, 0, 5, 0)
            ));
            tabGroup.add(tab);
            tabPanel.add(tab);
        }
        this.add(tabPanel);
    }

    // 创建信息项组件
    private void addInfoItem(JPanel panel, String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        label.setForeground(color);
        panel.add(label);
    }

    // 初始化窗口
    private void initFrame() {
        // 窗口设置
        this.setTitle("JTalk");
        this.setSize(260, 600);
        // 获取屏幕尺寸和窗口尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = this.getWidth();
        int windowHeight = this.getHeight();

        // 计算右侧位置（水平右侧对齐，垂直居中）
        int x = screenSize.width - windowWidth - 10; // 右侧对齐
        int y = (screenSize.height - windowHeight) / 2; // 垂直居中

        // 设置位置
        this.setLocation(x, y);
        this.setLayout(new BorderLayout(0, 4));

        this.setLayout(null);
        this.setBackground(new Color(240, 240, 240));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBackground(Color.WHITE);
    }
}
