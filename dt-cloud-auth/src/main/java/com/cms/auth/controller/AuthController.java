package com.cms.auth.controller;

import com.alibaba.fastjson.JSON;
import com.cms.common.entity.SecurityClaimsUser;
import com.cms.common.result.ResultUtil;
import com.cms.common.utils.SysCmsUtils;
import com.cms.common.utils.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.cms.common.constant.ConstantCommonCode.CACHE_CODE_KEY;
import static com.cms.common.constant.ConstantCommonCode.HEIGHT;
import static com.cms.common.constant.ConstantCommonCode.IMG_JPG;
import static com.cms.common.constant.ConstantCommonCode.WIDTH;

/**
 * // 第三方登录，参考：https://www.cnblogs.com/haoxianrui/
 * @author ydf Created by 2021/12/16 11:19
 */
@RestController
public class AuthController {

    private final StringRedisTemplate stringRedisTemplate;

    public AuthController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/valid_code")
    @ApiOperation(value = "获取验证码接口")
    public void getCode(HttpServletResponse response) throws IOException {
        // 禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 设置响应格式为png图片
        response.setContentType("image/png");
        // 生成图片验证码
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        String randomNumber = VerifyCodeUtils.drawRandomText(image,WIDTH,HEIGHT);
        SysCmsUtils.log.info("获取登录验证码："+randomNumber);
        // 存入redis
        stringRedisTemplate.opsForValue().set(CACHE_CODE_KEY, randomNumber, 5, TimeUnit.MINUTES);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, IMG_JPG, out);
        out.flush();
        out.close();
    }

    @GetMapping("/logout")
    public Object test(HttpServletRequest request) {
        String token = request.getHeader("Icc-Gateway-Authorization");
        SysCmsUtils.log.info("退出登录token->>>"+token);
        return ResultUtil.success();
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("不需要校验======================");
        return "hello!!!!";
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("获取资源===============");
        return "admin!!!!";
    }
}
