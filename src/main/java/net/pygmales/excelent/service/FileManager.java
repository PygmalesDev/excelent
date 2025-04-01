package net.pygmales.excelent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.pygmales.excelent.App;
import net.pygmales.excelent.Main;
import net.pygmales.excelent.record.Notepad;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static net.pygmales.excelent.common.Constants.*;

public class FileManager {
    private static final Logger LOGGER = Main.getLogger();
    private static final FileChooser FILE_CHOOSER = new FileChooser();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Path SAVE_PATH = Path.of("./data/saved.txt");

    public static Optional<File> getExcelFile() {
        return Optional.ofNullable(FILE_CHOOSER.showOpenDialog(App.getStage()));
    }

    public static void createDataFolderIfNotExists() {
        try {
            if (Files.notExists(DATA_PATH)) {
                LOGGER.info("Data folder not found, creating new folder at {}", DATA_PATH);
                Files.createDirectory(DATA_PATH);
            }
            if (Files.notExists(NOTEPAD_PATH)) Files.createDirectory(NOTEPAD_PATH);
            if (Files.notExists(DB_DIR_PATH)) Files.createDirectory(DB_DIR_PATH);
        } catch (IOException e) {
            LOGGER.fatal(e.getMessage());
        }
    }

    public static void createNotepad(String name) {
        Path notepadPath = Path.of(NOTEPAD_PATH.toString(), String.format("%s.json", name));
        Notepad notepad = new Notepad(name, String.valueOf(Math.abs(name.hashCode())), LocalDateTime.now().toString());
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(notepadPath.toFile(), notepad);
            LOGGER.info("Created new notepad `{}` with db_id `{}` at `{}`", name, notepad.db_id(), notepadPath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static ObservableList<Notepad> loadStoredNotepads() {
        ObservableList<Notepad> notepads = FXCollections.observableArrayList();
        File[] notepadDir = NOTEPAD_PATH.toFile().listFiles(pathname -> pathname.getName().contains(".json"));
        if (Objects.isNull(notepadDir)) return notepads;

        for (File json : notepadDir) {
            try {
                notepads.add(MAPPER.readValue(json, Notepad.class));
            } catch (IOException e) {
                Main.getLogger().warn("Failed to read information about notepad {}: {}", json.getName(), e.getMessage());
            }
        }
        return notepads;
    }

    public static void saveLatestFile(String path) {
        try {
            if (Files.notExists(SAVE_PATH)) Files.createFile(SAVE_PATH);

            BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_PATH.toFile()));
            writer.write(path);
            writer.close();
        } catch (IOException e) {
            LOGGER.fatal(e.getMessage());
        }
    }

    public static Optional<File> getLatestFile() {
        if (Files.exists(SAVE_PATH)) {
            try {
                Scanner scanner = new Scanner(SAVE_PATH);
                return Optional.of(new File(scanner.nextLine()));
            } catch (Exception e) {
                LOGGER.fatal(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public static void load() {
        createDataFolderIfNotExists();
        FILE_CHOOSER.setTitle("Select Excel file");
        FILE_CHOOSER.getExtensionFilters().addAll(
                new ExtensionFilter("Excel files", "*.xls", "*.xlsm", "*.xlsx", "*.xlt"));
    }
}
