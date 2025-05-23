package com.jeunesse.demo1create;

public class ThreadDemo2_2 {
    public static void main(String[] args) {
        // 目标：掌握多线程的创建方式二：使用Runnable接口的匿名内部类来创建
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("子线程1输出：" + i);
                }
            }
        };
        Thread t1 = new Thread(r);// public Thread(Runnable r)
        t1.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("子线程2输出：" + i);
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("子线程3输出：" + i);
            }
        }).start();
    }
}

// 1、定义一个线程任务类实现Runnable接口
class MyRunnable implements Runnable {
    // 2、重写run方法，设置线程任务
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程输出：" + i);
        }
    }
}
