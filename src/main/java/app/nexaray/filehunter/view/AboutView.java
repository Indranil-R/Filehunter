package app.nexaray.filehunter.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutView {
    public static void showAbout() throws IOException {
        System.out.println("File Hunter is a simple JavaFX application that scans a directory for files with the same name and displays them in a tree view.");

        FXMLLoader loader = new FXMLLoader(AboutView.class.getResource("about-view.fxml"));

        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("About File Hunter");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();

    }
}
