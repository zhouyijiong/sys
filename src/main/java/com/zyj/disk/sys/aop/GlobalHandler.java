package com.zyj.disk.sys.aop;

import com.zyj.disk.sys.entity.Record;
import com.zyj.disk.sys.entity.Response;
import com.zyj.disk.sys.exception.GlobalException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/** 全局异常处理 */
@Configuration
@ControllerAdvice
@ResponseBody
public class GlobalHandler{
    private final Record record = new Record(this.getClass());

    @ExceptionHandler(GlobalException.class)
    public Response<?> batteryException(GlobalException exception){
        record.error(exception);
        return Response.error(exception);
    }
}