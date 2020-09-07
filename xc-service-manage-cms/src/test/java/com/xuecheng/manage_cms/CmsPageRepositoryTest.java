package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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

    //测试自定义条件查询
    @Test
    public void testFindAllByExample(){
        int page=0;//从0开始
        int size=10;
        //分页对象
        Pageable pageable=PageRequest.of(page,size);
        //定义查询条件对象
        CmsPage cmsPage=new CmsPage();
        //定义查询条件：根据id精准查询，不定义条件匹配器内容
        //cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //根据页面别名模糊查询，必须定义条件匹配器
        cmsPage.setPageAliase("轮播");
        //条件匹配器
        ExampleMatcher matcher=ExampleMatcher.matching();
        //重新赋值添加匹配条件
        matcher=matcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //条件实例
        Example<CmsPage> example=Example.of(cmsPage,matcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }
}
