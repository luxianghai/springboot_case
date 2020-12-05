package cn.sea.controller;

import cn.sea.entity.TestUser;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    // 测试 axios 发送 post 请求，接口接收参数
    @PostMapping("/login")
    public ResponseEntity<ResultInfo> login(TestUser user, String code) {

        log.info("测试用户登录接收参数[{}] " + user);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(true);
        resultInfo.setMsg("code = " + code);
        resultInfo.setData(user);
        return ResponseEntity.ok(resultInfo);
    }

    // 测试获取项目中的 photos 文件的位置
    @GetMapping("/getPath")
    public String getPath() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath() + "photos";
        return path;
    }

    @PostMapping("/getImageBase64")
    public String getImageBase64(MultipartFile file) throws IOException {
        log.info("文件名：" + file.getOriginalFilename());
        // 1.0 获取 photos 文件夹的位置
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static/photos";
        String fullPath = realPath + "/3f486b311fca425ca62a7463e5fa9e6d.jpg";
        InputStream is = new FileInputStream(new File(fullPath));
        byte[] data = new byte[is.available()];
        is.read(data);
        is.close();

        BASE64Encoder base64 = new BASE64Encoder();
        String imageStr = "data:image/png;base64," + base64.encode(data);
        int length = imageStr.length();
        log.info("base64 image length [{}] " + length);
        String md5 = DigestUtils.md5DigestAsHex(imageStr.getBytes());

        log.info("md5 image [{}]" + md5);
        log.info("md5 image length [{}]" + md5.length());
        return imageStr;
    }

}
