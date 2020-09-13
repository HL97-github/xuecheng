package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
//若我们定义的接口继承了Repository,则该接口会被IOC容器识别为一个 Repository Bean;
//不需要使用@Repository
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
