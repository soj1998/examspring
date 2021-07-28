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
import com.rm.entity.ExamQue;
import com.rm.entity.ShouYeXinXi;
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
    
    @RequestMapping(value="/getquanbuxitibyszid1")
    public List<ExamQueChuanDi> listall2(@RequestParam("sid") int szid,@RequestParam("sqtm") String sqtm){    	
    	int zuidashu = 1000;    	
		return service.getTiMuBySqtmSzid(szid, sqtm, zuidashu);
    }
    
    @RequestMapping(value="/getquanbuxitibyszid2")
    public List<ExamQueChuanDi> listall3(@RequestParam("sid") int szid,@RequestParam("sqtm") String sqtm){    	
    	return service.getTiMuBySzid(szid,100);
    }
    
    @RequestMapping(value="/getquanbuxitibyszidbiaoti")
    public List<ExamQueChuanDi> listall4(@RequestParam("sid") int szid,@RequestParam("biaoti") int biaoti){    	
    	return service.getTiMuByBiaoTi(szid,biaoti);
    }
    @RequestMapping(value="/getquanbuxitibyszidbiaoticount")
    public int listall5(@RequestParam("sid") int szid,@RequestParam("biaoti") String biaoti){    	
    	return service.getTiMuByBiaoTiCount(szid,biaoti);
    }
    /*
     * id 标题 类型 学科 知识点 录入时间
     * */
    @RequestMapping(value="/getsyxxcount")
    public int listall1(){    	
        Long zlsl = service.getShouYeXinXiShuLiang();
        return zlsl.intValue();
    }
    
    @RequestMapping(value="/getsyxx")
    public List<ShouYeXinXi> listall31(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
        //totalrecord sortby
		List<ShouYeXinXi> list_glx=service.getShouYeXinXiList(pageNum, pageSize);
        return list_glx;
    }
    
    @RequestMapping(value="/getsycount")
    public ResquestZlsl listall(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
        Long timu = service.getXiTiShuLiang();
        Long zlsl = service.getZhuanLanShuLiang();
        Long jcsl = service.getJiChuShuLiang();
        LOG.info(timu + zlsl + jcsl + "");
        ResquestZlsl zlsl1 = new ResquestZlsl(timu.intValue(),zlsl.intValue(),jcsl.intValue());
        return zlsl1;
    }
    
    @RequestMapping(value="/getsy")
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