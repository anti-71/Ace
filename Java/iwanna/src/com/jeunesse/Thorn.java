package com.jeunesse;

import javax.swing.*;

// 陷阱类
// kid的碰撞箱与thorn有交叉部分时，游戏结束
public class Thorn extends JLabel {
    public Thorn(String imagePath) {
        super(new ImageIcon(imagePath));
    }
}
