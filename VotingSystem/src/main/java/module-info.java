module main.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens main.app to javafx.fxml;
    exports main.app;
}