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
    String army;
    private Hexagon[][] hexagons;
    int maxI = 9;
    int maxJ = 13;
    String currentPort;

    public MainController(ChoiceController choiceController, String army) {
        this.choiceController = choiceController;
        this.army = army;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket = choiceController.getSocket();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

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
        send("first:" + army);
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                System.out.println("reader: " + msg);
                String[] tokens = msg.split(":");

                String[] hexes = tokens[1].split(";");
                currentPort = tokens[0];
                board.parseMessage(hexes);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hexOnClicked(MouseEvent event) {
        Polygon source = (Polygon) event.getSource();

        if (currentPort.equals("" + socket.getLocalPort())) {


            if (board.isAnyHexActive() && !board.isFilledIn(source)) {
                if (board.isActiveNeighbour(source)) {
                    board.move((Polygon) event.getSource());
                    send(StartController.username + ":" + board.getContent());
                } else {
                    board.deactivateAll();
                }

            } else if (!board.isAnyHexActive() && board.isFilledIn(source)) {
                board.activate((Polygon) event.getSource());
            } else {
                board.deactivateAll();
            }
        }

    }

    public void send(String msg) {

        writer.println(msg);
        System.out.println(msg);

    }




}
