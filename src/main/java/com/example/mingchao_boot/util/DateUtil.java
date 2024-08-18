package com.example.mingchao_boot.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String SimpleDate(Date date){
        // 创建一个SimpleDateFormat对象，并定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 使用format方法格式化日期
        String formattedDate = dateFormat.format(date);
        //返回日期
        return formattedDate;
    }

    public static String SimpleLocalDateTime(LocalDateTime dateTime){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 使用format方法格式化日期
        String formattedDate = dateFormat.format(dateTime);
        //返回日期
        return formattedDate;
    }
}
