package com.zqy.mange.zhuan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/8 23:42
 * 描述: 格式化时间工具
 */
public class TimeUtil {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param pattern 模式
     * @param time    时间戳 单位毫秒
     * @return
     */
    public static String getTime(String pattern, long time) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);//设置日期格式
        String format = df.format(new Date(time));
        return format;
    }

    /**
     * 获取默认格式化时间
     *
     * @return
     */
    public static String getDefaultTime() {
        return getTime(DEFAULT_PATTERN);
    }

    /**
     * @param pattern
     * @return
     */
    public static String getTime(String pattern) {
        return getTime(pattern, System.currentTimeMillis());
    }

    /**
     * @param time
     * @return
     */
    public static String getTime(long time) {
        return getTime(DEFAULT_PATTERN, time);
    }
}
