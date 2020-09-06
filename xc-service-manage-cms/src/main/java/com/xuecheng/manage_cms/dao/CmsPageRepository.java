package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Administrator on 2020/9/6
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}
