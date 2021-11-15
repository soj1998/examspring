package com.rm.control.sys;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
import com.rm.entity.ExamZsd;
import com.rm.entity.ZhuanLan;
import com.rm.entity.lieju.Sz;
import com.rm.service.impl.FindServiceImpl;
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
	@Resource
	private FindServiceImpl findServiceImpl;
	
	private static final Logger LOG = LoggerFactory.getLogger(ZhuanLanController.class);
	
	@ResponseBody
	@RequestMapping(value="/listdaican",method=RequestMethod.POST)
    public List<ZhuanLan> listdaican(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlbybtidfuyi(pageRequest);
		return list_glx;        
    }
	
	@ResponseBody
	@RequestMapping(value="/listdaicanzhanshi",method=RequestMethod.POST)
    public List<ZhuanLan> listdaicanzhanshi(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,@RequestParam("szid") int szid){    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlbybtidfuyizhanshi(pageRequest,szid);
		return list_glx;        
    }
	
	@ResponseBody
	@RequestMapping(value="/getcount")
    public int getcount(){    	
		return zhuanLanDao.getCountbybtidfuyi();        
    }
	
	@ResponseBody
	@RequestMapping(value="/getcountbysz")
    public int getcountbysz(@RequestParam("szid") int szid){    	
		return zhuanLanDao.getCountbybtidfuyizhanshi(szid);        
    }
	
	@ResponseBody
	@RequestMapping(value="/getzlbyid",method=RequestMethod.POST)
    public List<ZhuanLan> getzlbyid(@RequestParam("tid") int glid){  
		ZhuanLan zl = zhuanLanDao.findById(glid).get();    	
    	List<ZhuanLan> zllist = zhuanLanDao.getzlbyid(zl.getId());
    	zllist.add(zl);
		return zllist;        
    }
	
	@ResponseBody
	@RequestMapping(value="/getzlztbyid",method=RequestMethod.POST)
    public ZhuanLan getzlztbyid(@RequestParam("tid") int glid){  
		ZhuanLan zl = zhuanLanDao.findById(glid).get();
		return zl;        
    }
	
	
	
    @ResponseBody
	@RequestMapping(value="/uploadsave",method=RequestMethod.POST)
	public String uploadsave(HttpServletRequest request,@RequestParam("wzlx") int wzlx,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+StringUtil.getUploadFiles()
				+File.separator
				+StringUtil.getTempFiles();
	    up.setSavePath(path,"zhuanlan");
	    String[] fileType = {".docx"};
	    up.setAllowFiles(fileType);
	    up.upload();
	    String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
	    LOG.info("r    "+result);
	    ZhuanLanFileSaveSql zhuanLanFileSaveSql = new ZhuanLanFileSaveSql();
	    String picurl = "";
	    String os = System.getProperty("os.name");
    	//如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
        	picurl = urlwin;
        } else {  //linux 和mac
        	picurl = urllinux;
        }
        path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+StringUtil.getUploadFiles();
	    zhuanLanFileSaveSql.asoneinsertToSql(request,saveServiceImpl,atcSjkDao,szDao,zhuanLanDao,picurl,path + up.getUrl(), wzlx);
	    return result + "update jieguo ";
	    
	}
    
    @ResponseBody
	@RequestMapping(value="/uploadztsave",method=RequestMethod.POST)
	public String uploadztsave(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name = "szid")int  szid,
			@RequestParam(name = "wzbiaoti")String  biaoti,
			@RequestParam(name = "wzriqi")String  wzrq,
			@RequestParam(name = "wzlaiyuan")String  wzlaiyuan,
			@RequestParam(name = "wzxilie")String  wzxilie,
			@RequestParam(name = "wzquanbu")String  wzquanbu,
			@RequestParam(name = "wzquanbutxt")String  wzquanbutxt,
			@RequestParam(name = "wzzsd")String  zsd) throws Exception{
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
		//搞知识点 知识点搞分级 是***
		String[] zsdzu = zsd.split("\\*\\*\\*");
		List<ExamZsd> zsdzulist = new ArrayList<ExamZsd>();
		for (String azsdzu : zsdzu) {
			ExamZsd exzsd = new ExamZsd();
			exzsd.setNeirong(StringUtil.myTrim(azsdzu));
			zsdzulist.add(exzsd);
		}
		ExamZsd exzsd1 = saveServiceImpl.saveExamZsd(zsdzulist);
		String rs = "";
		if (null == exzsd1) {
			LOG.error("添加ExamZsd 失败!,zsd没搞对");
			rs = "zsd wrong";
			return rs;
		}
		// -100 是单个专栏  -1 是整体保存的专栏
		ZhuanLan zlan = new ZhuanLan("Y",szid,-100,biaoti,wddate,wzlaiyuan,wzxilie,wzquanbu,wzquanbutxt,exzsd1.getId());
		if (StringUtil.isNotEmpty(wzxilie)) {
			//zlan = new ZhuanLan("Y",szid,-100,wddate,wzlaiyuan,wzxilie,wzquanbu,wzquanbutxt,exzsd1);
		} else {
			//zlan = new ZhuanLan("Y",szid,-100,wddate,wzlaiyuan,wzquanbu,wzquanbutxt,exzsd1);
		}
		Sz sz = findServiceImpl.getSz(zlan.getSzid());
		ExamZsd examzsd = findServiceImpl.getExamZsd(zlan.getExzsdid());
		zlan.setExzsd(examzsd);
		zlan.setSz(sz);
		ZhuanLan rs1 = saveServiceImpl.saveZhuanLan(zlan);
		if (rs1 == null) {
			rs = "problem,maybe alreay exists,don't save shouyexinxi";
			return rs;
		}
		//String wzlx = WenZhangLeiXing.ZHUANLAN.getName(); 
		//ShouYeXinXi syxx =new ShouYeXinXi(sz.getId(),sz.getSzmc(),
		//		biaoti,examzsd.getId(),examzsd.getNeirong(),
		//		wddate,wzlx,rs1.getId(),rs1.getBtid(),"Y");
		//ShouYeXinXi syxx1 =saveServiceImpl.saveShouYeXinXi(syxx);
		if (rs1 != null) { //&& syxx1 != null) {
			rs = "ok";
		} 
		//else {
		//	rs = "problem,maybe alreay exists";
		//}
	    return rs;
	    
	}
    
    
    @Value("${urlbyos.win}")
	private String urlwin;
	@Value("${urlbyos.linux}")
	private String urllinux;
    @ResponseBody
	@RequestMapping(value="/uploadimg",method=RequestMethod.POST)
	public JSONObject uploadimg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+StringUtil.getUploadFiles()
				+File.separator
				+StringUtil.getPicFiles();
	    up.setSavePath(path,"zhuanlan"+File.separator+"img");	    
	    up.upload();
	    String url = "";
	    String os = System.getProperty("os.name");
    	//如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
        	url = urlwin;
        } else {  //linux 和mac
        	url = urllinux;
        }
	    UploadImgReSult ur = new UploadImgReSult("http://" +url+":8080/houtai/image" + up.getZhuanlan_imgurl(),"1","2");
	    JSONObject js = new JSONObject();
	    js.put("errno", 0);
	    js.put("data", new Object[]{ur});
	    return js;	    
	}
    
    

    
    @RequestMapping(path = "delete",method=RequestMethod.POST)
    public String deletezhuanlan(@RequestParam(name = "szid") int  szid) {
    	ZhuanLan zl = zhuanLanDao.findById(szid).get();    	
    	//List<ZhuanLan> zllist = zhuanLanDao.getzlbyid(zl.getId());
    	//zllist.add(zl);
    	//zhuanLanDao.deleteAll(zllist);
    	zhuanLanDao.deleteById(szid);
    	if (zl.getAtcSjk() != null) {
    		atcSjkDao.deleteById(zl.getAtcSjk().getId());
    	}
    	// saveServiceImpl.delShouYeXinXi(szid, WenZhangLeiXing.ZHUANLAN.getName());
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
