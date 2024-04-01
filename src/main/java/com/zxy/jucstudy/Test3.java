package com.zxy.jucstudy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 可变类SimpleDateFormat、不可变类 DateTimeFormatter
 */
public class Test3 {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    synchronized (sdf) { // 如果没有synchronized会报错。
                        System.out.println(sdf.parse("2024-04-01"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 推荐使用
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                TemporalAccessor accessor = dtf.parse("2024-04-01");
                System.out.println(accessor);
            }).start();
        }
    }
}
