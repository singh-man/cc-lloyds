package com.lloyds.time.bean;

import com.lloyds.time.exception.HumanReadTimeException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class HumanReadableTime implements IHumanReadableTime {
    // Generate Time in words.
    private String processTime(int hour, int minute) {
        String humanReadTime = "";
        hour = hour > 12 ? hour - 12 : hour;
        if (minute == 0)
            humanReadTime = clock[hour] + " o'clock";

        else if (minute == 15)
            humanReadTime = "Quarter past " + clock[hour].toLowerCase();

        else if (minute == 30)
            humanReadTime = "Half past " + clock[hour].toLowerCase();

        else if (minute == 45)
            humanReadTime = "Quarter to " + clock[(hour % 12) + 1].toLowerCase();

        else if (minute >= 1 && minute <= 29)
            humanReadTime = clock[minute] + " past " + clock[hour].toLowerCase();

        else if (minute > 30 && minute < 60)
            humanReadTime = clock[60 - minute] + " to " + clock[hour + 1].toLowerCase();

        return humanReadTime;
    }


    @Override
    public String getTime() {
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String currentTIme = dtfTime.format(localTime);
        String[] split = currentTIme.split(":");
        return processTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    public String getTime(int hour, int minute) {
        if (hour < 0 || hour > 24) throw new HumanReadTimeException("Invalid hour");
        if (minute < 0 || minute > 60) throw new HumanReadTimeException("Invalid minutes");
        return processTime(hour, minute);
    }
}
