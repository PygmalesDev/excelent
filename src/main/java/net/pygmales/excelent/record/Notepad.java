package net.pygmales.excelent.record;

import java.time.LocalDateTime;

public record Notepad(
        String name,
        String tableID,
        LocalDateTime lastEdited
) {
    public Notepad updateEditTime() {
        return new Notepad(this.name, this.tableID, LocalDateTime.now());
    }

}
