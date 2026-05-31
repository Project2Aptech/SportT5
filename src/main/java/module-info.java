module com.sportt5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires java.net.http;
    requires java.desktop;
    requires java.prefs;
    requires java.management;

    opens com.sportt5.model to com.fasterxml.jackson.databind,
            com.fasterxml.jackson.datatype.jsr310;

    opens com.sportt5.model.enums to com.fasterxml.jackson.databind;
    opens com.sportt5 to javafx.fxml;
    opens com.sportt5.controller to javafx.fxml;
    exports com.sportt5;
    opens com.sportt5.controller.components to javafx.fxml;
    opens com.sportt5.controller.pages to javafx.fxml;
}