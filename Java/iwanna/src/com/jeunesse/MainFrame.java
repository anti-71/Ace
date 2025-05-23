package com.jeunesse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

// 游戏的主要界面
public class MainFrame extends JFrame implements GameOverListener{
    // 设置图片资源位置
    private static final String imagePath = "D:/Ace/Java/iwanna/src/image/";
    // 全局控制游戏是否能够胜利
    private boolean isGameWin = false;
    private Kid kid;
    private final List<Platform> platforms = new ArrayList<>(); // 平台列表
    private final List<Thorn> thorns = new ArrayList<>(); // 刺列表
    private final List<TriggerArea> triggerAreas = new ArrayList<>(); // 触发区域列表
    private final RotatingHead rotatingHead = new RotatingHead(imagePath, 29 * 32, 35 * 32);
    private final JLabel stag_apple = new JLabel(new ImageIcon(imagePath + "apple.png"));
    private final PushedApple pushedApple = new PushedApple(imagePath, platforms, triggerAreas);
    private final JLabel pressure_plate = new JLabel(new ImageIcon(imagePath + "pressure.png"));

    public MainFrame() {
        // 初始化窗口信息
        initFrame();
        // 初始化界面
        initImage();
        // 给当前窗口绑定键盘事件
        initKeyHandledEvent();

        // 显示窗口
        this.setVisible(true);
    }

