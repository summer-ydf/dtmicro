package com.cms.gateway.config.security;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 鉴权管理器配置类
 * @author DT辰白
 * @date 2022/4/25 20:50
 */
@Slf4j
@Component
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 1、预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 2、校验是否携带token令牌
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token) || !token.startsWith("Bearer ")) {
            return Mono.just(new AuthorizationDecision(false));
        }
        return Mono.just(new AuthorizationDecision(true));
    }

//    private final RedisTemplate redisTemplate;
//
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
//        if (request.getMethod() == HttpMethod.OPTIONS) { // 预检请求放行
//            return Mono.just(new AuthorizationDecision(true));
//        }
//        PathMatcher pathMatcher = new AntPathMatcher();
//        String method = request.getMethodValue();
//        String path = request.getURI().getPath();
//        String restfulPath = method + ":" + path; // RESTFul接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html
//
//        // 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证
//        String token = request.getHeaders().getFirst("Authorization");
//        System.out.println("获取到token:"+token);
//        if (StrUtil.isNotBlank(token) && StrUtil.startWithIgnoreCase(token, "Bearer ") ) {
//            if (pathMatcher.match("/*/app-api/**", path)) {
//                // 商城移动端请求需认证不需鉴权放行（根据实际场景需求）
//                return Mono.just(new AuthorizationDecision(true));
//            }
//        } else {
//            return Mono.just(new AuthorizationDecision(false));
//        }
//
//
//        /**
//         * 鉴权开始
//         *
//         * 缓存取 [URL权限-角色集合] 规则数据
//         * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
//         */
//        Map<String, Object> urlPermRolesRules = redisTemplate.opsForHash().entries("system:perm_roles_rule:url");
//
//        // 根据请求路径获取有访问权限的角色列表
//        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
//        authorizedRoles.add("ROOT");
//        authorizedRoles.add("ADMIN");
////        boolean requireCheck = false; // 是否需要鉴权，默认未设置拦截规则不需鉴权
////
////        for (Map.Entry<String, Object> permRoles : urlPermRolesRules.entrySet()) {
////            String perm = permRoles.getKey();
////            if (pathMatcher.match(perm, restfulPath)) {
////                List<String> roles = Convert.toList(String.class, permRoles.getValue());
////                authorizedRoles.addAll(roles);
////                if (requireCheck == false) {
////                    requireCheck = true;
////                }
////            }
////        }
////        // 没有设置拦截规则放行
////        if (requireCheck == false) {
////            return Mono.just(new AuthorizationDecision(true));
////        }
//
//        // 判断JWT中携带的用户角色是否有权限访问
//        Mono<AuthorizationDecision> authorizationDecisionMono = mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authority -> {
//                    //String roleCode = authority.substring("ROLE_".length()); // 用户的角色
//                    System.out.println("authority:"+authority);
//                    if ("SCOPE_all".equals(authority)) {
//                        return true; // 如果是超级管理员则放行
//                    }
//                    boolean hasAuthorized = CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(authority);
//                    return hasAuthorized;
//                })
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//        return authorizationDecisionMono;
//    }


}


