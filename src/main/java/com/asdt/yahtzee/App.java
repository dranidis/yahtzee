package com.asdt.yahtzee;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Yahtzee!");
        Game game = new Game();
        UI ui = new UI(game);

        ui.round();
    }
}
