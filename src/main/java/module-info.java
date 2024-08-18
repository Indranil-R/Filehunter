module app.nexaray.filehunter.filehunter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.apache.commons.io;
    requires org.slf4j;


    opens app.nexaray.filehunter to javafx.fxml;
    exports app.nexaray.filehunter;
    exports app.nexaray.filehunter.controller;
    opens app.nexaray.filehunter.controller to javafx.fxml;
}