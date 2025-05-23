package com.jeunesse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// 角色类
// 能够左右移动、跳跃（可以同时进行），碰撞体积为 32 * 32
// kid要和Platform有碰撞检测，kid要站在Platform上方
// kid左右移动时的朝向要不同
public class Kid extends JLabel {
    // 水平移动相关
    private volatile int speed; // 移动速度
    private volatile boolean isLeft = false; // 是否要往左移动
    private volatile boolean isRight = false; // 是否要往右移动
    private boolean isFacingLeft = false; // 是否正在朝左

    // 跳跃相关
    private int verticalSpeed = 0;     // 垂直速度
    private boolean isJumping = false; // 是否在跳跃中
    private boolean isOnGround = true; // 是否在地面

    // 动画相关
    private final List<ImageIcon> idleFrames = new ArrayList<>();
    private final List<ImageIcon> runFrames = new ArrayList<>();
    private final List<ImageIcon> jumpFrames = new ArrayList<>();
    private final List<ImageIcon> fallFrames = new ArrayList<>(); // 动画帧管理
    private int currentFrameIndex = 0; // 当前动画帧索引
    private long lastUpdateTime; // 上次更新时间
    private AnimationState currentState = AnimationState.IDLE; // 当前动画状态

    private final List<Platform> platforms; // 引用平台列表
    private final List<Thorn> thorns; // 引用刺列表
    private final List<TriggerArea> triggerAreas; // 引用触发区域列表
    private final RotatingHead rotatingHead; // 引用头
    private final JLabel stag_apple; // 引用静止的苹果
    private final PushedApple pushedApple; // 引用可推动的苹果
    private  final JLabel pressure_plate; // 引用压力板

    // 游戏结束相关
    private GameOverListener gameOverCallback; // 游戏结束回调
    private boolean isGameOver = false; // 游戏是否结束

    public void setGameOverCallback(MainFrame mainFrame) {
        this.gameOverCallback = mainFrame;
    }

    public Kid(String imagePath, List<Platform> platforms, List<Thorn> thorns, List<TriggerArea> triggerAreas, RotatingHead rotatingHead, JLabel stag_apple, PushedApple pushedApple, JLabel pressure_plate)
    {
        super(new ImageIcon(imagePath+"kid-common.png"));
        loadAnimations(imagePath);
        this.platforms = platforms;
        this.thorns = thorns;
        this.triggerAreas = triggerAreas;
        this.rotatingHead = rotatingHead;
        this.stag_apple = stag_apple;
        this.pushedApple = pushedApple;
        this.pressure_plate = pressure_plate;
    }

    // 加载动画帧
    private void loadAnimations(String imagePath) {
        // 加载静止帧
        idleFrames.add(new ImageIcon(imagePath + "kid-common.png"));
        // 加载奔跑动画帧
        for(int i = 0; i <= 4; i++) {
            runFrames.add(new ImageIcon(imagePath + "kid-running" + i + ".png"));
        }
        // 加载跳跃动画帧
        jumpFrames.add(new ImageIcon(imagePath + "kid-jumping.png"));
        //  加载下落动画帧
        fallFrames.add(new ImageIcon(imagePath + "kid-falling.png"));
    }

    // 设置水平移动方向
    public void setLeft(boolean moving) {
        isLeft = moving;
        if (moving) isFacingLeft = true;
        updateSpeed();
    }
    public void setRight(boolean moving) {
        isRight = moving;
        if (moving) isFacingLeft = false;
        updateSpeed();
    }

    // 更新水平速度
    public void updateSpeed() {
        // 根据移动方向设置速度
        if (isLeft) {
            speed = -5;
        } else if (isRight) {
            speed = 5;
        } else {
            speed = 0;
        }
    }

    // 跳跃触发，修改kid的状态参数
    public void jump() {
        if (isOnGround) {
            // 垂直参数改变
            // 跳跃初速度（向上）
            verticalSpeed = -15;
            isJumping = true;
            isOnGround = false;
            // 动画改变
            currentFrameIndex = 0; // 重置动画帧
            currentState = AnimationState.JUMPING; // 进入跳跃状态
        }
    }

    // 翻转图片
    private ImageIcon flipImage(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage flipped = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = flipped.createGraphics();
        g.drawImage(image, image.getWidth(null), 0, -image.getWidth(null), image.getHeight(null), null);
        g.dispose();
        return new ImageIcon(flipped);
    }


