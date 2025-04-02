package net.pygmales.excelent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.pygmales.excelent.Main;
import net.pygmales.excelent.record.Notepad;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static net.pygmales.excelent.common.Constants.*;

public class FileManager {
    private static final Logger LOGGER = Main.getLogger();
    private static final Storage STORAGE = Storage.getInstance();
    private static final DatabaseService DATABASE = DatabaseService.getInstance();

    private static final FileChooser FILE_CHOOSER = new FileChooser();
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

    public static void createDataFolderIfNotExists() {
        try {
            if (Files.notExists(DATA_DIR_PATH)) {
                LOGGER.info("Data folder not found, creating new folder at {}", DATA_DIR_PATH);
                Files.createDirectory(DATA_DIR_PATH);
            }
            if (Files.notExists(NOTEPAD_DIR_PATH)) Files.createDirectory(NOTEPAD_DIR_PATH);
            if (Files.notExists(DB_DIR_PATH)) Files.createDirectory(DB_DIR_PATH);
        } catch (IOException e) {
            LOGGER.fatal(e.getMessage());
        }
    }

    public static Optional<Notepad> createNotepad(String name) {
        Path notepadPath = Path.of(NOTEPAD_DIR_PATH.toString(), String.format("%s.json", name));
        Notepad notepad = new Notepad(name, "table_" + Math.abs(name.hashCode()), LocalDateTime.now());
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(notepadPath.toFile(), notepad);
            LOGGER.info("Created new notepad `{}` with tableID `{}` at `{}`", name, notepad.tableID(), notepadPath);
            STORAGE.setNotepad(notepad);
            DATABASE.linkWithNotepad(notepad);
            return Optional.of(notepad);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static ObservableList<Notepad> loadSavedNotepads() {
        ObservableList<Notepad> notepads = FXCollections.observableArrayList();
        File[] notepadDir = NOTEPAD_DIR_PATH.toFile().listFiles(pathname -> pathname.getName().contains(".json"));
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

    public static void updateNotepadEditTime(Notepad notepad) {
        Path notepadPath = Path.of(NOTEPAD_DIR_PATH.toString(), String.format("%s.json", notepad.name()));
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(notepadPath.toFile(), notepad.updateEditTime());
            LOGGER.info("Updated edit time for notepad `{}`", notepad.name());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static Optional<Notepad> getLastEditedNotepad() {
        Optional<Notepad> notepad = loadSavedNotepads().stream().max(Comparator.comparing(Notepad::lastEdited));
        notepad.ifPresent(np -> {
            updateNotepadEditTime(np);
            STORAGE.setNotepad(np);
            DATABASE.linkWithNotepad(np);
        });

        return notepad;
    }

    public static void load() {
        createDataFolderIfNotExists();
        FILE_CHOOSER.setTitle("Select Excel file");
        FILE_CHOOSER.getExtensionFilters().addAll(
                new ExtensionFilter("Excel files", "*.xls", "*.xlsm", "*.xlsx", "*.xlt"));
    }
}
