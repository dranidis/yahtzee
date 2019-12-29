package com.asdt.yahtzee.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.asdt.yahtzee.network.messages.IDMessage;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private boolean running;
    private Map<Integer, Connection> connections = new HashMap<>();

    private static int id = 0;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            Socket socket = null;
            try {
                System.out.println("Waiting for clients...");
                socket = serverSocket.accept();
                System.out.println("New client connected...");
                Connection connection = new Connection(socket);
                connections.put(id, connection);
                connection.setId(id);

                connection.sendObject(new Integer(id));
                id++;
                if(id == 2) {
                    System.out.println("Both players connected");
                    System.out.println("Not accepting any other connections.");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
