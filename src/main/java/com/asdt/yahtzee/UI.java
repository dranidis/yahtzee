package com.asdt.yahtzee;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.asdt.yahtzee.game.Game;
import com.asdt.yahtzee.game.players.ConsolePlayer;
import com.asdt.yahtzee.game.players.GamePlayer;
import com.asdt.yahtzee.game.players.RamdomBot;

public class UI {
    Game game;
    Scanner s;
    private Map<String, GamePlayer> gamePlayers;

    public UI(Game game) {
        this.game = game;
        s = new Scanner(System.in);

        gamePlayers = new HashMap<>();

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
        System.out.println("Enter player names or '--' to end. Name starting with 'r' are names for random bots.");

        String name = "";
        while (!name.equals("--")) {
            name = s.next();
            if (!name.equals("--")) {
                if (name.startsWith("r")) {
                    game.addPlayer(name);
                    gamePlayers.put(name, new RamdomBot(game, name));
                } else {
                    game.addPlayer(name);
                    gamePlayers.put(name, new ConsolePlayer());
                }
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

            // interact with the player
            int[] array = gamePlayers.get(name).rollKeeping();

            if (array.length == 0 || array[0] == -1) {
                System.out.println("Keeping all");
                System.out.println(game);
                break;
            }

            if (r == 0)
                System.out.println("**** 2nd ROLL ****");
            else
                System.out.println("**** 3rd ROLL ****");
            game.rollKeeping(name, array);
            System.out.println(game);
        }
        System.out.println("Enter an available scoring category xx: ");

        // interact with the player
        String categoryName = gamePlayers.get(name).selectCategory();

        int score = game.scoreACategory(name, categoryName);
        while (score < 0) {
            System.out.println("Invalid choice! " + "(score=" + score +")");

            // interact with the player
            categoryName = gamePlayers.get(name).selectCategory();

            score = game.scoreACategory(name, categoryName);
        }
        System.out.println("###### ROUND SCORING ######");
        System.out.println(game);
    }

}