    // 更新当前动画帧
    private void updateFrame() {
        // 获取当前状态的动画帧
        List<ImageIcon> currentFrames = switch (currentState) {
            case MOVING -> runFrames;
            case JUMPING -> jumpFrames;
            case FALLING -> fallFrames;
            default -> idleFrames;
        };

        if (!currentFrames.isEmpty()) {
            // 更新当前帧
            ImageIcon original = currentFrames.get(currentFrameIndex % currentFrames.size());
            // 根据朝向决定是否翻转
            setIcon(isFacingLeft ? flipImage(original) : original);
        }
    }

    // 更新位置
    public void updatePosition() {
        if (isGameOver) {
            return; // 游戏已结束，不再处理移动和碰撞
        }
        Rectangle kidRect = getBounds(); // kid的位置

        // 更新动画帧（每60ms更新一帧）
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdateTime > 10) {
            currentFrameIndex++;
            updateFrame();
            lastUpdateTime = currentTime;
        }

        // 水平移动
        int originalX = getX();
        int originalY = getY();
        double airControl = isOnGround ? 1 : 0.8; // 空中控制系数
        int deltaX = (int) (speed * airControl);
        int new_x = getX() + deltaX; // kid在空中时，水平移速变慢
        new_x = Math.max(0, Math.min(new_x, 1198 - 32));
        // 设置朝向
        if (speed < 0) {
            isFacingLeft = true;
        } else if (speed > 0) {
            isFacingLeft = false;
        }

        // 水平碰撞检测
        Rectangle horizontalFuture = new Rectangle(new_x, originalY, 32, 32);
        for (Platform platform : platforms) {
            Rectangle platformLoc = platform.getLoc();
            if (horizontalFuture.intersects(platformLoc)) {
                if (deltaX > 0) { // 向右碰撞，调整到平台左侧
                    new_x = platformLoc.x - 32;
                    speed = 0;
                } else if (deltaX < 0) { // 向左碰撞，调整到平台右侧
                    new_x = platformLoc.x + platformLoc.width;
                    speed = 0;
                }
                break;
            }
        }

        // 垂直移动
        int GRAVITY = 1; // 重力加速度
        verticalSpeed += GRAVITY;
        int new_y = getY() + verticalSpeed;

        // 垂直碰撞检测
        boolean isOnPlatform = false;
        Rectangle verticalFuture = new Rectangle(new_x, new_y, 32, 32);
        for (Platform platform : platforms) {
            Rectangle platformLoc = platform.getLoc();
            if (verticalFuture.intersects(platformLoc)) {
                if (verticalSpeed > 0) { // 下落时碰撞平台顶部
                    new_y = platformLoc.y - 32;
                    verticalSpeed = 0;
                    isOnGround = true;
                    isJumping = false;
                    isOnPlatform = true;
                } else if (verticalSpeed < 0) { // 上升时碰撞平台底部
                    new_y = platformLoc.y + platformLoc.height;
                    verticalSpeed = 0;
                }
                break;
            }
        }
        
