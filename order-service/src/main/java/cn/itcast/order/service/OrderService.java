package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import com.itcast.feign.clients.UserClient;
import com.itcast.feign.config.DefaultFeignConfiguration;
import com.itcast.feign.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    // 关于FeignClient注入问题，有两种解决方式
    //  方式一： 指定FeignClient所在包
    // @EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, basePackages = "com.itcast.feign.clients")
    // 方式二： 指定Feign字节码（黑马推荐，我觉得更多的看FeignClient的数量，合适选择）
    // @EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, clients = {UserClient.class})
    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2. 利用Feign发起http请求，查询用户
        User user = userClient.findById(order.getUserId());
        // 3.封装user到Order
        order.setUser(user);
        // 4.返回
        return order;
    }

    // @Autowired
    // private RestTemplate restTemplate;
    //
    // public Order queryOrderById(Long orderId) {
    //     // 1.查询订单
    //     Order order = orderMapper.findById(orderId);
    //     // 2. 利用restTemplate发起http请求，查询用户
    //     // 2.1 url路径
    //     String url = "http://userservice/user/" + order.getUserId();
    //     // 2.2 发送http请求，实现远程调用
    //     User user = restTemplate.getForObject(url, User.class);
    //     // 3.封装user到Order
    //     order.setUser(user);
    //     // 4.返回
    //     return order;
    // }
}
