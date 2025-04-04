package net.pygmales.excelent.record;

import net.pygmales.excelent.util.database.SendingStatus;

import java.time.LocalDate;

public record Sending(
        String companyName,
        String trackNumber,
        String driverTrackNumber,

        LocalDate messageSentDate,
        int messageCheckDays,
        LocalDate messageCheckDate,

        LocalDate messageReceivedDate,
        int messageAnswerDays,
        LocalDate messageAnswerDate,

        String phoneNumber,
        String notes,
        SendingStatus status
) {

    public static Sending empty() {
        return new Sending(
                null, null, null,
                null, 0, null,
                null, 0, null,
                null, null, null);
    }

}
