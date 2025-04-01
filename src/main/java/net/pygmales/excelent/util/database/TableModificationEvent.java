package net.pygmales.excelent.util.database;

public enum TableModificationEvent {
    INSERT("INSERT"),
    UPDATE("UPDATE");

    private final String modificationEvent;

    TableModificationEvent(String modificationEvent) {
        this.modificationEvent = modificationEvent;
    }

    public String get() {
        return this.modificationEvent;
    }
}
