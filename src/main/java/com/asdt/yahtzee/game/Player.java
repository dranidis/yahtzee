package com.asdt.yahtzee.game;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
    private List<Die> dice;
    private List<Die> kept;
    private int roll = 1;

    public Player(String name) {
        this.name = name;
	}

	public String getName() {
		return name;
	}

    public void rollKeeping(int... keep) {
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
            keepAll();
            return;
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
    public void keepAll() {
        for (Die d : dice) {
            kept.add(d);
        }
        dice.removeAll(kept);
        roll = 1; // for next time
    }

    @Override
    public String toString() {
        return name + " Dice: " + dice.toString() + " - Kept: " + kept.toString();
    }
}
