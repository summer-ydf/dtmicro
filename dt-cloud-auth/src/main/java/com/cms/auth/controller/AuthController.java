package com.cms.auth.controller;

import com.cms.auth.service.OlapRabbitMqService;
import com.cms.common.core.utils.ApiCallUtils;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.jdbc.utils.RedisUtils;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultException;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.common.tool.utils.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;
import static com.cms.common.tool.constant.ConstantCode.HEIGHT;
import static com.cms.common.tool.constant.ConstantCode.IMG_JPG;
import static com.cms.common.tool.constant.ConstantCode.WIDTH;

/**
 * @author DT辰白 Created by 2021/12/16 11:19
 */
@RestController
public class AuthController {

    private final IdGeneratorConfig idGeneratorConfig;
    private final OlapRabbitMqService olapRabbitMqService;
    private final RedisUtils redisUtils;

    public AuthController(RedisUtils redisUtils, OlapRabbitMqService olapRabbitMqService, IdGeneratorConfig idGeneratorConfig) {
        this.redisUtils = redisUtils;
        this.olapRabbitMqService = olapRabbitMqService;
        this.idGeneratorConfig = idGeneratorConfig;
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

    @ApiOperation(value = "退出登录")
    @GetMapping("/security/logout")
    public Object test(HttpServletRequest request) {
        SecurityClaimsUserEntity securityClaimsUser = null;
        try {
            securityClaimsUser = ApiCallUtils.securityClaimsUser(request);
            olapRabbitMqService.sendLoginLog(request,securityClaimsUser,false);
        } catch (ResultException e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
}
