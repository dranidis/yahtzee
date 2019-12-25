package com.asdt.yahtzee.game.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SumBuilderTest {

    @Test
    public void getSum() {
        SumBuilder sb = new SumBuilder();
        assertEquals("Get is 0", 0, sb.getSum());
    }

    @Test
    public void nulllIszero() {
        SumBuilder sb = new SumBuilder();
        assertEquals("Adding null is 0", 0, sb.add(null).getSum());
    }

    @Test
    public void nulllPlus1() {
        SumBuilder sb = new SumBuilder();
        assertEquals("Adding null and 1 is 1", 1, sb.add(null).add(1).getSum());
    }

    @Test
    public void nulllPlus1Plus2() {
        SumBuilder sb = new SumBuilder();
        assertEquals("Adding null and 1 and 2 is 3", 3, sb.add(null).add(1).add(2).getSum());
    }

    @Test
    public void plus1Plus2() {
        SumBuilder sb = new SumBuilder();
        assertEquals("Adding 1 and 2 is 3", 3, sb.add(1).add(2).getSum());
    }

}
