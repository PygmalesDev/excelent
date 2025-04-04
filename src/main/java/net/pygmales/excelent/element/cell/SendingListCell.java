package net.pygmales.excelent.element.cell;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import net.pygmales.excelent.common.Scenes;
import net.pygmales.excelent.record.Sending;
import net.pygmales.excelent.util.database.SendingStatus;

import java.time.LocalDate;

public class SendingListCell extends ListCell<Sending> {
    private final Node root = Scenes.SENDING_LIST_CELL.get().getRoot();
    private final ChoiceBox<SendingStatus> sendingStatusBox = (ChoiceBox<SendingStatus>) root.lookup("#sendingStatusBox");
    private final TextField companyNameField = (TextField) root.lookup("#companyNameField");
    private final TextField phoneNumberField = (TextField) root.lookup("#phoneNumberField");
    private final TextField trackNumberField = (TextField) root.lookup("#trackNumberField");
    private final TextField driverTrackNumberField = (TextField) root.lookup("#driverTrackNumberField");
    private final DatePicker msgSentDate = (DatePicker) root.lookup("#msgSentDate");
    private final Spinner<Integer> msgCheckSpinner = (Spinner<Integer>) root.lookup("#msgCheckSpinner");
    private final TextField msgCheckDateField = (TextField) root.lookup("#msgCheckDateField");
    private final DatePicker msgReceivedDate = (DatePicker) root.lookup("#msgReceivedDate");
    private final Spinner<Integer> msgAnswerSpinner = (Spinner<Integer>) root.lookup("#msgAnswerSpinner");
    private final TextField msgAnswerDateField = (TextField) root.lookup("#msgAnswerDateField");


    public SendingListCell() {
        this.setGraphic(this.root);

        this.sendingStatusBox.setItems(FXCollections.observableArrayList(SendingStatus.values()));
        this.sendingStatusBox.setValue(SendingStatus.SENT);

        this.msgCheckSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 7));
        this.msgAnswerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 7));
        this.msgSentDate.setValue(LocalDate.now());
    }

    @Override
    protected void updateItem(Sending sending, boolean empty) {
        super.updateItem(sending, empty);

        if (empty) {
            setGraphic(null);
            return;
        }

        this.setGraphic(this.root);
    }

}
