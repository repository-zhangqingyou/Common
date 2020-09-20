package com.zqy.mange.zhuan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zqy.mange.zhuan.em.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: zhangqingyou
 * Date: 2020/4/7
 * Des:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    /**
     * code : 200
     * msg : 发送成功
     * data : null
     */
   // 父节点id 包含大写字母
    @JsonProperty("Code")
    private int Code;
    // 父节点id 包含大写字母
    @JsonProperty("Desc")
    private String Desc;
    private Object data;


    /**
     * 成功
     *
     * @param msg  提示信息
     * @param data 数据源
     * @return 数据对象
     */
    public static BaseModel getSuccess(String msg, Object data) {
        BaseModel baseModel = new BaseModel(CodeEnum.i().getKey(CodeEnum.SUCCESS), msg, data);
        return baseModel;
    }

    /**
     * 成功
     *
     * @param data 数据源
     * @return 数据对象
     */
    public static BaseModel getSuccess(Object data) {
        return getSuccess("成功", data);
    }

    /**
     * 成功
     *
     * @return 数据对象
     */
    public static BaseModel getSuccess(String msg) {
        return getSuccess(msg, null);
    }

    /**
     * 成功
     *
     * @return 数据对象
     */
    public static BaseModel getSuccess() {
        return getSuccess("成功");
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param msg  提示信息
     * @return 数据对象
     */
    public static BaseModel getFailure(int code, String msg) {
        BaseModel baseModel = new BaseModel(code, msg, null);
        return baseModel;
    }

    /**
     * 失败
     *
     * @param msg  提示信息
     * @param data 数据源
     * @return 数据对象
     */
    public static BaseModel getFailure(String msg, Object data) {
        BaseModel baseModel = new BaseModel(CodeEnum.i().getKey(CodeEnum.FAILURE), msg, data);
        return baseModel;
    }

    /**
     * 失败
     *
     * @param data 数据源
     * @return 数据对象
     */
    public static BaseModel getFailure(Object data) {
        return getFailure("失败", data);
    }

    /**
     * 失败
     *
     * @param msg 提示信息
     * @return 数据对象
     */
    public static BaseModel getFailure(String msg) {
        return getFailure(msg, null);
    }

    /**
     * 失败
     *
     * @return 数据对象
     */
    public static BaseModel getFailure() {
        return getFailure("失败");
    }
}
