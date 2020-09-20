package com.zqy.common.utils;

import com.zqy.common.config.ParamConfig;
import com.zqy.common.em.LogTypeEnum;
import com.zqy.common.model.Log;
import org.springframework.stereotype.Component;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/5 10:30
 * 描述: 同时打印日志到控制台和本地
 */
@Component//实例化到spring容器中
public class LogUtil {
    public static final String TAG = ParamConfig.getInstance().getProjectName();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private LogCallBack logCallBack;

    public void setLogCallBack(LogCallBack logCallBack) {
        this.logCallBack = logCallBack;
    }



    /**
     * 打印日志
     *
     * @param logName 日志名
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void debug(String logName, String message, boolean cache) {
        String format = TimeUtil.getDefaultTime();
        System.out.println(ANSI_WHITE + format + "  " + ANSI_YELLOW + "DEBUG  " + ANSI_BLUE + TAG + logName + " : " + message + ANSI_RESET);
        if (cache) {
//
//            TLog tLog = new TLog();
//            tLog.setLogName(logName);
//            tLog.setLogText(message);
//            tLog.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.DEBUG));
//            tLog.setLogTypeName(LogTypeEnum.DEBUG);
//            tLog.setTimestamp(format);
//            itLogService.writeLogToRedis(tLog);
            if (logCallBack != null) {
                Log log = new Log();
                log.setLogName(logName);
                log.setLogText(message);
                log.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.DEBUG));
                log.setLogTypeName(LogTypeEnum.DEBUG);
                log.setTimestamp(format);
                logCallBack.onLog(log);
            }

        }

    }


    /**
     * 打印日志
     *
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void debug(String message, boolean cache) {
        debug("", message, cache);
    }

    /**
     * 只打印到控制台
     *
     * @param message 日志内容
     */
    public void debug(String message) {
        debug("", message, false);
    }

    /**
     * 打印日志
     *
     * @param logName 日志名
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void info(String logName, String message, boolean cache) {
        String format = TimeUtil.getDefaultTime();
        System.out.println(ANSI_WHITE + format + "  " + ANSI_YELLOW + "INFO  " + ANSI_GREEN + TAG + logName + " : " + message + ANSI_RESET);
        if (cache) {
//            TLog tLog = new TLog();
//            tLog.setLogName(logName);
//            tLog.setLogText(message);
//            tLog.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.INFO));
//            tLog.setLogTypeName(LogTypeEnum.INFO);
//            tLog.setTimestamp(format);
//            itLogService.writeLogToRedis(tLog);
            if (logCallBack != null) {
                Log log = new Log();
                log.setLogName(logName);
                log.setLogText(message);
                log.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.INFO));
                log.setLogTypeName(LogTypeEnum.INFO);
                log.setTimestamp(format);
                logCallBack.onLog(log);
            }
        }
    }

    /**
     * 打印日志
     *
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void info(String message, boolean cache) {
        info("", message, cache);
    }

    /**
     * 只打印到控制台
     *
     * @param message 日志内容
     */
    public void info(String message) {
        info("", message, false);
    }

    /**
     * 打印日志
     *
     * @param logName 日志名
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void error(String logName, String message, boolean cache) {
        String format = TimeUtil.getDefaultTime();
        System.out.println(ANSI_WHITE + format + "  " + ANSI_YELLOW + "ERROR  " + ANSI_RED + TAG + logName + " : " + message + ANSI_RESET);
        if (cache) {
//            TLog tLog = new TLog();
//            tLog.setLogName(logName);
//            tLog.setLogText(message);
//            tLog.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.ERROR));
//            tLog.setLogTypeName(LogTypeEnum.ERROR);
//            tLog.setTimestamp(format);
//            itLogService.writeLogToRedis(tLog);

            if (logCallBack != null) {
                Log log = new Log();
                log.setLogName(logName);
                log.setLogText(message);
                log.setLogType(LogTypeEnum.i().getKey(LogTypeEnum.ERROR));
                log.setLogTypeName(LogTypeEnum.ERROR);
                log.setTimestamp(format);
                logCallBack.onLog(log);
            }
        }

    }

    /**
     * 打印日志
     *
     * @param message 日志内容
     * @param cache   是否将当前日志缓存到Redis
     */
    public void error(String message, boolean cache) {
        error("", message, cache);
    }

    /**
     * 只打印到控制台
     *
     * @param message
     */
    public void error(String message) {
        error("", message, false);
    }


    public interface LogCallBack {
        void onLog(Log log);
    }
}
