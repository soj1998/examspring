package com.rm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    

	@Value("${fileweizhi.common.uploadWindow}")
	private String window_weizhi;
	@Value("${fileweizhi.common.uploadLinux}")
	private String linux_weizhi;
    /**
     * 防止@EnableMvc把默认的静态资源路径覆盖了，手动设置的方式
     * 配置了静态资源访问
     * 还能配置视图解析 访问服务器上的资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	String os = System.getProperty("os.name");
    	String fileweizhi = "";
        //如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
        	fileweizhi = window_weizhi;
        } else {  //linux 和mac
        	fileweizhi = linux_weizhi;
        }
    
    	//在更换目录后 注意修改${fileweizhi.common.uploadWindow}的地址
    	
    	// 解决静态资源无法访问
        registry.addResourceHandler("/houtai/**").addResourceLocations("classpath:/static/").addResourceLocations("classpath:/public/");
        //配置视图解析，把url中后面带/image/***的路径映射到c盘photo文件中的资源
       	registry.addResourceHandler("/image/**").addResourceLocations("file:" + fileweizhi);
    }

    //配置服务器跨域请求被允许
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

}
/**————————————————
//版权声明：本文为CSDN博主「Yinbin_」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/yinbin_/article/details/102647745
 * 
 * 
 * 
 */
