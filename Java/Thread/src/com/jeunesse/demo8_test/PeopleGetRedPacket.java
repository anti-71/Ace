package com.jeunesse.demo8_test;

import java.util.List;

//线程类
public class PeopleGetRedPacket extends Thread {
    private List<Integer> redPacket;

    public PeopleGetRedPacket(List<Integer> redPackets, String name) {
        super(name);
        this.redPacket = redPackets;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (true) {
            if (redPacket.size() == 0) {
                break;
            }
            // 100个人来抢redPacket集合中的钱
            synchronized (redPacket) {
                // 随机一个索引得到红包
                int index = (int) (Math.random() * redPacket.size());
                Integer money = redPacket.remove(index);
                System.out.println(name + "抢到了" + money + "元");
                if (redPacket.size() == 0) {
                    System.out.println("活动结束！");
                }
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
