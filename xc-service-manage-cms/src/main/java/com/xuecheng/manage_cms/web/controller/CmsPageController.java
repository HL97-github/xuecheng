package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms_manage.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/9/6
 */
@RestController
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService service;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,
                                        @PathVariable("size") int size,
                                        QueryPageRequest request) {
        QueryResponseResult result = service.findList(page, size, request);
        return result;
    }
}
