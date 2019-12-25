package com.asdt.yahtzee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.asdt.yahtzee.game.Game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.addPlayer("p1");
        game.addPlayer("p2");
        game.startRound();
    }

    @Test
    public void oneRoll() {
        game.rollKeeping("p1");
        int[] dice1 = game.getDice();
        int[] kept1 = game.getKept();

        assertEquals("5 numbers are rolled", 5, dice1.length);
        for (int i : dice1) {
            assertTrue("Each dice is 1-6", i >= 1 && i <= 6);
        }
        assertEquals("0 numbers are kept", 0, kept1.length);
    }

    @Test
    public void oneRoll2() {
        game.rollKeeping("p1");
        int[] dice1 = game.getDice();

        game.rollKeeping("p1", 1, 2);
        int[] dice2 = game.getDice();
        int[] kept2 = game.getKept();

        assertEquals("3 numbers are rolled", 3, dice2.length);
        for (int i : dice2) {
            assertTrue("Each dice is 1-6", i >= 1 && i <= 6);
        }
        assertEquals("2 numbers are kept", 2, kept2.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept2[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept2[1]);

        game.keepAll("p1");

        int[] dice3 = game.getDice();
        int[] kept3 = game.getKept();
        assertEquals("5 numbers are kept", 5, kept3.length);
        assertEquals("0 numbers are rolled", 0, dice3.length);
    }

    @Test
    public void oneRoll3() {
        game.rollKeeping("p1");

        int[] dice1 = game.getDice();

        game.rollKeeping("p1", 1, 2);
        int[] dice2 = game.getDice();
        int[] kept2 = game.getKept();

        assertEquals("3 numbers are rolled", 3, dice2.length);
        for (int i : dice2) {
            assertTrue("Each dice is 1-6", i >= 1 && i <= 6);
        }
        assertEquals("2 numbers are kept", 2, kept2.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept2[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept2[1]);

        // final reroll
        game.rollKeeping("p1", 3);
        int[] dice3 = game.getDice();
        int[] kept3 = game.getKept();

        assertEquals("0 numbers can be rolled", 0, dice3.length);
        assertEquals("5 numbers are kept", 5, kept3.length);
        assertEquals("Kept dice 1 is same", dice1[0], kept3[0]);
        assertEquals("Kept dice 2 is same", dice1[1], kept3[1]);
        assertEquals("Kept dice 2 is same", dice2[2], kept3[2]);
    }

    @Test
    public void oneRollEnd() {
        game.rollKeeping("p1");
        int[] dice1 = game.getDice();
        int[] kept1 = game.getKept();

        assertEquals("5 numbers are rolled", 5, dice1.length);
        for (int i : dice1) {
            assertTrue("Each dice is 1-6", i >= 1 && i <= 6);
        }
        assertEquals("0 numbers are kept", 0, kept1.length);

        game.keepAll("p1");

        int[] dice2 = game.getDice();
        int[] kept2 = game.getKept();
        assertEquals("0 numbers can be rolled", 0, dice2.length);
        assertEquals("5 numbers are kept", 5, kept2.length);
    }


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void notYourTurn() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Not your turn");
        game.rollKeeping("p2");
    }


    @Test
    public void scoredCategory() {
        game.getNextPlayer();

        game.rollKeeping("p1");
        game.keepAll("p1");
        int score1 = game.scoreACategory("p1", "1s");
        assertTrue("Category 1 not used by p1 positive score", score1 >= 0);

        /* scores */
        int totalScore1 = game.getScore("p1");
        assertEquals("Total score of p1 is first score", score1, totalScore1);
        int totalScore2 = game.getScore("p2");
        assertEquals("Total score of p2 (before playing) 0", 0, totalScore2);


        game.getNextPlayer();

        game.rollKeeping("p2");
        game.keepAll("p2");

        int score2 = game.scoreACategory("p2", "1s");
        assertTrue("Category 1 not used by p2 positive score", score2 >= 0);

        /* scores */
        totalScore1 = game.getScore("p1");
        assertEquals("Total score of p1 is first score", score1, totalScore1);
        totalScore2 = game.getScore("p2");
        assertEquals("Total score of p2 (after playing) score2", score2, totalScore2);


        game.startRound();

        game.rollKeeping("p1");
        game.keepAll("p1");
        int score11 = game.scoreACategory("p1", "2s");
        assertTrue("Category 2s not used by p1 positive score", score11 >= 0);

        /* scores */
        totalScore1 = game.getScore("p1");
        assertEquals("Total score of p1 is first and secod score", score1 + score11, totalScore1);
        totalScore2 = game.getScore("p2");
        assertEquals("Total score of p2 (after playing) score2", score2, totalScore2);

    }

    @Test
    public void alreadyScoredCategory() {
        game.getNextPlayer();

        game.rollKeeping("p1");
        game.keepAll("p1");
        int score = game.scoreACategory("p1", "1s");
        assertTrue("Category 1 not used by p1 positive score", score >= 0);

        System.out.println(game.getCurrentPlayersName());
        game.getNextPlayer();
        System.out.println(game.getCurrentPlayersName());

        game.rollKeeping("p2");
        game.keepAll("p2");
        score = game.scoreACategory("p2", "1s");
        assertTrue("Category 1 not used by p2 positive score", score >= 0);

        game.startRound();

        game.rollKeeping("p1");
        game.keepAll("p1");
        score = game.scoreACategory("p1", "1s");
        assertEquals("Category 1 already used by p1", -1, score);
    }


}
