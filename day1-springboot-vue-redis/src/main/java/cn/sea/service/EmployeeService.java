package cn.sea.service;

import cn.sea.entity.Employee;

import java.util.List;

public interface EmployeeService {

    // 查询所有
    List<Employee> findAll();

    // 保存员工信息
    void save(Employee emp);

    // 根据id删除员工
    void delete(String id);

    // 根据id查询员工
    Employee findOne(String id);

    // 更新员工信息
    void update(Employee emp);
}
