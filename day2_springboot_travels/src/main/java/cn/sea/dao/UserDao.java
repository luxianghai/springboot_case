package cn.sea.dao;

import cn.sea.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    // 保存用户
    int save(User user);

    // 根据用户名查询用户
    User findByUsername(String username);
}
