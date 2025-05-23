package com.jeunesse;

import javax.swing.*;
import java.awt.*;

// 不可移动的平台，使人物能够站立在其上
public class Platform extends JLabel {
    // 创建平台对象后，设置图片
    public Platform(String imagePath) {
        super(new ImageIcon(imagePath+"dirt.png"));
    }

    // 获取碰撞框
    public Rectangle getLoc() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
