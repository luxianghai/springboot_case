package cn.sea.controller;

import cn.sea.entity.Employee;
import cn.sea.service.EmployeeService;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@CrossOrigin  // 允许跨域请求
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Value("${photo.dir}") // 注入上传头像的位置
    private String photoPath;

    // 修改员工信息
    @PostMapping("/update")
    public ResponseEntity<ResultInfo> update(Employee emp, MultipartFile photo) {
        log.info("员工信息[{}] " + emp);
        ResultInfo resultInfo = new ResultInfo();
        boolean flag = false; // 用于判断头像是否删除成功，并换了一个新的  true：表示成功 false：表示失败
        try {
            String name = emp.getName();
            Integer age = emp.getAge();
            Double salary = emp.getSalary();
            if(ObjectUtils.isEmpty(name) || age == null || age <= 0 || salary == null || salary <= 0) {
                // 修改员工信息失败
                throw new RuntimeException("表单填写有误或有空值 ！！");
            }

            if( photo != null && photo.getSize() >= 0) {
                log.info("头像信息[{}]" + photo.getOriginalFilename());
                // 获取旧头像的文件名
                String oldFileName = emp.getImgPath();
                // 1.保存头像
                // 1.0 获取 photos 文件夹的位置
                String realPath = ResourceUtils.getURL("classpath:").getPath() + "static\\photos";
                // 1.1 设置头像的新文件名  FilenameUtils.getExtension():获取指定文件名的后缀
                String newFileName = UUID.randomUUID().toString().replace("-","") + "."
                        + FilenameUtils.getExtension(photo.getOriginalFilename());
                // 1.2 创建文件夹
                String fullPath = realPath + "\\" + newFileName;
                File photoDir = new File(fullPath);
                if (!photoDir.exists()) {
                    photoDir.mkdirs();
                }
                // 1.3 上传头像
                //photo.transferTo(new File(photoPath, newFileName));
                photo.transferTo(new File(fullPath));
                // 1.4 设置头像地址
                emp.setImgPath(newFileName);

                // 1.5 删除旧的头像
                File file = new File(realPath, oldFileName);
                if ( file.exists() ) file.delete();
                flag = true;


            }

            if(  photo != null && photo.getSize() >= 0 && !flag ) {
                throw new RuntimeException("员工信息修改失败，原因：头像更新失败！！");
            }

            // 2. 更新员工信息
            employeeService.update(emp);
            resultInfo.setStatus(true);
            resultInfo.setMsg("员工信息保存成功 ~ ");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg("员工信息保存失败！！！");
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 根据id查询员工信息
    @GetMapping("/findOne")
    public ResponseEntity<ResultInfo> findOne(String id) {
        ResultInfo resultInfo = new ResultInfo();

        try {
            if ( ObjectUtils.isEmpty(id) ) {
                throw new RuntimeException("查询员工失败 ！！");
            }
            Employee emp = employeeService.findOne(id);
            if ( ObjectUtils.isEmpty(emp) ) {
                throw new RuntimeException("查询员工失败 ！！");
            }
            resultInfo.setStatus(true);
            resultInfo.setData(emp);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 删除员工信息
    @GetMapping("/delete")
    public ResponseEntity<ResultInfo> delete(String id) {
        log.info("删除员工的id[{}]"+id);
        ResultInfo resultInfo = new ResultInfo();
        try {
            if ( ObjectUtils.isEmpty(id) ) {
                throw new RuntimeException("删除用户失败 ！！");
            }

            // 1.删除员工头像
            Employee emp = employeeService.findOne(id);
            // 1.1 获取员工头像文件名
            String imgPath = emp.getImgPath();
            // 1.2 获取存储头像的位置
            String dir = ResourceUtils.getURL("classpath:").getPath() + "static/photos";
            // 1.3 删除
            File file = new File(dir, imgPath);
            if( file.exists() ) file.delete();

            // 2.删除员工
            employeeService.delete(id);
            resultInfo.setStatus(true);
            resultInfo.setMsg("删除用户成功 ~");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg(e.getMessage());
        }

        return ResponseEntity.ok(resultInfo);

    }


    // 保存员工信息
    // 头像上传成功后的访问路径为  http://localhost:8989/ems_vue/photos/文件名
    @PostMapping("/save")
    public ResponseEntity<ResultInfo> save(Employee emp, MultipartFile photo) {
        log.info("员工信息[{}] " + emp);
        log.info("头像信息[{}]" + photo.getOriginalFilename());
        ResultInfo resultInfo = new ResultInfo();

        try {

            String name = emp.getName();
            Integer age = emp.getAge();
            Double salary = emp.getSalary();
            if(ObjectUtils.isEmpty(name) || age == null || age <= 0 || salary == null || salary <= 0) {
                // 添加员工信息失败
                throw new RuntimeException("表单填写有误或有空值 ！！");
            }

            // 1.保存头像
            // 1.0 获取 photos 文件夹的位置
            String realPath = ResourceUtils.getURL("classpath:").getPath() + "static\\photos";
            // 1.1 设置头像的新文件名  FilenameUtils.getExtension():获取指定文件名的后缀
            String newFileName = UUID.randomUUID().toString().replace("-","") + "."
                    + FilenameUtils.getExtension(photo.getOriginalFilename());
            // 1.2 创建文件夹
            String fullPath = realPath + "\\" + newFileName;
            File photoDir = new File(fullPath);
            if (!photoDir.exists()) {
                photoDir.mkdirs();
            }
            // 1.3 上传头像
            //photo.transferTo(new File(photoPath, newFileName));
            photo.transferTo(new File(fullPath));
            // 1.4 设置头像地址
            emp.setImgPath(newFileName);

            // 2. 保存员工信息
            employeeService.save(emp);
            resultInfo.setStatus(true);
            resultInfo.setMsg("员工信息保存成功 ~ ");
        } catch (IOException e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg("员工信息保存失败！！！");
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 获取所有员工信息
    @GetMapping("/findAll")
    public ResponseEntity<ResultInfo> findAll() {

        ResultInfo resultInfo = new ResultInfo();

        try {
            List<Employee> employees = employeeService.findAll();
            resultInfo.setStatus(true);
            resultInfo.setMsg("查询成功");
            resultInfo.setData(employees);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            resultInfo.setMsg("查询失败");
            resultInfo.setStatus(false);
        }

        return ResponseEntity.ok(resultInfo);
    }

}
