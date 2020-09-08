package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms_manage.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2020/9/6
 */
@RequestMapping("/cms/page")
@RestController
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService service;

    //条件查询页面列表并分页
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest request) {
        QueryResponseResult result = service.findList(page, size, request);
        return result;
    }

    /**
     * 添加页面信息
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/add")
    //@requestbody将json字符串转成java对象
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return service.add(cmsPage);
    }

    //根据ID查询页面信息
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return service.getById(id);
    }

    //修改页面信息
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult update(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {
        return service.update(id,cmsPage);
    }

    //根据id删除页面
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return service.delete(id);
    }
}
