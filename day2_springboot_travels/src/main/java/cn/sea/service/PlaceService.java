package cn.sea.service;

import cn.sea.entity.Place;
import cn.sea.vo.PageInfo;

public interface PlaceService {


    /**
     * 根据省份id分页查询当前省份下的景点
     * @param page   当前页
     * @param rows   页面大小
     * @param provinceid  省份id
     * @return
     */
    PageInfo<Place> findByProvinceIdWithPage(Integer page, Integer rows, String provinceid);

    // 添加景点信息
    public void save(Place place);

    // 删除景点信息,并更新景点所对应省份的景点数量
    void delete(String id, String provinceid);

    // 根据id查询景点
    Place findOne(String id);

    // 修改景点信息
    void update(Place place);
}
