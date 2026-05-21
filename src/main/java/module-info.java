module com.sportt5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.sportt5 to javafx.fxml;
    opens com.sportt5.controller to javafx.fxml;
    exports com.sportt5;
}