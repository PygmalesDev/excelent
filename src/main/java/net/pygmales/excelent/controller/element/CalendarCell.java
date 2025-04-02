package net.pygmales.excelent.controller.element;

import javafx.scene.layout.Pane;
import net.pygmales.excelent.record.CalendarCellData;
import net.pygmales.excelent.common.Scenes;
import org.controlsfx.control.GridCell;

public class CalendarCell extends GridCell<CalendarCellData> {
    private final Pane root = new Pane();

    public CalendarCell() {
        this.root.setPrefSize(60, 60);
        this.setGraphic(root);
    }

    @Override
    protected void updateItem(CalendarCellData data, boolean empty) {
        super.updateItem(data, empty);
        if (empty) return;

        //this.root.getStylesheets().add(data.color());
        this.setGraphic(Scenes.CALENDAR_ENTRY.get().getRoot());
    }
}
