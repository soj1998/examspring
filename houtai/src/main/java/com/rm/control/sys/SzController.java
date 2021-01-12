package com.rm.control.sys;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.sys.SzDao;
import com.rm.entity.lieju.Sz;
import com.rm.util.StringUtil;

@CrossOrigin(origins = "*")
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
    public String add(@RequestParam("szid") int szid,@RequestParam("szmc") String szmc){
    	if (StringUtil.isEmpty(szmc)) {
    		LOG.info("sz is null,not save");
			return "sz is null,not save";
    	}
    	List<Sz> szall=szDao.findAll();
    	for(Sz b:szall) {    		
			if(StringUtil.isNotEmpty(b.getSzmc()) && StringUtil.isNotEmpty(szmc)) {				
				if(b.getSzmc().equals(szmc)) {
					LOG.info("save sz exists");
					return "sz exists,not save";
				}
			}
    	}
    	Integer xuekeId= szDao.save(new Sz(szid,szmc)).getId();
    	LOG.info("sz ok");
    	return ""+xuekeId;
    }  
    
    @RequestMapping(path = "delete",method=RequestMethod.POST)
    public String urlParam(@RequestParam(name = "szid") int  szid) {
    	szDao.deleteById(szid);
    	return "ok";
    }
   
}