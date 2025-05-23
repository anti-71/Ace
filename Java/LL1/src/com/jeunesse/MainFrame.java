package com.jeunesse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 创建一个可视化界面用来接收输入，和可视化LL（1）分析部分
public class MainFrame extends JFrame {
    private LL1Analyzer analyzer; // LL（1）分析器
    private JTextArea grammarInput;
    private JTextArea stringInput; // 文法输入框

    public MainFrame() {
        analyzer  = new LL1Analyzer();
        initFrame();
        initComponent();

        setVisible(true);
    }

    // 组件：两个输入框，两个按钮对应输入，3个按钮实现功能：First、Follow集，LL（1）语法分析表，LL（1）语法分析
    private void initComponent() {
        // 文法输入区（上方）
        JLabel grammarLabel = new JLabel("请输入文法规则：");
        add(grammarLabel);
        grammarLabel.setBounds(10, 10, 150, 20);

        grammarInput = new JTextArea();
        JScrollPane grammarScroll = new JScrollPane(grammarInput);
        add(grammarScroll);
        grammarScroll.setBounds(10, 35, 450, 120);

        JButton grammarBtn = new JButton("确认文法");
        add(grammarBtn);
        grammarBtn.setBounds(470, 35, 100, 30);
        // 实现文法输入
        grammarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzer.inputGrammar(grammarInput.getText());
            }
        });

        // 字符串输入区（中部）
        JLabel stringLabel = new JLabel("请输入待分析字符串：");
        add(stringLabel);
        stringLabel.setBounds(10, 165, 150, 20);

        stringInput = new JTextArea();
        JScrollPane stringScroll = new JScrollPane(stringInput);
        add(stringScroll);
        stringScroll.setBounds(10, 190, 450, 30);

        JButton stringBtn = new JButton("确认字符串");
        add(stringBtn);
        stringBtn.setBounds(470, 190, 100, 30);
        // 实现字符串输入
        stringBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzer.inputString(stringInput.getText());
            }
        });

        // 功能按钮区（下方）
        JButton ffBtn = new JButton("First/Follow集");
        add(ffBtn);
        ffBtn.setBounds(50, 240, 150, 40);
        // 实现First/Follow集的计算
        ffBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzer.calculateFirstSets();
                analyzer.calculateFollowSets();
            }
        });

        JButton tableBtn = new JButton("预测分析表");
        add(tableBtn);
        tableBtn.setBounds(220, 240, 150, 40);
        // 实现预测分析表的生成
        tableBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzer.generateLL1Table();
            }
        });

        JButton analyzeBtn = new JButton("开始分析");
        add(analyzeBtn);
        analyzeBtn.setBounds(390, 240, 150, 40);
        // 实现LL（1）语法分析功能
        analyzeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzer.generateParseTable();
            }
        });
    }

    private void initFrame() {
        setTitle("LL(1)语法分析器 V 1.0 Jeunesse");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }
}
