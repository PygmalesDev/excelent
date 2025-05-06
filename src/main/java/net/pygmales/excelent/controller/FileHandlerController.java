package net.pygmales.excelent.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import net.pygmales.excelent.App;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.common.StyleSheets;
import net.pygmales.excelent.element.cell.SendingDayGridCell;
import net.pygmales.excelent.element.cell.SendingListCell;
import net.pygmales.excelent.record.Sending;
import net.pygmales.excelent.record.SendingDay;
import net.pygmales.excelent.service.CalendarService;
import net.pygmales.excelent.service.DatabaseService;
import net.pygmales.excelent.service.Storage;
import net.pygmales.excelent.util.database.SendingStatus;
import org.controlsfx.control.GridView;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class FileHandlerController implements Initializable {
    private static final Storage STORAGE = Storage.getInstance();
    private static final DatabaseService DATABASE = DatabaseService.getInstance();

    private final GridView<SendingDay> calendarGridView = new GridView<>();
    private final ObservableList<Sending> sendings = STORAGE.getSendingsObservableList();
    private YearMonth currentMonth = YearMonth.now();

    @FXML private Label companyNameLabel;
    @FXML private Label messageReceivedDateLabel;
    @FXML private Label messageAnswerDayMaxLabel;
    @FXML private Label trackNumberLabel;
    @FXML private Label openStatusLabel;
    @FXML private Label phoneNumberLabel;
    @FXML private Label driverTrackNumberLabel;

    @FXML private Text informationText;

    @FXML private Button confirmNewSendingButton;
    @FXML private TextField companyNameText;
    @FXML private DatePicker messageSentDate;
    @FXML private TextField trackNumberText;
    @FXML private Spinner<Integer> messageCheckSpinner;
    @FXML private TextField phoneNumberText;
    @FXML private ListView<Sending> sendingsListView;
    @FXML private AnchorPane sendingPane;

    @FXML private Pane shadowPane;
    @FXML private AnchorPane newSendingPane;
    @FXML private Label fileNameLabel;

    @FXML private AnchorPane calendarAnchorPane;
    @FXML private Tab calendarTab;
    @FXML private Label calendarLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setNewSendingWindowVisible(false);
        this.fileNameLabel.setText(STORAGE.getNotepad().name());
        this.messageCheckSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 7));

        this.sendingsListView.setItems(this.sendings);
        this.sendingsListView.setCellFactory(sendingListView -> new SendingListCell());
        this.sendingsListView.getFocusModel().focusedItemProperty().addListener((this::onSendingSelected));
        if (!this.sendings.isEmpty()) {
            Sending firstSending = this.sendings.getFirst();
            this.fillSendingPane(firstSending);
            this.sendingsListView.getSelectionModel().select(firstSending);
        }

        this.calendarGridView.getStylesheets().add(StyleSheets.CALENDAR);
        this.calendarGridView.setCellFactory(sendingDayGridView -> new SendingDayGridCell());
        this.calendarAnchorPane.getChildren().add(this.calendarGridView);
        this.calendarTab.setOnSelectionChanged(event -> this.updateCalendar());

        this.confirmNewSendingButton.disableProperty().bind(Bindings.createBooleanBinding(
                () -> this.companyNameText.getText().isBlank(),
                this.companyNameText.textProperty()));
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
        this.clearNewSendingFields();
    }

    private void clearNewSendingFields() {
        this.messageSentDate.setValue(LocalDate.now());
        this.messageCheckSpinner.getValueFactory().setValue(7);
        this.companyNameText.clear();
        this.trackNumberText.clear();
        this.phoneNumberText.clear();
    }

    @FXML
    public void closeNewSendingWindow() {
        this.setNewSendingWindowVisible(false);
    }

    @FXML
    public void confirmSendingCreation() {
        LocalDate msgSentDate = this.messageSentDate.getValue();
        int maxWorkDays = this.messageCheckSpinner.getValue();
        Sending sending = new Sending(
                this.companyNameText.getText().strip(),
                this.trackNumberText.getText().strip(),
                "",

                msgSentDate, maxWorkDays, CalendarService.getMaxAnswerDay(msgSentDate, maxWorkDays),

                msgSentDate, maxWorkDays, CalendarService.getMaxAnswerDay(msgSentDate, maxWorkDays),

                this.phoneNumberText.getText().strip(), "", SendingStatus.SENT);

        if (DATABASE.createSending(sending)) {
            this.setNewSendingWindowVisible(false);
            this.sendingsListView.getSelectionModel().select(sending);
        }
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
        this.openStatusLabel.setText(sending.status().toString());
        this.messageReceivedDateLabel.setText(sending.messageReceivedDate()
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        this.messageAnswerDayMaxLabel.setText(sending.messageAnswerDate()
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        this.trackNumberLabel.setText(sending.trackNumber());
        this.driverTrackNumberLabel.setText(sending.driverTrackNumber());

    }

    private void closeSendingPane() {
        this.sendingPane.setVisible(false);
        this.informationText.setVisible(true);
    }

    private void updateCalendar() {
        this.calendarGridView.setItems(CalendarService.getCalendarDaysForMonth(this.currentMonth, this.sendings));
        this.calendarLabel.setText(String.format("%s", this.currentMonth.toString()));
    }

    @FXML
    private void incrementMonth() {
        this.currentMonth = this.currentMonth.plusMonths(1);
        this.updateCalendar();
    }

    @FXML
    public void decrementMonth() {
        this.currentMonth = this.currentMonth.minusMonths(1);
        this.updateCalendar();
    }

    @FXML
    private void deleteSending() {
        if (DATABASE.deleteSending(this.sendingsListView.getSelectionModel().getSelectedItem())) {
            if (this.sendings.isEmpty()) this.closeSendingPane();
        }
    }

}
