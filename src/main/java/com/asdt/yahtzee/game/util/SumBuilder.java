package com.asdt.yahtzee.game.util;

public class SumBuilder {
    int sum = 0;
    public SumBuilder add(Integer i) {
        if (i != null) {
            sum += i.intValue();
        }
        return this;
    }
    public int getSum() {
        return sum;
    }
}
