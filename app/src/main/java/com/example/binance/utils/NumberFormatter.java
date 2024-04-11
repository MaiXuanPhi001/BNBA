package com.example.binance.utils;

import java.text.DecimalFormat;

public class NumberFormatter {
    public static String dotwithcomma(float number) {
        DecimalFormat df = new DecimalFormat("#,###,###.###");
        return df.format(number);
    }
}
