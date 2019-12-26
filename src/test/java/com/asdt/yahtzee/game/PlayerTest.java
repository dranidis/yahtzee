package com.asdt.yahtzee.game;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    @Test
    public void getScore0() {
        Player p = new Player("p1");
        assertEquals("Empty score is 0", 0, p.getScore());
    }

    @Mock
    Map<String, Integer> scored;

    @InjectMocks
    Player p = new Player("p");

    @Test
    public void bonusUpper() {
        Mockito.when(scored.get("1s")).thenReturn(3);
        Mockito.when(scored.get("2s")).thenReturn(6);
        Mockito.when(scored.get("3s")).thenReturn(9);
        Mockito.when(scored.get("4s")).thenReturn(12);
        Mockito.when(scored.get("5s")).thenReturn(15);
        Mockito.when(scored.get("6s")).thenReturn(18);

        assertEquals("Get bonus 35 when at least 63", 63+35, p.getScore());

        Mockito.when(scored.get("3k")).thenReturn(10);
        Mockito.when(scored.get("4k")).thenReturn(20);
        Mockito.when(scored.get("fh")).thenReturn(25);
        Mockito.when(scored.get("s4")).thenReturn(30);
        Mockito.when(scored.get("s5")).thenReturn(40);
        Mockito.when(scored.get("5k")).thenReturn(50);
        Mockito.when(scored.get("ch")).thenReturn(10);

        assertEquals("Total ", 63 + 35 + 185, p.getScore());    }

    @Test
    public void noBonusUpper() {
        Mockito.when(scored.get("1s")).thenReturn(2);
        Mockito.when(scored.get("2s")).thenReturn(6);
        Mockito.when(scored.get("3s")).thenReturn(9);
        Mockito.when(scored.get("4s")).thenReturn(12);
        Mockito.when(scored.get("5s")).thenReturn(15);
        Mockito.when(scored.get("6s")).thenReturn(18);

        assertEquals("No bonus 35 when 62", 62, p.getScore());

        Mockito.when(scored.get("3k")).thenReturn(10);
        Mockito.when(scored.get("4k")).thenReturn(20);
        Mockito.when(scored.get("fh")).thenReturn(25);
        Mockito.when(scored.get("s4")).thenReturn(30);
        Mockito.when(scored.get("s5")).thenReturn(40);
        Mockito.when(scored.get("5k")).thenReturn(50);
        Mockito.when(scored.get("ch")).thenReturn(10);

        assertEquals("Total ", 62 + 185, p.getScore());
    }

    @Test
    public void yahtzeeBonus() {
        Map<String, Integer> mockScored = new HashMap<>();
        mockScored.put("1s", 2);
        mockScored.put("2s", 6);
        mockScored.put("3s", 9);
        mockScored.put("4s", 12);
        mockScored.put("5s", 15);
        mockScored.put("6s", 18);
        mockScored.put("s4", 30);
        mockScored.put("s5", 40);
        mockScored.put("5k", 50);
        mockScored.put("ch", 10);

        Player p = new Player("p");
        p.setScored(mockScored);
        p.setDice(new Die[] {new Die(2), new Die(2), new Die(2), new Die(2), new Die(2)});

        assertEquals("score fh as joker", 25, p.score("fh"));
        assertEquals("bonus yahtzee", 100, p.scored.get("YB").intValue());
        assertEquals("Total ", 62 + 130 + 100 + 25, p.getScore());
    }

    @Test
    public void yahtzeeBonusOnlyInUpper() {
        Map<String, Integer> mockScored = new HashMap<>();
        mockScored.put("1s", 2);

        mockScored.put("3s", 9);
        mockScored.put("4s", 12);
        mockScored.put("5s", 15);
        mockScored.put("6s", 18);
        mockScored.put("s4", 30);
        mockScored.put("s5", 40);
        mockScored.put("5k", 50);
        mockScored.put("ch", 10);

        Player p = new Player("p");
        p.setScored(mockScored);
        p.setDice(new Die[] {new Die(2), new Die(2), new Die(2), new Die(2), new Die(2)});

        assertEquals("score fh as joker not possible", -5, p.score("fh"));

        assertEquals("score 2s as joker is possible", 10, p.score("2s"));
        assertEquals("bonus yahtzee", 100, p.scored.get("YB").intValue());
        assertEquals("Total (bonus)", 56 + 130 + 100 + 10 + 35, p.getScore());
    }

    @Test
    public void secondYahtzeeBonus() {
        Map<String, Integer> mockScored = new HashMap<>();
        mockScored.put("1s", 2);

        mockScored.put("3s", 9);
        mockScored.put("4s", 12);
        mockScored.put("5s", 15);
        mockScored.put("6s", 18);
        mockScored.put("s4", 30);
        mockScored.put("s5", 40);
        mockScored.put("5k", 50);
        mockScored.put("ch", 10);

        Player p = new Player("p");
        p.setScored(mockScored);
        p.setDice(new Die[] {new Die(2), new Die(2), new Die(2), new Die(2), new Die(2)});

        assertEquals("score 2s as joker is possible", 10, p.score("2s"));
        assertEquals("score fh as joker is possible", 25, p.score("fh"));
        assertEquals("bonus yahtzee", 200, p.scored.get("YB").intValue());
        assertEquals("Total 56+10 upper plus 35 bonus, 130+25 plus 200 YBonus", 56 + 130 + 200 + 10 + 35 + 25, p.getScore());
    }
}