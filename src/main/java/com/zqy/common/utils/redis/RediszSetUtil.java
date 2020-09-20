package com.zqy.common.utils.redis;

import com.zqy.common.config.ParamConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/5 10:06
 * 描述: zSet相关操作
 * 这儿用单列或静态，在自动装配RedisTemplate时都会为空
 * <p>
 * Redis 有序集合(sorted set)
 * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
 * <p>
 * 不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
 * <p>
 * 有序集合的成员是唯一的,但分数(score)却可以重复。
 * <p>
 * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
 */
@Component//实例化到spring容器中
public class RediszSetUtil extends RedisKeyImp {

    //    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ZSetOperations<String, String> opsForZSets;

    @Override
    public String getKeyPrefix() {
        return ParamConfig.getInstance().getProjectName() + ":有序集合(SortedSet):";//key前缀
    }
    /**------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean zAdd(String key, String value, double score) {
        return opsForZSets.add(getKeyPrefix() + key, value, score);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<String>> values) {
        return opsForZSets.add(getKeyPrefix() + key, values);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public Long zRemove(String key, Object... values) {
        return opsForZSets.remove(getKeyPrefix() + key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return opsForZSets.incrementScore(getKeyPrefix() + key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return opsForZSets.rank(getKeyPrefix() + key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key
     * @param value
     * @return
     */
    public Long zReverseRank(String key, Object value) {
        return opsForZSets.reverseRank(getKeyPrefix() + key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public Set<String> zRange(String key, long start, long end) {
        return opsForZSets.range(getKeyPrefix() + key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeWithScores(String key, long start,
                                                                   long end) {
        return opsForZSets.rangeWithScores(getKeyPrefix() + key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return opsForZSets.rangeByScore(getKeyPrefix() + key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                                          double min, double max) {
        return opsForZSets.rangeByScoreWithScores(getKeyPrefix() + key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key,
                                                                          double min, double max, long start, long end) {
        return opsForZSets.rangeByScoreWithScores(getKeyPrefix() + key, min, max,
                start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return opsForZSets.reverseRange(getKeyPrefix() + key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key,
                                                                          long start, long end) {
        return opsForZSets.reverseRangeWithScores(getKeyPrefix() + key, start,
                end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zReverseRangeByScore(String key, double min,
                                            double max) {
        return opsForZSets.reverseRangeByScore(getKeyPrefix() + key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return opsForZSets.reverseRangeByScoreWithScores(getKeyPrefix() + key,
                min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public Set<String> zReverseRangeByScore(String key, double min,
                                            double max, long start, long end) {
        return opsForZSets.reverseRangeByScore(getKeyPrefix() + key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zCount(String key, double min, double max) {
        return opsForZSets.count(getKeyPrefix() + key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public Long zSize(String key) {
        return opsForZSets.size(getKeyPrefix() + key);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public Long zZCard(String key) {
        return opsForZSets.zCard(getKeyPrefix() + key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key
     * @param value
     * @return
     */
    public Double zScore(String key, Object value) {
        return opsForZSets.score(getKeyPrefix() + key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zRemoveRange(String key, long start, long end) {
        return opsForZSets.removeRange(getKeyPrefix() + key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return opsForZSets.removeRangeByScore(getKeyPrefix() + key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return opsForZSets.unionAndStore(getKeyPrefix() + key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        return opsForZSets
                .unionAndStore(getKeyPrefix() + key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long zIntersectAndStore(String key, String otherKey,
                                   String destKey) {
        return opsForZSets.intersectAndStore(getKeyPrefix() + key, otherKey,
                destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        return opsForZSets.intersectAndStore(getKeyPrefix() + key, otherKeys,
                destKey);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<String>> zScan(String key, ScanOptions options) {
        return opsForZSets.scan(getKeyPrefix() + key, options);
    }
}
