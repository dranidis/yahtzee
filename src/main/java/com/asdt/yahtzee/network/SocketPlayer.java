package com.asdt.yahtzee.network;


public class SocketPlayer{

    private Connection connection;

    public SocketPlayer(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

}
