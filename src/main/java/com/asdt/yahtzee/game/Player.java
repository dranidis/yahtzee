package com.asdt.yahtzee.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.asdt.yahtzee.game.score.ScoreFactory;
import com.asdt.yahtzee.game.score.ScoreStrategy;
import com.asdt.yahtzee.game.util.SumBuilder;

public class Player {

    private String name;
    private Die[] dice = new Die[5];
    private boolean[] kept = new boolean[5];
    private int roll = 1;
    Map<String, Integer> scored;
    private int score = 0;

    public Player(String name) {
        this.name = name;
        for (int d = 0; d < dice.length; d++) {
            dice[d] = new Die(6);
        }
        scored = ScoreFactory.getInstance().getScoringSheet();
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
        // already scored category
        if (scored.get(categoryName) != null)
            return -1;

        // bonus categories are not selected for scoring by user
        if (categoryName.equals("UB") || categoryName.equals("YB"))
            return -3;

        ScoreStrategy ss = ScoreFactory.getInstance().getScoreStrategy(categoryName);
        // unimplemented strategy, invalid category name
        if (ss == null)
            return -2;

        roll = 1;
        for (int d = 0; d < kept.length; d++)
            kept[d] = false;

        int s = ss.calculate(new ArrayList<Die>(Arrays.asList(dice)));
        scored.put(categoryName, s);
        return s;
    }

    public int getScore() {
        SumBuilder sb = new SumBuilder();
        sb.add(scored.get("1s")).add(scored.get("2s")).add(scored.get("3s")).add(scored.get("4s")).add(scored.get("5s"))
                .add(scored.get("6s"));

        if (sb.getSum() >= 63) {
            scored.put("UB", 35);
            sb.add(35);
        }

        sb.add(scored.get("3k")).add(scored.get("4k")).add(scored.get("s4")).add(scored.get("s5")).add(scored.get("fh"))
                .add(scored.get("5k")).add(scored.get("ch"));

        return sb.getSum();
    }
}
