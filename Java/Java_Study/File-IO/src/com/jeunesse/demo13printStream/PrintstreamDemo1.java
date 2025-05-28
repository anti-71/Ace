package com.jeunesse.demo13printStream;

import java.io.PrintStream;

public class PrintstreamDemo1 {
    public static void main(String[] args) {
        // 目标：打印流的使用
        try (
                PrintStream  ps = new PrintStream("File-IO\\src\\ps.txt");
                ) {
            ps.println(97);
            ps.println('a');
            ps.println(true);
            ps.println(99.99);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
