package com.asdt.yahtzee.network;

import com.asdt.yahtzee.network.messages.*;

public class Listener {

    private Connection connection;
    String name;
    ServerGame sg = ServerGame.getInstance();

    public Listener(Connection connection) {
        this.connection = connection;
    }

    /**
     * The server listens and handles objects.
     *
     * @param object
     */
    public void on(Object object) {
        System.out.println("RECEIVED from " + connection.id + " " + object.toString());
        if (object instanceof UserRequest) {
            // sent by client to server
            // a new user has given a name

            if (sg.isAccepting()) {
                UserRequest user = (UserRequest) object;

                this.name = user.name;

                sg.addPlayer(user.name, connection);

                if (sg.isReady()) {
                    sg.start();
                }
            } else {
                // todo create new games for more users
            }
        }
        // else if (object instanceof KeepRequest) {
        // // server -> client
        // // server asks client which dice to keep
        // System.out.println("KEEP REQUEST FROM SERVER");
        // int[] keep = connection.getClientUI().rollKeeping();
        // // send to the server
        // connection.sendObject(new KeepResponse(name, keep));

        // }
        else if (object instanceof KeepRequest) {
            // client -> server
            KeepRequest keepRequest = (KeepRequest) object;

            sg.game.rollKeeping(name, keepRequest.getKeep());

            // clients are waiting
            sg.broadCast(sg.game.toString());

        } else if (object instanceof ScoreRequest) {
            ScoreRequest scoreRequest = (ScoreRequest) object;

            int score = sg.game.scoreACategory(scoreRequest.name, scoreRequest.categoryName);

            connection.sendObject(score);

            if (score >= 0) {
                sg.broadCast(sg.game.toString());

                String player = sg.game.getNextPlayer();
                if (player == null) {
                    sg.game.startRound();
                    sg.game.getNextPlayer();
                }
            }

        } else if (object instanceof String) {
            String str = (String) object;
            if (str.equals(Request.GAMEINFO)) {
                // if player has not rolled yet wait
                connection.sendObject(sg.game.toString());
            } else if (str.equals(Request.CURRENTPLAYER)) {
                connection.sendObject(sg.game.getCurrentPlayersName());
            } else
                System.out.println("UNHANDLED " + object.toString());
        }

        // ((Packet) object).handle();
    }

}
