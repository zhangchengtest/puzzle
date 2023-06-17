package com.elephant.utils;

import org.apache.commons.lang.time.DateUtils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
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


    public static Date subtractSecondsFromDate(Date currentDate, long seconds, int days) {
        // 将Date转换为时间戳，并减去指定的秒数
        long currentTimeInMillis = currentDate.getTime();
        long newTimeInMillis = currentTimeInMillis - (seconds * 1000L); //Remeber to convert to millis

        // 使用新的时间戳创建新的Date对象
        Date newDate = new Date();
        newDate.setTime(newTimeInMillis);


        // 假设需要去掉时间部分的日期为date

//Date -> LocalDate
        LocalDate localDate = newDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

//去掉时间部分
        LocalDateTime dateWithoutTime = localDate.atStartOfDay();

//LocalDate -> Date
        Date finalDate =  Date.from(dateWithoutTime.atZone(ZoneId.systemDefault()).toInstant());
        if(days > 0){
             finalDate = DateUtils.addDays(finalDate, days);
        }

        return finalDate;
    }


    public static void main(String[] args) {
        int day = 5;
        long seconds = calculateDeadline(4, day);
        String r = formatSeconds(seconds);
        Date ss = getWeek(day);

        System.out.println(ss);
    }


    public static Date getLastWeek(Date currentDate) {


        //Date -> LocalDate
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

//去掉时间部分
        LocalDateTime dateWithoutTime = localDate.atStartOfDay();

//LocalDate -> Date
        Date finalDate =  Date.from(dateWithoutTime.atZone(ZoneId.systemDefault()).toInstant());
        currentDate = DateUtils.addDays(finalDate, -6);

        return currentDate;
    }


    public static Date getWeek(int week) {
        week = week + 1;
        if(week > 7){
            week = week -1;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, week);
        Date startDate = getLastWeek(cal.getTime());

        return startDate;
    }


}
