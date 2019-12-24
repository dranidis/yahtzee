package com.asdt.yahtzee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.asdt.yahtzee.game.Game;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void oneRoll()
    {
        Game game = new Game();

        game.rollKeeping("p1");
        int [] dice1 = game.getDice();
        int [] kept1 = game.getKept();

        assertEquals("5 numbers are rolled", 5, dice1.length);
        for(int i: dice1) {
            assertTrue("Each dice is 1-6", i >=1 && i <=6);
        }
        assertEquals("0 numbers are kept", 0, kept1.length);
    }

    @Test
    public void oneRoll2()
    {
        Game game = new Game();

        game.rollKeeping("p1");
        int [] dice1 = game.getDice();

        game.rollKeeping("p1", 1, 2);
        int [] dice2 = game.getDice();
        int [] kept2 = game.getKept();

        assertEquals("3 numbers are rolled", 3, dice2.length);
        for(int i: dice2) {
            assertTrue("Each dice is 1-6", i >=1 && i <=6);
        }
        assertEquals("2 numbers are kept", 2, kept2.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept2[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept2[1]);


        game.keepAll("p1");

        int [] dice3 = game.getDice();
        int [] kept3 = game.getKept();
        assertEquals("5 numbers are kept", 5, kept3.length);
        assertEquals("0 numbers are rolled", 0, dice3.length);
    }

    @Test
    public void oneRoll3()
    {
        Game game = new Game();

        game.rollKeeping("p1");

        int [] dice1 = game.getDice();

        game.rollKeeping("p1", 1, 2);
        int [] dice2 = game.getDice();
        int [] kept2 = game.getKept();

        assertEquals("3 numbers are rolled", 3, dice2.length);
        for(int i: dice2) {
            assertTrue("Each dice is 1-6", i >=1 && i <=6);
        }
        assertEquals("2 numbers are kept", 2, kept2.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept2[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept2[1]);

        // final reroll
        game.rollKeeping("p1", 3);
        int [] dice3 = game.getDice();
        int [] kept3 = game.getKept();

        assertEquals("0 numbers can be rolled", 0, dice3.length);
        assertEquals("5 numbers are kept", 5, kept3.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept3[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept3[1]);
        assertEquals("Kept dice 2 is same", dice2[2], kept3[2]);
    }

    @Test
    public void oneRollEnd()
    {
        Game game = new Game();

        game.rollKeeping("p1");
        int [] dice1 = game.getDice();
        int [] kept1 = game.getKept();

        assertEquals("5 numbers are rolled", 5, dice1.length);
        for(int i: dice1) {
            assertTrue("Each dice is 1-6", i >=1 && i <=6);
        }
        assertEquals("0 numbers are kept", 0, kept1.length);

        game.keepAll("p1");

        int [] dice2 = game.getDice();
        int [] kept2 = game.getKept();
        assertEquals("0 numbers can be rolled", 0, dice2.length);
        assertEquals("5 numbers are kept", 5, kept2.length);
    }



}
