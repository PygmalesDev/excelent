package net.pygmales.excelent.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.pygmales.excelent.App;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.controller.element.CalendarGridView;
import net.pygmales.excelent.controller.element.SendingListCell;
import net.pygmales.excelent.record.CalendarCellData;
import net.pygmales.excelent.record.Sending;
import net.pygmales.excelent.service.AnswerDateService;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.Storage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class FileHandlerController implements Initializable {

    private static final Storage STORAGE = Storage.getInstance();
    private static final DatabaseService DATABASE = DatabaseService.getInstance();
    private final ObservableList<CalendarCellData> calendarList = FXCollections.observableArrayList();
    
    @FXML private Label companyNameLabel;
    @FXML private Label messageReceivedDateLabel;
    @FXML private Label messageAnswerDayMaxLabel;
    @FXML private Label trackNumberLabel;
    @FXML private Label openStatusLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label driverTrackNumberLabel;

    @FXML private Text informationText;

    @FXML private TextField companyNameText;
    @FXML private DatePicker messageReceivedDate;
    @FXML private TextField trackNumberText;
    @FXML private TextField driverTrackNumberText;
    @FXML private Spinner<Integer> maxWorkDaysSpinner;
    @FXML private TextField phoneNumberText;
    @FXML private ListView<Sending> sendingsListView;
    @FXML private AnchorPane sendingPane;

    @FXML private Pane shadowPane;
    @FXML private AnchorPane newSendingPane;
    @FXML private AnchorPane calendarPane;
    @FXML private Label fileNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setNewSendingWindowVisible(false);
        this.fileNameLabel.setText(STORAGE.getNotepad().name());
        this.maxWorkDaysSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        this.sendingsListView.setItems(STORAGE.getSendingsObservableList());
        this.sendingsListView.setCellFactory(sendingListView -> new SendingListCell());
        this.sendingsListView.getFocusModel().focusedItemProperty().addListener((this::onSendingSelected));

        this.calendarPane.getChildren().add(new CalendarGridView(this.calendarList));
        this.calendarList.addAll(new CalendarCellData(Color.RED), new CalendarCellData(Color.BLACK));

    }

    private void onSendingSelected(ObservableValue<? extends Sending> observableValue, Sending oldSending, Sending newSending) {
        if (Objects.nonNull(newSending)) this.fillSendingPane(newSending);
    }

    @FXML
    public void returnToMainScreen() {
        App.setScene(Scenes.MAIN_MENU);
    }

    @FXML
    public void openNewSendingWindow() {
        this.setNewSendingWindowVisible(true);
        this.messageReceivedDate.setValue(LocalDate.now());
    }

    @FXML
    public void closeNewSendingWindow() {
        this.setNewSendingWindowVisible(false);
    }

    @FXML
    public void confirmSendingCreation() {
        LocalDate msgReceivedDate = this.messageReceivedDate.getValue();
        int maxWorkDays = this.maxWorkDaysSpinner.getValue();
        Sending sending = new Sending(
                this.companyNameText.getText().strip(),
                this.trackNumberText.getText().strip(),
                this.driverTrackNumberText.getText().strip(),
                msgReceivedDate, maxWorkDays,
                AnswerDateService.getMaxAnswerDay(msgReceivedDate, maxWorkDays),
                this.phoneNumberText.getText().strip(),
                0);

        if (DATABASE.createSendingEntry(sending)) this.setNewSendingWindowVisible(false);
    }

    private void setNewSendingWindowVisible(boolean isVisible) {
        this.shadowPane.setVisible(isVisible);
        this.newSendingPane.setVisible(isVisible);
        this.newSendingPane.setMouseTransparent(!isVisible);
    }

    private void fillSendingPane(Sending sending) {
        this.sendingPane.setVisible(true);
        this.informationText.setVisible(false);

        this.companyNameLabel.setText(sending.companyName());
        this.phoneNumberLabel.setText(sending.phoneNumber());
        this.openStatusLabel.setText(""+sending.openStatus());
        this.messageReceivedDateLabel.setText(sending.messageReceivedDate()
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        this.messageAnswerDayMaxLabel.setText(sending.messageAnswerDaysMax()
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        this.trackNumberLabel.setText(sending.trackNumber());
        this.driverTrackNumberLabel.setText(sending.driverTrackNumber());

    }

}
