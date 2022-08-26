package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@RestController
@RequestMapping("/user")
// 与方式一结合实现热更新
// @RefreshScope
public class UserController {

    @Autowired
    private UserService userService;
    // 这里是配置信息获取的一种方式
    // 这里可以看nacos配置管理中的示例代码
    // 冒号的作用 ：可以设置默认值  pattern.dateformat
    // spring.application.name
    // @Value("${pattern.dateformat}")
    // private String dateformat;

    // 方式二：
    @Autowired
    private PatternProperties patternProperties;

    @GetMapping("/prop")
    public PatternProperties patternProperties() {
        return patternProperties;
    }

    @GetMapping("/now")
    public String now() {

        DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern(patternProperties.getDateformat());
        return dfDateTime.format(LocalDateTime.now());
    }


    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }
}
