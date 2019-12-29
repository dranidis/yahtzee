package com.asdt.yahtzee.network.messages;

import java.io.Serializable;

public class IDMessage implements Serializable {

    private int id;

    public IDMessage(int id) {
        this.id = id;
	}

	private static final long serialVersionUID = 1L;


}
