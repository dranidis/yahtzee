package com.asdt.yahtzee.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    Listener listener;

    protected Connection() {}

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            listener = new Listener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isAlive()) {
            try {
                listener.on(in.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connection disconnected");
        close();
    }

    protected boolean isAlive() {
        return socket.isConnected();
    }

    public void sendObject(Object object) {
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
