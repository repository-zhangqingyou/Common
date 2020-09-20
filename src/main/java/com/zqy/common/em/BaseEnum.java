package com.zqy.common.utils;


/**
 * 任务-任务类型
 *
 * @作者 :virens
 * @创建时间 :2017年2月6日
 */
public enum BaseEnum {
    TYPE0(0, "正常返利"), //
    TYPE1(1, "免费试用"), //
    TYPE2(2, "中奖商品");

    private final Integer key;
    private final String desc;

    private BaseEnum(Integer key, String desc) {
        this.desc = desc;
        this.key = key;
    }

    /**
     * key查value
     *
     * @param _int
     * @return
     */
    public static String searchDesc(Integer _int) {
        for (final BaseEnum temp : values()) {
            if (temp.key == _int) {
                return temp.desc;
            }
        }
        return "";
    }

    /**
     * value查 key
     *
     * @param _desc
     * @return
     */
    public static int searchKey(String _desc) {
        for (final BaseEnum temp : values()) {
            if (temp.desc.equals(_desc)) {
                return temp.key;
            }
        }
        return -1;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

}
