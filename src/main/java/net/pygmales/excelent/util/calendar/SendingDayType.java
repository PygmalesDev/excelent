package net.pygmales.excelent.util.calendar;

public enum SendingDayType {
    EMPTY("cell-empty"),
    CURRENT("cell-current"),
    NORMAL("cell-normal"),
    WEEKEND("cell-weekend");

    private final String styleClass;

    SendingDayType(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
