package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2020/9/6
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
    //根据springdata规则自定义查询方法，命名中顺序要和参数字段顺序一致
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
