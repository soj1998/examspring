package com.rm;




import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rm.entity.TreeNodeSjk;

@Controller

//@CrossOrigin(origins = "*")
public class MyTest {
	
	@ResponseBody
	@RequestMapping("/hello")
    public String say(){
        return "Spring Boot 你大爷222！";
    }
	@RequestMapping("/say")
    public ModelAndView say2(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message", "SpringBoot 大爷你好！");
        mav.setViewName("hh.html");
        return mav;
    }
	@ResponseBody
	@RequestMapping("/say2")
    public String say3(){
        return "hh3";
	}
	
	@ResponseBody
	@RequestMapping(value="/gettree",method=RequestMethod.GET)
    public void listerji(@RequestParam("parentid") int pid){    	
        System.out.println(pid);
    }
	
}
