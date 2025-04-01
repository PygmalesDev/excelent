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
import net.pygmales.excelent.service.Storage;

import java.net.URL;
import java.util.ResourceBundle;

public class FileHandlerController implements Initializable {
    private final Storage storage = Storage.getInstance();
    private final ObservableList<CalendarCellData> list = FXCollections.observableArrayList();

    @FXML private AnchorPane calendarPane;
    @FXML private AnchorPane editPane;
    @FXML private Label fileNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.fileNameLabel.setText(this.storage.getOpenedFile().getName());

        this.calendarPane.getChildren().add(new CalendarGridView(this.list));
        this.list.addAll(new CalendarCellData(Color.RED), new CalendarCellData(Color.BLACK));

    }
}
