package com.rm;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.util.file.FileSaveSql;



@Controller

//@CrossOrigin(origins = "*")
public class MyTest {
	@Resource
    private TreeNodeSjkDao treeNodeSjkDao;
	
	@Resource
    private TnsQbNeiRongDao tnsQbNeiRongDao;
	
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
		FileSaveSql fileSaveSql = new FileSaveSql();
		fileSaveSql.asoneinsertToSql(tnsQbNeiRongDao, treeNodeSjkDao, "d:\\菜鸟税法.docx", "zsd", "1.0.0.2", "zzs");
		return "hh3";
	}
	
	@ResponseBody
	@RequestMapping(value="/gettree",method=RequestMethod.GET)
    public void listerji(@RequestParam("parentid") int pid){    	
        System.out.println(pid);
    }
	
}
