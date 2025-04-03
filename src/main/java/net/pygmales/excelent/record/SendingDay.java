package net.pygmales.excelent.record;

import net.pygmales.excelent.util.calendar.SendingDayType;

import java.util.List;

public record SendingDay(
        SendingDayType type,
        List<Sending> sendingsOfDay,
        int day
) {

    public static SendingDay asEmpty() {
        return new SendingDay(SendingDayType.EMPTY, null, -1);
    }
}
