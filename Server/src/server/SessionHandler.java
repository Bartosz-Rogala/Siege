package server;

import game.Game;
import game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SessionHandler extends Thread {
    private Player player1;
    private Player player2;
    private Game game;
    private BufferedReader reader1;
    private BufferedReader reader2;
    private PrintWriter writer1;
    private PrintWriter writer2;

    public SessionHandler(Socket socket1, Socket socket2) {
        try {
            this.player1 = new Player();
            this.player2 = new Player();

            player1.setSocket(socket1);
            player2.setSocket(socket2);

            this.game = new Game(player1, player2);


            this.reader1 = new BufferedReader(new InputStreamReader(player1.getSocket().getInputStream()));
            this.reader2 = new BufferedReader(new InputStreamReader(player2.getSocket().getInputStream()));

            this.writer1 = new PrintWriter(player1.getSocket().getOutputStream(), true);
            this.writer2 = new PrintWriter(player2.getSocket().getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Runnable r = () -> {
            doRun(player1, reader1, writer1, writer2);
        };

        Runnable r2 = () -> {
            doRun(player2, reader2, writer1, writer2);
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }

    public void doRun(Player player, BufferedReader reader, PrintWriter w1, PrintWriter w2) {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {

                String response;

                String[] tokens = msg.split(":");

                if (tokens[0].equals("first")) {
                    game.generateArmy(player, tokens[1]);


                } else {
                    player.setPlayerName(tokens[0]);
                    game.countTurn(player.getPlayerId());

                    String[] actionTokens = tokens[1].split("/");


                    if(actionTokens[0].equals("attack")) {
                        game.attack(actionTokens[1], actionTokens[2]);
                    } else if (actionTokens[0].equals("move")) {
                        game.move(actionTokens[1], actionTokens[2]);
                    }

                }

                response = game.getCurrentPlayer().getSocket().getPort() + ":" + game.toString();


                System.out.println("response: " + response);
                w1.println(response);
                w2.println(response);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();

                w1.close();
                w2.close();

                player.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
