module com.atp.atpgui {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.atp.gui;
    opens com.atp.gui to javafx.fxml;
}