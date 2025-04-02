package net.pygmales.excelent.controller.element;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import net.pygmales.excelent.record.Sending;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SendingListCell extends ListCell<Sending> {
    private final Pane mainPane = new Pane();
    private final Label companyNameLabel = new Label();
    private final Label trackNumberLabel = new Label();
    private final Label maxDateLabel = new Label();

    public SendingListCell() {
        mainPane.getChildren().addAll(companyNameLabel, trackNumberLabel, maxDateLabel);
        companyNameLabel.setLayoutX(14);
        companyNameLabel.setLayoutY(11);

        trackNumberLabel.setLayoutX(14);
        trackNumberLabel.setLayoutY(32);

        maxDateLabel.setLayoutX(130);
        maxDateLabel.setLayoutY(32);

        setGraphic(mainPane);
    }

    @Override
    protected void updateItem(Sending sending, boolean empty) {
        if (empty) return;

        mainPane.setPrefSize(getListView().getWidth()-16, 60);
        companyNameLabel.setText(sending.companyName());
        if (!sending.trackNumber().isEmpty())
            trackNumberLabel.setText(sending.trackNumber());
        else
            trackNumberLabel.setText(sending.driverTrackNumber());

        maxDateLabel.setText(sending.messageAnswerDaysMax().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        setGraphic(mainPane);
    }
}
