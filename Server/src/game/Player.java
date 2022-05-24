package game;

import java.net.Socket;

public class Player {
    private Socket socket;
    private int playerId;
    private String playerName;

    public void setSocket(Socket socket) {
        this.socket = socket;
        this.playerId = socket.getPort();
        this.playerName = "tmp" + this.playerId;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
