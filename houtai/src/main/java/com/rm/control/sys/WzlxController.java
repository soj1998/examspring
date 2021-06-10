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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.sys.WzlxDao;
import com.rm.entity.lieju.WenZhangLeiXing;
import com.rm.entity.lieju.Wzlx;
import com.rm.util.StringUtil;

@CrossOrigin(origins = "*")
@RequestMapping(value="/sys/wzlx")
@RestController
public class WzlxController {
	@Resource
    private WzlxDao wzlxDao;    
	
	private static final Logger LOG = LoggerFactory.getLogger(WzlxController.class);
    @RequestMapping(value="/listall")
    public JSONArray listall(){    	
        //List<Wzlx> list_glx=wzlxDao.findAll();
        //return list_glx;
    	JSONArray rs =new JSONArray();
    	JSONObject one = new JSONObject();
    	one.put("id", WenZhangLeiXing.JICHU.getIndex());
    	one.put("wzlxmc", WenZhangLeiXing.JICHU.getName());
    	rs.add(one);
    	one = new JSONObject();
    	one.put("id", WenZhangLeiXing.SHITI.getIndex());
    	one.put("wzlxmc", WenZhangLeiXing.SHITI.getName());
    	rs.add(one);
    	one = new JSONObject();
    	one.put("id", WenZhangLeiXing.ZHUANLAN.getIndex());
    	one.put("wzlxmc", WenZhangLeiXing.ZHUANLAN.getName());
    	rs.add(one);
    	return rs;
    }

    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@RequestParam("szid") int szid,@RequestParam("szmc") String szmc){
    	if (StringUtil.isEmpty(szmc)) {
    		LOG.info("wzlx is null");
			return "wzlx is null,not save";
    	}
    	List<Wzlx> szall=wzlxDao.findAll();
    	for(Wzlx b:szall) {    		
			if(StringUtil.isNotEmpty(b.getWzlxmc()) && StringUtil.isNotEmpty(szmc)) {				
				if(b.getWzlxmc().equals(szmc)) {
					LOG.info("save wzlx exists");
					return "wzlx exists,not save";
				}
			}
    	}
    	Integer xuekeId= wzlxDao.save(new Wzlx(szid,szmc)).getId();
    	LOG.info("wzlx ok");
    	return ""+xuekeId;
    }  
    
    @RequestMapping(path = "delete",method=RequestMethod.POST)
    public String urlParam(@RequestParam(name = "szid") int  szid) {
    	wzlxDao.deleteById(szid);
    	return "ok";
    }
   
}