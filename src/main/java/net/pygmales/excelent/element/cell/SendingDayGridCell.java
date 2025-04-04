package net.pygmales.excelent.element.cell;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import net.pygmales.excelent.element.tooltip.SendingDayTooltip;
import net.pygmales.excelent.record.SendingDay;
import net.pygmales.excelent.util.calendar.SendingDayType;
import org.controlsfx.control.GridCell;

import java.util.Objects;

public class SendingDayGridCell extends GridCell<SendingDay> {
    private final Tooltip tooltip = new SendingDayTooltip();
    private final Pane root = new Pane();
    private final Label dayLabel = new Label();

    private final Circle sendingsCircle = new Circle();
    private final Label sendingsLabel = new Label();

    public SendingDayGridCell() {
        this.root.setPrefSize(60, 60);
        this.root.getChildren().addAll(
                this.sendingsCircle,
                this.sendingsLabel,
                this.dayLabel
        );

        this.setTooltip(this.tooltip);

        this.dayLabel.getStyleClass().add("day-label");
        this.dayLabel.setLayoutX(40);
        this.dayLabel.setLayoutY(40);

        this.sendingsCircle.getStyleClass().add("circle");
        this.sendingsCircle.setRadius(14);
        this.sendingsCircle.setLayoutX(20);
        this.sendingsCircle.setLayoutY(20);
        this.sendingsCircle.setVisible(false);

        this.sendingsLabel.getStyleClass().add("sendings-label");
        this.sendingsLabel.setLayoutX(13);
        this.sendingsLabel.setLayoutY(12);
        this.sendingsLabel.setVisible(false);

        this.setMouseTransparent(true);

        this.setGraphic(this.root);
    }

    @Override
    protected void updateItem(SendingDay sendingDay, boolean empty) {
        if (empty) return;

        SendingDayType type = sendingDay.type();

        this.getStyleClass().clear();
        this.getStyleClass().addAll("grid-cell", type.getStyleClass());

        if (Objects.equals(type, SendingDayType.EMPTY)) return;
        this.setMouseTransparent(false);

        this.dayLabel.setText("" + sendingDay.day());

        if (sendingDay.sendingsOfDay().isEmpty()) return;

        this.sendingsCircle.setVisible(true);
        this.sendingsLabel.setVisible(true);
        this.sendingsLabel.setText(""+sendingDay.sendingsOfDay().size());


    }
}
