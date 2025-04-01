package net.pygmales.excelent.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import net.pygmales.excelent.element.CalendarGridView;
import net.pygmales.excelent.record.CalendarCellData;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.ExcelManagerService;
import net.pygmales.excelent.service.Storage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FileHandlerController implements Initializable {
    private final Storage storage = Storage.getInstance();
    private final DatabaseService databaseService = DatabaseService.getInstance();
    private final ExcelManagerService excelManagerService = ExcelManagerService.getInstance();
    private final ObservableList<CalendarCellData> list = FXCollections.observableArrayList();

    @FXML private AnchorPane calendarPane;
    @FXML private AnchorPane editPane;
    @FXML private Label fileNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final File openedFile = this.storage.getOpenedFile();
        this.databaseService.linkWithTable(openedFile);
        this.excelManagerService.openExcelFile(openedFile);

        this.fileNameLabel.setText(this.storage.getOpenedFile().getName());

        this.calendarPane.getChildren().add(new CalendarGridView(this.list));
        this.list.addAll(new CalendarCellData(Color.RED), new CalendarCellData(Color.BLACK));

    }
}
