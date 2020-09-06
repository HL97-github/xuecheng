package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by Administrator on 2020/9/6
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    //分页测试
    @Test
    public void testFindList(){
        Pageable pageable=PageRequest.of(1,10);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5abefd525b05aa293098fca6");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageAliase("cc");
            CmsPage newPage = cmsPageRepository.save(cmsPage);
            System.out.println(newPage);
        }
    }
}
