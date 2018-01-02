package com.yeyue.bizhi.utils;

import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {
    public static final TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
    public static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

    private static final long THREDDAY = 172800000;
    private static final long ONEDAY = 86400000;
    public static final int SHOW_TYPE_SIMPLE = 0;
    public static final int SHOW_TYPE_COMPLEX = 1;
    public static final int SHOW_TYPE_ALL = 2;
    public static final int SHOW_TYPE_CALL_LOG = 3;
    public static final int SHOW_TYPE_CALL_DETAIL = 4;

    /**
     * 获取当前当天日期的毫秒数 2012-03-21的毫秒数
     *
     * @return
     */
    public static long getCurrentDayTime() {
        Date d = new Date(System.currentTimeMillis());
        String formatDate = yearFormat.format(d);
        try {
            return (yearFormat.parse(formatDate)).getTime();
        } catch (ParseException e) {
        }
        return 0;
    }

    public static String formatDate(int year, int month, int day) {
        Date d = new Date(year - 1900, month, day);
        return yearFormat.format(d);
    }

    public static long getDateMills(int year, int month, int day) {
        //Date d = new Date(year, month, day);
        // 1960 4 22
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        calendar.setTimeZone(tz);
        return calendar.getTimeInMillis();
    }

    public static String getDateString(long time, int type) {
        Calendar c = Calendar.getInstance();
        c = Calendar.getInstance(tz);
        c.setTimeInMillis(time);
        long currentTime = System.currentTimeMillis();
        Calendar current_c = Calendar.getInstance();
        current_c = Calendar.getInstance(tz);
        current_c.setTimeInMillis(currentTime);

        int currentYear = current_c.get(Calendar.YEAR);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        long t = currentTime - time;
        long t2 = currentTime - getCurrentDayTime();
        String dateStr = "";
        if (t < 300000) {
            return "刚刚";
        } else if (t >= 60000 && t < 3600000) {
            return t / 60000 + "分钟前";
        }

        if (t < t2 && t > 0) {
            if (type == SHOW_TYPE_SIMPLE) {
                dateStr = (hour < 10 ? "0" + hour : hour) + ":"
                        + (minute < 10 ? "0" + minute : minute);
            } else if (type == SHOW_TYPE_COMPLEX) {
                dateStr =  (hour < 10 ? "0" + hour : hour) + ":"
                        + (minute < 10 ? "0" + minute : minute);
            } else if (type == SHOW_TYPE_CALL_LOG) {
                dateStr =  (hour < 10 ? "0" + hour : hour) + ":"
                        + (minute < 10 ? "0" + minute : minute);
            } else if (type == SHOW_TYPE_CALL_DETAIL) {
                dateStr = "今天  ";
            }else {
                dateStr = (hour < 10 ? "0" + hour : hour) + ":"
                        + (minute < 10 ? "0" + minute : minute) + ":"
                        + (second < 10 ? "0" + second : second);
            }
        } else if (t < (t2 + THREDDAY) && t > 0) {
            if(t<=(t2+ONEDAY)){
                dateStr = "昨天 ";
            }else{
                dateStr = "前天 ";
            }
            dateStr =  dateStr+(hour < 10 ? "0" + hour : hour) + ":"
                    + (minute < 10 ? "0" + minute : minute);
        } else if (y == currentYear) {
            if (type == SHOW_TYPE_SIMPLE) {
                dateStr = (m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d);
            } else if (type == SHOW_TYPE_COMPLEX) {
                dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
                        + "日";
            } else if (type == SHOW_TYPE_CALL_LOG || type == SHOW_TYPE_COMPLEX) {
                dateStr = (m < 10 ? "0" + m : m) + /* 月 */"/"
                        + (d < 10 ? "0" + d : d);
            } else if (type == SHOW_TYPE_CALL_DETAIL) {
                dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
                        + (d < 10 ? "0" + d : d);
            } else {
                dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
                        + "日 ";
            }
        } else {
            if (type == SHOW_TYPE_SIMPLE) {
                dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
                        + (d < 10 ? "0" + d : d);
            } else if (type == SHOW_TYPE_COMPLEX ) {
                dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
                        + (d < 10 ? "0" + d : d) + "日";
            } else if (type == SHOW_TYPE_CALL_LOG || type == SHOW_TYPE_COMPLEX) {
                dateStr = y + /* 年 */"/" + (m < 10 ? "0" + m : m) + /* 月 */"/"
                        + (d < 10 ? "0" + d : d) + /* 日 */"  "/*
																 * + (hour < 10
																 * ? "0" + hour
																 * : hour) + ":"
																 * + (minute <
																 * 10 ? "0" +
																 * minute :
																 * minute)
																 */;
            } else if (type == SHOW_TYPE_CALL_DETAIL) {
                dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
                        + (d < 10 ? "0" + d : d);
            } else {
                dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
                        + (d < 10 ? "0" + d : d) + "日 "
                        + (hour < 10 ? "0" + hour : hour) + ":"
                        + (minute < 10 ? "0" + minute : minute) + ":"
                        + (second < 10 ? "0" + second : second);
            }
        }
        return dateStr;
    }

    /**
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static long getActiveTimelong(String result) {
        try {
            Date parse = yearFormat.parse(result);
            return parse.getTime();
        } catch (ParseException e) {
            LogUtils.e(e.getMessage());
        }
        return System.currentTimeMillis();
    }

    public static String getDefaultFormat() {
        return getDateFormat(sequenceFormat);
    }

    public static String getDateFormat(DateFormat df) {
        return getDateFormat(System.currentTimeMillis(), df);
    }

    public static String getDateFormat(long time, DateFormat df) {
        Date d = new Date(time);
        return df.format(d);
    }
    public static final SimpleDateFormat sequenceFormat = new SimpleDateFormat(
            "yyyyMMddHHmmss",Locale.CHINA);

    public static long getMilliSeconds(String reqtime){
        try {
            long time = TimeUtils.sequenceFormat.parse(reqtime).getTime();
            return time;
        } catch (ParseException e) {
            LogUtils.e(e.getMessage());
        }
        return 0;
    }

    public static String toGMTString(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss 'GMT'", Locale.US);
        TimeZone gmtZone = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(gmtZone);
        GregorianCalendar gc = new GregorianCalendar(gmtZone);
        gc.setTimeInMillis(milliseconds);
        return sdf.format(new Date(milliseconds));
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        return sf.format(d);
    }

    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);

        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }
    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        return format.format(date);
    }

    public static String getYearMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM",Locale.CHINA);
        return format.format(date);
    }
}
