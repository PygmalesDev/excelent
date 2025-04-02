package net.pygmales.excelent.service;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AnswerDateService {
    public static LocalDate getMaxAnswerDay(LocalDate from, int workDays) {
        while (workDays != 0) {
            from = from.plusDays(1);
            DayOfWeek day = from.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) continue;
            workDays--;
        }
        return from;
    }
}
