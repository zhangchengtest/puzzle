package com.elephant.utils;

import com.cunw.framework.vo.PageList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConvertUtils {

    public static <T, U, R> List<R> mapping(List<T> sourceList, BiFunction<T, U, R> converter, U data) {
        List<R> resultList = new ArrayList<>();
        for (T source : sourceList) {
            R result = converter.apply(source,data);
            resultList.add(result);
        }
        return resultList;
    }

    public static  <T, U, R> PageList<R> mapping(PageList<T> sourceList, BiFunction<T, U, R> converter, U data) {
        PageList<R> resultList = new PageList<>();
        resultList.setPageNum(sourceList.getPageNum());
        resultList.setPageSize(sourceList.getPageSize());
        resultList.setSize(sourceList.getSize());
        resultList.setStartRow(sourceList.getStartRow());
        resultList.setEndRow(sourceList.getEndRow());
        resultList.setPages(sourceList.getPages());
        resultList.setPrePage(sourceList.getPrePage());
        resultList.setNextPage(sourceList.getNextPage());
        resultList.setTotal(sourceList.getTotal());
        resultList.setList(mapping(sourceList.getList(), converter, data));
        return resultList;
    }


}
