package com.asdt.yahtzee.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private int currentPlayerIndex = 0;
    private Player currentPlayer;
    private Map<String, Player> players = new HashMap<>();
    private ArrayList<Player> roundPlayers;

    public Game() {
    }

    /**
     * Roll the dice. Optionally write which dice to keep from previous roll.
     *
     * @param playerName Player calling
     * @param keep       vararg which dice to keep
     */
    public void rollKeeping(String playerName, int... keep) {
        Player called = players.get(playerName);
        if (called != currentPlayer) {
            throw new RuntimeException(playerName + " Not your turn");
        }
        called.rollKeeping(keep);
    }

    /**
     * Decide to finish your turn (before final roll) by keeping all dice.
     *
     * @param playerName Player calling
     */
    public void keepAll(String playerName) {
        Player called = players.get(playerName);
        if (called != currentPlayer) {
            throw new RuntimeException("Not your turn");
        }
        called.keepAll();
    }

    /**
     * Choose category to score for all kept dice
     *
     * @param playerName
     * @param categoryName
     * @return
     */
    public int scoreACategory(String playerName, String categoryName) {
        Player called = players.get(playerName);
        if (called != currentPlayer) {
            throw new RuntimeException("Not your turn");
        }
        return called.score(categoryName);
    }

    public int[] getDice() {
        return currentPlayer.getDice();
    }

    public int[] getKept() {
        return currentPlayer.getKept();
    }

    @Override
    public String toString() {
        return currentPlayer.toString();
    }

    public String getNextPlayer() {
        if (currentPlayerIndex < roundPlayers.size()) {
            currentPlayer = roundPlayers.get(currentPlayerIndex++);
            return currentPlayer.getName();
        } else
            return null;
    }

    public void addPlayer(String name) {
        players.put(name, new Player(name));
    }

    public void startRound() {
        roundPlayers = new ArrayList<Player>(players.values());
        currentPlayerIndex = 0;
        currentPlayer = roundPlayers.get(currentPlayerIndex);
    }

    public String getCurrentPlayersName() {
        return currentPlayer.getName();
    }

	public int getScore(String name) {
		return players.get(name).getScore();
	}
}
