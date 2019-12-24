package com.asdt.yahtzee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.asdt.yahtzee.game.Die;

public class Game {

    private List<Die> dice;
    private List<Die> kept;
    private int roll = 1;
    private String[] players;

    public Game() {
        players = new String[] {"Dimitris", "Andreas"};
    }

    public void rollKeeping(String playerName, int... keep) {
        if (roll == 1) {
            if (keep.length > 0) {
                throw new RuntimeException("Cannot keep dice at first roll");
            }
            dice = new ArrayList<>();
            kept = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                dice.add(new Die());
            }
        }
        for (int k : keep) {
            if (k < 1 || k > dice.size()) {
                throw new RuntimeException("Invalid die index");
            }
            Die die = dice.get(k - 1);
            kept.add(die);
        }
        for (Die k : kept) {
            dice.remove(k);
        }
        for (Die die : dice) {
            die.reroll();
        }
        if (roll == 3) {
            keepAll(playerName);
        }
        roll++;
    }

    public int[] getDice() {
        int[] ds = new int[dice.size()];
        for (int i = 0; i < dice.size(); i++) {
            ds[i] = dice.get(i).getNumber();
        }
        return ds;
    }

    public int[] getKept() {
        int[] ds = new int[kept.size()];
        for (int i = 0; i < kept.size(); i++) {
            ds[i] = kept.get(i).getNumber();
        }
        return ds;
    }

    @Override
    public String toString() {
        return "Dice: " + dice.toString() + " - Kept: " + kept.toString();
    }

    public void play(String name) {
        Scanner s = new Scanner(System.in);

        roll = 1;
        rollKeeping(name);

        System.out.println(name + "'s turn");
        System.out.println(this);

        for (int r = 0; r < 2; r++) {
            System.out.println(name + " Pick dice to keep:");
            List<Integer> list = new ArrayList<>();
            int keep;
            do {
                keep = s.nextInt();
                if (keep > 0) {
                    list.add(keep);
                }
            } while (keep != 0);
            int[] array = list.stream().mapToInt(i -> i).toArray();

            rollKeeping(name, array);

            System.out.println(this);
        }
        // s.close();
    }

    public void round() {
        for(String player: players)
            play(player);
    }

    public void keepAll(String name) {
        for (Die d : dice) {
            kept.add(d);
        }
        dice.removeAll(kept);

    }
}
