package com.zqy.common.utils.redis;

import com.zqy.common.config.ParamConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/5 10:00
 * 描述: string相关操作 -- 常用
 * 这儿用单列或静态，在自动装配RedisTemplate时都会为空
 */
@Component//实例化到spring容器中
public class RedisStringUtil extends RedisKeyImp {
 
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> opsForValue;
    @Override
    public String getKeyPrefix() {
        return  ParamConfig.getInstance().getProjectName() + ":字符串(String):";//key前缀
    }
    /** -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     */
    public  void set(String key, String value) {
        opsForValue.set( getKeyPrefix() + key, value);
    }

    /**
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return opsForValue.get( getKeyPrefix() + key);
    }

    public String get(String key, Class<String> vClass) {
        return  opsForValue.get( getKeyPrefix() + key);
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String getRange(String key, long start, long end) {
        return opsForValue.get( getKeyPrefix() + key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(String key, String value) {
        return  opsForValue.getAndSet( getKeyPrefix() + key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key
     * @param offset
     * @return
     */
    public Boolean getBit(String key, long offset) {
        return opsForValue.getBit( getKeyPrefix() + key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys
     * @return
     */
    public List<String> multiGet(Collection<String> keys) {
        List<String> keyList = new ArrayList<>();
        for (String key : keys) {
            keyList.add( getKeyPrefix() + key);
        }
        return opsForValue.multiGet(keyList);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key
     * @param value 值,true为1, false为0
     * @return
     */
    public boolean setBit(String key, long offset, boolean value) {
        return opsForValue.setBit( getKeyPrefix() + key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        opsForValue.set( getKeyPrefix() + key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key
     * @param value
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean setIfAbsent(String key, String value) {
        return opsForValue.setIfAbsent( getKeyPrefix() + key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key
     * @param value
     * @param offset 从指定位置开始覆写
     */
    public void setRange(String key, String value, long offset) {
        opsForValue.set( getKeyPrefix() + key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return opsForValue.size( getKeyPrefix() + key);
    }

    /**
     * 批量添加
     *
     * @param maps
     */
    public void multiSet(Map<String, String> maps) {
        opsForValue.multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean multiSetIfAbsent(Map<String, String> maps) {
        return opsForValue.multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @return
     */
    public Long incrBy(String key, long increment) {
        return opsForValue.increment( getKeyPrefix() + key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @return
     */
    public Double incrByFloat(String key, double increment) {
        return opsForValue.increment( getKeyPrefix() + key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key
     * @param value
     * @return
     */
    public Integer append(String key, String value) {
        return opsForValue.append( getKeyPrefix() + key, value);
    }

}
