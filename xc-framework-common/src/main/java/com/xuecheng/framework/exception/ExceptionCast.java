package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * Created by Administrator on 2020/9/9
 */
//异常抛出类
public class ExceptionCast {
    //定义静态方法抛出自定义异常
    public static void  cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
