package cn.sea.dao;

import cn.sea.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    // 添加用户信息
    int save(User user);

    // 根据用户名查询用户
    User findByUsername(String username);

}
