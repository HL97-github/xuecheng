package com.xuecheng.test.freemarker.controller;

import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Administrator on 2020/9/11
 */
@Controller //使用controller表示返回url，restcontroller表示返回json字符串
@RequestMapping("/freemarker")
public class FreeMarkerController {

    @Autowired
    private RestTemplate restTemplate;

    //测试轮播图模板
    @RequestMapping("/banner")
    public String indexBanner(Map<String, Object> map) {
        //调用轮播图接口
        ResponseEntity<Map> entity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = entity.getBody();
        //存入request域，方便模板文件获取
        map.putAll(body);
        return "index_banner";
    }

    @RequestMapping("/test1")
    public String freemaker(Map<String, Object> map) {
        //map在springMVC中作为形参，map数据最终会放到request域响应给用户
        // freemarker在生成html就能拿到这里的数据，map就是数据模型
        map.put("name", "张三");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMondy(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMondy(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型放数据
        map.put("stus", stus);
        //准备map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        //向数据模型放数据
        //map.put("stu1",stu1);
        //向数据模型放数据
        map.put("stuMap", stuMap);
        map.put("xyz", 102920122);
        //返回模板位置，基于resources/templates
        return "test1";
    }
}
