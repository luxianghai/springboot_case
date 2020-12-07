package cn.sea.controller;

import cn.sea.entity.Place;
import cn.sea.service.PlaceService;
import cn.sea.vo.PageInfo;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/place")
@CrossOrigin // 允许跨域请求
@Slf4j
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Value("${upload.dir}") // 注入文件上传路径
    private String uploadDir;

    // 修改景点信息
    /**
     * 修改景点信息
     * @param pic  用户上传的图片
     * @param place 要保存的景点信息
     * @return
     */
    @PostMapping("/update") // post 请求使用 @ReqeustBody 结束， FormData 使用原始的方式接收即可
    public ResponseEntity<ResultInfo> update(MultipartFile pic, Place place) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            String id = place.getId();
            String provinceid = place.getProvinceid();
            if (ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(provinceid)) {
                throw new RuntimeException("系统异常");
            }

            String name = place.getName();
            Date hottime = place.getHottime();
            Double hotticket = place.getHotticket();
            Double dimticket = place.getDimticket();

            if (ObjectUtils.isEmpty(name)) {
                throw new RuntimeException("请完整填写表单");
            }

            if (hotticket == null || hotticket <= 0 || dimticket == null || dimticket <= 0) {
                throw new RuntimeException("请完整填写表单");
            }
            if (!ObjectUtils.isEmpty(pic)) {
                // 保存图片
                String extension = FilenameUtils.getExtension(pic.getOriginalFilename()); // 获取文件后缀
                String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + extension; // 拼接新的文件名
                // 将图片进行base64编码并保存到place对象中（先编码，后上传）
                place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            }


            placeService.update(place);

            resultInfo.setStatus(true).setMsg("景点信息修改成功");
        }  catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 根据id查询景点信息
    @GetMapping("/findOne")
    public ResponseEntity<ResultInfo> findOne(String id) {

        ResultInfo resultInfo = new ResultInfo();

        try {
            if (ObjectUtils.isEmpty(id)) {
                throw new RuntimeException("参数异常！！");
            }
            Place place = placeService.findOne(id);
            resultInfo.setStatus(true).setData(place);
        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setStatus(false).setMsg("查询数据失败");
        }

        return ResponseEntity.ok(resultInfo);
    }


    // 删除景点信息
    @GetMapping("/delete")
    public ResponseEntity<ResultInfo> delete(String id, String provinceid) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            if (ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(provinceid)) {
                throw new RuntimeException("景点id或省份id为空");
            }

            placeService.delete(id, provinceid);
            resultInfo.setStatus(true).setMsg("成功删除节点信息");
        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setStatus(false).setMsg("删除景点信息失败！！");
        }

        return ResponseEntity.ok(resultInfo);
    }


    /**
     * 保存景点信息
     * @param pic  用户上传的图片
     * @param place 要保存的景点信息
     * @return
     */
    @PostMapping("/save") // post 请求使用 @ReqeustBody 结束， FormData 使用原始的方式接收即可
    public ResponseEntity<ResultInfo> save(MultipartFile pic, Place place) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            String id = place.getId();
            String provinceid = place.getProvinceid();
            if (!ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(provinceid)) {
                throw new RuntimeException("系统异常");
            }

            if (ObjectUtils.isEmpty(pic)) {
                throw new RuntimeException("请完整填写表单");
            }

            String name = place.getName();
            Date hottime = place.getHottime();
            Double hotticket = place.getHotticket();
            Double dimticket = place.getDimticket();

            if (ObjectUtils.isEmpty(name)) {
                throw new RuntimeException("请完整填写表单");
            }

            if (hotticket == null || hotticket <= 0 || dimticket == null || dimticket <= 0) {
                throw new RuntimeException("请完整填写表单");
            }

            // 保存图片
            String extension = FilenameUtils.getExtension(pic.getOriginalFilename()); // 获取文件后缀
            String newFilename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + extension; // 拼接新的文件名
            // 将图片进行base64编码并保存到place对象中（先编码，后上传）
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));
            // 上传文件
            //pic.transferTo(new File(uploadDir,newFilename));

            placeService.save(place);

            resultInfo.setStatus(true).setMsg("成功添加省份信息");
        }  catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
        }
        return ResponseEntity.ok(resultInfo);
    }

    /**
     * 分页查询景点信息
     * @param page 当前页
     * @param rows 页面大小
     * @param provinceid  省份id
     * @return
     */
    @GetMapping("findByProvinceIdWithPage")
    public ResponseEntity<ResultInfo> findByProvinceIdWithPage(Integer page, Integer rows, String provinceid) {

        ResultInfo resultInfo = new ResultInfo();
        try {
            if (ObjectUtils.isEmpty(provinceid)) {
                throw new RuntimeException("系统异常！！！");
            }
            page = (page == null || page <= 0 ) ? 1 : page;
            rows = (rows == null || rows <= 0 || rows > 50 ) ? 4 : rows;

            PageInfo<Place> pageInfo = placeService.findByProvinceIdWithPage(page, rows, provinceid);
            //log.info(" pageInfo [{}]" + pageInfo);
            resultInfo.setStatus(true).setData(pageInfo).setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
        }

        return ResponseEntity.ok(resultInfo);
    }


}
