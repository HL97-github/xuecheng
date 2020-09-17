package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2020/9/13
 */
@Service
public class CourseService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    private CourseMapper courseMapper;

    //查询所有课程计划节点
    public TeachplanNode findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

    //添加课程计划，操作多表添加事务
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //校验课程ID和课程计划名称
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //取出课程id
        String courseid = teachplan.getCourseid();
        //取出父节点ID
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            //父节点为空，获取根节点
            parentid=this.getTeachplanRoot(courseid);
        }
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //父节点信息
        Teachplan parent = optional.get();
        //父节点级别
        String parentGrade = parent.getGrade();
        //设置父节点
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");//未发布
        //设置子节点级别
        if("1".equals(parentGrade)){
            teachplan.setGrade("2");
        }else if("2".equals(parentGrade)){
            teachplan.setGrade("3");
        }
        //设置课程ID
        teachplan.setCourseid(courseid);
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取课程根结点，如果没有则添加根结点
    public String getTeachplanRoot(String courseId) {
        //校验课程ID是否已经添加了该课程，必须先添加课程再添加课程计划
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(!courseBaseOptional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_NOTEXIST);
        }
        //课程信息存在
        CourseBase courseBase = courseBaseOptional.get();
        //取出课程计划根节点
        //判断是不是刚添加的课程，有没有根节点
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList==null||teachplanList.size()==0){
            //是刚添加的课程，还没有任何大章节，为该课程设置一个根节点
            Teachplan root = new Teachplan();
            root.setCourseid(courseId);
            root.setGrade("1");//1级，大章节为2级，小章节为3级
            root.setParentid("0");
            root.setPname(courseBase.getName());//根节点名称就是课程名称
            root.setStatus("0");//未发布
            teachplanRepository.save(root);
            return root.getId();
        }
        //如果课程有大章节
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }

    //课程列表分页查询
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest){
        if(courseListRequest==null){
            courseListRequest=new CourseListRequest();
        }
        if(page<=0){
            page=0;
        }
        if(size<=0){
            size=10;
        }
        //设置分页参数
        PageHelper.startPage(page, size);
        //分页查询
        Page<CourseInfo> listPage = courseMapper.findCourseListPage(courseListRequest);
        //查询列表
        List<CourseInfo> result = listPage.getResult();
        //总记录数
        long total = listPage.getTotal();
        //封装返回查询结果集
        QueryResult<CourseInfo> queryResult=new QueryResult<>();
        queryResult.setList(result);
        queryResult.setTotal(total);
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS, queryResult);
    }
}
