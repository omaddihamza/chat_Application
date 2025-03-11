package net.me.chat_application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatServerController implements Initializable {

    @FXML
    private VBox vbox_messages;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private Button envoyerBtn;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        envoyerBtn.setOnAction(event -> sendMessage());

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(9090);
                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    final String message = receivedMessage;
                   Platform.runLater(() -> displayMessage( message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String message = chatTextArea.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);  // Send to client
            displayMessage("Me: " + message);
            chatTextArea.clear();
        }
    }

    private void displayMessage(String message) {
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        vbox_messages.getChildren().add(textFlow);
    }
}
