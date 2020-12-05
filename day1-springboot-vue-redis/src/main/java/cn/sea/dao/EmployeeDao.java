package cn.sea.dao;

import cn.sea.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {

    // 查询所有
    List<Employee> findAll();

    // 保存员工信息
    int save(Employee emp);

    // 根据id删除员工
    int delete(String id);

    // 根据id查询员工
    Employee findOne(String id);

    // 根据id更新用户信息
    int update(Employee emp);
}
