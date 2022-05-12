package com.cms.oauth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.cms.common.jdbc.config.IdGeneratorConfig;
import com.cms.common.jdbc.utils.RedisUtils;
import com.cms.common.tool.result.ResultUtil;
import com.cms.common.tool.utils.SysCmsUtils;
import com.cms.common.tool.utils.VerifyCodeUtils;
import com.cms.oauth.service.TencentSmsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.cms.common.tool.constant.ConstantCode.CACHE_CODE_KEY;
import static com.cms.common.tool.constant.ConstantCode.HEIGHT;
import static com.cms.common.tool.constant.ConstantCode.IMG_JPG;
import static com.cms.common.tool.constant.ConstantCode.WIDTH;

/**
 * @author ydf Created by 2022/4/26 12:36
 */
@Slf4j
@RestController
public class ApiController {

    @Autowired
    private WxMaService wxMaService;
    private final IdGeneratorConfig idGeneratorConfig;
    private final RedisUtils redisUtils;

    public ApiController(RedisUtils redisUtils, IdGeneratorConfig idGeneratorConfig) {
        this.redisUtils = redisUtils;
        this.idGeneratorConfig = idGeneratorConfig;
    }

    @ApiOperation(value = "解析code获取微信用户基础信息")
    @GetMapping(value = "/anonymous/getWxInfo")
    public ResultUtil<?> getWxInfo(String code, String encryptedData, String iv) {
        try {
            // 解析code获取微信用户基础信息
            WxMaJscode2SessionResult session =  wxMaService.getUserService().getSessionInfo(code);
            String sessionKey = session.getSessionKey();
            log.info("解析微信小程序用户信息："+session);
            // 获取微信用户信息
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            log.info("userInfo："+userInfo);
            return ResultUtil.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
            int errorCode = e.getError().getErrorCode();
            String errorMsg = e.getError().getErrorMsg();
            log.error("错误代码【{}】",errorCode);
            log.error(">>>错误信息【{}】",errorMsg);
            return ResultUtil.error("WX解析错误");
        }
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


    @Autowired
    private TencentSmsService tencentSmsService;

    @ApiOperation(value = "发送短信和验证")
    @GetMapping("/send")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, example = "13800000000", paramType = "query", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "code", value = "验证码验证", required = false, example = "889520", paramType = "query")})
    public ResultUtil<?> sendSms(@NotEmpty(message = "非法的手机号") @Pattern(regexp = "^1[0-9]{10}$", message = "非法的手机号") String phone, String code) {
        if (StringUtils.isNotBlank(code)) {
            if (tencentSmsService.validationCode(phone, code)) {
                return ResultUtil.success("验证码正确");
            }
            return ResultUtil.error("验证码错误");
        }
        String result = tencentSmsService.sendSms(phone);
        return ResultUtil.success(result);
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("不需要校验======================");
        return "hello!!!!";
    }
}
