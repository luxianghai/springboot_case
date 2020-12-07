package cn.sea;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    void testRegister() {

        userService.register(new User().setUsername("张三").setPassword("123456").setEmail("123@qq.com"));

    }

}
