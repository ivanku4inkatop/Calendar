package com.example.calendar.TodayTasks;

import java.util.Calendar;

public class NowData {
   private static Calendar calendar;

   public static Calendar getDate(){
       return Calendar.getInstance();
   }

    public static int getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SUNDAY) {
            return 6;
        } else {
            return dayOfWeek - 2;
        }
    }

    public static Calendar fromHourMinute(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        return c;
    }

}