        // 头左右碰撞检测
        Rectangle headRect = rotatingHead.getBounds();
        if (kidRect.intersects(headRect)) {
            // 判断是否碰撞到左右两侧
            // 判断是否从上方踩到头
            if (kidRect.y + kidRect.height <= headRect.y + 5 && verticalSpeed > 0) {
                // 启动线程让头移出画布
                new Thread(() -> {
                    int moveSpeed = 10; // 移动速度
                    int maxY = 674; // 最大移动到的Y坐标
                    while (rotatingHead.getY() < maxY) {
                        try {
                            SwingUtilities.invokeLater(() -> {
                                rotatingHead.setLocation(rotatingHead.getX(), rotatingHead.getY() + moveSpeed);
                            });
                            Thread.sleep(16);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                // 隐藏静止的苹果
                stag_apple.setVisible(false);
                // 启动线程让静止的苹果移出画布
                new Thread(() -> {
                    int moveSpeed = 10; // 移动速度
                    int minY = -32; // 最大移动到的Y坐标
                    while (stag_apple.getY() > minY) {
                        try {
                            SwingUtilities.invokeLater(() -> {
                                stag_apple.setLocation(stag_apple.getX(), stag_apple.getY() - moveSpeed);
                            });
                            Thread.sleep(16);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                // 显示可推动的苹果
                pushedApple.setVisible(true);
                // 显示压力板
                pressure_plate.setVisible(true);
                // 启动苹果移动线程
                AppleMovementThread appleMovementThread = new AppleMovementThread(pushedApple);
                appleMovementThread.start();
            } else if (isCollidingWithLeftOrRight(headRect, kidRect)) { // 判断是否碰撞到左右两侧
                triggerGameOver();
                return;
            }
        }

        // 苹果碰撞检测
        Rectangle appleRect1 = pushedApple.getBounds();
        if (kidRect.intersects(appleRect1)) {
            if (deltaX > 0) { // 向右推动苹果
                int newAppleX = appleRect1.x + deltaX;
                pushedApple.setLocation(newAppleX, appleRect1.y);
            } else if (deltaX < 0) { // 向左推动苹果
                int newAppleX = appleRect1.x + deltaX;
                pushedApple.setLocation(newAppleX, appleRect1.y);
            }
        }

        // 更新动画状态
        if (!isOnGround) {
            if (verticalSpeed < 0) {
                currentState = AnimationState.JUMPING;   // 上升阶段
            } else {
                currentState = AnimationState.FALLING; // 下降阶段
            }
        } else {
            currentState = (speed != 0) ? AnimationState.MOVING : AnimationState.IDLE;
        }
        // 更新动画帧
        updateFrame();

        // 如果kid的碰撞箱与thorn有交叉，游戏结束
        for (Thorn thorn : thorns) {
            Rectangle thornRect = thorn.getBounds();
            if (kidRect.intersects(thornRect) && !isGameOver) {
                // 立刻停止移动
                speed = 0;
                verticalSpeed = 0;
                isLeft = false;
                isRight = false;
                isJumping = false;
                isOnGround = true;
                currentState = AnimationState.IDLE;
                triggerGameOver();
                return;
            }
        }

        // 如果kid的碰撞箱与stag_apple有交叉，游戏结束
        Rectangle appleRect2 = stag_apple.getBounds();
        if (kidRect.intersects(appleRect2) && !isGameOver) {
            // 立刻停止移动
            speed = 0;
            verticalSpeed = 0;
            isLeft = false;
            isRight = false;
            isJumping = false;
            isOnGround = true;
            currentState = AnimationState.IDLE;
            triggerGameOver();
        }

        // 检查前4个触发区域，如果kid触发了某个区域，则执行区域事件
        for (int i = 0; i < 5; i++) {
            TriggerArea triggerArea = triggerAreas.get(i);
            Rectangle area = triggerArea.getArea();
            if (kidRect.intersects(area) && !triggerArea.isTriggered()) {
                triggerArea.setTriggered(true);
                triggerArea.getListener().onTrigger();
            }
        }
        // 检查第5个触发区域，如果kid与该区域完全重叠，则执行区域事件
        TriggerArea triggerArea1 = triggerAreas.get(4);
        if (kidRect.equals(triggerArea1.getArea())) {
            triggerArea1.getListener().onTrigger();
        }
        // 检查第7个触发区域，如果kid与该区域完全重叠，则执行区域事件
        TriggerArea triggerArea2 = triggerAreas.get(6);
        Rectangle area = triggerArea2.getArea();
        if (kidRect.intersects(area) && !triggerArea2.isTriggered()) {
            triggerArea2.getListener().onTrigger();
        }

        // 如果kid在屏幕外，则游戏结束
        if (new_y > 674 && !isGameOver) {
            triggerGameOver();
            return;
        }

        setLocation(new_x, new_y);
    }

    // 判断是否碰撞到左右两侧
    private boolean isCollidingWithLeftOrRight(Rectangle headRect, Rectangle kidRect) {
        // 计算碰撞区域的X轴重叠范围
        double overlapLeft = Math.max(kidRect.getMinX(), headRect.getMinX());
        double overlapRight = Math.min(kidRect.getMaxX(), headRect.getMaxX());
        double overlapWidth = overlapRight - overlapLeft;

        // 如果X轴重叠范围较小，说明碰撞发生在左右边缘
        return overlapWidth < Math.min(kidRect.getWidth(), headRect.getWidth()) * 0.3;
    }

    // 游戏结束触发方法
    private void triggerGameOver() {
        // 更新游戏结束状态
        isGameOver = true;
        if (gameOverCallback != null) {
            gameOverCallback.gameOver();
        }
    }
}
