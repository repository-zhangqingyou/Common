package com.zqy.common.config;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/8 16:14
 * 描述:  主要存放一些配置参数
 */
public class ParamConfig {
    private static ParamConfig mInstance;
    private String packageName;//项目包名
    private String projectName;//项目名称

    private ParamConfig() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public static ParamConfig getInstance() {
        if (mInstance == null) {
            synchronized (ParamConfig.class) {
                if (mInstance == null) {
                    mInstance = new ParamConfig();
                }
            }
        }
        return mInstance;
    }

}
