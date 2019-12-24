package com.asdt.yahtzee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    Game game;
    Scanner s;

    public UI(Game game) {
        this.game = game;
        s = new Scanner(System.in);
    }

    public void round() {
        String player = game.getNextPlayer();
        while (player != null) {
            play(player);
            player = game.getNextPlayer();
        }
        s.close();
    }

    public void play(String name) {

        game.rollKeeping(name);

        System.out.println(name + "'s turn");
        System.out.println(game);

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

            game.rollKeeping(name, array);

            System.out.println(game);
        }
    }

}
