package com.cms.oauth.controller;

import com.cms.common.core.utils.ApiCallUtils;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.jdbc.utils.RedisUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultException;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.common.tool.utils.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;
import static com.cms.common.tool.constant.ConstantCode.GATEWAY_AUTHORIZATION;
import static com.cms.common.tool.constant.ConstantCode.HEIGHT;
import static com.cms.common.tool.constant.ConstantCode.IMG_JPG;
import static com.cms.common.tool.constant.ConstantCode.WIDTH;

/**
 * @author ydf Created by 2022/4/26 12:36
 */
@Slf4j
@RestController
public class ApiController {

    private final IdGeneratorConfig idGeneratorConfig;
    private final RedisUtils redisUtils;

    public ApiController(RedisUtils redisUtils, IdGeneratorConfig idGeneratorConfig) {
        this.redisUtils = redisUtils;
        this.idGeneratorConfig = idGeneratorConfig;
    }

    @PostMapping(value = "/test")
    public ResultUtil<?> test() {
        return ResultUtil.success();
    }

    @GetMapping("/anonymous/valid_code")
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
        redisUtils.set((CACHE_CODE_KEY + randomNumber).toLowerCase(), randomNumber,60 * 3L);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, IMG_JPG, out);
        out.flush();
        out.close();
    }

    @GetMapping("/anonymous/generate_id")
    @ApiOperation(value = "生成分布式唯一ID")
    public ResultUtil<Long> generateId() {
        return ResultUtil.success(idGeneratorConfig.nextId(Object.class));
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("不需要校验======================");
        return "hello!!!!";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request) {
        String token = request.getHeader("payload");
        System.out.println("获取资源==============="+token);
        try {
            SecurityClaimsUserEntity claimsUser = ApiCallUtils.securityClaimsUser(request);
            System.out.println("获取携带参数==============="+claimsUser);
        } catch (ResultException e) {
            e.printStackTrace();
        }
        return "admin!!!!";
    }
}
