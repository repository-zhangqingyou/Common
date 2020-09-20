package com.zqy.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangqingyou
 * @since 2020-08-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exception implements Serializable {
    private String className;//类名称
    private String methodName;//方法名称
    private String exceptionName;//异常类型
    private String errMsg;//错误信息
    private String stackTrace;//异常堆栈信息
    private String timestamp;//时间
}
