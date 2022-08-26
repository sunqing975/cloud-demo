package cn.itcast.order;

import com.itcast.feign.clients.UserClient;
import com.itcast.feign.config.DefaultFeignConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class, clients = {UserClient.class})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 创建RestTemplate 并注入Spring容器中
     *
     * @return
     * @LoadBalanced 默认轮询机制
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 代码方式：全局
     * 将默认轮询机制 设置为随机
     *
     * @return
     */
    /*@Bean
    public IRule randomRule() {
        return new RandomRule();
    }*/
}