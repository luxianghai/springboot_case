package cn.sea.controller;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.CreateImageCode;
import cn.sea.utils.MyStringUtils;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin  // 允许跨域
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<ResultInfo> login(@RequestBody User user, HttpServletRequest request) {
        log.info("login user [{}] " + user);
        ResultInfo resultInfo = new ResultInfo();
        // 封装请求头消息的Map
        MultiValueMap<String, String> headers = new HttpHeaders();
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            if (MyStringUtils.checkStringsIsEmpty(Arrays.asList(username,password))) {
                throw new RuntimeException("请完整填写表单");
            }

            // 调用业务层查询
            User userDB = userService.login(username, password);
            // 登录成功之后将用户保存到 ServletContext application  Redis  usreid userdb
            request.getServletContext().setAttribute(userDB.getId(), userDB);
            headers.set("userid", userDB.getId() );
            resultInfo.setStatus(true);
            resultInfo.setMsg("登录成功");
            userDB.setPassword(null);
            resultInfo.setData(userDB);
        } catch (RuntimeException e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg(e.getMessage());
        }

        return new ResponseEntity<ResultInfo>(resultInfo,headers, HttpStatus.OK);
    }

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<ResultInfo> register(String code, String key, @RequestBody User user, HttpServletRequest request) {

        log.info("接收到的验证码[{}]"+code);
        log.info("接收到的用户信息[{}]"+user);
        ResultInfo resultInfo = new ResultInfo();

        try {
            String id = user.getId();
            if(!ObjectUtils.isEmpty(id)){
                throw new RuntimeException("非法注册！！！");
            }
            String username = user.getUsername();
            String password = user.getPassword();
            String email = user.getEmail();
            if (MyStringUtils.checkStringsIsEmpty(Arrays.asList(username,password,email))) {
                throw new RuntimeException("请完整填写表单");
            }

            // 1. 验证验证码
            String keyCode = (String) request.getServletContext().getAttribute(key);
            if ( ObjectUtils.isEmpty(keyCode) || ObjectUtils.isEmpty(code) || !keyCode.equalsIgnoreCase(code)) {
                throw new RuntimeException("验证码错误 ！！！");
            }

            // 2. 查询用户名是否已被注册
            User userDB = userService.findByUsername(username);
            if (!ObjectUtils.isEmpty(userDB)) { // 如果不为空
                throw new RuntimeException("用户名已被使用，请更换用户名进行注册！！");
            }

            // 3. 调用业务层方法注册用户
            userService.register(user);
            resultInfo.setStatus(true);
            resultInfo.setMsg("注册成功 ~");

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false);
            resultInfo.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(resultInfo);
    }

    // 生成验证码图片,返回base64字符串
    @GetMapping("/getImage")
    public ResponseEntity<ResultInfo> getImage(HttpServletResponse response, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            CreateImageCode createImageCode = new CreateImageCode();
            // 获取验证码
            String code = createImageCode.getCode();
            // 验证码存入 servletContext 作用域中
            String key = UUID.randomUUID().toString();
            request.getServletContext().setAttribute(key, code);
            // 生成图片
            BufferedImage image = createImageCode.getBuffImg();
            // 进行base64编码
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            String string = "data:image/png;base64," + Base64Utils.encodeToString(bos.toByteArray());
            Map<String,String> map = new HashMap<>();
            map.put("key", key);
            map.put("image", string);
            map.put("UMD5U",code);
            resultInfo.setStatus(true);
            resultInfo.setData(map);

        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setStatus(false).setMsg("服务器异常！！");
        }

        return ResponseEntity.ok(resultInfo);
    }

}
