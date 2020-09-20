package com.zqy.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    /**
     * 日志名
     */
    private String logName;

    /**
     * 日志类型
     */
    private Integer logType;

    /**
     * 日志类型名
     */
    private String logTypeName;

    /**
     * 日志类容
     */
    private String logText;

    /**
     * 时间
     */
    private String timestamp;


}
