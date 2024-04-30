package com.sky.annoutation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动以注解，用于标识某个方法需要进行功能字段填充处理
 */
@Target(ElementType.METHOD)//标识这个注解只能添加在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //指定类型 数据库操作类型  update  insert
    OperationType value();
}
