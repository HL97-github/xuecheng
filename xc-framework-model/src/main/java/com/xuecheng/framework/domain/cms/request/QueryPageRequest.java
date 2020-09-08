package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2020/9/5
 */
@Data
public class QueryPageRequest extends RequestData {
    //页面ID
    @ApiModelProperty("页面ID")
    private String pageId;
    //站点ID
    @ApiModelProperty("站点ID")
    private String siteId;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //页面别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
    //模板ID
    @ApiModelProperty("模板ID")
    private String templateId;
}
