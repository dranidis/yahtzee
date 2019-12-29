package com.asdt.yahtzee.network.messages;

import java.io.Serializable;

public class UserRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;

    public UserRequest(String name) {
        this.name = name;
	}

}
