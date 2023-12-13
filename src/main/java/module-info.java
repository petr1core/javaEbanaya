module com.example.clockfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.google.gson;

    opens com.example.clockfx to javafx.fxml, com.google.gson;
    exports com.example.clockfx;
}
