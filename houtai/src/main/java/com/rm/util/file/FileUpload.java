package com.rm.util.file;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUpload {
	public static final String FileUploadOk="FileUploadOk";
	public static final String FileUploadWrong="FileUploadWrong";
	private static final String FileExists="FileExists";
	private static final String FileNOTExists="FileNotExists";
	// 保存路径
	private String savePath = "";
	
	public String fileExist(HttpServletRequest request,String filename) throws Exception{
		String path = this.savePath;
		File fdir=new File(path);		
		if(!fdir.exists()||!fdir.isDirectory()){
			fdir.mkdirs();
		}
		path=path+File.separator+filename;
		/*File localFile = new File(path);*/
		if(fdir.isDirectory()){
			File[] fileList=fdir.listFiles();
			for(int i=0;i<fileList.length;i++){
				if(fileList[i].getName().equals(filename)){
					return FileExists;
				}
			}
		}
		return FileNOTExists;
	}
	
	public String uploadFile(HttpServletRequest request) throws Exception{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (!multipartResolver.isMultipart(request)) {
			System.out.println("file save kong return");
			return "";
		}
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String>  iter = multiRequest.getFileNames();
			System.out.println("file save begin");
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					String fileName = file.getOriginalFilename();
					String path = this.savePath;
					File fdir=new File(path);
					if(!fdir.exists()||!fdir.isDirectory()){
						fdir.mkdirs();
					}
					path=path+File.separator+fileName;
					File localFile = new File(path);
					try {
						file.transferTo(localFile);
						System.out.println("file save end");
					} catch (IllegalStateException e) {
						e.printStackTrace();
						return FileUploadWrong;
					} catch (IOException e) {
						e.printStackTrace();
						return FileUploadWrong;
					}
				}	
			}
		}
		return FileUploadOk;
	}

	public InputStream fenxiUploadFile(HttpServletRequest request) throws Exception{
		CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String>  iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				MultipartFile file = multiRequest.getFile((String)iter.next());
				if(file != null){
					return file.getInputStream();
				}	
			}
		}
		return null;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	

}