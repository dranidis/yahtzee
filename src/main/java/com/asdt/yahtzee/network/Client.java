package com.asdt.yahtzee.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Connection {
    boolean running = false;

    public Client(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            System.out.println("Connected to server...");

             // It is important that you create first the output stream and then the input
            // stream. Otherwise it mmight deadlock.
            // Creation of the input stream is a blocking operation
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            listener = new Listener();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
        running = true;
    }

    @Override
    protected boolean isAlive() {
        return running;
    }

    public void close() {
        running = false;
        super.close();
    }

}
