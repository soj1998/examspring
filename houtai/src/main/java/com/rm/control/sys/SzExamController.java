package com.rm.control.sys;

import java.io.File;
import java.util.HashMap;
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
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.util.StringUtil;
import com.rm.util.file.SzExamFileSaveSql;
import com.rm.util.file.UMEditor_Uploader;


@CrossOrigin(origins = "*")
@RequestMapping(value="/sys/szexam")
@RestController
public class SzExamController {
	@Resource
    private AtcSjkDao atcSjkDao; 	
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	@Resource
    private ExamQueZongHeDaDao examQueZongHeDaDao;
	@Resource
    private ExamQueZongHeXiaoDao examQueZongHeXiaoDao;
	@Resource
    private ExamChoiZongHeDao examChoiZongHeDao;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SzExamController.class);
	
	@ResponseBody
	@RequestMapping(value="/listdaican",method=RequestMethod.POST)
    public List<ExamQue> listall(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamQue> list_glx=examQueDao.findAll(pageRequest).getContent();
		return list_glx;        
    }
	
	@ResponseBody
	@RequestMapping(value="/getcount")
    public Long listall2(){    	
		return examQueDao.count();        
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
	public String getUploadUmImage(HttpServletRequest request,@RequestParam("wzlx") int wzlx,@RequestParam("sz") int sz,@RequestParam("wzlaiyuan") String wzlaiyuan,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+"uploadfiles";
	    up.setSavePath(path,"szexam");
	    String[] fileType = {".docx"};
	    up.setAllowFiles(fileType);
	    up.upload();
	    String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
	    LOG.info("r    "+result);
	    SzExamFileSaveSql szExamFileSaveSql = new SzExamFileSaveSql();
	    szExamFileSaveSql.asoneinsertToSql(atcSjkDao,examQueDao, examChoiDao, examQueZongHeDaDao,examQueZongHeXiaoDao,examChoiZongHeDao,path + up.getUrl(), wzlx, sz,wzlaiyuan);
	    return result + "update jieguo ";
	    
	}
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@RequestBody Param data){
    	System.out.println("aaaa");
    	System.out.println(data.echoi);
    	List<Object> echoilist = (List<Object>)data.echoi;
    	for(Object a: echoilist) {
            System.out.println(a);
            JSONObject json = new JSONObject((Map<String,Object>)a);
            ExamChoi echo = JSON.toJavaObject(json,ExamChoi.class);
            System.out.println(echo.getXuanxiang());
    	}
    	return "ok";
    }  
    
    @RequestMapping(path = "delete",method=RequestMethod.POST)
    public String urlParam(@RequestParam(name = "szid") int  szid) {
    	List<ExamChoi> zbchoidel = examChoiDao.getExamChoiListByQue(szid);
    	examChoiDao.deleteAll(zbchoidel);
    	examQueDao.deleteById(szid);
    	return "ok";
    }
   
}

class Param{
  public Object eque;
  public Object echoi;
}