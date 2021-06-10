package com.rm.dynamicsource;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



//@Aspect
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
            //DataSourceContextHolder.setDataSource("slaveDataSource");
            LOG.info("switch to slave datasource...");
        } else {
            //DataSourceContextHolder.setDataSource("masterDataSource");
            LOG.info("switch to master datasource...");
        }

        try {
            return joinPoint.proceed();
        }finally {
        	LOG.info("清除 datasource router...");
            DataSourceContextHolder.clear();
        }
    }
    
    @Pointcut("execution(* com.rm.service.impl..*.*(..))")
    private void aspect1() {}

    @Around("aspect1()")
    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();

        if (method.startsWith("saveExam")) {
            //DataSourceContextHolder.setDataSource("slaveDataSource");
            LOG.info("saveExam saveExam saveExam...");
        } else {
            //DataSourceContextHolder.setDataSource("masterDataSource");
            LOG.info("saveZhuanLan saveZhuanLan saveZhuanLan...");
        }
        try {
            return joinPoint.proceed();
        }catch (Error e) {
        	LOG.info(e.getMessage());
        }
        return null;
    }
    @AfterReturning(returning = "retObj", pointcut = "aspect1()")
    public void doAfterReturning1(JoinPoint joinPoint,Object retObj) throws Throwable {
        String method = joinPoint.getSignature().getName();
    	if (method.startsWith("saveZhuanLan")) {
            //保存专栏的首页信息
    		//ZhuanLan rs = (ZhuanLan)retObj; 
        }
    	LOG.info("返回值service.impl : " + retObj);
    }
    
    
    @Pointcut("execution(* com.rm.control.sys..*.*(..))")
    private void aspect2() {}

    @Around("aspect2()")
    public Object around3(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        LOG.info("..." + method);
        if (method.startsWith("list")) {
            //DataSourceContextHolder.setDataSource("slaveDataSource");
            LOG.info("list list list...");
        } else {
            //DataSourceContextHolder.setDataSource("masterDataSource");
            LOG.info("saveZhuanLan1 saveZhuanLan saveZhuanLan...");
        }
        try {
            return joinPoint.proceed();
        }catch (Error e) {
        	LOG.info(e.getMessage());
        }
        return null;
    }
        
    @After("aspect2()")
    public void after(JoinPoint joinPoint) {
    	//joinPoint.getThis()
    	LOG.info("保存后方法：afterSave()");
    }
    
    @AfterReturning(returning = "retObj", pointcut = "aspect2()")
    public void doAfterReturning(JoinPoint joinPoint,Object retObj) throws Throwable {
        // 处理完请求，返回内容
    	String method = joinPoint.getSignature().getName();
    	if (method.startsWith("list")) {
            //DataSourceContextHolder.setDataSource("slaveDataSource");
            LOG.info("list list list123..." + method);
        }
    	LOG.info("返回值 : " + retObj);
    }
}
