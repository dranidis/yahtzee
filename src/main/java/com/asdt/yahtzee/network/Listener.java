package com.asdt.yahtzee.network;

import com.asdt.yahtzee.network.messages.KeepRequest;
import com.asdt.yahtzee.network.messages.Request;
import com.asdt.yahtzee.network.messages.ScoreRequest;
import com.asdt.yahtzee.network.messages.UserRequest;

/**
 * A separate Listener object is created for each Connection object created by
 * the server. The listener is responsible for handling the messages sent by the
 * client associated to the connection. The listener can send messages to the
 * client by using the connection attribute.
 */
public class Listener {

    private Connection connection;
    String name;

    /*
     * TODO: currently a single game can be played by two players. Change: get a
     * game from a factory and connect player connection to games. Each listener
     * should remember the game.
     */
    ServerGame sg = ServerGame.getInstance();

    public Listener(Connection connection) {
        this.connection = connection;
    }

    /**
     * The on method listens and handles objects sent by clients.
     *
     * @param object the object sent by clients
     */
    public void on(Object object) {
        System.out.println("RECEIVED from " + connection.id + " " + object.toString());
        if (object instanceof UserRequest) {
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
        } else if (object instanceof KeepRequest) {
            /* the client has sent the dice to keep */
            KeepRequest keepRequest = (KeepRequest) object;
            sg.game.rollKeeping(name, keepRequest.getKeep());

            // clients are waiting
            sg.broadCast(sg.game.toString());

        } else if (object instanceof ScoreRequest) {
            /* the client has sent the category to score */
            ScoreRequest scoreRequest = (ScoreRequest) object;
            int score = sg.game.scoreACategory(scoreRequest.name, scoreRequest.categoryName);

            connection.sendObject(score);

            /*
             * The client has scored a category. Change to the next player. If no next
             * player start a new round and get the next player.
             */
            if (score >= 0) {
                // clients are waiting
                sg.broadCast(sg.game.toString());

                String player = sg.game.getNextPlayer();
                if (player == null) {
                    sg.game.startRound();
                    sg.game.getNextPlayer();
                }
            }
        } else if (object instanceof String) {
            /* handes string messages sent by the client */
            String str = (String) object;
            if (str.equals(Request.CURRENTPLAYER)) {
                // client is waiting
                connection.sendObject(sg.game.getCurrentPlayersName());
            } else
                System.out.println(
                        "UNHANDLED String request from client:" + connection.id + " Request:" + object.toString());
        }
    }

}
