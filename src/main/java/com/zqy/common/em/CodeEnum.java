package com.zqy.common.em;

import java.util.LinkedHashMap;

/**
 * 自建Enum -- 官方Enum消耗内存过大，不推荐使用
 * <p>
 * 作者: zhangqingyou
 * 时间: 2020/8/6 10:41
 * 描述: 接口状态码
 */
public class CodeEnum extends Enum<Integer, String> {
    private static CodeEnum mInstance;
    public final static String SUCCESS = "成功";
    public final static String FAILURE = "失败";

    private CodeEnum() {
        super();
    }

    public static CodeEnum i() {
        if (mInstance == null) {
            synchronized (CodeEnum.class) {
                if (mInstance == null) {
                    mInstance = new CodeEnum();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected LinkedHashMap<Integer, String> addEnum() {
        LinkedHashMap<Integer, String> kvMap = new LinkedHashMap<>();
        kvMap.put(200, SUCCESS);
        kvMap.put(-1, FAILURE);
        return kvMap;
    }
}
