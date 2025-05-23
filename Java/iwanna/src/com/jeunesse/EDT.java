package com.jeunesse;

import javax.swing.*;

// 目标：基于i wanna be the guy的资源设计一个碰撞主题的小游戏
// 此处为主程序，用于触发GUI更新
// 通关过程：不能跳过刺，从下面走，踩死头，另一棵树上的苹果掉下来，推动苹果至踏板，出现假传送门，跳过假传送门，进入真传送门，胜利
public class EDT {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame();
        });
    }
}