package cn.sea.controller;

import cn.sea.entity.User;
import cn.sea.service.UserService;
import cn.sea.utils.MyStringUtils;
import cn.sea.utils.VerifyCodeUtils;
import cn.sea.vo.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

@RestController
@RequestMapping("/user")
@CrossOrigin // 配置允许跨域
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<ResultInfo> login(@RequestBody User user) {
        log.info("login user [{}]" + user );
        ResultInfo info = new ResultInfo();
        try {
            if( ObjectUtils.isEmpty(user)) {
                throw new RuntimeException("登陆失败：请完整填写表单！！");
            }
            String username = user.getUsername();
            String password = user.getPassword();
            if (MyStringUtils.checkStringsIsEmpty(Arrays.asList(username, password))) {
                info.setMsg("登录失败：用户名或密码为空！！");
                return ResponseEntity.ok(info);
            }
            User userDB = userService.login(user);
            userDB.setPassword(null);
            info.setStatus(true);
            info.setMsg("登录成功 ~");
            info.setData(userDB);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            e.printStackTrace();
            info.setMsg("登录失败："+e.getMessage());
            return ResponseEntity.ok(info);
        }

    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<ResultInfo> register(@RequestBody User user, String code, HttpServletRequest request) { // 一定要加上 @RequestBody注解

        log.info("用户信息：[{}]" + user);
        log.info("用户输入的验证码 ：[{}]" + code);

        ResultInfo info = new ResultInfo();

        try {
            String key = (String) request.getServletContext().getAttribute("code");
            if ( !key.equalsIgnoreCase(code)) {
                info.setMsg("提示：验证码错误 ~ ");
                return ResponseEntity.ok(info);
            }

            User user1 = userService.findByUsername(user.getUsername());
            if ( !ObjectUtils.isEmpty(user1) ) {
                throw new RuntimeException("用户名已存在！！");
            }

            // 调用业务方法
            userService.register(user);
            info.setStatus(true);
            info.setMsg("提示：注册成功！！");
        } catch (Exception e) {
            e.printStackTrace();
            info.setMsg("注册失败,原因："+e.getMessage());
        }
        return ResponseEntity.ok(info);
    }


    /**
     * 生成验证码图片,返回的是base64
     */
    @GetMapping("/getImage")
    public String getImage(HttpServletRequest request) {

        try {
            // 1. 使用工具类生成验证码
            String code = VerifyCodeUtils.generateVerifyCode(4);
            // 2. 将验证码放入 servletContext 作用域中
            request.getServletContext().setAttribute("code",code);
            // 3. 将图片转为 base64
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            VerifyCodeUtils.outputImage(120, 30, byteArrayOutputStream, code);
            String base64 = "data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
            return base64;
        } catch (Exception e) {
            log.info("生成图片验证码失败 ~ ~");
            throw new RuntimeException(e);
        }

    }

}
