package net.me.chat_application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatServer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatServer.class.getResource("views/chat_server_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 540);
        stage.setTitle("Chat Server");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
