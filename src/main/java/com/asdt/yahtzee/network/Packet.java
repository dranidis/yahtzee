package com.asdt.yahtzee.network;

import java.io.Serializable;

public interface Packet extends Serializable {

	public void handle();

}
