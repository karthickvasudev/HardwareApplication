package com.application.hardwarapplication.utils;

public class Utils {

    public static String incrementAndGet(String number) {
        String prefix = number.split("-")[0];
        String num = number.split("-")[1];
        String incNum = String.format("%05d", (Integer.parseInt(num) + 1));
        return prefix + "-" + incNum;
    }
}
