package cn.sea.service.impl;

import cn.sea.dao.UserDao;
import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    // 用户注册
    @Override
    public void register(User user) {

        // 判断用户信息是否为空
        String name = user.getUsername();
        String password = user.getPassword();
        String realname = user.getRealname();
        String sex = user.getSex();
        if (MyStringUtils.checkStringsIsEmpty(Arrays.asList(name,password,realname,sex))) {
            log.info("register 有空参数");
            throw new RuntimeException("注册用户参数异常 ！！");
        }

        // 处理用户信息
        user.setId(null);
        user.setStatus("已激活");
        user.setRegisterTime(new Date());

        // 注册
        int save = userDao.save(user);
        if( save != 1 ) throw new RuntimeException("新增用户失败！！！");
    }

    // 根据用户名查询用户
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {

        User user = userDao.findByUsername(username);
        return user;
    }

    @Override
    @Transactional
    public User login(User user) {

        // 根据用户名查询用户信息
        User userDB = userDao.findByUsername(user.getUsername());
        if (ObjectUtils.isEmpty( userDB ) ) {
            throw new RuntimeException("用户名不存在！！");
        }

        String password = user.getPassword();
        String passwordDB = userDB.getPassword();
        if (!password.equals(passwordDB)) {
            throw new RuntimeException("密码错误！！");
        }

        return userDB;
    }
}
