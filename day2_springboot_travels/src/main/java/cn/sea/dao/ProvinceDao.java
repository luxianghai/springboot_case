package cn.sea.dao;

import cn.sea.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProvinceDao extends BaseDao<Province, String> {



}
