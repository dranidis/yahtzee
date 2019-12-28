package com.asdt.yahtzee.network;

public class Welcome implements Packet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void handle() {
        System.out.println("Welcome!");
    }
}
