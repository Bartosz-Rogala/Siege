package pw.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {

    /**
     *
     * EndController is a class used to inform clients about endgame result.
     * Both players receive different information about game status (win/lose).
     *
     * This class cooperates with EndWindow.fxml file.
     *
     * @Author Bartosz Rogala
     */

    String winner;
    String client;

    @FXML Label resultLabel;

    @FXML Button leaveButton;

    public EndController(String winner, String client) {
        this.winner = winner;
        this.client = client;



    }

    public void handleLeave() {

        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (winner.equals(client)) {
            resultLabel.setText("You won, you magnificent beast!");
        } else {
            resultLabel.setText("You lost, you dumb schmuck!");
        }
    }
}
