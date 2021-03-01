package com.rm.control.sys;

import java.io.File;
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
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.ZhuanLan;
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
