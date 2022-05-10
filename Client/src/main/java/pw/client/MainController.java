package pw.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Thread implements Initializable {

    @FXML public VBox vbox01;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    ChoiceController choiceController;

    Board board;
    private Hexagon[][] hexagons;
    int maxI = 9;
    int maxJ = 13;

    public MainController(ChoiceController choiceController) {
        this.choiceController = choiceController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket = choiceController.getSocket();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        hexagons = new Hexagon[maxI][maxJ];

        for(int i = 0; i < vbox01.getChildren().toArray().length; i++) {
            HBox hbox = (HBox) vbox01.getChildren().toArray()[i];
            for (int j = 0; j < hbox.getChildren().toArray().length; j++) {
                Polygon hex = (Polygon) hbox.getChildren().toArray()[j];
                hexagons[i][j] = new Hexagon(hex);
            }
        }

        board = new Board(hexagons);
        board.addNeighbours(1);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(":");
                String username = tokens[0];
                String[] activeHexes = tokens[1].split(";");
                board.parseMessage(activeHexes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hexOnClicked(MouseEvent event) {

        if (board.isAnyHexActive()) {
            if (board.isActiveNeighbour((Polygon) event.getSource())) {
                board.deactivateAll();
                board.activate((Polygon) event.getSource());
            } else {
                board.deactivateAll();
            }

        } else {
            board.activate((Polygon) event.getSource());
        }

        send();
    }

    public void send() {

        String msg = board.getContent();
        writer.println(StartController.username + ":" + msg);
        System.out.println(StartController.username + ":" + msg);

        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }




}
