package com.rm;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.util.StringUtil;
import com.rm.util.file.FileUpload;
import com.rm.util.file.UMEditor_Uploader;



@Controller

@CrossOrigin(origins = "*")
public class MyTest {
	@Resource
    private TreeNodeSjkDao treeNodeSjkDao;
	
	@Resource
    private TnsQbNeiRongDao tnsQbNeiRongDao;
	
	private FileUpload fup=new FileUpload();
	
	@Value("${helloWorld.abc}")
	private String hello;
	
	@ResponseBody
	@RequestMapping("/hello")
    public String say(){
        return "Spring Boot 你大爷222！";
    }
	//@RequestMapping("/say") 搞了访问外部资源后访问不了html 暂时不弄了
    public ModelAndView say2(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message", "SpringBoot 大爷你好！");
        mav.setViewName("hh.html");
        return mav;
    }
	@ResponseBody
	@RequestMapping("/up")
    public JSONObject sayup(){
		System.out.println("aaaa");
		JSONObject mav=new JSONObject();
        mav.put("message", "SpringBoot 大爷你好！" + hello);
        return mav;
    }
	@ResponseBody
	@RequestMapping("/say2")
    public String say3(){
		return "hh3";
	}
	
	@ResponseBody
	@RequestMapping(value="/gettree",method=RequestMethod.GET)
    public void listerji(@RequestParam("pid") int pid){    	
        System.out.println(pid);
    }
	@ResponseBody
	@RequestMapping(value="/say3",method=RequestMethod.POST)
	public String getUploadUmImage(HttpServletRequest request,@RequestParam("wzlx") int wzlx,@RequestParam("sz") int sz,@RequestParam("wzversion") String banben,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UMEditor_Uploader up = new UMEditor_Uploader(request);
		String path=StringUtil.getRootDir(request,"houtai")
				+File.separator
				+"uploadfiles";
	    up.setSavePath(path,"aa");
	    String[] fileType = {".docx"};
	    up.setAllowFiles(fileType);
	    up.upload();
	    String callback = request.getParameter("callback");
	    String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
	    // 保存的位置是 path + geturl
	    System.out.println("r    "+result);
	    //FileSaveSql fileSaveSql = new FileSaveSql();
		//fileSaveSql.asoneinsertToSql(tnsQbNeiRongDao, treeNodeSjkDao, path + up.getUrl(), wzlx, banben, sz);
	    return "<script>"+ callback +"(" + result + ")</script>";
	    
	}
	@ResponseBody
	@RequestMapping("/say4")
	public void fileUpload(HttpServletRequest request,String file) throws Exception{
		System.out.println("say4 begin");
		if (!"without file".equals(file)){
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
			String savePath=StringUtil.getRootDir(request)
					+File.separator
					+"uploadfiles";
			savePath += File.separator + formater.format(new Date());
			fup.setSavePath(savePath);
			fup.uploadFile(request);
		}		
	}
	@PostMapping("/say5")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		if (file.isEmpty()) {
        	System.out.println("上传失败，请选择文件");
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String savePath=StringUtil.getRootDir(request)
				+File.separator
				+"uploadfiles";
        File dest = new File(savePath + fileName);
        try {
            file.transferTo(dest);
            System.out.println("上传成功");
            return "上传成功";
        } catch (IOException e) {
        	System.out.println("上传失败" + e.toString());
        }
        System.out.println("上传失败");
        return "上传失败！";
    }
}
