package com.xuecheng.api.cms_manage;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "CMS管理页面", description = "Cms页面管理接口，提供页面的增删改查")
@RestController
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数",required = true,paramType = "path",dataType = "int")
    })
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(int page, int size, QueryPageRequest request);

    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("修改页面信息")
    public CmsPage findById(String id);

    @ApiOperation("修改页面信息")
    public CmsPageResult update(String id,CmsPage cmsPage);

    @ApiOperation("删除页面信息")
    public ResponseResult delete(String id);
}
