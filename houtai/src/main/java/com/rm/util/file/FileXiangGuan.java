package com.rm.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.util.StringUtil;


public class FileXiangGuan {
	/**
	 * 1.根据doc得到段落array 
	 * */
	private static final Logger LOG = LoggerFactory.getLogger(FileXiangGuan.class);
    
	public JSONArray transFiletoList(String filePath){
		InputStream is = null;
		XWPFDocument doc = null;
		JSONArray jsonDuanArray = new JSONArray();	
		try {
			is = new FileInputStream(filePath);
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				LOG.info("当前文档没有读取到的段落数");
				doc.close();
				return null;
			}
			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				List<XWPFRun> runs = paras.get(i).getRuns();				
				String jpgcunchu = poitoPic(doc,runs);
				if (null != jpgcunchu) {
					JSONObject jsonDuan = new JSONObject();
					jsonDuan.put("neirong", jpgcunchu);
					jsonDuan.put("hangshu", i);					
					jsonDuanArray.add(jsonDuan);
				}
				String abc = paras.get(i).getParagraphText().trim();				
				abc = StringUtil.removeTailSpace(abc);
				if(StringUtil.isEmpty(abc)) {
					continue;
				}else {
					JSONObject jsonDuan = new JSONObject();
					jsonDuan.put("neirong", abc);
					jsonDuan.put("hangshu", i);					
					jsonDuanArray.add(jsonDuan);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonDuanArray;
	}
	
	public JSONArray transFiletoList(String filePath,HttpServletRequest request,String pirurl){
		InputStream is = null;
		XWPFDocument doc = null;
		JSONArray jsonDuanArray = new JSONArray();	
		try {
			is = new FileInputStream(filePath);
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				LOG.info("当前文档没有读取到的段落数");
				doc.close();
				return null;
			}
			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				List<XWPFRun> runs = paras.get(i).getRuns();				
				String jpgcunchu = poitoPic(request,doc,runs,pirurl);
				if (null != jpgcunchu) {
					JSONObject jsonDuan = new JSONObject();
					jsonDuan.put("neirong", jpgcunchu);
					jsonDuan.put("hangshu", i);					
					jsonDuanArray.add(jsonDuan);
				}
				String abc = paras.get(i).getParagraphText().trim();				
				abc = StringUtil.removeTailSpace(abc);
				if(StringUtil.isEmpty(abc)) {
					continue;
				}else {
					JSONObject jsonDuan = new JSONObject();
					jsonDuan.put("neirong", abc);
					jsonDuan.put("hangshu", i);					
					jsonDuanArray.add(jsonDuan);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonDuanArray;
	}
	
	public String poitoPic(XWPFDocument document,List<XWPFRun> runs) throws IOException {
		for (XWPFRun run : runs) {
			Node node = run.getCTR().getDomNode();
			// drawing 一个绘画的图片
			Node drawingNode = getChildNode(node, "w:drawing");
			if (drawingNode == null) {
				continue;
			}
			// 绘画图片的宽和高
			Node extentNode = getChildNode(drawingNode, "wp:extent");
			NamedNodeMap extentAttrs = extentNode.getAttributes();
			System.out.println("宽：".concat(extentAttrs.getNamedItem("cx").getNodeValue()).concat("emu"));
			System.out.println("高：".concat(extentAttrs.getNamedItem("cy").getNodeValue()).concat("emu"));
			
			// 绘画图片具体引用
			Node blipNode = getChildNode(drawingNode, "a:blip");
			NamedNodeMap blipAttrs = blipNode.getAttributes();
			String rid = blipAttrs.getNamedItem("r:embed").getNodeValue();
			System.out.println("word中图片ID：".concat(rid));
			
			// 获取图片信息
			PackagePart part = document.getPartById(rid);
			System.out.println(part.getContentType());
			System.out.println(part.getPartName().getName());
			String filenameext = part.getPartName().getName().substring(part.getPartName().getName().lastIndexOf("."));
			System.out.println(part.getInputStream());
			System.out.println("------ run ------");
			String abc = transInputToPic(part.getInputStream(),filenameext);
			return abc;
		}
		return null;

	}
	
	public String poitoPic(HttpServletRequest request, XWPFDocument document,List<XWPFRun> runs,String pirurl) throws IOException {
		for (XWPFRun run : runs) {
			Node node = run.getCTR().getDomNode();
			// drawing 一个绘画的图片
			Node drawingNode = getChildNode(node, "w:drawing");
			if (drawingNode == null) {
				continue;
			}
			// 绘画图片的宽和高
			Node extentNode = getChildNode(drawingNode, "wp:extent");
			NamedNodeMap extentAttrs = extentNode.getAttributes();
			System.out.println("宽：".concat(extentAttrs.getNamedItem("cx").getNodeValue()).concat("emu"));
			System.out.println("高：".concat(extentAttrs.getNamedItem("cy").getNodeValue()).concat("emu"));
			
			// 绘画图片具体引用
			Node blipNode = getChildNode(drawingNode, "a:blip");
			NamedNodeMap blipAttrs = blipNode.getAttributes();
			String rid = blipAttrs.getNamedItem("r:embed").getNodeValue();
			System.out.println("word中图片ID：".concat(rid));
			
			// 获取图片信息
			PackagePart part = document.getPartById(rid);
			System.out.println(part.getContentType());
			System.out.println(part.getPartName().getName());
			String filenameext = part.getPartName().getName().substring(part.getPartName().getName().lastIndexOf("."));
			System.out.println(part.getInputStream());
			System.out.println("------ run ------");
			String abc = transInputToPic(request, part.getInputStream(),filenameext,pirurl);
			return abc;
		}
		return null;

	}
	
	
	public String transInputToPic(HttpServletRequest request,InputStream inputs,String filenameext,String pirurl) throws IOException {
		String path = null;
		FileOutputStream fos = null;
		try {
			path = StringUtil.getRootDir(request,"houtai")
					+File.separator
					+StringUtil.getUploadFiles()
					+File.separator
					+StringUtil.getPicFiles();
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
			path += File.separator + formater.format(new Date());
			Random random = new Random();
			String setSavePathDir = path+File.separator
					+ "zhuanlan" + File.separator+"img";
			File fdir=new File(setSavePathDir);
			if(!fdir.exists()||!fdir.isDirectory()){
				fdir.mkdirs();
			}
			String filename = random.nextInt(10000)
					+ System.currentTimeMillis() 
					+ filenameext;
			fos = new FileOutputStream(setSavePathDir + File.separator + filename);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = inputs.read(b)) != -1){
				fos.write(b,0,length);
			}
			
			String rs = setSavePathDir + File.separator + filename;
			String url2=rs.substring(rs.lastIndexOf("uploadfiles") + 11);
			String urlr = url2.replaceAll("\\\\","/");
			String rs1 = "http://" +pirurl+":8080/houtai/image" + urlr;
			System.out.println(rs1);
			return rs1;
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (inputs != null ) {
				inputs.close();
			}
			if (fos != null ) {
				fos.close();
			}
		}
		return null;
	}
	
