package cn.sea;

import cn.sea.entity.TestUser;
import cn.sea.utils.ApplicationContextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test1() {
        stringRedisTemplate.opsForValue().set("name","张三");
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name = " + name );
    }

    @Test
    void test2() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        TestUser testUser = new TestUser();
        testUser.setName("xiaohei");
        testUser.setAge(23);

        redisTemplate.opsForValue().set(testUser.getName(), testUser);
        TestUser user = (TestUser) redisTemplate.opsForValue().get(testUser.getName());
        System.out.println(user);
    }

    @Test
    void test3() {
        RedisTemplate template = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.opsForValue().set("name","小金豆");

        String name = (String) template.opsForValue().get("name");
        System.out.println("name = " + name);
    }

}
