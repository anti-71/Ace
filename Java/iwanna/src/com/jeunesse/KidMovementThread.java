package com.jeunesse;

import javax.swing.*;

// 线程类，用于控制kid的移动
public class KidMovementThread extends Thread{
    private final Kid kid; // 传入kid对象
    private boolean isRunning = true; // 线程是否运行

    public KidMovementThread(Kid kid) {
        this.kid = kid;
    }

    public void run()
    {
        // 如果判断正在移动，在EDT中更新位置
        while (isRunning)
        {
            SwingUtilities.invokeLater(() -> {
                    kid.updatePosition();
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
