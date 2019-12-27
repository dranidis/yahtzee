package com.asdt.yahtzee;

import com.asdt.yahtzee.game.Game;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Yahtzee!");
        Game game = new Game();

        int tries = 0;

        if (args.length > 0) {
            try {
                tries = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }

        if (tries == 0)
            new UI(game);
        else
            new SimStat(game, tries);
    }
}
