import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SessionHandler extends Thread {
    private Socket player1;
    private Socket player2;
    private BufferedReader reader1;
    private BufferedReader reader2;
    private PrintWriter writer1;
    private PrintWriter writer2;

    public SessionHandler(Socket player1, Socket player2) {
        try {
            this.player1 = player1;
            this.player2 = player2;

            this.reader1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
            this.reader2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

            this.writer1 = new PrintWriter(player1.getOutputStream(), true);
            this.writer2 = new PrintWriter(player2.getOutputStream(), true);
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

                    System.out.println(msg1);
                    writer1.println(msg1);
                    writer2.println(msg1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader1.close();

                    writer1.close();
                    writer2.close();

                    player1.close();
                    player2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2 = () -> {
            try {
                String msg2;
                while ((msg2 = reader2.readLine()) != null) {

                    System.out.println(msg2);
                    writer1.println(msg2);
                    writer2.println(msg2);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader1.close();

                    writer1.close();
                    writer2.close();

                    player1.close();
                    player2.close();
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
