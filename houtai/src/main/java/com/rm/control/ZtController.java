package com.rm.control;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.ZhuanLanDao;
import com.rm.entity.ExamQue;
import com.rm.entity.ZhuanLan;
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
    /*
     * id 标题 类型 学科 知识点 录入时间
     * */
    @RequestMapping(value="/getsycount")
    public ResquestZlsl listall(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
        Long timu = service.getXiTiShuLiang();
        Long zlsl = service.getZhuanLanShuLiang();
        Long jcsl = service.getJiChuShuLiang();
        LOG.info(timu + zlsl + jcsl + "");
        ResquestZlsl zlsl1 = new ResquestZlsl(timu.intValue(),zlsl.intValue(),jcsl.intValue());
        return zlsl1;
    }
    
    @RequestMapping(value="/getsyzl")
    public List<ZhuanLan> listall2(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,@RequestParam("zlsl")ResquestZlsl zlsl){    	
        //totalrecord sortby
		List<ZhuanLan> list_glx=service.getZhuanLanListOrderBysj(pageNum, pageSize);
        return list_glx;
    }
    
    @RequestMapping(value="/getsyexam")
    public List<ExamQue> listall3(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,@RequestParam("zlsl")ResquestZlsl zlsl){    	
        //totalrecord sortby
		List<ExamQue> list_glx=service.getExamQueListOrderBysj(pageNum, pageSize);
        return list_glx;
    }
    
    @SuppressWarnings("unused")
    class ResponseFanHui{    	
		private int idxh;
    	private int id;
    	private String xinxiyuan;
    	
    	ResponseFanHui(int idxh,int id,String xinxiyuan) {
    		this.idxh = idxh;
    		this.id = id;
    		this.xinxiyuan = xinxiyuan;
    	}
    }
    
    @SuppressWarnings("unused")
    class ResquestZlsl{    	
		private int xtsl;
    	private int zlsl;
    	private int jcsl;
    	private int total;
    	
    	ResquestZlsl(int xtsl,int zlsl,int jcsl) {
    		this.xtsl = xtsl;
    		this.zlsl = zlsl;
    		this.jcsl = jcsl;
    		this.total = xtsl +zlsl +jcsl;
    	}
    }
    
}