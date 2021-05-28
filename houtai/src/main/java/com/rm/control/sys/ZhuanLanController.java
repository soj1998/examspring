package com.rm.control.sys;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.ZhuanLan;
import com.rm.service.impl.SaveServiceImpl;
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
	private SaveServiceImpl saveServiceImpl;
	
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
	@RequestMapping(value="/getzlztbyid",method=RequestMethod.POST)
    public ZhuanLan listall7(@RequestParam("tid") int glid){  
		ZhuanLan zl = zhuanLanDao.findById(glid).get();
		return zl;        
    }
	
	
	
	
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
    
    @ResponseBody
	@RequestMapping(value="/uploadztsave",method=RequestMethod.POST)
	public String getUploadUmImage2(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "szid")int  szid,
			@RequestParam(name = "wzriqi")String  wzrq,
			@RequestParam(name = "wzlaiyuan")String  wzlaiyuan,
			@RequestParam(name = "wzxilie")String  wzxilie,
			@RequestParam(name = "wzquanbu")String  wzquanbu) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//szid riqi laiyuan xielie btid = -100
	    System.out.println("传过来的" + szid + wzquanbu + wzxilie.length());
	    Date wddate = null;
	    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate wddate2 = LocalDate.parse(wzrq, dateTimeFormatter);
			ZonedDateTime zonedDateTime = wddate2.atStartOfDay(ZoneId.systemDefault());
			wddate = Date.from(zonedDateTime.toInstant());
		} catch (DateTimeException e) {
			LOG.info("---时间转换错误---" + wzrq);				
			e.printStackTrace();
			return "---时间转换错误---";
		}
		ZhuanLan zlan = null;
		if (StringUtil.isNotEmpty(wzxilie)) {
			zlan = new ZhuanLan("Y",szid,-100,wddate,wzlaiyuan,wzxilie,wzquanbu);
		} else {
			zlan = new ZhuanLan("Y",szid,-100,wddate,wzlaiyuan,wzquanbu);
		}
		ZhuanLan rs1 = saveServiceImpl.saveZhuanLan(zlan);
		String rs = "";
		if (rs1 != null) {
			rs = "ok";
		} else {
			rs = "problem,maybe alreay exists";
		}
	    return rs;
	    
	}
    
    @ResponseBody
	@RequestMapping(value="/uploadimg",method=RequestMethod.POST)
	public JSONObject getUploadUmImage3(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+"uploadfiles";
	    up.setSavePath(path,"zhuanlan"+File.separator+"img");	    
	    up.upload();
	    UploadImgReSult ur = new UploadImgReSult("http://localhost:8080/houtai/image" + up.getZhuanlan_imgurl(),"1","2");
	    JSONObject js = new JSONObject();
	    js.put("errno", 0);
	    js.put("data", new Object[]{ur});
	    return js;	    
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
    
    class UploadImgReSult{
    	private String url;
    	private String alt;
    	private String href;
        
    	UploadImgReSult(String u,String a,String h) {
    		this.url = u;
    		this.alt = a;
    		this.href = h;
    	}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getAlt() {
			return alt;
		}

		public void setAlt(String alt) {
			this.alt = alt;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}
    	
    }
    
}
