package net.pygmales.excelent.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import net.pygmales.excelent.App;
import net.pygmales.excelent.Main;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.record.Notepad;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.FileManager;
import net.pygmales.excelent.service.Storage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML private AnchorPane creationConfirmPane;
    @FXML private Pane shadowPane;
    @FXML private Button openNotepadButton;
    @FXML private TextField notepadNameField;
    @FXML private Button confirmCreationButton;
    @FXML private ListView<Notepad> notepadsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setConfirmPaneVisible(false);

        this.notepadsListView.setItems(FileManager.loadSavedNotepads());

        this.confirmCreationButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                        this.notepadNameField.getText().isBlank(),
                        this.notepadNameField.textProperty()));

        this.openNotepadButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                        this.notepadsListView.getFocusModel().getFocusedItem() == null,
                        this.notepadsListView.getFocusModel().focusedItemProperty()));

        this.notepadNameField.textProperty().addListener((MainMenuController::changed));
    }

    private static void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {

    }

    @FXML
    public void openFileManager() {
    }

    @FXML
    public void openCreationWindow() {
        this.setConfirmPaneVisible(true);
        this.notepadNameField.clear();
    }

    @FXML
    public void closeCreationWindow() {
        this.setConfirmPaneVisible(false);
    }

    @FXML
    public void confirmNotepadCreation() {
        String notepadName = this.notepadNameField.getText();
        FileManager.createNotepad(notepadName).ifPresentOrElse(
                notepad -> App.setScene(Scenes.FILE_HANDLER),
                () -> {
                    Main.getLogger().error("File handler screen cannot be opened because notepad `{}` was not initialized properly", notepadName);
                    this.setConfirmPaneVisible(false);
                });
    }

    private void setConfirmPaneVisible(boolean isVisible) {
        this.creationConfirmPane.setVisible(isVisible);
        this.shadowPane.setVisible(isVisible);
        this.creationConfirmPane.setMouseTransparent(!isVisible);
    }
}
