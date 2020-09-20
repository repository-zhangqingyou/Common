package com.zqy.common.utils.redis;

import com.zqy.common.config.ParamConfig;
import com.zqy.common.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 存入对象取出时格式会异常
 * 比如：[[{"id":1,"userName":"admin","mailbox":"","password":"123456","state":1},{"id":17,"userName":"aa","password":"123456","state":0},{"id":18,"userName":"aa","password":"5","state":0},{"id":19,"userName":"a","password":"5245","state":0}]]
 * 会多个 [
 * <p>
 * 作者: zhangqingyou
 * 时间: 2020/8/5 10:03
 * 描述:  list相关操作 -- 常用
 * <p>
 * 这儿用单列或静态，在自动装配RedisTemplate时都会为空
 * <p>
 * Redis 列表(List)
 * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
 * <p>
 * 一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。
 */
@Component//实例化到spring容器中
public class RedisListUtil extends RedisKeyImp {
    //    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ListOperations<String, String> opsForList;
    @Autowired
    private LogUtil log;

    @Override
    public String getKeyPrefix() {
        return ParamConfig.getInstance().getProjectName() + ":列表(List):";//key前缀
    }

/** ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index
     * @return
     */
    public String lIndex(String key, long index) {
        return opsForList.index( getKeyPrefix() + key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return
     */
    public List<String> lRange(String key, long start, long end) {
        List<String> objectList = opsForList.range( getKeyPrefix() + key, start, end);
        return objectList;
    }

    /**
     * 存储在list头部
     *
     * @param key
     * @param value
     * @return
     */
    public Long lLeftPush(String key, String value) {
        return opsForList.leftPush( getKeyPrefix() + key, value);
    }

    /**
     * 将多个元素插在指定集合头部
     *
     * @param key
     * @param value
     * @return
     */
    public Long lLeftPushAll(String key, String... value) {
        return opsForList.leftPushAll( getKeyPrefix() + key, value);
    }

    /**
     * 将集合插在指定集合头部
     *
     * @param key
     * @param value
     * @return
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        return opsForList.leftPushAll( getKeyPrefix() + key, value);
    }

    /**
     * 如果存在集合则添加元素
     *
     * @param key
     * @param value
     * @return
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return opsForList.leftPushIfPresent( getKeyPrefix() + key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return opsForList.leftPush( getKeyPrefix() + key, pivot, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Long lRightPush(String key, String value) {
        return opsForList.rightPush( getKeyPrefix() + key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Long lRightPushAll(String key, String... value) {
        return opsForList.rightPushAll( getKeyPrefix() + key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        return opsForList.rightPushAll( getKeyPrefix() + key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key
     * @param value
     * @return
     */
    public Long lRightPushIfPresent(String key, String value) {
        return opsForList.rightPushIfPresent( getKeyPrefix() + key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public Long lRightPush(String key, String pivot, String value) {
        return opsForList.rightPush( getKeyPrefix() + key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key
     * @param index 位置
     * @param value
     */
    public void lSet(String key, long index, String value) {
        opsForList.set( getKeyPrefix() + key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public String lLeftPop(String key) {
        return opsForList.leftPop( getKeyPrefix() + key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public String lBLeftPop(String key, long timeout, TimeUnit unit) {
        return opsForList.leftPop( getKeyPrefix() + key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public String lRightPop(String key) {
        return opsForList.rightPop( getKeyPrefix() + key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public String lBRightPop(String key, long timeout, TimeUnit unit) {
        return opsForList.rightPop( getKeyPrefix() + key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      源密钥
     * @param destinationKey 目标键
     * @return
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return opsForList.rightPopAndLeftPush( getKeyPrefix() + sourceKey,
                 getKeyPrefix() + destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      源密钥
     * @param destinationKey 目标键
     * @param timeout
     * @param unit
     * @return
     */
    public String lBRightPopAndLeftPush(String sourceKey, String destinationKey,
                                        long timeout, TimeUnit unit) {
        return opsForList.rightPopAndLeftPush( getKeyPrefix() + sourceKey,
                 getKeyPrefix() + destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    public Long lRemove(String key, long index, String value) {
        return opsForList.remove( getKeyPrefix() + key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key
     * @param start
     * @param end
     */
    public void lTrim(String key, long start, long end) {
        opsForList.trim( getKeyPrefix() + key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key
     * @return
     */
    public Long lLen(String key) {
        return opsForList.size( getKeyPrefix() + key);
    }

}
