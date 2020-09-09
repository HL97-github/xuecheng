package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Created by Administrator on 2020/9/6
 */
@Service
public class CmsPageService {
    @Autowired
    private CmsPageRepository repository;

    /**
     * 列表页面分页查询，约定前端页面从1开始，没条件查所有
     */
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size,
                                        QueryPageRequest request) {
        if (page <= 0) {//设置默认值
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        page = page - 1;//为了适应mongodb的页面从0开始，将页面减1
        //分页对象
        //Pageable pageable=new PageRequest(page,size);//已过时，被下面的代替
        Pageable pageable = PageRequest.of(page, size);
        //查询条件
        CmsPage cmsPage = new CmsPage();
        //防止查询全部时，没有条件空指针异常
        if (request == null) {
            request = new QueryPageRequest();
        }
        //查询条件：站点ID
        if (StringUtils.isNotEmpty(request.getSiteId())) {
            cmsPage.setSiteId(request.getSiteId());
        }
        //查询条件：模板ID
        if (StringUtils.isNotEmpty(request.getTemplateId())) {
            cmsPage.setTemplateId(request.getTemplateId());
        }
        //设置查询条件：模糊查询别名
        if (StringUtils.isNotEmpty(request.getPageAliase())) {
            cmsPage.setPageAliase(request.getPageAliase());
        }
        //设置匹配器和匹配条件
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        //分页查询
        Page<CmsPage> all = repository.findAll(example, pageable);
        QueryResult queryResult = new QueryResult<CmsPage>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    //新增页面
    public CmsPageResult add(CmsPage cmsPage) {
        //校验页面是否存在，根据页面名称、站点Id、页面webpath查询
        CmsPage existPage = repository.findByPageNameAndSiteIdAndPageWebPath(
                cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(existPage!=null){
            //抛出异常：是否成功，异常编号，错误信息
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);//添加页面主键由springdata生成,覆盖前端对象中的pageId
        existPage = repository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, existPage);
    }

    //根据ID查询页面
    public CmsPage getById(String id) {
        Optional<CmsPage> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public CmsPageResult update(String id, CmsPage cmspage) {
        //查询
        CmsPage existCmsPage = this.getById(id);
        if (existCmsPage != null) {
            //更新
            existCmsPage.setTemplateId(cmspage.getTemplateId());
            existCmsPage.setSiteId(cmspage.getSiteId());
            existCmsPage.setPageAliase(cmspage.getPageAliase());
            existCmsPage.setPageName(cmspage.getPageName());
            existCmsPage.setPageWebPath(cmspage.getPageWebPath());
            existCmsPage.setPagePhysicalPath(cmspage.getPagePhysicalPath());
            existCmsPage.setDataUrl(cmspage.getDataUrl());
            //更新
            CmsPage save = repository.save(existCmsPage);
            if (save != null) {
                return new CmsPageResult(CommonCode.SUCCESS, save);
            }
        }
        //记录不存在和保存失败都返回null
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public ResponseResult delete(String id) {
        CmsPage exist = this.getById(id);
        if (exist != null) {
            repository.delete(exist);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
