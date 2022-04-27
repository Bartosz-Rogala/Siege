package pw.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class StartController {

    @FXML public TextField userName;

    public static String username;
    public static ArrayList<String> users = new ArrayList<>();

    public void handleJoin() {
        System.out.println(userName.getText());
        username = userName.getText();
        users.add(username);
        changeWindow();
    }

    public void changeWindow() {
        try {
            Stage stage = (Stage) userName.getScene().getWindow();
//            Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainWindow.fxml"));

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(username + "");
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
}