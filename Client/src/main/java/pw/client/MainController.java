package pw.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
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
    @FXML public TextField topTextField;

    @FXML public Label statLabelName;
    @FXML public Label statLabelFaction;
    @FXML public Label statLabelClass;
    @FXML public Label statLabelType;
    @FXML public Label statLabelHealth;
    @FXML public Label statLabelMove;
    @FXML public Label statLabelAttDmg;
    @FXML public Label statLabelAttRng;


    //chat
    @FXML public TextField chatTextField;
    @FXML public TextArea chatTextArea;


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
                hexagons[i][j] = new Hexagon(hex, i, j);
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

                if (tokens[0].equals("endgame")) {
                    endgame();
                } else if (tokens[0].equals("chat")) {
                    String[] msgTokens = tokens[2].split(" ");
                    String cmd = msgTokens[0];
                    System.out.println(cmd);
                    StringBuilder fulmsg = new StringBuilder();
                    for(int i = 1; i < msgTokens.length; i++) {
                        fulmsg.append(msgTokens[i]);
                    }
                    if (cmd.equalsIgnoreCase(StartController.username + ":")) {
                        continue;
                    }
                    chatTextArea.appendText( tokens[1] + ": " + fulmsg + "\n");
                } else {
                    String[] hexes = tokens[1].split(";");
                    currentPort = tokens[0];
                    board.deactivateAll();
                    board.parseMessage(hexes, socket.getLocalPort());
                    displayCurrentPlayer();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hexOnClicked(MouseEvent event) {
        Polygon source = (Polygon) event.getSource();

        if (currentPort.equals("" + socket.getLocalPort())) {
            if (board.isAnyHexActive()) {
                if (board.isFilledIn(source)) {
                    if(board.isOpponent(currentPort, source) && board.isShootingActiveNeighbour(source)) {

                        send(StartController.username + ":" + board.takeAction("attack", (Polygon) event.getSource()));
                        board.deactivateAll();
                        System.out.println("attack");
                    } else {
                        board.deactivateAll();
                        board.activate(currentPort, source);
                    }
                } else {
                    if (board.isActiveNeighbour(source)) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            System.out.println("move");
                            send(StartController.username + ":" + board.takeAction("move", (Polygon) event.getSource()));
                        } else if (event.getButton() == MouseButton.SECONDARY && board.canBuild() && board.isShootingActiveNeighbour(source)) {
                            System.out.println("build");
                            send(StartController.username + ":" + board.takeAction("build", (Polygon) event.getSource()));
                        }

                    } else {
                        board.deactivateAll();
                    }
                }
            } else if (board.isFilledIn(source)) {
                board.activate(currentPort, source);
            }
        }

    }

    public void sendChatMessage(MouseEvent event) {
        String msg = chatTextField.getText();
        writer.println("chat:" + StartController.username + ": " + msg);
        chatTextField.setText("");
    }

    public void hexOnRightClicked(MouseEvent event) {
        Polygon source = (Polygon) event.getSource();

        if (currentPort.equals("" + socket.getLocalPort())) {
            if (board.isAnyHexActive()) {
                if (!board.isFilledIn(source)) {
                    if (board.isActiveNeighbour(source)) {
                        System.out.println("build");
                        send(StartController.username + ":" + board.takeAction("build", (Polygon) event.getSource()));
                        board.deactivateAll();
                    } else {
                        board.deactivateAll();
                    }
                }
            } else if (board.isFilledIn(source)) {
                board.activate(currentPort, source);
                displayCurrentUnitStatistics();
            }
        }
    }


    public void send(String msg) {

        writer.println(msg);
        System.out.println(msg);

    }

    private void endgame() {
        System.out.println();
    }


    public void displayCurrentPlayer() {

       String message = "Welcome " + StartController.username + "!";
       topTextField.setText(message);
    }

    public void displayCurrentUnitStatistics() {

        if (board.isAnyHexActive()) {
            statLabelName.setText("");
            statLabelFaction.setText("");
            statLabelClass.setText("");
            statLabelType.setText("");
            statLabelHealth.setText("");
            statLabelMove.setText("");
            statLabelAttDmg.setText("");
            statLabelAttRng.setText("");
        }
    }
}
