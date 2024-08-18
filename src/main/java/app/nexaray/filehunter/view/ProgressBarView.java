package app.nexaray.filehunter.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ProgressBarView {
    public static void ShowProgressBar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProgressBarView.class.getResource("progress-view.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 300, 100);
        stage.setTitle("File Hunter");
        stage.setScene(scene);
        stage.setResizable(false);
        //set a decorated stage
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
