package com.elephant.utils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.TimeUnit;

public class EventHelper {

    public static long calculateDeadline(int eventType, int day) {

        LocalDateTime deadline = null;
        if (eventType == 1) {
            deadline = LocalDate.now().atTime(23, 59, 59);
        } else if (eventType == 4) {
            deadline = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.of(day))).atTime(23, 59, 59);
        }
        Duration duration = Duration.between(LocalDateTime.now(), deadline);
        long seconds = duration.getSeconds();
        long days = TimeUnit.SECONDS.toDays(seconds);
        if (days == 7) {
            long secondsInAWeek = 7L * 24L * 60L * 60L;
            seconds = seconds -secondsInAWeek;
        }

        return seconds;
    }

    public static String formatSeconds(long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0 && days != 7) {
            sb.append(days).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("小时");
        }
        if (minutes > 0 ) {
            sb.append(minutes).append("分钟");
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        long seconds = calculateDeadline(4, 5);
        String r = formatSeconds(seconds);
       System.out.println(r);
    }

}
