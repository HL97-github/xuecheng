package com.xuecheng.manage_cms;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by Administrator on 2020/9/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsConfigTest {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    public void testStore() throws FileNotFoundException {
        //要存储的文件
        File file=new File("d:/test1.html");
        FileInputStream inputStream=new FileInputStream(file);
        //向GridFS存储文件
        ObjectId id = gridFsTemplate.store(inputStream, "test1.html");
        //得到文件ID
        String fileId = id.toString();
        System.out.println(fileId);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> entity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        System.out.println(entity);
        Map map = entity.getBody();
        System.out.println(map);
    }
}
