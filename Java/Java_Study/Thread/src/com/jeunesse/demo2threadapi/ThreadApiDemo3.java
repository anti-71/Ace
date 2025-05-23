package com.jeunesse.demo2threadapi;

public class ThreadApiDemo3 {
    public static void main(String[] args) {
        // 目标：搞清楚线程的join方法：线程插队：让调用这个方法线程先执行完毕
        MyThread2 t1 = new MyThread2();
        t1.start();

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "主线程输出：" + i);
            if (i == 1) {
                try {
                    t1.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MyThread2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "子线程输出：" + i);
        }
    }
}