package com.asdt.yahtzee.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Game {

    private int currentPlayerIndex = 0;
    private Player currentPlayer;
    private Map<String, Player> players = new HashMap<>();
    private ArrayList<Player> roundPlayers;

    /**
     * Roll the dice. Optionally, provide any dice index to keep from previous roll.
     *
     * @param playerName Player calling
     * @param keep       vararg which dice indices to keep
     */
    public void rollKeeping(String playerName, int... keep) {
        Player called = players.get(playerName);
        if (called != currentPlayer) {
            throw new RuntimeException(playerName + " Not your turn");
        }
        boolean[] kept = new boolean[5];
        for (int i = 0; i < keep.length; i++) {
            kept[keep[i] - 1] = true;
        }
        called.rollKeeping(kept);
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
        Die[] dice = currentPlayer.getDice();
        int diceNum[] = new int[5];
        for (int i = 0; i < 5; i++) {
            diceNum[i] = dice[i].getNumber();
        }
        return diceNum;
    }

    public boolean[] getKept() {
        return currentPlayer.getKept();
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(currentPlayer);
        // System.out.println(json);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class); // parse
        jsonObject.addProperty("score", currentPlayer.getScore()); // modify
        // return currentPlayer.toString();
        return jsonObject;
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

    public int getPlayerScore(String name) {
        return players.get(name).getScore();
    }

	public Player getPlayer(String name) {
		return players.get(name);
	}
}
