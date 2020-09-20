package com.zqy.mange.zhuan.utils.redis;

import com.zqy.mange.zhuan.config.ParamConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/5 10:01
 * 描述:  hash相关操作  -- 常用
 * 这儿用单列或静态，在自动装配RedisTemplate时都会为空
 * <p>
 * Redis 哈希(Hash)
 * Redis hash 是一个 string 类型的 field（字段） 和 value（值） 的映射表，hash 特别适合用于存储对象。
 * <p>
 * Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）
 */
@Component//实例化到spring容器中
public class RedisHashUtil extends RedisKeyImp {

    //    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private HashOperations<String, String, String> opsForHash;

    @Override
    public String getKeyPrefix() {
        return ParamConfig.getInstance().getProjectName() + ":哈希集合(Hash):";//key前缀
    }
    /** -------------------hash相关操作------------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   哈希表 key
     * @param field 字段
     * @return
     */
    public String hGet(String key, String field) {
        return opsForHash.get(getKeyPrefix() + key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(String key) {
        return opsForHash.entries(getKeyPrefix() + key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    哈希表 key
     * @param fields 字段集合
     * @return
     */
    public List<String> hMultiGet(String key, Collection<String> fields) {
        return opsForHash.multiGet(getKeyPrefix() + key, fields);
    }

    /**
     * @param key     哈希表 key
     * @param hashKey 字段
     * @param value   值
     */
    public void hPut(String key, String hashKey, String value) {
        opsForHash.put(getKeyPrefix() + key, hashKey, value);
    }

    /**
     * @param key  哈希表 key
     * @param maps 字段-值   集合
     */
    public void hPutAll(String key, Map<String, String> maps) {
        opsForHash.putAll(getKeyPrefix() + key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public Boolean hPutIfAbsent(String key, String hashKey, String value) {
        return opsForHash.putIfAbsent(getKeyPrefix() + key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public Long hDelete(String key, Object... fields) {
        return opsForHash.delete(getKeyPrefix() + key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public boolean hExists(String key, String field) {
        return opsForHash.hasKey(getKeyPrefix() + key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public Long hIncrBy(String key, String field, long increment) {
        return opsForHash.increment(getKeyPrefix() + key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public Double hIncrByFloat(String key, String field, double delta) {
        return opsForHash.increment(getKeyPrefix() + key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public Set<String> hKeys(String key) {
        return opsForHash.keys(getKeyPrefix() + key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public Long hSize(String key) {
        return opsForHash.size(getKeyPrefix() + key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public List<String> hValues(String key) {
        return opsForHash.values(getKeyPrefix() + key);
    }

    /**
     * 迭代哈希表中的键值对
     * <p>
     * 这个命令千万别在生产环境乱用。特别是数据庞大的情况下。因为Keys会引发Redis锁，并且增加Redis的CPU占用。很多公司的运维都是禁止了这个命令的
     *
     * @param key
     * @param options
     * @return
     */
    public Cursor<Map.Entry<String, String>> hScan(String key, ScanOptions options) {
        return opsForHash.scan(getKeyPrefix() + key, options);
    }


}
