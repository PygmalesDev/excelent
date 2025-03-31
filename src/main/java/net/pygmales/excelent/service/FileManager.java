package net.pygmales.excelent.service;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.pygmales.excelent.App;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class FileManager {
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    private static final Path DATA_PATH = Path.of("./data");
    private static final Path SAVE_PATH = Path.of("./data/saved.txt");

    public static Optional<File> getExcelFile() {
        return Optional.ofNullable(FILE_CHOOSER.showOpenDialog(App.getStage()));
    }

    public static void saveLatestFile(String path) {
        try {
            if (Files.notExists(DATA_PATH)) Files.createDirectory(DATA_PATH);
            if (Files.notExists(SAVE_PATH)) Files.createFile(SAVE_PATH);

            BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_PATH.toFile()));
            writer.write(path);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<File> getLatestFile() {
        if (Files.exists(SAVE_PATH)) {
            try {
                Scanner scanner = new Scanner(SAVE_PATH);
                return Optional.of(new File(scanner.nextLine()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("no file found :(");
        return Optional.empty();
    }

    public static void load() {
        FILE_CHOOSER.setTitle("Select Excel file");
        FILE_CHOOSER.getExtensionFilters().addAll(
                new ExtensionFilter("Excel files", "*.xls", "*.xlsm", "*.xlsx", "*.xlt"));
    }
}
