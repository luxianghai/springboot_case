package cn.sea.dao;

import cn.sea.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PlaceDao extends  BaseDao<Place,String> {

    /**
     * 根据省份id分页查询当前省份下的景点
     * @param start  开始索引
     * @param rows   页面大小
     * @param provinceid  省份id
     * @return
     */
    List<Place> findByProvinceIdWithPage(@Param("start") Integer start, @Param("rows") Integer rows,
                                       @Param("provinceid") String provinceid);

    /**
     * 根据省份id查询当前省份下的景点总数
     * @param id
     * @return
     */
    Long findTotalsByProvinceId(String id);

}
