package com.rm.control.sys;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.sys.WzlxDao;
import com.rm.entity.lieju.Wzlx;
import com.rm.util.StringUtil;
@RequestMapping(value="/sys/wzlx")
@RestController
public class WzlxController {
	@Resource
    private WzlxDao wzlxDao;    
	
	private static final Logger LOG = LoggerFactory.getLogger(WzlxController.class);
    @RequestMapping(value="/listall")
    public List<Wzlx> listall(){    	
        List<Wzlx> list_glx=wzlxDao.findAll();
        return list_glx;
    }

    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Wzlx sz){
    	List<Wzlx> szall=wzlxDao.findAll();
    	for(Wzlx b:szall) {    		
			if(StringUtil.isNotEmpty(sz.getWzlxmc())) {				
				if(b.getWzlxmc().equals(sz.getWzlxmc())) {
					LOG.info("save wzlx exists");
					return "wzlx exists,not save";
				}
			}else {
				LOG.info("wzlx is null");
				return "wzlx is null,not save";
			}
    	}
    	Integer xuekeId= wzlxDao.save(sz).getId();
    	LOG.info("wzlx ok");
    	return ""+xuekeId;
    }  
    
    @GetMapping(path = "delete")
    public String urlParam(@RequestParam(name = "szid") String  szid) {
    	LOG.info(szid);
    	wzlxDao.deleteById(Integer.parseInt(szid));
    	return "ok";
    }
   
}