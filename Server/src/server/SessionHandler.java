package server;

import game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SessionHandler extends Thread {
    private Socket socket1;
    private Socket socket2;
    private Game game;
    private BufferedReader reader1;
    private BufferedReader reader2;
    private PrintWriter writer1;
    private PrintWriter writer2;

    public SessionHandler(Socket socket1, Socket socket2) {
        try {
            this.socket1 = socket1;
            this.socket2 = socket2;

            this.game = new Game();

            this.reader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            this.reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));

            this.writer1 = new PrintWriter(socket1.getOutputStream(), true);
            this.writer2 = new PrintWriter(socket2.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Runnable r = () -> {
            try {
                String msg1;

                while ((msg1 = reader1.readLine()) != null) {
                    String response = msg1;

                    String[] tokens = msg1.split(":");
                    if (tokens[0].equals("first")) {
                        game.generateArmy(tokens[1]);
                        response = "first:" + tokens[0] + ":" + game.toString();
                    }

                    System.out.println(response);
                    writer1.println(response);
                    writer2.println(response);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader1.close();

                    writer1.close();
                    writer2.close();

                    socket1.close();
                    socket2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2 = () -> {
            try {
                String msg2;
                while ((msg2 = reader2.readLine()) != null) {
                    String response = msg2;

                    String[] tokens = msg2.split(":");
                    if (tokens[0].equals("first")) {
                        game.generateArmy(tokens[1]);
                        response = "first:" + tokens[0] + ":" + game.toString();
                    }

                    System.out.println(response);
                    writer1.println(response);
                    writer2.println(response);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader1.close();

                    writer1.close();
                    writer2.close();

                    socket1.close();
                    socket2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

    }

}
