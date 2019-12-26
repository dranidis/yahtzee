package com.asdt.yahtzee.game.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.asdt.yahtzee.game.Game;
import com.asdt.yahtzee.game.Player;
import com.asdt.yahtzee.game.score.ScoreFactory;

public class RamdomBot implements GamePlayer {
    Random r;
    private Player player;

    public RamdomBot(Game game, String name) {
        r = new Random();
        player = game.getPlayer(name);
    }

    @Override
    public int[] rollKeeping() {
        List<Integer> list = new ArrayList<>();
        double prob;
        for (int i = 0; i < 5; i++) {
            prob = r.nextDouble();
            if (prob > 0.5) {
                list.add(i + 1);
            }
        }
        System.out.println("RandomBot keeps: " + list);
        return list.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public String selectCategory() {
        String randomCategory = "";
        Set<String> cat = ScoreFactory.getInstance().getCategories();
        Map<String, Integer> alreadyScored = player.getScored();
        for (String as : alreadyScored.keySet()) {
            if (alreadyScored.get(as) != null)
                cat.remove(as);
        }
        int size = cat.size();
        int index = r.nextInt(size);
        int i = 0;
        for (String c : cat) {
            if (i == index) {
                randomCategory = c;
                break;
            }
            i++;
        }
        System.out.println("RandomBot chooses category: " + randomCategory);
        return randomCategory;
    }
}
