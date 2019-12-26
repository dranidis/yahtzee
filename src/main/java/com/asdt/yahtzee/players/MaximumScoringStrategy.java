package com.asdt.yahtzee.players;

import java.util.Set;

import com.asdt.yahtzee.game.Player;
import com.asdt.yahtzee.game.score.ScoreFactory;

public class MaximumScoringStrategy implements ScoringStrategy {

    @Override
    public String selectCategory(Player player) {

        Set<String> categories = ScoreFactory.getInstance().getCategories();
        categories.remove("ch");

        int maxScore = -1;
        String maxCategory = "";
        for(String category: categories) {
            int score = player.getScoreForCategory(category);
            if (score > maxScore) {
                maxScore = score;
                maxCategory = category;
            }
        }

        // todo:
        // if score is 0 select categories according to difficulty
        // choose the easiest of the hardest or the one giving the less reward?
        //
        int chanceScore = player.getScoreForCategory("ch");
        if (chanceScore > maxScore) {
            maxCategory = "ch";
        }

        if (maxCategory.equals("")) {
            throw new RuntimeException(this.getClass().toString() + " maxCategory empty");
        }
        System.out.println(this.getClass().toString() + "  chooses category: " + maxCategory);
        return maxCategory;
    }
}
