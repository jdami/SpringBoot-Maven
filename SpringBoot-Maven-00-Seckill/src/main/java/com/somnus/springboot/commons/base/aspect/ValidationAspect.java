package com.somnus.springboot.commons.base.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.somnus.springboot.commons.base.exception.ParamValidException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(1)
@Slf4j
public class ValidationAspect {

    @Around("execution(public * *(..)) && @within(org.springframework.validation.annotation.Validated)")
    public Object validateMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        
        ExecutableValidator executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables();
        
        log.info("args:{}", Arrays.toString(pjp.getArgs()));
        
        Set<ConstraintViolation<Object>> validResult = executableValidator.
        		validateParameters(pjp.getTarget(), signature.getMethod(), pjp.getArgs());
        
        if (!validResult.isEmpty()) {
        	 List<FieldError> errors = validResult.stream().map(constraintViolation -> {
        		 String path = constraintViolation.getPropertyPath().toString();
                 int index = path.lastIndexOf('.');
                 index = index>0 ? index+1 : 0;
                 FieldError error = new FieldError(); 
                 // 参数名称（校验错误的参数名称）
                 error.setAttribute(path.substring(index));  
                 // 校验的错误信息
                 error.setErrMsg(constraintViolation.getMessage()); 
                 return error;
             }).collect(Collectors.toList());
        	 
             throw new ParamValidException(JSON.toJSONString(errors));  
        }
        Object result = pjp.proceed(); //Execute the method

        return result;
    }
    
    @Data
    public static class FieldError {
    	
    	private String attribute;
    	
    	private String errMsg;
    	
    }

}