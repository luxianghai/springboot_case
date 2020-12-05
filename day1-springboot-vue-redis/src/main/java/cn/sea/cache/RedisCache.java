package cn.sea.cache;

import cn.sea.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义redis缓存实现
 *  需要实现 org.apache.ibatis.cache.Cache 接口
 */

@Slf4j
public class RedisCache implements Cache {

    // 当前放入缓存的mapper的namespace（即此时的缓存在被那个namespace使用） 必须要有该属性
    private String id;

    public RedisCache() {

    }

    // 必须存在该构造方法
    public RedisCache(String id) {
        log.info("当前缓存id ：[{}]" + id);
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    // 放入reids缓存
    // 参数1：hash中的小key， value：小key对应的值
    @Override
    public void putObject(Object field, Object value) {
        log.info("put key :[{}] " + id);
        log.info("put field :[{}] " + field );
        log.info("put value :[{}] " + value );
        redisTemplate().opsForHash().put(id, field.toString(), value);
    }

    // 获取redis缓存
    // 参数：小key
    @Override
    public Object getObject(Object field) {
        log.info("get key :[{}] " + id);
        log.info("get field :[{}] " + field);
        return redisTemplate().opsForHash().get(id, field.toString());
    }

    // 删除指定缓存（redis中还没有实现）
    @Override
    public Object removeObject(Object o) {
        return null;
    }

    // 清除缓存
    @Override
    public void clear() {
        log.info("clear key : [{}] " + id);
        redisTemplate().delete(id);
    }

    // 获取缓存数量
    @Override
    public int getSize() {
        return redisTemplate().opsForHash().size(id).intValue();
    }

    // 封装一个用于获取 RedisTemplate 对象的方法
    private RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        log.info("redisTemplate: [{}] " + redisTemplate);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
