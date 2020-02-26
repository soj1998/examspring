package com.rm.control;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.XueKeDao;
import com.rm.entity.XueKeBaoCun;
import com.rm.util.SimCalculator;
import com.rm.util.StringUtil;
@RequestMapping(value="/xueke")
@RestController
public class XueKeController {
	@Resource
    private XueKeDao xkDao;    
	
	private static final Logger LOG = LoggerFactory.getLogger(XueKeController.class);
    @RequestMapping(value="/listall")
    public List<XueKeBaoCun> listall(){    	
        List<XueKeBaoCun> list_glx=xkDao.findAll();
        return list_glx;
    }
    /**
 // 绑定变量名字和属性，参数封装进类  
    @InitBinder("xueke")  
    public void initBinderAddr(WebDataBinder binder) {  
        binder.setFieldDefaultPrefix("xueke.");  
    }      
      
*/
    @RequestMapping(value="/listxueke")
    public List<String> listxueke(){    	
        List<String> list_glx=xkDao.getXueKeNative();
        return list_glx;
    }
    
    @RequestMapping(value="/listerji",method=RequestMethod.POST)
    public List<String> listerji(@RequestParam("xueken") String xueke){    	
        List<String> list_glx=xkDao.getErJiFenLeiByXueKeNative(xueke);
        return list_glx;
    }
    
    @RequestMapping(value="/listzhangjie",method=RequestMethod.POST)
    public List<XueKeBaoCun> listzhangjie(@RequestParam("xueken") String xueke,
    		@RequestParam("erji") String erjifenlei){    	
        List<XueKeBaoCun> list_glx=xkDao.getZhangJieByXueKeNative(xueke, erjifenlei);
        return list_glx;
    }
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(XueKeBaoCun xueke){
    	SimCalculator sc=new SimCalculator();
    	List<XueKeBaoCun> bkall=xkDao.findAll();
    	for(XueKeBaoCun b:bkall) {
    		double bl=sc.calculate(b.getXueKe(), xueke.getXueKe(), 20); 
    		if(bl<0.8) {
    			continue;
    		}
    		bl=sc.calculate(b.getErJiFenLei(), xueke.getErJiFenLei(), 20); 
    		if(bl<0.8) {
    			continue;
    		}
    		bl=sc.calculate(b.getZhang(), xueke.getZhang(), 20); 
    		if(bl<0.8) {
    			continue;
    		}
			if( StringUtil.isNotEmpty(xueke.getJie())) {
				bl=sc.calculate(b.getJie(), xueke.getJie(), 20);
				if(bl>0.8) {
					LOG.info("already have same zhang jie");
					return "存在相同的章和节";
				}
			}else {
				LOG.info("already have same zhang");
				return "存在相同的章且节为空";
			}
    	}
    	Integer xuekeId= xkDao.save(xueke).getId();
    	LOG.info("xueke already save");
    	return ""+xuekeId;
    }  
    
    @GetMapping(path = "delete")
    public String urlParam(@RequestParam(name = "xuekeid") String  xuekeid) {
    	LOG.info(xuekeid);
    	xkDao.deleteById(Integer.parseInt(xuekeid));
    	return "ok";
    }
   
}