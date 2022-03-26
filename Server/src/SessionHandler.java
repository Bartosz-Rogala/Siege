import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SessionHandler extends Thread {
    private ArrayList<SessionHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SessionHandler(Socket socket, ArrayList<SessionHandler> clients) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            this.socket = socket;
            this.clients = clients;
            this.reader = reader;
            this.writer = writer;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String msg;
            while ((msg = reader.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit")) {
                    break;
                }
                for (SessionHandler cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
