package com.asdt.yahtzee.game;

import static org.junit.Assert.assertEquals;

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
}
