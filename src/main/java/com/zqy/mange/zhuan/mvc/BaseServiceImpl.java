package com.zqy.mange.zhuan.mvc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zqy.mange.zhuan.utils.LogUtil;
import com.zqy.mange.zhuan.utils.json.JsonUtils;
import com.zqy.mange.zhuan.utils.redis.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/9 14:38
 * 描述:
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    /**
     * 日志打印 支持打印到控制台和Redis
     */
    @Autowired
    public LogUtil logs;
    @Autowired
    public JsonUtils jsonUtils;
    /**
     * 字符串相关操作
     */
    @Autowired
    public RedisStringUtil redisStringUtil;
    /**
     * <p>
     * 这儿用单列或静态，在自动装配RedisTemplate时都会为空
     * <p>
     * Redis 列表(List)
     * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
     * <p>
     * 一个列表最多可以包含 232 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。
     */
    @Autowired
    public RedisListUtil redisListUtil;
    /**
     * <p>
     * Redis 哈希(Hash)
     * Redis hash 是一个 string 类型的 field（字段） 和 value（值） 的映射表，hash 特别适合用于存储对象。
     * <p>
     * Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）
     */
    @Autowired
    public RedisHashUtil redisHashUtil;
    /**
     * <p>
     * Redis 集合(Set)
     * Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     * <p>
     * Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     * <p>
     * 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    @Autowired
    public RedisSetUtil redisSetUtil;
    /**
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
    @Autowired
    public RediszSetUtil rediszSetUtil;



}
