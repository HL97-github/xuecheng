package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Administrator on 2020/9/12
 */
@Service
public class CmsConfigService {
    @Autowired
    CmsConfigRepository repository;
    //根据id查询配置管理信息
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
