package com.elephant.test;

import cn.hutool.core.date.DateUtil;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {


        System.out.println(   DateUtil.offsetMonth(new Date(), 4)); // 输出一年后的时间
    }

    public static Date addOneYearToDate(Date date) {
        // 将Date对象转换为LocalDate对象
        LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 创建一个表示一年的Period对象
        Period oneYear = Period.ofYears(1);

        // 将当前日期加上一年的Period对象
        LocalDate expireDate = currentDate.plus(oneYear);

        // 将结果转换为Date类型
        return Date.from(expireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
