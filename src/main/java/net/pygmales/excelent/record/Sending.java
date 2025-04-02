package net.pygmales.excelent.record;

import java.time.LocalDate;

public record Sending(
        String companyName,
        String trackNumber,
        String driverTrackNumber,
        LocalDate messageReceivedDate,
        int messageAnswerTimeDays,
        LocalDate messageAnswerDaysMax,
        String phoneNumber,
        int openStatus
) {}
