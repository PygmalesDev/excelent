package net.pygmales.excelent.service;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AnswerDateService {
    public static LocalDate getMaxAnswerDay(LocalDate from, int workDays) {
        LocalDate next = from;
        while (workDays != 0) {
            next = from.plusDays(1);
            DayOfWeek day = next.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;
            workDays--;
        }
        return next;
    }
}
