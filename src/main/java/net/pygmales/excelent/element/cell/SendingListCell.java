package net.pygmales.excelent.element.cell;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import net.pygmales.excelent.record.Sending;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class SendingListCell extends ListCell<Sending> {
    private final Pane root = new Pane();
    private final Label companyNameLabel = new Label();
    private final Label trackNumberLabel = new Label();
    private final Label maxDateLabel = new Label();

    public SendingListCell() {
        root.getChildren().addAll(companyNameLabel, trackNumberLabel, maxDateLabel);

        root.setPrefSize(180, 60);

        companyNameLabel.setLayoutX(14);
        companyNameLabel.setLayoutY(11);

        trackNumberLabel.setLayoutX(14);
        trackNumberLabel.setLayoutY(32);

        maxDateLabel.setLayoutX(110);
        maxDateLabel.setLayoutY(32);

        setGraphic(root);
    }

    @Override
    protected void updateItem(Sending sending, boolean empty) {
        super.updateItem(sending, empty);

        if (empty) {
            setGraphic(null);
            return;
        }

        this.root.getStyleClass().clear();
        this.root.getStyleClass().addAll("sending", "sending-open");

        companyNameLabel.setText(sending.companyName());
        if (!sending.trackNumber().isEmpty())
            trackNumberLabel.setText(sending.trackNumber());
        else
            trackNumberLabel.setText(sending.driverTrackNumber());

        maxDateLabel.setText(sending.messageAnswerDaysMax().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
        setGraphic(this.root);
    }
}
