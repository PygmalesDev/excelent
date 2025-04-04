package net.pygmales.excelent.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.pygmales.excelent.record.Sending;
import net.pygmales.excelent.record.SendingDay;
import net.pygmales.excelent.util.calendar.SendingDayType;

import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CalendarService {
    public static LocalDate getMaxAnswerDay(LocalDate from, int workDays) {
        while (workDays != 0) {
            from = from.plusDays(1);
            DayOfWeek day = from.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;
            workDays--;
        }
        return from;
    }

    public static ObservableList<SendingDay> getCalendarDaysForMonth(YearMonth month, List<Sending> sendings) {
        ObservableList<SendingDay> sendingDays = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();

        List<Sending> sendingsForMonth = sendings.stream()
                .filter(sending -> Objects.equals(sending.messageAnswerDate().getMonth(), month.getMonth())
                                && Objects.equals(sending.messageAnswerDate().getYear(), month.getYear()))
                .toList();

        int emptyDays = month.atDay(1).getDayOfWeek().getValue()-1;
        IntStream.range(0, emptyDays).forEach(i -> sendingDays.add(SendingDay.asEmpty()));

        IntStream.range(1, month.lengthOfMonth()+1).forEach(i -> {
            LocalDate day = month.atDay(i);
            DayOfWeek dayOfWeek = day.getDayOfWeek();
            List<Sending> sendingsForDay = sendingsForMonth.stream()
                    .filter(sending -> sending.messageAnswerDate().getDayOfMonth() == i)
                    .toList();
            if (Objects.equals(day, today))
                sendingDays.add(new SendingDay(SendingDayType.CURRENT, sendingsForDay, i));
            else if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY))
                sendingDays.add(new SendingDay(SendingDayType.WEEKEND, sendingsForDay, i));
            else
                sendingDays.add(new SendingDay(SendingDayType.NORMAL, sendingsForDay, i));
        });

        IntStream.range(0, 42-sendingDays.size()).forEach(i -> sendingDays.add(SendingDay.asEmpty()));

        return sendingDays;
    }
}
