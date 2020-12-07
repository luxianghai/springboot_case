package cn.sea.service.impl;

import cn.sea.dao.PlaceDao;
import cn.sea.dao.ProvinceDao;
import cn.sea.entity.Place;
import cn.sea.entity.Province;
import cn.sea.service.PlaceService;
import cn.sea.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Override
    @Transactional( propagation = Propagation.SUPPORTS)
    public PageInfo<Place> findByProvinceIdWithPage(Integer page, Integer rows, String provinceid) {

        // 1. 查询总记录数
        Long totals = placeDao.findTotalsByProvinceId(provinceid);
        if (totals == null || totals <= 0) {
            throw new RuntimeException("分页查询景点信息失败！！");
        }
        // 2. 计算总页数
        int pageTotals = (int) (totals % rows == 0 ? (totals / rows) : (totals / rows + 1));
        if (page > pageTotals) page = pageTotals;

        // 3. 计算开始索引
        int start = (page - 1) * rows;

        // 4. 查询
        List<Place> placeList = placeDao.findByProvinceIdWithPage(start, rows, provinceid);
        if (CollectionUtils.isEmpty(placeList)) {
            throw new RuntimeException("分页查询景点信息失败！！");
        }
        PageInfo<Place> pageInfo = new PageInfo<>();
        pageInfo.setStart(start).setRows(rows).setTotals(totals)
                .setPage(page).setPageTotals(pageTotals).setData(placeList);

        //System.out.println(" ============ " + pageInfo);

        return pageInfo;
    }

    // 添加景点信息
    @Override
    public void save(Place place) {
        // 保存景点信息
        int save = placeDao.save(place);
        if (save != 1) {
            throw new RuntimeException("添加景点信息失败");
        }
        // 查询原始的省份信息
        Province province = provinceDao.findByPrimaryKey(place.getProvinceid());
        // 更新省份信息的景点个数
        province.setPlacecounts(province.getPlacecounts() + 1);
        // 更新省份信息
        int update = provinceDao.update(province);
        if (update != 1) {
            throw new RuntimeException("添加景点信息失败，具体原因：省份信息更新失败");
        }
    }

    @Override
    public void delete(String id, String provinceid) {

        // 1.删除景点信息
        int delete = placeDao.delete(id);
        if (delete != 1) {
            throw new RuntimeException("删除景点信息失败！！");
        }

        // 2.更新省份信息
        // 1.1先查询
        Province province = provinceDao.findByPrimaryKey(provinceid);
        // 1.2 设置景点个数
        province.setPlacecounts(province.getPlacecounts() - 1);
        // 1.3 更新省份信息
        int update = provinceDao.update(province);
        if (update != 1) {
            throw new RuntimeException("删除景点信息失败,具体原因：更新省份信息失败");
        }

    }

    // 根据id查询景点信息
    @Override
    public Place findOne(String id) {

        Place place = placeDao.findByPrimaryKey(id);
        if (ObjectUtils.isEmpty(place)) {
            throw new RuntimeException("根据id查询景点数据为空！！");
        }
        return place;
    }

    @Override
    public void update(Place place) {

        int update = placeDao.update(place);
        if (update != 1) {
            throw new RuntimeException("更新景点信息失败！！");
        }
    }
}
