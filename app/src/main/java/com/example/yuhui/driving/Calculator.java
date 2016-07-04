package com.example.yuhui.driving;

/**
 * Created by yuhui on 2016-7-4.
 */
public class Calculator {
    public double sum(double a, double b) {
        return a + b;
    }

    public double substract(double a, double b) {
        return a - b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("b is 0 , this is illegal");
        } else {
            return a / b;
        }
    }

    public double multiply(double a, double b) {
        return a * b;
    }
}
