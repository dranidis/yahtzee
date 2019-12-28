package com.asdt.yahtzee.network;

public class Listener {
    /**
     * The server listens and handles objects.
     * @param object
     */
	public void on(Object object) {
        ((Packet) object).handle();
	}
}
