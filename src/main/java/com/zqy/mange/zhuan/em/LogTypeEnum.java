package com.zqy.mange.zhuan.em;

import java.util.LinkedHashMap;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/8 13:12
 * 描述: 日志类型 （写入到数据库）
 */
public class LogTypeEnum extends Enum<Integer, String> {
    private static LogTypeEnum mInstance;
    public final static String DEBUG = "DEBUG";
    public final static String INFO = "INFO";
    public final static String ERROR = "ERROR";


    private LogTypeEnum() {
        super();
    }

    public static LogTypeEnum i() {
        if (mInstance == null) {
            synchronized (LogTypeEnum.class) {
                if (mInstance == null) {
                    mInstance = new LogTypeEnum();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected LinkedHashMap<Integer, String> addEnum() {
        LinkedHashMap<Integer, String> kvMap = new LinkedHashMap<>();
        kvMap.put(1, DEBUG);
        kvMap.put(2, INFO);
        kvMap.put(2, ERROR);
        return kvMap;
    }


}
