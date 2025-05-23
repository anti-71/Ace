package com.jeunesse.demo1create;

public class ThreadDemo1 {
    public static void main(String[] args) {
        // 目标：认识多线程，掌握创建线程的方式一：继承Thread类来实现
        // 4、创建线程类的对象：代表线程
        Thread t1 = new MyThread();
        // 5、调用start方法，启动线程。还是调用run方法执行的
        t1.start();// 启动线程,让线程执行run方法
    }
}

// 1、定义一个子类继承Thread类，成为一个线程类
class MyThread extends Thread {
    // 2.重写Thread类的run方法
    @Override
    public void run() {
        // 3、在run方法中编写线程的任务代码（线程要干的活儿）
        for (int i = 0; i < 5; i++) {
            System.out.println("子线程输出：" + i);
        }
    }
}
