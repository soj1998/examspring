package com.rm.control.sys;

import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rm.dao.AtcSjkDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.AtcSjk;
import com.rm.util.StringUtil;
import com.rm.util.file.FileSaveSql;
import com.rm.util.file.UMEditor_Uploader;


@CrossOrigin(origins = "*")
@RequestMapping(value="/sys/szwz")
@RestController
public class SzWenZhangController {
	@Resource
    private AtcSjkDao atcSjkDao;  
	
	@Resource
    private TreeNodeSjkDao treeNodeSjkDao;  
	
	@Resource
    private TnsQbNeiRongDao tnsQbNeiRongDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(SzWenZhangController.class);
    @RequestMapping(value="/getbanben")
    public List<AtcSjk> listall(@RequestParam("szid") int szid,@RequestParam("wzlxid") int wzlxid){    	
    	List<AtcSjk> listall = atcSjkDao.getBanbenBySzWzlx(szid, wzlxid);
    	if (listall.size() > 0) {
    		LOG.info("banben " + listall.get(0).getVersion());
    	}
        return listall;
    }

    @ResponseBody
	@RequestMapping(value="/uploadsave",method=RequestMethod.POST)
	public String getUploadUmImage(HttpServletRequest request,@RequestParam("wzlx") int wzlx,@RequestParam("sz") int sz,@RequestParam("wzversion") String banben,@RequestParam("wzlaiyuan") String wzlaiyuan,@RequestParam("wzjiagou") String wzjiagou,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+"uploadfiles";
	    up.setSavePath(path);
	    String[] fileType = {".docx"};
	    up.setAllowFiles(fileType);
	    up.upload();
	    String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
	    // 保存的位置是 path + geturl
	    System.out.println("r    "+result);
	    int r1 = atcSjkDao.updateBySzWzlx(sz, wzlx);
	    if (r1 > 0) {
	    	System.out.println("update结果 update更新的条数    "+r1);
	    }
	    FileSaveSql fileSaveSql = new FileSaveSql();
		fileSaveSql.asoneinsertToSql(tnsQbNeiRongDao, treeNodeSjkDao, atcSjkDao,path + up.getUrl(), wzlx, banben, sz,wzlaiyuan,wzjiagou);
	    return result + "update jieguo " + r1;
	    
	}
    
   
}