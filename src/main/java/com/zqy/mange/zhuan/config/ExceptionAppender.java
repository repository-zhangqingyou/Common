package com.zqy.mange.zhuan.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.zqy.mange.zhuan.utils.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/8 23:35
 * 描述: 错误日志写数据库自定义Appender类
 */
@Component
public class ExceptionAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private ExceptionCallBack exceptionCallBack;

    public void setExceptionCallBack(ExceptionCallBack exceptionCallBack) {
        this.exceptionCallBack = exceptionCallBack;
    }

    /**
     * 错误日志数据库增删改查服务
     */


    /**
     * DbErrorLogAppender初始化
     */
    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel("ERROR");
        filter.setContext(context);
        filter.start();
        this.addFilter(filter);
        this.setContext(context);

        context.getLogger("ROOT").addAppender(ExceptionAppender.this);

        super.start();
    }

    /**
     * 错误日志拼装成实体类，写入数据库
     */
    @Override
    protected void append(ILoggingEvent loggingEvent) {
        error(loggingEvent);
    }


    /**
     * 错误日志拼装成实体类，写入Redis(合适时机在写入数据库)
     *
     * @param loggingEvent
     */
    public void error(ILoggingEvent loggingEvent) {

        IThrowableProxy tp = loggingEvent.getThrowableProxy();

        String time = TimeUtil.getTime(loggingEvent.getTimeStamp());
        // TErrorLog数据表实体类
        com.zqy.mange.zhuan.model.Exception exception = new com.zqy.mange.zhuan.model.Exception();
        exception.setErrMsg(loggingEvent.getMessage());
        exception.setTimestamp(time);

        if (loggingEvent.getCallerData() != null && loggingEvent.getCallerData().length > 0) {
            StackTraceElement element = loggingEvent.getCallerData()[0];
            exception.setClassName(element.getClassName());
            exception.setMethodName(element.getMethodName());
        }
        if (tp != null) {
            exception.setExceptionName(tp.getClassName());
            String stackTraceMsg = getStackTraceMsg(tp);
            int length = stackTraceMsg.length();
            if (length >= 3000) {
                //大于3000行截取
                stackTraceMsg = stackTraceMsg.substring(0, 3000);
            }
            exception.setStackTrace(stackTraceMsg);
        }
        if (exceptionCallBack!=null){
            exceptionCallBack.onException(exception);
        }

    }


    /**
     * 拼装堆栈跟踪信息
     */
    private String getStackTraceMsg(IThrowableProxy tp) {
        StringBuilder buf = new StringBuilder();
        buf.append(tp.getMessage()).append("\n");
        StackTraceElementProxy[] array = tp.getStackTraceElementProxyArray();
        if (array != null && array.length > 0) {
            for (StackTraceElementProxy proxy : array) {
                buf.append(proxy.getSTEAsString()).append("\n");
            }
        }
        return buf.toString();
    }

    public interface ExceptionCallBack {
        void onException( com.zqy.mange.zhuan.model.Exception exception);
    }
}
