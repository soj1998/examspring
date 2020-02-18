package com.rm.control;

import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.XueKeDao;
import com.rm.entity.QueandAns;
import com.rm.entity.XueKe;
import com.rm.util.SimCalculator;
import com.rm.util.StringUtil;
@RequestMapping(value="/xueke")
@RestController
public class XueKeController {
	@Resource
    private XueKeDao xkDao;    
	@Resource
    private QueandAnsDao qaDao;  
	private static final Logger LOG = LoggerFactory.getLogger(XueKeController.class);
    @RequestMapping(value="/listall")
    public List<XueKe> listall(){    	
        List<XueKe> list_glx=xkDao.findAll();
        return list_glx;
    }
    /**
     * 添加图书
     * @param book
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(XueKe xueke,QueandAns qaa){
    	SimCalculator sc=new SimCalculator();
    	List<XueKe> bkall=xkDao.findAll();
    	for(XueKe b:bkall) {
    		double bl=sc.calculate(b.getZhang(), xueke.getZhang(), 20);    
    		if(bl>0.8) {
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
    	}
    	Integer xuekeId= xkDao.save(xueke).getId();
    	LOG.info("xueke already save");
    	qaa.setXueKeId(xuekeId);
    	List<QueandAns> qaaall=qaDao.findAll();
    	for(QueandAns b:qaaall) {
    		double bl=sc.calculate(b.getQuestion(), qaa.getQuestion(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
				return "存在相同的问题";    			
    		}
    	}
    	qaDao.save(qaa);
    	LOG.info("question already save");
    	return "保存成功";
    }
}