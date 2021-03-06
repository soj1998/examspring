package com.rm.control;


import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.rm.entity.pt.ExamQueChuanDi;
import com.rm.service.impl.FindServiceImpl;

@CrossOrigin(origins = "*")
@RequestMapping(value="/wbzt")
@RestController
public class ZtController {  
	
	@Resource
	private FindServiceImpl service;
	
	private static final Logger LOG = LoggerFactory.getLogger(ZtController.class);
    @RequestMapping(value="/gettab")
    public JSONObject listall(@RequestParam("szid") int szid){    	
        JSONObject jSONObject= new JSONObject();
        Long timu = service.getXiTiShuLiang(szid);
        jSONObject.put("xiti", timu.intValue());
        Long zlsl = service.getZhuanLanShuLiang(szid);
        jSONObject.put("zhuanlan", zlsl.intValue());
        Long jcsl = service.getJiChuShuLiang(szid);
        jSONObject.put("jichu", jcsl.intValue());
        LOG.info(timu + zlsl + jcsl + "");
        return jSONObject;
    }
    
    @RequestMapping(value="/getquanbuxitibyszid")
    public List<ExamQueChuanDi> listall2(@RequestParam("sid") int szid,@RequestParam("sqtm") String sqtm){    	
    	int zuidashu = 1000;    	
		return service.getTiMuBySqtmSzid(szid, sqtm, zuidashu);
    }
    
}