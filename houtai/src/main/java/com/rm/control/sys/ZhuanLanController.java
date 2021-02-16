package com.rm.control.sys;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.entity.ZhuanLan;
import com.rm.util.SimCalculator;
import com.rm.util.StringUtil;
import com.rm.util.file.UMEditor_Uploader;
import com.rm.util.file.ZhuanLanFileSaveSql;


@CrossOrigin(origins = "*")
@RequestMapping(value="/sys/zhuanlan")
@RestController
public class ZhuanLanController {
	@Resource
    private ZhuanLanDao zhuanLanDao; 	
	@Resource
    private AtcSjkDao atcSjkDao;
	@Resource
    private SzDao szDao;
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	private static final Logger LOG = LoggerFactory.getLogger(ZhuanLanController.class);
	
	@ResponseBody
	@RequestMapping(value="/listdaican",method=RequestMethod.POST)
    public List<ZhuanLan> listall(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlbybtidfuyi(pageRequest);
		return list_glx;        
    }
	
	@ResponseBody
	@RequestMapping(value="/listdaicanzhanshi",method=RequestMethod.POST)
    public List<ZhuanLan> listall2(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,@RequestParam("szid") int szid){    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlbybtidfuyizhanshi(pageRequest,szid);
		return list_glx;        
    }
	
	@ResponseBody
	@RequestMapping(value="/getcount")
    public int listall2(){    	
		return zhuanLanDao.getCountbybtidfuyi();        
    }
	
	@ResponseBody
	@RequestMapping(value="/getcountbysz")
    public int listall5(@RequestParam("szid") int szid){    	
		return zhuanLanDao.getCountbybtidfuyizhanshi(szid);        
    }
	
	@ResponseBody
	@RequestMapping(value="/getzlbyid",method=RequestMethod.POST)
    public List<ZhuanLan> listall6(@RequestParam("tid") int glid){  
		ZhuanLan zl = zhuanLanDao.findById(glid).get();    	
    	List<ZhuanLan> zllist = zhuanLanDao.getzlbyid(zl.getId());
    	zllist.add(zl);
		return zllist;        
    }
	
	@ResponseBody
	@RequestMapping(value="/listchoi",method=RequestMethod.POST)
    public List<ExamChoi> listall3(@RequestParam("tid") int glid){    	
		List<ExamChoi> zbchoidel = examChoiDao.getExamChoiListByQue(glid);
    	return zbchoidel;        
    }
	
	@ResponseBody
	@RequestMapping(value="/getquebyid",method=RequestMethod.POST)
    public ExamQue listall4(@RequestParam("tid") int glid){    	
		ExamQue zbchoidel = examQueDao.findById(glid).get();
    	return zbchoidel;        
    }
	
	//试题没有文章架构
    @ResponseBody
	@RequestMapping(value="/uploadsave",method=RequestMethod.POST)
	public String getUploadUmImage(HttpServletRequest request,@RequestParam("wzlx") int wzlx,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+"uploadfiles";
	    up.setSavePath(path,"zhuanlan");
	    String[] fileType = {".docx"};
	    up.setAllowFiles(fileType);
	    up.upload();
	    String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
	    LOG.info("r    "+result);
	    ZhuanLanFileSaveSql zhuanLanFileSaveSql = new ZhuanLanFileSaveSql();
	    zhuanLanFileSaveSql.asoneinsertToSql(atcSjkDao,szDao,zhuanLanDao,path + up.getUrl(), wzlx);
	    return result + "update jieguo ";
	    
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@RequestBody Param data){    	
    	int a1 = 2;
    	if (a1==1) {
    		return "";
    	}
    	JSONObject jsonque = new JSONObject((Map<String,Object>)data.eque);
    	ExamQue equ = JSON.toJavaObject(jsonque,ExamQue.class);
    	if (StringUtil.isEmpty(equ.getExamque())) {
    		LOG.info("que is null,not save");
			return "que is null,not save";
    	}
    	List<ExamQue> szall=examQueDao.findAll();
    	SimCalculator sc=new SimCalculator();
    	System.out.println(equ);
    	if (null == equ.getId() || equ.getId() < 0) {
	    	for(ExamQue b:szall) {    		
				double bl=sc.calculate(b.getExamque(), equ.getExamque(), 40);    
	    		if(bl>0.8) {
	    			LOG.info("already have same question");
	    			return "question exists same,not save";  			
	    		}
	    	}
    	}
    	equ.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
    	Integer xuekeId= examQueDao.save(equ).getId();
    	equ.setId(xuekeId);
    	LOG.info("sz ok");
    	List<Object> echoilist = (List<Object>)data.echoi;
    	for(Object a: echoilist) {
    		if (null!= a) {
	            JSONObject json = new JSONObject((Map<String,Object>)a);
	            ExamChoi echo = JSON.toJavaObject(json,ExamChoi.class);
	            echo.setExamQue(equ);
	            System.out.println(echo);
	            examChoiDao.save(echo);
    		}
    	}
    	System.out.println(data.duoyuxuanxiang);
    	List<Integer> duoyuxx = (List<Integer>)data.duoyuxuanxiang;
    	examChoiDao.deleteAll(examChoiDao.findAllById(duoyuxx));
    	return ""+xuekeId;
    }  
    
    @RequestMapping(path = "delete",method=RequestMethod.POST)
    public String urlParam(@RequestParam(name = "szid") int  szid) {
    	ZhuanLan zl = zhuanLanDao.findById(szid).get();    	
    	List<ZhuanLan> zllist = zhuanLanDao.getzlbyid(zl.getId());
    	zllist.add(zl);
    	zhuanLanDao.deleteAll(zllist);
    	atcSjkDao.delete(zl.getAtcSjk());
    	return "ok";
    }
   
}
