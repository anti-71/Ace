package com.jeunesse;

import java.awt.*;

// 触发区域类
// 包含对应的触发范围，和事件处理器
public class TriggerArea {
    private final Rectangle area;
    private final TriggerEventListener listener;
    private boolean isTriggered = false; // 是否被触发

    public TriggerArea(Rectangle area, TriggerEventListener listener) {
        this.area = area;
        this.listener = listener;
    }

    // 返回触发范围
    public Rectangle getArea() {
        return area;
    }

    // 返回事件监听器
    public TriggerEventListener getListener() {
        return listener;
    }

    // 判断是否被触发
    public boolean isTriggered() {
        return isTriggered;
    }

    // 改变触发状态
    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }
}
