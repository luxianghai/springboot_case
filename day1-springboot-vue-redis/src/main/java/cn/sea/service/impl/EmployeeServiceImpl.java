package cn.sea.service.impl;

import cn.sea.dao.EmployeeDao;
import cn.sea.entity.Employee;
import cn.sea.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS )
    public List<Employee> findAll() {

        List<Employee> employees = employeeDao.findAll();
        if (CollectionUtils.isEmpty(employees)) {
            throw new RuntimeException("查询员工失败");
        }
        return employees;
    }

    // 保存员工信息
    @Override
    public void save(Employee emp) {
        emp.setId(null);
        int row = employeeDao.save(emp);
        if( row != 1) {
            throw new RuntimeException("新增员工信息失败 ~");
        }
    }

    // 删除员工信息
    @Override
    public void delete(String id) {

        int delete = employeeDao.delete(id);
        if( delete != 1 ) {
            throw new RuntimeException("删除用户失败 ！！");
        }
    }

    // 根据id查询员工信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Employee findOne(String id) {
        Employee emp = employeeDao.findOne(id);
        if (ObjectUtils.isEmpty(emp) ) {
            throw new RuntimeException("查询失败");
        }
        return emp;
    }

    // 更新员工信息
    @Override
    public void update(Employee emp) {
        int update = employeeDao.update(emp);
        if ( update != 1) {
            throw new RuntimeException("更新用户信息失败");
        }
    }
}
