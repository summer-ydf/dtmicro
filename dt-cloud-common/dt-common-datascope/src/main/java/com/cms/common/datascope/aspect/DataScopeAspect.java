package com.cms.common.datascope.aspect;

import com.cms.common.core.domain.search.SysOperatorPage;
import com.cms.common.core.domain.search.SysSearchPage;
import com.cms.common.core.utils.ApiCallUtils;
import com.cms.common.core.utils.ServletUtils;
import com.cms.common.datascope.annotation.DataScope;
import com.cms.common.tool.domain.SecurityClaimsUserEntity;
import com.cms.common.tool.result.ResultException;
import com.cms.common.tool.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色数据权限过滤
 * @author ydf Created by 2022/3/18 17:27
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) throws Throwable {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope) {
        // 获取当前登录的用户
        SecurityClaimsUserEntity currentUser = null;
        try {
            currentUser = ApiCallUtils.securityClaimsUser(ServletUtils.getRequest());
        } catch (ResultException ex) {
            ex.printStackTrace();
        }
        if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
            // 如果是超级管理员，则不过滤数据
            dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SecurityClaimsUserEntity user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();

//        for (SysRole role : user.getRoles()) {
//            String dataScope = role.getDataScope();
//            if (DATA_SCOPE_ALL.equals(dataScope)) {
//                sqlString = new StringBuilder();
//                break;
//            }
//            else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
//                sqlString.append(StringUtils.format(
//                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
//                        role.getRoleId()));
//            }
//            else if (DATA_SCOPE_DEPT.equals(dataScope)) {
//                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
//            }
//            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
//                sqlString.append(StringUtils.format(
//                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
//                        deptAlias, user.getDeptId(), user.getDeptId()));
//            }
//            else if (DATA_SCOPE_SELF.equals(dataScope)) {
//                if (StringUtils.isNotBlank(userAlias)) {
//                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
//                }
//                else {
//                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
//                    sqlString.append(" OR 1=0 ");
//                }
//            }
//        }

        sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));

        if (StringUtils.isNotBlank(sqlString.toString())) {
            // 获取查询的参数
            Object params = joinPoint.getArgs()[0];
            System.out.println("获取查询的参数>>>>>"+params);
            if (StringUtils.isNotNull(params) && params instanceof SysSearchPage) {
                SysSearchPage baseEntity = (SysSearchPage) params;
                baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
                System.out.println("sql拼接查询为："+baseEntity);
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof SysSearchPage) {
            SysSearchPage baseEntity = (SysSearchPage) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
