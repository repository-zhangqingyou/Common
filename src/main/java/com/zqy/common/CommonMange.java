package com.zqy.common;

import com.zqy.common.config.ExceptionAppender;
import com.zqy.common.config.ParamConfig;
import com.zqy.common.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 作者: zhangqingyou
 * 时间: 2020/9/20 16:22
 * 描述:
 */
@Component//实例化到spring容器中
public class CommonMange {
    @Autowired
    private LogUtil log;
    @Autowired
    private ExceptionAppender exceptionAppender;

    /**
     * Log回调
     *
     * @param logCallBack
     */
    public void setLogCallBack(LogUtil.LogCallBack logCallBack) {
        log.setLogCallBack(logCallBack);
    }

    /**
     * 错误日志回调
     *
     * @param exceptionCallBack
     */
    public void setExceptionCallBack(ExceptionAppender.ExceptionCallBack exceptionCallBack) {
        exceptionAppender.setExceptionCallBack(exceptionCallBack);
    }

    /**
     * 设置项目信息
     *
     * @param packageName 项目包名
     * @param projectName 项目名称
     */
    public void setParamConfig(String packageName, String projectName) {
        ParamConfig.getInstance().setPackageName(packageName);
        ParamConfig.getInstance().setProjectName(projectName);
    }
}
