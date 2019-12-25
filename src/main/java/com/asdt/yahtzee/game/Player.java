package com.asdt.yahtzee.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asdt.yahtzee.game.score.ScoreFactory;
import com.asdt.yahtzee.game.score.ScoreStrategy;

public class Player {

    private String name;
    private Die[] dice = new Die[5];
    private boolean[] kept = new boolean[5];
    private int roll = 1;
    private List<String> scored;
    private int score = 0;

    public Player(String name) {
        this.name = name;
        for (int d = 0; d < dice.length; d++) {
            dice[d] = new Die(6);
        }
        scored = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void rollKeeping(boolean[] keep) {
        if (roll > 3) {
            throw new RuntimeException("You cannot roll anymore");
        }
        if (roll != 1) { // ignore if first roll
            for (int d = 0; d < dice.length; d++) {
                kept[d] = keep[d];
            }
        }
        for (int d = 0; d < dice.length; d++) {
            if (!kept[d])
                dice[d].reroll();
        }
        roll++;
    }

    public Die[] getDice() {
        return dice;
    }

    public boolean[] getKept() {
        return kept;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\n");
        sb.append("Already scored: " + scored + "\n");
        sb.append("Total: " + score + "\n");
        sb.append("Dice: " + new ArrayList<Die>(Arrays.asList(dice)) + "\nKept: " + keptToString());
        return sb.toString();
    }

    private String keptToString() {
        String s = " ";
        for (int i = 0; i < 5; i++) {
            if (kept[i]) {
                s += "K  ";
            } else {
                s += "-  ";
            }
        }
        return s;
    }

    public int score(String categoryName) {
        if (scored.contains(categoryName))
            return -1;

        ScoreStrategy ss = ScoreFactory.getInstance().getScoreStrategy(categoryName);
        if (ss == null)
            return -2;

        roll = 1;
        for (int d = 0; d < kept.length; d++)
            kept[d] = false;

        scored.add(categoryName);
        int s = ss.calculate(new ArrayList<Die>(Arrays.asList(dice)));
        score += s;
        return s;
    }

    public int getScore() {
        return score;
    }
}
