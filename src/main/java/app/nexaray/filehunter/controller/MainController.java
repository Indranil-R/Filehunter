package app.nexaray.filehunter.controller;

import app.nexaray.filehunter.view.AboutView;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.nexaray.filehunter.service.FileService.listFilesWithSameNames;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    HashMap<String, List<File>> filesGroupedByName;
    @FXML
    private TreeTableView<File> treeTableView;
    @FXML
    private TreeTableColumn<File, String> fileNameColumn;
    @FXML
    private TreeTableColumn<File, String> pathNameColumn, fileSizeColumn;
    @FXML
    private Label fileDirectoryInfoLabel;
    @FXML
    private TextField directoryToScanTextField;

    @FXML
    protected void openGithubPage() {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Indranil-R/Filehunter"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void showAbout() throws IOException {
        AboutView.showAbout();
    }

    public void updateTableTreeWithResults() {
        try {
            treeTableView.setVisible(true);

//            HashMap<String, List<File>> filesGroupedByName = listFilesWithSameNames();

            fileDirectoryInfoLabel.setText("Found " + filesGroupedByName.size() + " files names with duplicates");

            TreeItem<File> root = new TreeItem<>(new File(""));
            treeTableView.setRoot(root);
            treeTableView.setShowRoot(false);
            root.setExpanded(true);

            fileNameColumn.setCellValueFactory(param -> {
                if (param.getValue().getParent() != null && param.getValue().getParent().getParent() == null) {
                    return new SimpleStringProperty(param.getValue().getValue().getName());
                } else {
                    return new SimpleStringProperty("");
                }
            });


            for (Map.Entry<String, List<File>> entry : filesGroupedByName.entrySet()) {
                // Create a parent node for the group
                TreeItem<File> groupItem = new TreeItem<>(new File(entry.getKey()));

                // Add files as children of the group, displaying their absolute paths
                for (File file : entry.getValue()) {
                    TreeItem<File> fileItem = new TreeItem<>(new File(String.valueOf(file.getAbsoluteFile())));
                    groupItem.getChildren().add(fileItem);
                }
                root.setExpanded(true);
                // Add the group to the root
                root.getChildren().add(groupItem);
            }

            pathNameColumn.setCellValueFactory(param -> {
                if (param.getValue().getParent() != null && param.getValue().getParent().getParent() != null) {
                    return new SimpleStringProperty(param.getValue().getValue().getAbsolutePath());
                } else {
                    return new SimpleStringProperty("");
                }
            });

            fileSizeColumn.setCellValueFactory(param -> {
                if (param.getValue().getParent() != null && param.getValue().getParent().getParent() != null) {
                    return new SimpleStringProperty(FileUtils.byteCountToDisplaySize(param.getValue().getValue().length()));
                } else {
                    return new SimpleStringProperty("");
                }
            });

        } catch (Exception e) {
            logger.error("Error while scanning files: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void updateUIWithExceptionData(String message) {
        logger.info("update UI with exception");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Occurred");
        alert.setHeaderText(message);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void scanDuplicateFiles() {
        try {
            long startTime = System.currentTimeMillis();
            String directoryToScan = directoryToScanTextField.getText();
            filesGroupedByName = listFilesWithSameNames(directoryToScan);
            long endTime = System.currentTimeMillis();
            logger.info("Time taken: {}", (endTime - startTime));
        } catch (Exception e) {
            logger.error("Error while scanning duplicate files: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void startScanning() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                scanDuplicateFiles();
                return null;
            }
        };

        task.setOnRunning(e -> fileDirectoryInfoLabel.setText("Scanning files..."));

        task.setOnSucceeded(e -> {
            updateTableTreeWithResults();
            fileDirectoryInfoLabel.setText("Scanning completed");

        });

        task.setOnFailed(e -> fileDirectoryInfoLabel.setText("Scanning failed"));

        new Thread(task).start();

    }
}

//fuck you AI