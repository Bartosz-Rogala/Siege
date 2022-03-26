import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApp {
    private ArrayList<SessionHandler> clients = new ArrayList<>();
    private Socket socket;
    private int sessionNumber;

    public ServerApp() {

    }

    public void startRunning() {

        try (ServerSocket serverSocket = new ServerSocket(9001)) {

            while(true) {
                System.out.println("Waiting for clients...");
                socket = serverSocket.accept();
                System.out.println("Connected");
                SessionHandler clientThread = new SessionHandler(socket, clients);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
