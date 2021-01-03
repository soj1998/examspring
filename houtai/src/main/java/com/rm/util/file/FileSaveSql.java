package com.rm.util.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.czentity.CzTreeNode;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;

public class FileSaveSql {

	/*
	 * 1. 读取文件
	 * 2. 文件按照段落保存为List
	 * 3. List搞成多叉树
	 * 4. 多叉树存到数据库
	 * 	函数有：
	 * 1、读取文件并搞成List
	 * 2、区分文件段落标题大小
	 * 3、List存为多叉树
	 * 4、多叉树存到数据库
	 * */
	/**
     * Word中的大纲级别，可以通过getPPr().getOutlineLvl()直接提取，但需要注意，Word中段落级别，通过如下三种方式定义： 
     *  1、直接对段落进行定义； 
     *  2、对段落的样式进行定义； 
     *  3、对段落样式的基础样式进行定义。 
     *  因此，在通过“getPPr().getOutlineLvl()”提取时，需要依次在如上三处读取。
     * @param doc
     * @param para
     * @return
   */
	public String getDocTitleLvl(XWPFDocument doc, XWPFParagraph para) {
		String titleLvl = "";
		try {
			// 判断该段落是否设置了大纲级别
			if (para.getCTP().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getCTP()");
				 //System.out.println(para.getParagraphText());
				 //System.out.println(para.getCTP().getPPr().getOutlineLvl().getVal());
				return String.valueOf(para.getCTP().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式是否设置了大纲级别
			if (doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getStyle");
				 //System.out.println(para.getParagraphText());
				 //System.out.println(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String.valueOf(
						doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式的基础样式是否设置了大纲级别
			if (doc.getStyles().getStyle(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal())
					.getCTStyle().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getBasedOn");
				 //System.out.println(para.getParagraphText());
				 String styleName = doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal();
				 //System.out.println(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String
						.valueOf(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			//System.out.println("getStyleID");
			if (para.getStyleID() != null) {
				//System.out.println("getStyleID" + 11);
				return para.getStyleID();
			}
		} catch (Exception e) {

		}

		return titleLvl;
	}
	
	/**
	 * 1.根据doc得到段落大纲 0 对应biaoti 1
	 * 2.返回一个jsonarray biaoti btneirong hangshu qbneirong
	 * 		qbneirong 又包括 neirong hangshu
	 * */
	public JSONArray transFiletoList(String filePath){
		InputStream is = null;
		XWPFDocument doc = null;
		JSONArray zhengLiArray = new JSONArray();
		try {
			is = new FileInputStream(filePath);
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return null;
			}
			JSONArray jsonDuanArray = new JSONArray();			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				JSONObject jsonDuan = new JSONObject();
				if("".equals(paras.get(i).getParagraphText())) {
					//jsonDuan.put("neirong", "");
					continue;
				}else {
					jsonDuan.put("neirong", paras.get(i).getParagraphText());
				}
				String titleLvl = getDocTitleLvl(doc, paras.get(i));// 获取段落级别
				jsonDuan.put("hangshu", i);
				switch(titleLvl) {
					case "0":
						jsonDuan.put("biaoti",1);
						break;
					case "1":
						jsonDuan.put("biaoti",2);
						break;
					case "2":
						jsonDuan.put("biaoti",3);
						break;
					case "3":
						jsonDuan.put("biaoti",4);
						break;
					case "4":
						jsonDuan.put("biaoti",5);
						break;
					case "5":
						jsonDuan.put("biaoti",6);
						break;
					case "6":
						jsonDuan.put("biaoti",7);
						break;
					case "7":
						jsonDuan.put("biaoti",8);
						break;
					case "8":
						jsonDuan.put("biaoti",9);
						break;
					default:
						jsonDuan.put("biaoti", 0);
						break;
				}
				//System.out.println("titleLvl:" + titleLvl + ",i-" + i+":"+paras.get(i).getParagraphText());
				jsonDuanArray.add(jsonDuan);
				/**
				System.out.println("1----:"+titleLvl);				
				if ("a5".equals(titleLvl) || "HTML".equals(titleLvl) || "".equals(titleLvl) || null == titleLvl) {
					titleLvl = "8";
				}
				//System.out.println(titleLvl + "-----");// 0,1,2
				if (!"8".equals(titleLvl)) {
					//System.out.println(titleLvl + "====" + para.getParagraphText());
				}
				*/
			}
			
			for(Object obj:jsonDuanArray) {
				JSONObject obj1 = (JSONObject)obj;
				JSONObject nobj = new JSONObject();
				if(obj1.getShort("biaoti")>0) {
					nobj.put("biaoti", obj1.getShort("biaoti"));
					nobj.put("btneirong", obj1.getString("neirong"));
					nobj.put("hangshu", obj1.getShort("hangshu"));
				}
				JSONArray zL2Array = new JSONArray();
				for(Object obj2:jsonDuanArray) {
					JSONObject obj3 = (JSONObject)obj2;
					if(obj3.getShort("biaoti") == 0
						&&
						obj3.getShort("hangshu") > obj1.getShort("hangshu")
					) 
					{						
						JSONObject nobj2 = new JSONObject();
						nobj2.put("neirong", obj3.getString("neirong"));
						nobj2.put("hangshu", obj3.getShort("hangshu"));
						zL2Array.add(nobj2);
					}
					if(obj3.getShort("biaoti") > 0
							&&
					   obj3.getShort("hangshu") > obj1.getShort("hangshu")) {
						break;
					}
				}
				if(null != nobj && nobj.size() > 0 )//&& null != zL2Array && zL2Array.size() > 0)
					nobj.put("qbneirong", zL2Array);
				if(null != nobj && nobj.size() > 0)
					zhengLiArray.add(nobj);
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
		return zhengLiArray;
	}
	
	public CzTreeNode transJsontoTreeMode(JSONArray zhengLiArray) {
		CzTreeNode mtree = new CzTreeNode();
		diGuiQiu(mtree, zhengLiArray);
		return mtree;
	}
	/**
	 *	用递归，求节点
	 */
	private CzTreeNode diGuiQiu(CzTreeNode mtree, JSONArray jsonDuanArray) {
		if (null == jsonDuanArray ||jsonDuanArray.size() == 0) {
			return mtree;
		}
		for (Object obj :jsonDuanArray) {
			JSONObject obj1 = (JSONObject)obj;
			if(null!= obj1.get("biaoti"))
	        {
	        	mtree.addTreeNodeByStack(obj1.getIntValue("biaoti") - 1,obj1);
	        	jsonDuanArray.remove(obj1);
	        	diGuiQiu(mtree, jsonDuanArray);	
	        	break;     	
	        }
			
		}		
		return mtree;
	}
	public void insertToSql(CzTreeNode mtree,TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao,String wzlx,String banben,String shuizhong,String fileweizhi) {
		//mtree.listAndInsSql(tnsneirongDao,tnDao,"zsd", "1.0.0.0", "zzs");
		mtree.listAndInsSql(tnsneirongDao,tnDao,wzlx, banben, shuizhong,fileweizhi);
	}
	
	
	//一个整体的存取
	public void asoneinsertToSql(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao,String fileweizhi,String wzlx,String banben,String shuizhong) {
		//mtree.listAndInsSql(tnsneirongDao,tnDao,"zsd", "1.0.0.0", "zzs");
		JSONArray mtransFiletoList =transFiletoList(fileweizhi);
		CzTreeNode mtransJsontoTreeMode = transJsontoTreeMode(mtransFiletoList);
		mtransJsontoTreeMode.listAndInsSql(tnsneirongDao,tnDao,wzlx, banben, shuizhong,fileweizhi);
	}
}
