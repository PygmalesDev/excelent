package net.pygmales.excelent.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.pygmales.excelent.App;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.Storage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private final Storage storage = Storage.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void openFileManager() {
        FileManager.getExcelFile().ifPresent(file -> {
            FileManager.saveLatestFile(file.getAbsolutePath());
            this.storage.setOpenedFile(file);

            App.setScene(Scenes.FILE_HANDLER);
        });
    }
}
