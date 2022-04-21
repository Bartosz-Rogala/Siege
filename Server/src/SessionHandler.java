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
        try {
            String msg;
            while ((msg = reader1.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit"))
                    break;

                writer2.println(msg);
            }

            while ((msg = reader2.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit"))
                    break;

                writer1.println(msg);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader1.close();
                reader2.close();

                writer1.close();
                writer2.close();

                player1.close();
                player2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