    // 绑定键盘事件，控制kid的左右和跳
    private void initKeyHandledEvent() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        kid.setLeft(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        kid.setRight(true);
                        break;
                    case KeyEvent.VK_SPACE:
                        kid.jump();
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        kid.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        kid.setRight(false);
                        break;
                }
            }
        });
    }

    private void initImage() {
        // 放置角色
        kid = new Kid(imagePath,  platforms ,thorns ,triggerAreas,rotatingHead,stag_apple,pushedApple,pressure_plate);
        kid.setGameOverCallback(this); // 设置回调
        kid.setBounds(32, 13 * 32, 32, 32);
        this.add(kid);

        // 启动kid移动线程
        KidMovementThread movementThread = new KidMovementThread(kid);
        movementThread.start();
        // 窗口关闭时停止线程
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                movementThread.stopMovement();
            }
        });

        // 放置传送门
        JLabel real_door = new JLabel(new ImageIcon(imagePath + "door.png"));
        real_door.setBounds(32, 4 * 32, 32, 32);
        real_door.setVisible(false);
        this.add(real_door);
        JLabel fake_door = new JLabel(new ImageIcon(imagePath + "door.png"));
        fake_door.setBounds(5 * 32, 4 * 32, 32, 32);
        fake_door.setVisible(true);
        this.add(fake_door);

        // 放置平台
        for (int i = 0; i < 5; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(i * 32, 14 *32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }
        for (int i = 0; i < 5; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(8 * 32 + i * 32, 11 * 32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }
        for (int i = 0; i < 10; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(15 * 32 + i * 32, 14 * 32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }
        for (int i = 0; i < 12; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(14 * 32 + i * 32, 17 * 32, 32, 32);
            pl.setVisible(false);
            this.add(pl);
            platforms.add(pl);
        }
        for (int i = 0; i < 8; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(28 * 32 + i * 32, 11 * 32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }
        Platform pl1 = new Platform(imagePath);
        pl1.setBounds(26 * 32, 8 * 32, 32, 32);
        this.add(pl1);
        platforms.add(pl1);
        Platform pl2 = new Platform(imagePath);
        pl2.setBounds(23 * 32, 7 * 32, 32, 32);
        this.add(pl2);
        platforms.add(pl2);
        for (int i = 0; i < 8; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(22 * 32 - i * 32, 5 * 32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }
        for (int i = 0; i < 11; i++) {
            Platform pl = new Platform(imagePath);
            pl.setBounds(10 * 32 - i * 32, 5 * 32, 32, 32);
            this.add(pl);
            platforms.add(pl);
        }

        // 放置刺
        Thorn thorn1 = new Thorn(imagePath + "thornup.png");
        thorn1.setBounds(15 * 32, 14 * 32, 32, 32);
        thorn1.setVisible(false);
        this.add(thorn1);
        thorns.add(thorn1);
        for (int i = 0; i < 3; i++) {
            Thorn thorn = new Thorn(imagePath + "thornup.png");
            thorn.setBounds(19 * 32 + i * 32, 13 * 32, 32, 32);
            this.add(thorn);
            thorns.add(thorn);
        }
        Thorn thorn2 = new Thorn(imagePath + "thornleft.png");
        thorn2.setBounds(24 * 32, 16 * 32, 32, 32);
        thorn2.setVisible(false);
        this.add(thorn2);
        thorns.add(thorn2);
        Thorn thorn3 = new Thorn(imagePath + "thornright.png");
        thorn3.setBounds(0, 4 * 32, 32, 32);
        thorn3.setVisible(false);
        this.add(thorn3);
        thorns.add(thorn3);

        // 放置一个静止的苹果
        stag_apple.setBounds(16 * 32 + 32, 5 * 32 - 168 + 32, 21, 24);
        this.add(stag_apple);
        // 放置一个可被推动的苹果
        pushedApple.setBounds(16 * 32 + 32, 5 * 32 - 168 + 32, 21, 24);
        this.add(pushedApple);
        // 放置压力板
        pressure_plate.setBounds(23 * 32, 7 * 32 - 8, 32, 8);
        pressure_plate.setVisible(false);
        this.add(pressure_plate);

        // 放置旋转的头
        rotatingHead.setBounds(29 * 32, 10 * 32, 32, 32);
        this.add(rotatingHead);

        // 设置触发区域1：刺1
        TriggerArea area1 = new TriggerArea(
                new Rectangle(15 * 32,7 * 32,32,8 * 32),
                () -> {
                    thorn1.setVisible(true);
                    new Thread(() -> {
                        int moveSpeed = 10; // 移动速度
                        int maxY = -1 * 32; // 最大移动到的Y坐标
                        while (thorn1.getY() > maxY) {
                            try {
                                SwingUtilities.invokeLater(() -> {
                                    thorn1.setLocation(thorn1.getX(), thorn1.getY() - moveSpeed);
                                });
                                Thread.sleep(16);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
        );
        // 设置触发区域2：刺2
        TriggerArea area2 = new TriggerArea(
                new Rectangle(8 * 32,15 * 32,7 * 32,32),
                () -> {
                    thorn2.setVisible(true);
                    new Thread(() -> {
                        int moveSpeed = 10; // 移动速度
                        int maxX = -1 * 32; // 最小移动到的X坐标
                        while (thorn2.getX() > maxX) {
                            try {
                                SwingUtilities.invokeLater(() -> {
                                    thorn2.setLocation(thorn2.getX() - moveSpeed, thorn2.getY());
                                });
                                Thread.sleep(16);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
        );
        // 设置触发区域3：刺3
        TriggerArea area3 = new TriggerArea(
                new Rectangle(6 * 32, 4 * 32, 5 * 32,  32),
                () -> {
                    thorn3.setVisible(true);
                    new Thread(() -> {
                        int moveSpeed = 10; // 移动速度
                        int maxX = 39 * 32; // 最大移动到的X坐标
                        while (thorn3.getX() < maxX) {
                            try {
                                SwingUtilities.invokeLater(() -> {
                                    thorn3.setLocation(thorn3.getX() + moveSpeed, thorn3.getY());
                                });
                                Thread.sleep(16);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
        );
        // 设置触发区域4：落地杀
        TriggerArea area4 = new TriggerArea(
                new Rectangle(23 * 32, 7 * 32 - 8, 32, 8),
                () -> {
                    // 跳到平台上即死
                    movementThread.stopMovement();
                    gameOver();
                }
        );
        // 设置触发区域5：假传送门
        TriggerArea area5 = new TriggerArea(
                new Rectangle(5 * 32, 4 * 32, 32, 32),
                () -> {
                    // 碰到假传送门，游戏结束
                    movementThread.stopMovement();
                    pressure_plate.setVisible(false);
                    gameOver();
                }
        );
        // 触发事件6：苹果触发压力板
        TriggerArea area6 = new TriggerArea(
                new Rectangle(23 * 32, 7 * 32 - 8, 32, 8),
                () -> {
                    pressure_plate.setVisible(false);
                    isGameWin = true;
                    real_door.setVisible(true);
                }
        );
        // 触发事件7：游戏胜利
        TriggerArea area7 = new TriggerArea(
                new Rectangle(32, 4 * 32, 32, 32),
                () -> {
                    if (isGameWin) {
                        movementThread.stopMovement();
                        // 显示游戏胜利
                        JOptionPane.showMessageDialog(this, "游戏胜利！");
                    }
                }
        );
        triggerAreas.add(area1);
        triggerAreas.add(area2);
        triggerAreas.add(area3);
        triggerAreas.add(area4);
        triggerAreas.add(area5);
        triggerAreas.add(area6);
        triggerAreas.add(area7);

        // 放置苹果树
        JLabel appleTree = new JLabel(new ImageIcon(imagePath + "tree.png"));
        appleTree.setBounds(16 * 32, 5 * 32 - 168, 111, 168);
        this.add(appleTree);

        // 设置背景图片
        JLabel bgLabel = new JLabel(new ImageIcon(imagePath + "background.png"));
        bgLabel.setBounds(0, 0, 1198, 674);
        this.add(bgLabel);
    }

    private void initFrame() {
        this.setTitle("iwanna V 1.0 jeunesse");
        this.setSize(1198, 674);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
    }

    // 监听游戏结束
    @Override
    public void gameOver() {
        // 显示游戏结束画面
        JOptionPane.showMessageDialog(this, "游戏结束！");
        System.exit(0);
    }
}
