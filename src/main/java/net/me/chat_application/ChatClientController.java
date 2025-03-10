package net.me.chat_application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChatController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}