	public String transInputToPic(InputStream inputs,String filenameext) throws IOException {
		String path = null;
		FileOutputStream fos = null;
		try {
			path = "D:"
					+File.separator
					+"uploadfiles";
			SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
			path += File.separator + formater.format(new Date());
			Random random = new Random();
			String setSavePathDir = path+File.separator
					+ "zhuanlan" + File.separator+"img";
			File fdir=new File(setSavePathDir);
			if(!fdir.exists()||!fdir.isDirectory()){
				fdir.mkdirs();
			}
			String filename = random.nextInt(10000)
					+ System.currentTimeMillis() 
					+ filenameext;
			
			fos = new FileOutputStream(setSavePathDir + File.separator + filename);
			byte[] b = new byte[1024];
			int length = 0;
			while((length = inputs.read(b)) != -1){
				fos.write(b,0,length);
			}
			
			String rs = setSavePathDir + File.separator + filename;
			String url2=rs.substring(rs.lastIndexOf("uploadfiles") + 11);
			String urlr = url2.replaceAll("\\\\","/");
			String rs1 = "http://" +"123"+":8080/houtai/image" + urlr;
			System.out.println(rs1);
			return rs1;
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (inputs != null ) {
				inputs.close();
			}
			if (fos != null ) {
				fos.close();
			}
		}
		return null;
	}
	
