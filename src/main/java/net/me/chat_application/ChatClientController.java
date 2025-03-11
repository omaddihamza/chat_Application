package net.me.chat_application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatClientController implements Initializable {

    @FXML
    private VBox vbox_messages;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private Button envoyerBtn;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String clientName;  // Sera défini via le popup

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Demander le nom de l'utilisateur via un popup
        clientName = askUserName();
        if (clientName == null || clientName.trim().isEmpty()) {
            showAlert("Erreur", "Vous devez entrer un nom pour continuer.");
            return;
        }

        envoyerBtn.setOnAction(event -> sendMessage());

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 9090);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    final String message = receivedMessage;
                   Platform.runLater(() -> displayMessage("Server: " + message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String message = chatTextArea.getText().trim();
        if (!message.isEmpty()) {
            out.println(clientName + ":  " + message);  // Envoyer au serveur
            displayMessage("me:  " + message);  // Afficher dans l'interface client
            chatTextArea.clear();
        }
    }

    private void displayMessage(String message) {
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        vbox_messages.getChildren().add(textFlow);
    }

    private String askUserName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom d'utilisateur");
        dialog.setHeaderText("Entrez votre nom avant de commencer à discuter");
        dialog.setContentText("Nom:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
