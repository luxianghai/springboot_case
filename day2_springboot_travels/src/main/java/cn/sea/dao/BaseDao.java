package cn.sea.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @param <T> 当前操作的对象
 * @param <K> 主键的数据类型
 */
public interface BaseDao<T,K> {

    // 保存
    int save(T t);

    // 更新
    int update(T t);

    // 删除
    int delete(K k);

    // 查询所有
    List<T> findAll();

    // 根据主键查询
    T findByPrimaryKey(K k);

    /**
     * 分页查询
     * @param start  开始索引
     * @param rows   页面大小
     * @return
     */
    List<T> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    // 查询总记录数
    Long findTotals();
}
