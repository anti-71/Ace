package com.jeunesse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

// 旋转头类：在指定区域[minX, maxX]内左右移动，并持续旋转
public class RotatingHead extends JLabel {
    private final List<ImageIcon> headFrames = new ArrayList<>(); // 存储4个方向的图片
    private double rotationAngle = 0;          // 当前旋转角度（度）
    private final int moveSpeed = 2;               // 水平移动速度（像素/帧）
    private boolean movingRight = true;       // 当前是否向右移动
    private final int minX;                    // 左边界
    private final int maxX;                    // 右边界
    private final int rotationSpeed = 1;           // 旋转速度（度/帧）

    public RotatingHead(String imagePath, int minX, int maxX) {
        this.minX = minX;
        this.maxX = maxX;

        // 加载4个方向的图片
        try {
            for (int i = 0; i < 4; i++) {
                headFrames.add(new ImageIcon(imagePath + "head" + i + ".png"));
            }
        } catch (Exception e) {
            System.err.println("图片加载失败：" + e.getMessage());
        }

        // 初始位置设为左边界
        setBounds(minX, getY(), 32, 32);

        // 定时器：控制移动和旋转逻辑
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMovement();
                updateRotation();
                repaint(); // 触发重绘
            }
        });
        timer.start();
    }

    // 更新移动逻辑
    private void updateMovement() {
        int currentX = getX();
        if (movingRight) {
            if (currentX + moveSpeed <= maxX) {
                setLocation(currentX + moveSpeed, getY());
            } else {
                movingRight = false; // 到达右边界，转向左
            }
        } else {
            if (currentX - moveSpeed >= minX) {
                setLocation(currentX - moveSpeed, getY());
            } else {
                movingRight = true; // 到达左边界，转向右
            }
        }
    }

    // 更新旋转逻辑
    private void updateRotation() {
        rotationAngle += rotationSpeed;
        if (rotationAngle >= 360) {
            rotationAngle -= 360; // 保持角度在0~360度之间
        }
    }

    // 获取组件的边界
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // 设置旋转中心为组件中心点
        AffineTransform transform = new AffineTransform();
        transform.rotate(
                Math.toRadians(rotationAngle),
                getWidth() / 2.0,
                getHeight() / 2.0
        );
        g2d.setTransform(transform);

        // 根据方向选择图片帧
        ImageIcon currentImage = movingRight ?
                headFrames.get(1) : // 向右移动使用第2帧
                headFrames.get(3);  // 向左移动使用第4帧

        // 绘制图像（使用组件内部坐标，从(0,0)开始）
        if (!headFrames.isEmpty()) {
            g2d.drawImage(
                    currentImage.getImage(),
                    0, 0, getWidth(), getHeight(),
                    null
            );
        }

        g2d.dispose();
    }
}