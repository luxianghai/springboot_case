package cn.sea.service;

import cn.sea.entity.Province;
import cn.sea.vo.PageInfo;

import java.util.List;

public interface ProvinceService {

    /**
     * 分页查询
     * @param page  当前页
     * @param rows   页面大小
     * @return
     */
    PageInfo<Province> findByPage(Integer page, Integer rows);

    // 添加省份信息
    void save(Province province);

    // 删除省份信息
    void delete(String id);

    // 根据id查询省份信息
    Province findOne(String id);

    // 更新省份信息
    void update(Province province);

    // 查询所有省份信息
    List<Province> findAll();

}
