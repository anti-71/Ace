package com.jeunesse;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// 可被kid推动的苹果，与平台接触时修改胜利条件
public class PushedApple extends JLabel {
    private int verticalSpeed = 0; // 垂直速度
    private final int GRAVITY = 1; // 重力加速度
    private final List<Platform> platforms; // 引用平台列表
    private final List<TriggerArea> triggerAreas;

    public PushedApple(String imagePath, List<Platform> platforms, List<TriggerArea> triggerAreas) {
        super(new ImageIcon(imagePath + "apple.png"));
        this.platforms = platforms;
        this.triggerAreas = triggerAreas;
    }

    // 更新位置
    public void updatePosition() {
        Rectangle appleRect = getBounds(); // 苹果的位置

        // 垂直移动
        verticalSpeed += GRAVITY;
        int new_y = getY() + verticalSpeed;

        // 垂直碰撞检测
        boolean isOnPlatform = false;
        Rectangle verticalFuture = new Rectangle(getX(), new_y, getWidth(), getHeight());
        for (Platform platform : platforms) {
            Rectangle platformLoc = platform.getLoc();
            if (verticalFuture.intersects(platformLoc)) {
                if (verticalSpeed > 0) { // 下落时碰撞平台顶部
                    new_y = platformLoc.y - getHeight();
                    verticalSpeed = 0;
                    isOnPlatform = true;
                } else if (verticalSpeed < 0) { // 上升时碰撞平台底部
                    new_y = platformLoc.y + platformLoc.height;
                    verticalSpeed = 0;
                }
                break;
            }
        }

        // 如果pushedApple触碰触发区域，则执行区域事件
        TriggerArea triggerArea = triggerAreas.get(5);
        Rectangle area = triggerArea.getArea();
        if (appleRect.intersects(area) && !triggerArea.isTriggered()) {
            triggerArea.setTriggered(true);
            triggerArea.getListener().onTrigger();
        }

        setLocation(getX(), new_y);
    }
}
