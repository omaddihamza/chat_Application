module net.me.chat_application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens net.me.chat_application to javafx.fxml;
    exports net.me.chat_application;
}