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
import com.rm.dao.sys.SzDao;
import com.rm.entity.lieju.Sz;
import com.rm.util.StringUtil;
@RequestMapping(value="/sys/sz")
@RestController
public class SzController {
	@Resource
    private SzDao szDao;    
	
	private static final Logger LOG = LoggerFactory.getLogger(SzController.class);
    @RequestMapping(value="/listall")
    public List<Sz> listall(){    	
        List<Sz> list_glx=szDao.findAll();
        return list_glx;
    }

    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Sz sz){
    	List<Sz> szall=szDao.findAll();
    	for(Sz b:szall) {    		
			if(StringUtil.isNotEmpty(sz.getSzmc())) {				
				if(b.getSzmc().equals(sz.getSzmc())) {
					LOG.info("save sz exists");
					return "sz exists,not save";
				}
			}else {
				LOG.info("sz is null");
				return "sz is null,not save";
			}
    	}
    	Integer xuekeId= szDao.save(sz).getId();
    	LOG.info("sz ok");
    	return ""+xuekeId;
    }  
    
    @GetMapping(path = "delete")
    public String urlParam(@RequestParam(name = "szid") String  szid) {
    	LOG.info(szid);
    	szDao.deleteById(Integer.parseInt(szid));
    	return "ok";
    }
   
}