package me.hellofwy.v2ex.util;

import java.util.Date;

/**
 * Created by fwy on 2016/9/29.
 */

public class LatestTimeFormat {
    public static String format(Long utcTime) {
        long cTime = (new Date()).getTime();
        long dSecond = cTime/1000 - utcTime;
        if(dSecond < 1) {
            return "刚刚";
        }
        if(dSecond < 60) {
            return dSecond + " 秒前";
        }

        long dMinute = dSecond/60;
        if(dMinute < 60) {
            return dMinute + " 分钟前";
        }
        long dHour = dMinute/60;
        if(dHour < 24) {
            return dHour + " 小时前";
        }
        long dDay = dHour/24;
        if(dDay < 365) {
            return dDay + " 天前";
        }
        long dYear = dDay/365;
        return dYear + " 年前";
    }
}
