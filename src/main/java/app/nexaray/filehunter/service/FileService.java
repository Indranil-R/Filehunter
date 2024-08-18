package app.nexaray.filehunter.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public static HashMap<String, List<File>> listFilesWithSameNames(String directoryPath) {

        try {

            HashMap<String, List<File>> filesGroupedBySameName = new HashMap<>();
            logger.info("Listing files with same names");
            File directory = new File(directoryPath);
            if (directoryPath.isBlank()) {
                throw new RuntimeException("Directory path is empty");
            }

            Collection<File> files = FileUtils.listFilesAndDirs(directory, FileFilterUtils.trueFileFilter(), DirectoryFileFilter.DIRECTORY);

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        filesGroupedBySameName.computeIfAbsent(file.getName(), k -> new ArrayList<>()).add(file);
                    }
                }
            }

            filesGroupedBySameName.entrySet().removeIf(entry -> entry.getValue().size() == 1);
            logger.info("Listing finished");

            return filesGroupedBySameName;
        } catch (Exception e) {
            logger.error("Error while listing files with same names {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void listAllFilesInComputer() {
        // list all drives
        File[] paths = File.listRoots();
        // for each pathname in pathname array
        for (File path : paths) {
            // prints file and directory paths
            System.out.println("Drive Name: " + path);
            System.out.println("Files: ");
            File f = new File(path.toString());
            File[] files = f.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        }
    }

    public static void scanFiles() {

        try {

            File directory = new File("C:\\Users\\indranil.roy\\Downloads");

            Collection<File> filesAndDirs = FileUtils.listFilesAndDirs(directory, FileFilterUtils.trueFileFilter(), DirectoryFileFilter.DIRECTORY);

            for (File file : filesAndDirs) {

                logger.info("{} {} {} {}", file.getName(), file.getAbsoluteFile(), file.length(), FileUtils.byteCountToDisplaySize(file.length()));


            }

        } catch (Exception e) {
            logger.error("Error while scanning files {}", e.getMessage());
        }
    }
}
