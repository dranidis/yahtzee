package com.asdt.yahtzee.game.score;

import java.util.HashMap;
import java.util.Map;

public class ScoreFactory {

    private Map<String,ScoreStrategy> scoreStrategies;

    private ScoreFactory() {
        scoreStrategies = new HashMap<>();
        scoreStrategies.put("1s", new NumberScoreStrategy(1));
        scoreStrategies.put("2s", new NumberScoreStrategy(2));
        scoreStrategies.put("3s", new NumberScoreStrategy(3));
        scoreStrategies.put("4s", new NumberScoreStrategy(4));
        scoreStrategies.put("5s", new NumberScoreStrategy(5));
        scoreStrategies.put("6s", new NumberScoreStrategy(6));
        scoreStrategies.put("3k", new OfaKindScoreStrategy(3));
        scoreStrategies.put("4k", new OfaKindScoreStrategy(4));
        scoreStrategies.put("s4", new SequenceScoreStrategy(4));
        scoreStrategies.put("s5", new SequenceScoreStrategy(5));
        scoreStrategies.put("5k", new OfaKindScoreStrategy(5));
        scoreStrategies.put("fh", new FullHouseScoreStrategy());
        scoreStrategies.put("ch", new ChanceScoreStrategy());
    }

    private static ScoreFactory instance = new ScoreFactory();

	public static ScoreFactory getInstance() {
		return instance;
	}

	public ScoreStrategy getScoreStrategy(String categoryName) {
		return scoreStrategies.get(categoryName);
	}

}
