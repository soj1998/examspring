package com.rm.util.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
				JSONObject jsonDuan = new JSONObject();
				String abc = paras.get(i).getParagraphText().trim();				
				abc = StringUtil.myTrim(abc);
				if("".equals(abc)) {
					continue;
				}else {
					jsonDuan.put("neirong", abc);
					jsonDuan.put("hangshu", i);
					if(StringUtil.isNotEmpty(abc)) {
						jsonDuanArray.add(jsonDuan);	
					}
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
	
	public JSONArray diGuiHz(int hs,JSONArray rsArray,JSONArray csArray,String jiangebz) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = hs;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
	        {
				String d = obj1.get("neirong").toString();
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
}
