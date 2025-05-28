package com.jeunesse.demo13printStream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class DataStreamDemo2 {
    public static void main(String[] args) {
        // 目标：特殊数据流的使用
        try (
                DataOutputStream  dos = new DataOutputStream(new FileOutputStream("File-IO/src/data.txt"));
                ) {
            dos.writeByte(34);
            dos.writeUTF("你好");
            dos.writeInt(100);
            dos.writeDouble(3.14);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