	private Node getChildNode(Node node, String nodeName) {
		if (!node.hasChildNodes()) {
			return null;
		}
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			if (nodeName.equals(childNode.getNodeName())) {
				return childNode;
			}
			childNode = getChildNode(childNode, nodeName);
			if (childNode != null) {
				return childNode;
			}
		}
		return null;
	}

	// ---------正式开始--------- 以此为开始标志
	public int diGuiHzKaiShiHangShu (JSONArray jsarray,String kaishibiaozhi) {
		for(Object mapping1:jsarray){
			JSONObject mapping = (JSONObject)mapping1;
        	String a1 = mapping.getString("neirong");
        	int hs = mapping.getIntValue("hangshu");		        	
        	if (StringUtil.isNotEmpty(a1)
        			&&
        		a1.indexOf(kaishibiaozhi) >= 0) {
        		return hs;
        	}
        }
		return 0;
	}
	
	
	public JSONArray diGuiHz(int hs,JSONArray rsArray,JSONArray csArray,String jiangebz) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = 0;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			int ac = obj1.getIntValue("hangshu");
			if(ac <= hs) {
				continue;
			}
			String d = obj1.getString("neirong");
			if(StringUtil.isNotEmpty(d))
	        {
				jarray.add(obj1);
				if(d.indexOf(jiangebz) >= 0 || i == csArray.size() - 1) {
					if(StringUtil.isNotEmpty(j.getString("szbz"))) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
						rsArray.add(j);
						diGuiHz(i,rsArray,csArray,jiangebz);
						break;
					}
					j.put("al", jarray);
					j.put("szbz", "1");
					continue;
				}				
	        }			
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	public List<JSONArray> fenSanDigui(int hs,JSONArray rsArray,JSONArray csArray,String[] panduanchuanru){
		List<JSONArray> rs = new ArrayList<JSONArray>();
		//先把多余的去除
		JSONArray csArrayxin = new JSONArray();
		if (hs > 0) {
			for (int i = 0;i<csArray.size();i++) {
				JSONObject obj1 = (JSONObject)csArray.get(i);
				int ac = obj1.getIntValue("hangshu");
				if(ac <= hs) {
					continue;
				}
				csArrayxin.add(obj1);
			}
		}
		JSONArray csArrayxin2 = hs > 0 && csArrayxin != null  ? csArrayxin : csArray;
		//再考虑太多行会发生内存溢出
		int xinshul = csArrayxin2.size()/2;
		if (csArrayxin2 != null && csArrayxin2.size() > 100) {
			//防止内存泄漏，分成两块
			JSONArray csArray1 = new JSONArray();	
			JSONArray csArray2 = new JSONArray();			
			for (int i = 0;i<csArrayxin2.size();i++) {
				JSONObject obj1 = (JSONObject)csArray.get(i);
				if(i <= xinshul) {
					csArray1.add(obj1);
				}
				else {
					csArray2.add(obj1);
				}
			}
			List<JSONArray> rsArrayxin1 = fenSanDigui(0,new JSONArray(),csArray1,panduanchuanru);
			List<JSONArray> rsArrayxin2 = fenSanDigui(0,new JSONArray(),csArray2,panduanchuanru);
			rs.addAll(rsArrayxin1);
			rs.addAll(rsArrayxin2);
		}else {
			rs.add(csArrayxin2);
		}
		return rs;
	}
	
	
	public JSONArray diGuiHzXin(int hs,JSONArray rsArray,JSONArray csArray,String[] panduanchuanru) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = 0;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			int ac = obj1.getIntValue("hangshu");
			if(hs > 0 && ac <= hs) {
				continue;
			}
			String d = obj1.getString("neirong");
			boolean panduandao = false;
			for (String abc : panduanchuanru) {
				int zhaodao1 = d.indexOf(abc);
				int zhaodao2 = abc.indexOf(d);
				String abc1 = abc.replaceAll("【", "");
				abc1 = abc1.replaceAll("】", "");
				int zhaodao3 = d.indexOf(abc1);
				if (zhaodao1 >= 0 || zhaodao2 >= 0
						|| zhaodao3 >= 0) {
					panduandao = true;
					break;
				}
			}
			if(StringUtil.isNotEmpty(d))
	        {
				jarray.add(obj1);
				if(panduandao || i == csArray.size() - 1) {
					if(StringUtil.isNotEmpty(j.getString("szbz"))) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
						rsArray.add(j);
						diGuiHzXin(ac-1,rsArray,csArray,panduanchuanru);
						break;
					}
					j.put(StringUtil.getJianGeBiaoZHi(), jarray);
					j.put("szbz", "1");
					continue;
				}				
	        }			
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	
	//对选项为数字而言 要等于0才行 即开头是1. 2.才行
	public JSONArray diGuiHzXin2(int hs,JSONArray rsArray,JSONArray csArray,String[] panduanchuanru) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = 0;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			int ac = obj1.getIntValue("hangshu");
			if(hs > 0 && ac <= hs) {
				continue;
			}
			String d = obj1.getString("neirong");
			boolean panduandao = false;
			for (String abc : panduanchuanru) {
				int zhaodao1 = d.indexOf(abc);
				int zhaodao2 = abc.indexOf(d);
				String abc1 = abc.replaceAll("【", "");
				abc1 = abc1.replaceAll("】", "");
				int zhaodao3 = d.indexOf(abc1);
				if (zhaodao1 == 0 || zhaodao2 == 0
						|| zhaodao3 == 0) {
					panduandao = true;
					break;
				}
			}
			if(StringUtil.isNotEmpty(d))
	        {
				jarray.add(obj1);
				if(panduandao || i == csArray.size() - 1) {
					if(StringUtil.isNotEmpty(j.getString("szbz"))) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
						rsArray.add(j);
						diGuiHzXin2(ac-1,rsArray,csArray,panduanchuanru);
						break;
					}
					j.put(StringUtil.getJianGeBiaoZHi(), jarray);
					j.put("szbz", "1");
					continue;
				}				
	        }			
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	//map key是唯一的 当有重复的行数时 造成数据的丢失 改用 jsonobject jsonarray
	public JSONArray getGuiShu(int[]qizhi,JSONArray crArray) {
		JSONArray rs = new JSONArray();
		int qi = qizhi[0];
		int zhi = qizhi[1];
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			int a = jb.getIntValue("hangshu");
			if (qi>=0 && a >= qi && a <= zhi) {
				JSONObject one = new JSONObject();
				one.put("hangshu", a);
				one.put("neirong", jb.getString("neirong"));
				rs.add(one);
			}
		}
		return rs;
	}
	
	public static void writeLogToFile(String filename,String a) {
		
		File file = new File(filename);
		//String content = "This is the text content";

		try (FileOutputStream fop = new FileOutputStream(file, true)) {
	
	    // if file doesn't exists, then create it
	    if (!file.exists()) {
	    	file.createNewFile();
	    }

	    // get the content in bytes
	    byte[] contentInBytes = a.getBytes();

	    fop.write(contentInBytes);
	    fop.write("\r\n".getBytes());
	    fop.flush();
	    fop.close();


	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	 
	}
	
	
}
