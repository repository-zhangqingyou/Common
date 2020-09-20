package com.zqy.mange.zhuan.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 作者: zhangqingyou
 * 时间: 2020/8/5 11:19
 * 描述:  Redis缓存配置
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //.entryTtl(Duration.ofHours(24)); // 设置缓存有效期24小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 存入对象取出时格式会异常
     * 比如：[[{"id":1,"userName":"admin","mailbox":"","password":"123456","state":1},{"id":17,"userName":"aa","password":"123456","state":0},{"id":18,"userName":"aa","password":"5","state":0},{"id":19,"userName":"a","password":"5245","state":0}]]
     * 会多个 [
     * <p>
     * 不推荐
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();


        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        RedisSerializer stringSerializer = new StringRedisSerializer();
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSeial);

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jacksonSeial);

        // 配置连接工厂
        template.setConnectionFactory(factory);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 存入对象取出时格式会异常
     * 比如：[[{"id":1,"userName":"admin","mailbox":"","password":"123456","state":1},{"id":17,"userName":"aa","password":"123456","state":0},{"id":18,"userName":"aa","password":"5","state":0},{"id":19,"userName":"a","password":"5245","state":0}]]
     * 会多个 [
     * <p>
     * 所以全部用 StringRedisSerializer
     *
     * @param factory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        // 配置连接工厂
        template.setConnectionFactory(factory);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 对hash类型的数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, String> opsForHash(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, String> opsForValue(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, String> opsForList(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, String> opsForSet(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param stringRedisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, String> opsForZSets(StringRedisTemplate stringRedisTemplate) {
        return stringRedisTemplate.opsForZSet();
    }
}
