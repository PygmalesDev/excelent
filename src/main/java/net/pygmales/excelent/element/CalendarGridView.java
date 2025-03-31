package net.pygmales.excelent.element;

import javafx.collections.ObservableList;
import net.pygmales.excelent.common.Styles;
import net.pygmales.excelent.record.CalendarCellData;
import org.controlsfx.control.GridView;

public class CalendarGridView extends GridView<CalendarCellData> {

    public CalendarGridView(ObservableList<CalendarCellData> items) {
        super(items);

        this.setCellFactory(gridView -> new CalendarCell());
        this.getStyleClass().add(Styles.CALENDAR);

        setLayoutX(100);
        setLayoutY(100);
        setCellHeight(60);
        setCellWidth(60);
        setPrefWidth(420);
        setCellHeight(360);
    }
}
