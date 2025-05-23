package com.jeunesse.demo2threadapi;

public class ThreadApiDemo2 {
    public static void main(String[] args) {
        // 目标：搞清楚Thread类的Sleep方法（线程休眠）
        for (int i = 0; i <= 10; i++) {
            System.out.println(i);
            try {
                //让当前执行的线程进入休眠状态，直到时间到了，才会继续执行
                Thread.sleep(1000); // 1000md=1s
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
