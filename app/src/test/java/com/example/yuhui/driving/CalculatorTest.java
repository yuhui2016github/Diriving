package com.example.yuhui.driving;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Created by yuhui on 2016-7-4.
 */
public class CalculatorTest {
    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void testSum() throws Exception {
        assertEquals(6, mCalculator.sum(1, 5), 0);
        assertEquals(3, mCalculator.sum(1, 2));
    }

    @Test
    public void testSubstract() throws Exception {
        assertEquals(5, mCalculator.substract(10, 5), 0);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivide() throws Exception {
        mCalculator.divide(10, 0);
        assertEquals(5, mCalculator.divide(10, 2));
    }

    @Test
    public void testMultiply() throws Exception {

    }
}