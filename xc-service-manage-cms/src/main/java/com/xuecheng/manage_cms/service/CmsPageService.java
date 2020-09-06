package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2020/9/6
 */
@Service
public class CmsPageService {
    @Autowired
    private CmsPageRepository repository;

    /**
     * 列表页面分页查询，约定前端页面从1开始
     */
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size,
                                        QueryPageRequest request) {
        if (request == null) {
            request = new QueryPageRequest();
        }
        if(page<=0){//设置默认值
            page=1;
        }
        if(size<=0){
            size=10;
        }
        page=page-1;//为了适应mongodb的页面从0开始，将页面减1
        //分页对象
        //Pageable pageable=new PageRequest(page,size);//已过时，被下面的代替
        Pageable pageable=PageRequest.of(page,size);
        //分页查询
        Page<CmsPage> all = repository.findAll(pageable);
        QueryResult queryResult=new QueryResult<CmsPage>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
