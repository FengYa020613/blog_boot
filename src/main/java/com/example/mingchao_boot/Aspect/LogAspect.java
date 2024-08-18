package com.example.mingchao_boot.Aspect;


import com.example.mingchao_boot.Annotations.MethodFunction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Component
@Aspect
public class LogAspect {

    @Before("execution(* com.example.mingchao_boot.Service..*.*(..))")
    public void sysLog(JoinPoint jp) {
        StringJoiner log = new StringJoiner("|", "{", "}");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
        log.add(formatter.format(LocalDateTime.now()));
        //获取执行的业务方法
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        //获取.class对应的注解
        MethodFunction function =  method.getAnnotation(MethodFunction.class);
        if (function!=null){
            String fun = function.Function();
            log.add(fun);
        }
        //获取方法的所有注解
        Annotation[][] parameterAnnotations= method.getParameterAnnotations();
        //当前执行的业务方法名称
        String methodName = jp.getSignature().getName();
        log.add(methodName);
        //方法的参数
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            log.add(arg == null ? "-" : arg.toString());
        }
        System.out.println("AOP日志启动！" + log);
    }
}
