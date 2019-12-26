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
        s = new Scanner(System.in);

        readPlayers();

        for (int i = 0; i < 13; i++) {
            System.out.println("\n-----ROUND " + (i + 1) + "------");
            round();
        }

        System.out.println("\nFINAL RESULTS\n");
        System.out.println(game);
        s.close();
    }

    private void readPlayers() {
        System.out.println("Enter player names or '--' to end");

        String name = "";
        while (!name.equals("--")) {
            name = s.next();
            if (!name.equals("--")) {
                game.addPlayer(name);
            }
        }
    }

    public void round() {
        game.startRound();
        String player = game.getNextPlayer();
        while (player != null) {
            play(player);
            player = game.getNextPlayer();
        }
    }

    public void play(String name) {
        System.out.println("\n" + name + "'s turn\n");
        System.out.println("**** 1st ROLL ****");
        game.rollKeeping(name);

        System.out.println(game);

        for (int r = 0; r < 2; r++) {
            System.out.println(name + " Write numbers of dice to keep with a 0 at the end, (-1) to keep all");
            List<Integer> list = new ArrayList<>();
            int keep = 7;
            do {
                if (s.hasNextInt()) {
                    keep = s.nextInt();

                    if (keep > 0) {
                        if (keep <= 6)
                            list.add(keep);
                        else
                            System.out.println("1 to 6!");
                    }
                } else {
                    s.next();
                    System.out.println("1 to 6!");
                }
            } while (keep != 0 && keep != -1);
            if (keep == -1) {
                System.out.println(game);
                break;
            }
            int[] array = list.stream().mapToInt(i -> i).toArray();
            if (r == 0)
                System.out.println("**** 2nd ROLL ****");
            else
                System.out.println("**** 3rd ROLL ****");
            game.rollKeeping(name, array);
            System.out.println(game);
        }
        System.out.println("Enter an available scoring category xx: ");
        String categoryName = s.next();
        int score = game.scoreACategory(name, categoryName);
        while (score < 0) {
            System.out.println("Invalid choice!");
            categoryName = s.next();
            score = game.scoreACategory(name, categoryName);
        }
    }

}
