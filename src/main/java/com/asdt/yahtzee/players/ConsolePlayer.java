package com.asdt.yahtzee.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsolePlayer implements GamePlayer {

    Scanner s;

    public ConsolePlayer() {
        s = new Scanner(System.in);
    }

    @Override
    public int[] rollKeeping() {

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
                } else { // -1 will keep all
                    break;
                }
            } else {
                s.next();
                System.out.println("1 to 6!");
            }
        } while (keep != 0);
        if (keep < 0) {
            for (int i = 1; i < 6; i++)
                list.add(i);
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public String selectCategory() {
        return s.next();
    }
}
