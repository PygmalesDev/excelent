package net.pygmales.excelent.scene.custom;

import javafx.scene.shape.Rectangle;
import net.pygmales.excelent.record.CalendarEntryData;
import org.controlsfx.control.GridCell;

import java.util.Objects;

public class CalendarEntry extends GridCell<CalendarEntryData> {
    private final Rectangle rectangle = new Rectangle(40, 40);

    public CalendarEntry() {
        this.setGraphic(this.rectangle);
    }

    @Override
    protected void updateItem(CalendarEntryData data, boolean empty) {
        super.updateItem(data, empty);
        if (Objects.isNull(data)) return;

        this.rectangle.setFill(data.color());
    }
}
