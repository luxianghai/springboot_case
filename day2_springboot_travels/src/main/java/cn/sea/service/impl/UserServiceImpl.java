package cn.sea.service.impl;

import cn.sea.dao.UserDao;
import cn.sea.entity.User;
import cn.sea.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    // 用户注册
    @Override
    public void register(User user) {
        user.setId(null);
        int save = userDao.save(user);
        if (save!=1) {
            throw new RuntimeException("用户注册失败 ！！！");
        }
    }

    // 根据用户名查询用户
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    // 用户登录
    @Override
    public User login(String username, String password) {

        User userDB = userDao.findByUsername(username);
        if(ObjectUtils.isEmpty(userDB) ) {
            throw new RuntimeException("用户不存在！！");
        }
        if (!userDB.getPassword().equals(password)) {
            throw new RuntimeException("密码错误！！");
        }

        return userDB;
    }
}
