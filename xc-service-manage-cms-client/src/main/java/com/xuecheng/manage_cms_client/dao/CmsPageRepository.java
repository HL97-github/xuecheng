package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2020/9/13
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}
