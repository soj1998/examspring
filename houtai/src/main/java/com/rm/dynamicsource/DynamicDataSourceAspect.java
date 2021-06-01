package com.rm.dynamicsource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DynamicDataSourceAspect {
	@Pointcut("execution(* com.rm.service..*.*(..))")
    private void aspect() {}

	
	private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();

        if (method.startsWith("find") || method.startsWith("select") || method.startsWith("query") || method
                .startsWith("search")) {
            DataSourceContextHolder.setDataSource("slaveDataSource");
            LOG.info("switch to slave datasource...");
        } else {
            DataSourceContextHolder.setDataSource("masterDataSource");
            LOG.info("switch to master datasource...");
        }

        try {
            return joinPoint.proceed();
        }finally {
        	LOG.info("清除 datasource router...");
            DataSourceContextHolder.clear();
        }
    }
}
