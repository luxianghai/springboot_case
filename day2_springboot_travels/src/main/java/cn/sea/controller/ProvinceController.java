package cn.sea.controller;

import cn.sea.entity.Province;
import cn.sea.service.ProvinceService;
import cn.sea.utils.MyStringUtils;
import cn.sea.vo.PageInfo;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/province")
@Slf4j
@CrossOrigin // 允许跨域
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    // 查询所有省份信息
    @GetMapping("/findAll")
    public ResponseEntity<ResultInfo> findAll() {

        ResultInfo resultInfo = new ResultInfo();
        try {
            List<Province> provinceList = provinceService.findAll();
            resultInfo.setStatus(true).setData(provinceList);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false).setMsg("系统异常！！！");
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 更新省份信息
    @PostMapping("/update")
    public ResponseEntity<ResultInfo> update(@RequestBody Province province) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            String id = province.getId();
            if (ObjectUtils.isEmpty(id)) {
                throw new RuntimeException("数据异常，更新失败！！");
            }
            String name = province.getName();
            String tags = province.getTags();
            if (ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(tags)) {
                throw new RuntimeException("请完整填写表单！！");
            }

            provinceService.update(province);
            resultInfo.setMsg("更新成功").setStatus(true);

        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setStatus(false).setMsg(e.getMessage());
        }

        return ResponseEntity.ok(resultInfo);
    }

    // 根据id查询省份信息
    @GetMapping("/findOne")
    public ResponseEntity<ResultInfo> findOne(String id) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            if (ObjectUtils.isEmpty(id)) {
                throw new RuntimeException("查询省份时id为空异常！！");
            }
            Province province = provinceService.findOne(id);
            resultInfo.setStatus(true).setData(province);
        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg("系统异常，请稍后重试！！");
        }

        return ResponseEntity.ok(resultInfo);

    }

    // 删除省份信息
    @GetMapping("/delete")
    public ResponseEntity<ResultInfo> delete(String id) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            if (ObjectUtils.isEmpty(id)) {
                throw new RuntimeException("系统错误，删除失败！！");
            }
            provinceService.delete(id);
            resultInfo.setStatus(true);
            resultInfo.setMsg("删除省份信息成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getMessage());
            resultInfo.setStatus(false);
        }

        return ResponseEntity.ok(resultInfo);
    }

    // 添加省份信息
    @PostMapping("/save")
    public ResponseEntity<ResultInfo> save(@RequestBody Province province) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            String id = province.getId();
            Integer placecounts = province.getPlacecounts();
            if (!ObjectUtils.isEmpty(id) || placecounts != null) {
                throw new RuntimeException("ERROR：非法操作！！！");
            }
            String name = province.getName();
            String tags = province.getTags();
            if (MyStringUtils.checkStringsIsEmpty(Arrays.asList(name, tags))) {
                throw new RuntimeException("请完整填写表单！！！");
            }

            // 保存省份信息
            provinceService.save(province);
            resultInfo.setMsg("添加省份信息成功 ~ ");
            resultInfo.setStatus(true);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg(e.getMessage());
        }

        return ResponseEntity.ok(resultInfo);
    }

    /**
     * 分页查询数据
     * @param page  当前也页
     * @param rows  页面大小
     * @return
     */
    @GetMapping("/findByPage")
    public ResponseEntity<PageInfo<Province>> findByPage(Integer page, Integer rows){
        PageInfo<Province> pageInfo = null;
        try {
            page = (page == null || page <= 0 ) ? 1 : page;
            rows = (rows == null || rows <= 0 || rows > 50 ) ? 4 : rows;

            // 调用业务层查询数据
            pageInfo = provinceService.findByPage(page, rows);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(pageInfo);
    }
}
