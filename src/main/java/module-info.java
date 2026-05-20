module com.sportt5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.zaxxer.hikari;
    requires java.sql;

    opens com.sportt5 to javafx.fxml;
    opens com.sportt5.controller to javafx.fxml;
    exports com.sportt5;
    opens com.sportt5.controller.home to javafx.fxml;
}