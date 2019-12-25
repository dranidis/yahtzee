package com.asdt.yahtzee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.asdt.yahtzee.game.Game;

public class UI {
    Game game;
    Scanner s;

    public UI(Game game) {
        this.game = game;
        game.addPlayer("Dimitris");
        // game.addPlayer("Andreas");
        s = new Scanner(System.in);
    }

    public void round() {
        game.startRound();
        String player = game.getNextPlayer();
        while (player != null) {
            play(player);
            player = game.getNextPlayer();
        }
        // s.close();
    }

    public void play(String name) {

        game.rollKeeping(name);

        System.out.println(name + "'s turn");
        System.out.println(game);

        for (int r = 0; r < 2; r++) {
            System.out.println(name + " Pick dice to keep: (0) to end choice, (-1) to keep all");
            List<Integer> list = new ArrayList<>();
            int keep;
            do {
                keep = s.nextInt();
                if (keep > 0) {
                    list.add(keep);
                }
            } while (keep > 0);
            if (keep == -1) {
                game.keepAll(name);
                System.out.println(game);
                break;
            }
            int[] array = list.stream().mapToInt(i -> i).toArray();
            game.rollKeeping(name, array);
            System.out.println(game);
        }
        System.out.println("Enter a scoring category: ");
        String categoryName = s.next();
        int score = game.scoreACategory(name, categoryName);
        while (score < 0) {
            System.out.println("Invalid choice!");
            categoryName = s.next();
            score = game.scoreACategory(name, categoryName);
        }
        System.out.println("Score: " + score);
        System.out.println("Total Score: " + game.getScore(name));
    }

}
