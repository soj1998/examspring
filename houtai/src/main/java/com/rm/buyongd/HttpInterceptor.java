package com.rm.buyongd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class HttpInterceptor implements HandlerInterceptor{
    
    Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	int a = 1;
    	if (a == 1) {
    		return true;
    	}
        logger.info("preHandle :" +request.getContextPath());
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null)
            return true;
        // 跳转登录
        String url = request.getContextPath() + "/login";
        response.sendRedirect(url);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
   
    }
}