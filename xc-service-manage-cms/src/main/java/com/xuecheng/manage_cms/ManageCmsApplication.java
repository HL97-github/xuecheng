package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2020/9/6
 */
@SpringBootApplication
//扫描其他依赖工程中的实体类，当前工程如果依赖了这些实体类，会自动注入，不写也可以。加了此注解也不会与自动注入冲突
@EntityScan("com.xuecheng.framework.domain.cms")
//扫描依赖工程接口，不加的话比如swagger配置类会加载不进来，因为当前工程没有依赖该对象，该对象不能自动注入当前spring容器中
@ComponentScan(basePackages={"com.xuecheng.api"})
@ComponentScan(basePackages={"com.xuecheng.manage_cms"})//扫描本项目下的所有类，可以不加
@ComponentScan(basePackages = "com.xuecheng.framework")//扫描common工程下的类，有异常相关类
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
