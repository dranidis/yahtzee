package com.asdt.yahtzee.network;

import java.util.HashMap;
import java.util.Map;

import com.asdt.yahtzee.game.Game;

public class ServerGame {
    private static ServerGame instance = new ServerGame();

    Map<String, Connection> gamePlayers;

    Game game;

    protected ServerGame() {
        game = new Game();
        gamePlayers = new HashMap<>();
    }

    public static ServerGame getInstance() {
        return instance;
    }

    public void addPlayer(String name, Connection connection) {
        System.out.println("User: " + name + " joined a Game");

        game.addPlayer(name);
        gamePlayers.put(name, connection);
    }

    public void start() {
        System.out.println("Starting a game with players: " + gamePlayers.keySet());

        game.startRound();
        game.getNextPlayer();
        broadCast("START");
    }

    public boolean isAccepting() {
        return gamePlayers.size() < 2;
    }

    public boolean isReady() {
        return gamePlayers.size() == 2;
    }

    public void broadCast(Object obj) {
        for(Connection connection: gamePlayers.values()) {
            connection.sendObject(obj);
        }
    }

}
