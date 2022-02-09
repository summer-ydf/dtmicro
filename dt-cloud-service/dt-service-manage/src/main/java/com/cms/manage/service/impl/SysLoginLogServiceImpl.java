package com.cms.manage.service.impl;

import cn.hutool.http.useragent.UserAgent;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cms.manage.entity.SysLoginLogEntity;
import com.cms.manage.mapper.SysLoginLogMapper;
import com.cms.manage.service.SysLoginLogService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志数据实现类
 * @author DT
 * @date 2021/11/20 21:28
 */
@CommonsLog
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLogEntity> implements SysLoginLogService {

//    @Async("sysTaskExecutor")
//    @Override
//    public void saveLoginLog(String key, String loginUserName, UserAgent userAgent,String ip) {
//        SysLoginLogEntity sysLoginLogEntity = new SysLoginLogEntity();
//        sysLoginLogEntity.setId(UuidUtils.getUniqueIdByUUId());
//        sysLoginLogEntity.setLoginUserName(loginUserName);
//        // 获取客户端浏览器名称
//        String browser = userAgent.getBrowser().toString();
//        sysLoginLogEntity.setBrowser(browser);
//        // 获取客户端操作系统名称
//        String os = ConstantCode.LOGIN_STRING_OS;
//        // 检测是否是移动终端
//        if(!userAgent.isMobile()) {
//            os = userAgent.getOs().toString();
//            String toString = userAgent.getPlatform().toString();
//            System.out.println("登录操作系统->>>"+toString);
//        }
//        sysLoginLogEntity.setOperatingSystem(os);
//        // 获取IP地址
//        sysLoginLogEntity.setLoginIp(ip);
//        if(key.equals(ConstantCode.LOGIN_STRING_KEY)) {
//            // 登录系统日志
//            sysLoginLogEntity.setTitle(ConstantCode.LOGIN_STRING_TITLE);
//            sysLoginLogEntity.setMessage(ConstantCode.LOGIN_MESSAGE_SUCCESS);
//            sysLoginLogEntity.setStatus(ConstantCode.INT_ONE);
//            sysLoginLogEntity.setType(ConstantCode.INT_ONE);
//        }else if(key.equals(ConstantCode.SIGNOUT_STRING_KEY)) {
//            // 退出系统日志
//            sysLoginLogEntity.setTitle(ConstantCode.SIGNOUT_STRING_TITLE);
//            sysLoginLogEntity.setMessage(ConstantCode.SIGNOUT_MESSAGE_SUCCESS);
//            sysLoginLogEntity.setStatus(ConstantCode.INT_ONE);
//            sysLoginLogEntity.setType(ConstantCode.INT_TWO);
//        }else {
//            // 登录失败账号锁定
//            sysLoginLogEntity.setTitle(ConstantCode.LOGINLOCK_STRING_TITLE);
//            sysLoginLogEntity.setMessage(ConstantCode.LOGINLOCK_MESSAGE_ERROR);
//            sysLoginLogEntity.setStatus(ConstantCode.INT_TWO);
//            sysLoginLogEntity.setType(ConstantCode.INT_ONE);
//        }
//        this.baseMapper.insert(sysLoginLogEntity);
//        log.info("===============登录日志成功写入数据库===============");
//    }

}
