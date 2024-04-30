package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){


        String message = ex.getMessage();
        //如果错误信息中包含着字符串
        if (message.contains("Duplicate entry")){
            String[] s = message.split(" ");
            //读取第三个，查看重复的姓名是什么
            String username = s[2];
            String msg =username+ MessageConstant.ALREADY_EXISTS;
            return  Result.error(msg);

        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
