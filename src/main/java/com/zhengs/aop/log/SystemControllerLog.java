package com.zhengs.aop.log;  
  
import java.lang.annotation.*;  
  
/**
 * 自定义注解 拦截controller
 * @author zhengshan
 * @Date 2016-5-17
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public  @interface SystemControllerLog {  
	
    String description()  default "";
}  
  
