package cn.sea.service;

import cn.sea.entity.User;

public interface UserService {

    // 用户注册
    void register(User user);

    // 根据用户名查询用户
    User findByUsername(String username);

    // 用户登录
    User login(String username,String password);

}
