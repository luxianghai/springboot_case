package cn.sea.service.impl;

import cn.sea.dao.ProvinceDao;
import cn.sea.entity.Province;
import cn.sea.service.ProvinceService;
import cn.sea.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDao provinceDao;

    /**
     * 分页查询数据
     * @param page  当前页
     * @param rows   页面大小
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageInfo<Province> findByPage(Integer page, Integer rows) {

        // 1.查询总记录数
        Long totals = provinceDao.findTotals();
        // 2.计算总页数
        int pageTotals = (int) (totals % rows == 0 ? (totals / rows) : (totals / rows + 1));
        if( page > pageTotals ) {
            page = pageTotals;
        }

        // 3.计算开始索引 start = (page-1)*rows
        int start = (page - 1) * rows;

        // 4.查询数据
        List<Province> provinceList = provinceDao.findByPage(start, rows);
        if (CollectionUtils.isEmpty(provinceList)) {
            throw new RuntimeException("查询数据失败！！");
        }

        // 5. 封装数据
        PageInfo<Province> pageInfo = new PageInfo<>();
        pageInfo.setRows(rows).setStart(start).setPage(page)
                .setTotals(totals).setPageTotals(pageTotals).setData(provinceList);

        return pageInfo;
    }

    // 添加省份信息
    @Override
    public void save(Province province) {

        province.setId(null);
        province.setPlacecounts(0);
        int save = provinceDao.save(province);
        if ( save != 1 ) {
            throw new RuntimeException("添加省份信息失败");
        }
    }

    // 根据id删除省份信息
    @Override
    public void delete(String id) {
        int delete = provinceDao.delete(id);
        if (delete != 1) {
            throw new RuntimeException("删除省份信息失败！！");
        }
    }

    // 根据id查询省份信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Province findOne(String id) {

        Province province = provinceDao.findByPrimaryKey(id);
        if (ObjectUtils.isEmpty(province)) {
            throw new RuntimeException("查询结果为空！！");
        }
        return province;
    }

    // 更新省份信息
    @Override
    public void update(Province province) {

        int update = provinceDao.update(province);
        if (update != 1) {
            throw new RuntimeException("更新失败！！");
        }
    }

    // 查询所有省份信息
    @Override
    public List<Province> findAll() {

        List<Province> provinceList = provinceDao.findAll();
        if (CollectionUtils.isEmpty(provinceList)) {
            throw new RuntimeException("省份信息查询为空");
        }
        return provinceList;
    }
}
