package com.ldlood.utils;

/**
 * Created by Ldlood on 2017/8/1.
 */
public class MathUtil {
    private static final Double Money_Range = 0.01;

    public static Boolean equals(Double num1, Double num2) {
        Double result = Math.abs(num1 - num2);
        if (result < Money_Range) {
            return true;
        }
        return false;

    }
}
