package pw.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceController {

    public StartController startController;
    @FXML
    public VBox goblinBox;
    public VBox humanBox;
    public VBox monsterBox;

    Socket socket;

    public ChoiceController (StartController startController) {
        this.startController = startController;
    }

    @FXML
    public void initialize() {
        try {
            socket = new Socket("localhost", 9001);
            System.out.println("Socket is connected with server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleChooseGoblin() {
        changeWindow("goblin");
    }

    public void handleChooseHuman() {
        changeWindow("human");
    }

    public void handleChooseMonster() {
        changeWindow("monster");
    }

    public void changeWindow(String army) {
        try {
            Stage stage;
            switch (army) {
//                case "goblin":
//                    stage = (Stage) goblinBox.getScene().getWindow();
//                    break;
                case "human":
                    stage = (Stage) humanBox.getScene().getWindow();
                    break;
                case "monster":
                    stage = (Stage) monsterBox.getScene().getWindow();
                    break;
                default:
                    stage = (Stage) goblinBox.getScene().getWindow();
            }


            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainWindow.fxml"));
            MainController mainController = new MainController(this);
            fxmlLoader.setController(mainController);
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(startController.getUsername() + "");
            stage.setFullScreen(true);
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }


}
