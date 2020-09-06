package com.xuecheng.api.cms_manage;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "CMS管理页面", description = "Cms页面管理接口，提供页面的增删改查")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数",required = true,paramType = "path",dataType = "int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest request);
}
