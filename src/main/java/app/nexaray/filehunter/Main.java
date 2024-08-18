package app.nexaray.filehunter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

//        scanFiles();

//        listFilesBySimilarNames();
//        listAllFilesInComputer();

        logger.info("Application started");
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("File Hunter");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}