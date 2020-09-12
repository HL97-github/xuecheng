package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2020/9/10
 */
//控制器增强
@ControllerAdvice
public class ExceptionCatch {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //定义ImmutableMap,配置异常类型对应的错误代码，异常类型是Threowable的子类
    // ImmutableMap数据一放进去不可更改，线程安全，是谷歌的一个map
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    //定义map的build对象，构建ImmutableMap，proctected子类可用
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        //定义异常类型对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    //捕获Exception异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        //记录日志
        LOGGER.error("catch exception:{}", e.getMessage());
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();//EXCEPTIONS的map构建成功
        }
        //从EXCEPTIONS的map中找异常类型对应的错误代码，找到了就返回，找不到就响应99999未知异常
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        }
        //找不到已知的系统异常类型，返回未知99999异常
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

    //捕获customerException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException exception) {
        //记录日志
        LOGGER.error("catch exception:{}:\r\nexception:", exception.getMessage(), exception);
        //封装返回结果返回给前端
        ResultCode resultCode = exception.getResultCode();
        ResponseResult result = new ResponseResult(resultCode);
        return result;
    }
}
