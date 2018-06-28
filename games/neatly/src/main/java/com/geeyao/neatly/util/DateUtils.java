package com.geeyao.neatly.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static long timeDiff(Date date1, Date date2){
        return Math.abs(date1.getTime() - date2.getTime()) / 1000;
    }

    public static void main(String[] args){
        Calendar c = Calendar.getInstance();
        Date date1 = c.getTime();
        c.add(Calendar.HOUR, 1);
        Date date2 = c.getTime();
        long a = timeDiff(date1, date2);
        System.out.println(a / 1000);

        int d = 52;
        int b = 60;
        System.out.println(d > b ?b:d);
    }

}
