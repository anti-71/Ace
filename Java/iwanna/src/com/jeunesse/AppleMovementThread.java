package com.jeunesse;

import javax.swing.*;

// 线程类，用于控制pushedApple的移动
public class AppleMovementThread extends Thread{
    private final PushedApple pushedApple; // 传入苹果对象
    private boolean isRunning = true; // 线程是否运行

    public AppleMovementThread(PushedApple pushedApple) {
        this.pushedApple = pushedApple;
    }

    public void run() {
        // 如果判断正在移动，在EDT中更新位置
        while (isRunning) {
            SwingUtilities.invokeLater(() -> {
                pushedApple.updatePosition();
            });
            try {
                Thread.sleep(16);
            } catch (Exception e) {
                break;
            }
        }
    }

    public void stopMovement() {
        isRunning = false;
    }
}
