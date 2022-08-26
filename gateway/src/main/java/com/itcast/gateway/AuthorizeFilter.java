package com.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
// @Order(-1) 设置过滤器的优先级
// 注解@Order或者接口Ordered的作用是
//          定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序，
//          Bean的加载顺序不受@Order或Ordered接口的影响；
// 注解有一个int类型的参数，可以不传，默认是最低优先级；
// 通过常量类的值我们可以推测参数值越小优先级越高；
@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        // 2. 获取参数中的 authorization 参数
        String authorization = queryParams.getFirst("authorization");
        // 3. 判断参数值是否等于 admin
        if ("admin".equals(authorization)){
            // 4. 放行
            return chain.filter(exchange);
        }
        // 5. 拦截
        // 5.1 设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 5.2 拦截请求
        return exchange.getResponse().setComplete();
    }
}
