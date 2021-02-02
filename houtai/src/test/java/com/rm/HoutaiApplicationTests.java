package com.rm;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.czentity.CzTreeNode;
import com.rm.dao.BookDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.entity.TreeNode;
import com.rm.entity.TreeNodeSjk;
import com.rm.util.StringUtil;


@SpringBootTest
class HoutaiApplicationTests {
	@Resource
    private BookDao bookDao;
	@Resource
    private XueKeDao xkDao;   
	@Resource
    private QueandAnsDao qaDao;
	@Resource
    private TreeNodeSjkDao tnDao; 
	@Resource
    private TnsQbNeiRongDao tnsneirongDao;
	@Resource
    private AuthorDao authorDao;
	@Resource
    private ArticleDao articleDao;
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	
	
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	private String[] zonghetimu = new String[] {"【综合单选题】","【综合多选题】","【综合计算题】"};
	private String[] xuanxiang = new String[] {"A.","B.","C.","D.","E.","F.","G.","H."};
	private String zsd = "【知识点】";
	private String zongheti = "【综合题】";
	private String daan = "【答案】";	
	private String jiexi = "【解析】";
	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		LOG.info("【解析】aaaaaaaabbbbbbbb" + "\n" + "aaa");
		LOG.info("【解析】aaaaaaaabbbbbbbb".substring(4));
		JSONObject p = new JSONObject();
		p.put("a", 1);
		System.out.println("aa" + p.getIntValue("b"));
		if(p.getIntValue("b") >= 0) {
			System.out.println("bb" + p.getIntValue("b"));
		}
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		
	}
	

	
	@Test
    public void test(){
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("d:\\学研社-zzs-xt2.docx");
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return;
			}
			JSONArray jsonDuanArray = new JSONArray();			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				JSONObject jsonDuan = new JSONObject();
				if("".equals(paras.get(i).getParagraphText())) {
					continue;
				}else {
					jsonDuan.put("neirong", paras.get(i).getParagraphText().trim());
					jsonDuan.put("hangshu", i);
					String d = paras.get(i).getParagraphText().trim();
					ExamChoi examChoi = new ExamChoi();
					ExamQue examQue = new ExamQue();
					switch ("abcddddd".substring(0, 4)) {
						case "【知识点" :
							examQue.setZzd(d.substring(5));
							break;
						case "【单选题" :
							break;
						case "【多选题" :
							break;
						case "【计算题" :
							break;
						case "【综合题" :
							break;
						case "【答案】" :
							break;
						case "【解析】" :
							break;
					}
					jsonDuanArray.add(jsonDuan);	
				}
							
			}
			JSONArray hzarray = new JSONArray();
			diGuiHz(0,hzarray,jsonDuanArray);
			JSONArray hzzharray = new JSONArray();
			Map<Integer,String> zsdlist,timulist,xuanxianglist,daanlist,jiexilist;
			Map<Integer,String> zhzsdlist,zhdatimulist,zhtimulist,zhxuanxianglist,zhdaanlist,zhjiexilist;
			for (Object fd:hzarray) {
				JSONObject jb = (JSONObject)fd;
				JSONArray arr = jb.getJSONArray("al");
				//先进行是否是综合题目的判断 非综合题进入非综合题
				if(!getpanDuanZonghe(arr,zongheti)) {
					int[] zsd_qz = getpanDuanGuiShuDian("zsd",arr);
					int[] timu_qz = getpanDuanGuiShuDian("timu",arr);
					int[] xuanxiang_qz = getpanDuanGuiShuDian("xuanxiang",arr);
					int[] daan_qz = getpanDuanGuiShuDian("daan",arr);
					int[] jiexi_qz = getpanDuanGuiShuDian("jiexi",arr);
					zsdlist = getGuiShu(zsd_qz,arr);
					timulist = getGuiShu(timu_qz,arr);
					xuanxianglist = getGuiShu(xuanxiang_qz,arr);
					daanlist = getGuiShu(daan_qz,arr);
					jiexilist = getGuiShu(jiexi_qz,arr);
					int szid = 3;
					ExamQue examQue = new ExamQue(szid,zsdlist,timulist,"Y",daanlist,jiexilist);
					examQueDao.save(examQue);
					saveExamChoi(examQue,xuanxianglist); 
				} else {					
					diGuiHzZhsy(0,hzzharray,arr);
					for (Object fd1:hzzharray) {
						JSONObject jb1 = (JSONObject)fd1;
						JSONArray arr1 = jb1.getJSONArray("al");
						int[] zsd_qz1 = getpanDuanGuiShuDian("zsd",arr1);
						int[] datimu_qz1 = getpanDuanGuiShuDian("datimu",arr1);
						int[] timu_qz1 = getpanDuanGuiShuDian("timu",arr1);
						int[] xuanxiang_qz1 = getpanDuanGuiShuDian("xuanxiang",arr1);
						int[] daan_qz1 = getpanDuanGuiShuDian("daan",arr1);
						int[] jiexi_qz1 = getpanDuanGuiShuDian("jiexi",arr1);
						zhzsdlist = getGuiShu(zsd_qz1,arr1);
						zhdatimulist = getGuiShu(datimu_qz1,arr1);
						zhtimulist = getGuiShu(timu_qz1,arr1);
						zhxuanxianglist = getGuiShu(xuanxiang_qz1,arr1);
						zhdaanlist = getGuiShu(daan_qz1,arr1);
						zhjiexilist = getGuiShu(jiexi_qz1,arr1);
						
					}
				}
			}
			System.out.println(hzarray.size());
			
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
    }
	
	

	
	
	
	 
	/**
	 *	用递归，求节点 List<ExamQue> quelist,List<ExamChoi> choilist
	 */
	private void saveExamChoi(ExamQue examQue, Map<Integer,String> map) {
		List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	String a = mapping.getValue();
        	if (StringUtil.isNotEmpty(a)) {
        		ExamChoi examChoi = new ExamChoi(a,examQue);
        		examChoiDao.save(examChoi);
        	}
        }
	}
	
	
	private String getMapString(Map<Integer,String> map) {
		StringBuilder sb = new StringBuilder();		
		//这里将map.entrySet转换为List
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	for(String tm:timu) {
        		mapping.getValue().replaceAll(tm, "");
			}
        	mapping.getValue().replaceAll(zsd, "");
        	mapping.getValue().replaceAll(daan, "");
        	mapping.getValue().replaceAll(jiexi, "");
        	sb = sb.append(mapping.getValue()); 
       } 
		return sb.toString();
	}
	
	
    
	private boolean getpanDuanZonghe(JSONArray crArray,String zongheti) {
		boolean rs= false;
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
				String gets = jb.getString("neirong");
				if (gets.indexOf(zongheti)>= 0) {
					rs = true;
					break;
				}
			}
		}
		return rs;
	}
	
	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray) {
		int ksd = 0;
		int jsd = 0;
		int maxhangshu  = 0;
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			if(jb.getIntValue("hangshu")>= maxhangshu) {
				maxhangshu = jb.getIntValue("hangshu");
			}
		}
		switch(pdKey) {
			case "zsd":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zsd)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						for(String tm:timu) {
							if (gets.indexOf(tm)>= 0) {
								jsd = jb.getIntValue("hangshu");
								return new int[] {ksd,jsd};
							}
						}
					}
				}
				break;
			}
			case "datimu":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zongheti)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						for(String tm:timu) {
							if(tm.equals(zongheti)) {
								continue;
							}
							if (gets.indexOf(tm)>= 0) {
								jsd = jb.getIntValue("hangshu");
								return new int[] {ksd,jsd};
							}
						}
					}
				}
				break;
			}
			case "timu":{
				boolean sfcz = false;
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf("【单选题】")>= 0) {
							sfcz = true;
							break;
						}
						if (gets.indexOf("【多选题】")>= 0) {
							sfcz = true;
							break;
						}
						if (gets.indexOf("【判断题】")>= 0) {
							sfcz = true;
							break;
						}
					}
				}
				
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");						
						for(String tm:timu) {
							if (gets.indexOf(tm)>= 0) {
								ksd = jb.getIntValue("hangshu");
								break;
							}
						}
						if (sfcz) {
							for(String tm:xuanxiang) {
								if (gets.indexOf(tm)>= 0) {
									jsd = jb.getIntValue("hangshu");
									return new int[] {ksd,jsd};
								}
							}
						} else {
							if (gets.indexOf(daan)>= 0) {
								jsd = jb.getIntValue("hangshu");
								return new int[] {ksd,jsd};
							}
						}
					}
				}
				break;
			}
			case "xuanxiang":{
				boolean sfcz = false;
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf("【单选题】")>= 0) {
							sfcz = true;
							break;
						}
						if (gets.indexOf("【多选题】")>= 0) {
							sfcz = true;
							break;
						}
						if (gets.indexOf("【判断题】")>= 0) {
							sfcz = true;
							break;
						}
					}
				}
				if (sfcz) {
					for (Object fd:crArray) {
						JSONObject jb = (JSONObject)fd;
						if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
							String gets = jb.getString("neirong");
							if (gets.indexOf(xuanxiang[0])>= 0) {
								ksd = jb.getIntValue("hangshu");
							}
							if (gets.indexOf(daan)>= 0) {
								jsd = jb.getIntValue("hangshu");
							}
						}
					}
				}
				break;
			}
			case "daan":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(daan)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(jiexi)>= 0) {
							jsd = jb.getIntValue("hangshu");							
						}					
					}
				}
				break;
			}
			case "jiexi":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(jiexi)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
					}
				}
				break;
			}
		}		
		jsd = jsd==0?maxhangshu:jsd;
		return new int[] {ksd,jsd};
	}
	private Map<Integer,String> getGuiShu(int[]qizhi,JSONArray crArray) {
		Map<Integer,String> rs = new HashMap<Integer,String>();
		int qi = qizhi[0];
		int zhi = qizhi[1];
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			int a = jb.getIntValue("hangshu");
			if (a >= qi && a < zhi) {
				Map<Integer,String> hashmap = new HashMap<Integer,String>();
				hashmap.put(a, jb.getString("neirong"));
				rs.put(a, jb.getString("neirong"));
			}
		}
		return rs;
	}
	
	
	private JSONArray diGuiHz(int hs,JSONArray rsArray,JSONArray csArray) {
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
				if(d.indexOf("【知识点】") == 0) {
					if(StringUtil.isNotEmpty(j.getString("zsd"))) {
						jarray.remove(jarray.size()-1);
						rsArray.add(j);
						diGuiHz(i,rsArray,csArray);
						break;
					}
					j.put("al", jarray);
					j.put("zsd", "1");
					continue;
				}				
	        }
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	
	private JSONArray diGuiHzZhsy(int hs,JSONArray rsArray,JSONArray csArray) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		boolean br = false;
		for (int i = hs;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
	        {
				String d = obj1.get("neirong").toString();
				jarray.add(obj1);
				for(String tm:timu) {
					if (d.indexOf(tm)>= 0) {
						if(StringUtil.isNotEmpty(j.getString("zh"))) {
							jarray.remove(jarray.size()-1);
							rsArray.add(j);
							diGuiHzZhsy(i,rsArray,csArray);
							return rsArray;
						}
						j.put("al", jarray);
						j.put("zh", "1");
						br = true;
						break;
					}
				}
				if(br) {
					continue;
				}
	        }
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	
	private JSONArray diGuiHuiZong(int hs,JSONArray rsArray,JSONArray csArray) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray xuanxarray = new JSONArray();
		StringBuilder strb = new StringBuilder();
		for (int i = hs;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
	        {
				String d = obj1.get("neirong").toString();
				strb.append(d);
				if(d.indexOf("【知识点】") == 0) {
					if(StringUtil.isNotEmpty(j.getString("zsd"))) {
						rsArray.add(j);
						diGuiHuiZong(i,rsArray,csArray);
						break;
					}
					//j.put("zsd", d.substring(5));
					continue;
				}
				if(d.indexOf("【单选题】") == 0) {
					j.put("tmlx", "danxuan");
					j.put("zsd", strb.substring(5));
					strb = new StringBuilder();
					j.put("wenti", d.substring(5));
					continue;
				}
				if(d.indexOf("【多选题】") == 0) {
					j.put("tmlx", "duoxuan");
					j.put("zsd", strb.substring(5));
					strb = new StringBuilder();
					j.put("wenti", d.substring(5));
					continue;
				}				
				if(d.indexOf("【计算题】") == 0) {
					j.put("tmlx", "jisuan");
					j.put("zsd", strb.substring(5));
					strb = new StringBuilder();
					j.put("wenti", d.substring(5));
					continue;
				}
				if(d.indexOf("【综合题】") == 0) {
					j.put("tmlx", "zonghe");
					j.put("zsd", strb.substring(5));
					strb = new StringBuilder();
					j.put("wenti", d.substring(5));
					continue;
				}
				if(d.indexOf("【答案】") == 0) {
					if(xuanxarray.size()>0) {
						j.put("xuanx", xuanxarray);
					}
					j.put("daan", d.substring(4));
					continue;
				}
				if(d.indexOf("【解析】") == 0) {
					j.put("jiexi", d.substring(4));
					continue;
				}
				if(StringUtil.isNotEmpty(j.getString("tmlx")) && j.getString("tmlx").indexOf("xuan") > 0) {
					j.put("wenti", strb.substring(5));
					strb = new StringBuilder();
					for (String xx:xuanxiang) {
						if(d.indexOf(xx)>=0) {
							JSONObject j1 = new JSONObject();
							j1.put("xuanx", d);
							xuanxarray.add(j1);
							break;
						}
					}
				}
	        }
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	private JSONArray diGuiGao(JSONArray rsArray,JSONArray csArray) {
		if (null == csArray ||csArray.size() == 0) {
			return rsArray;
		}
		for (Object obj :csArray) {
			JSONObject obj1 = (JSONObject)obj;
			if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
	        {
				String d = obj1.get("neirong").toString();
				ExamChoi examChoi = new ExamChoi();
				ExamQue examQue = new ExamQue();
				switch (d.substring(0, 4)) {
					case "【知识点" :
						examQue.setZzd(d.substring(5));
						break;
					case "【单选题" :
						break;
					case "【多选题" :
						break;
					case "【计算题" :
						break;
					case "【综合题" :
						break;
					case "【答案】" :
						break;
					case "【解析】" :
						break;
				}
	        	csArray.remove(obj1);
	        	diGuiGao(csArray, rsArray);	
	        	break;     	
	        }
			
		}
		return rsArray;
	}

	/**
	 *1.搞一个json 把读到的信息储存到mysql，信息包括多少段，多少章，多少节，多少目，
	 *章节目对应的段落数 多少空段落 对应的段落数。
	 *2.对每一个章节目 存到mysql 包括上下文和具体文字
	 *3.概括有个数字 对应着详细	
	 *4.用多叉树存储相关信息
	 *5.按多叉树的id存到mysql数据库
	 *6.建表 myacl article 
	 *  id 自增字段  文章类型  类型为 知识点，随笔，案例，前沿
	 *  涉及税种 增值税、消费税  版本 1.0.0.0 录入时间
	 *  rootid 多叉树id biaoti 标题段落   btneirong 标题内容
	 *  qbneirong 标题下的全部内容
	*/
	@Test
    public void test2(){
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("d:\\菜鸟税法.docx");
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return;
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
				String titleLvl = getTitleLvl(doc, paras.get(i));// 获取段落级别
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
			JSONArray zhengLiArray = new JSONArray();
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
			zhengLiArray.forEach(e->{
				JSONObject obj1 = (JSONObject)e;
				if (obj1.getString("btneirong").equals("自来水"))
				{
					//System.out.println("找到一个");
				}
			});
			CzTreeNode mtree = new CzTreeNode();
			diGuiQiu(mtree, zhengLiArray);
			//非递归 遍历
			//mtree.displayTreeByStack();
			//mtree.list();
			//list并且插入到数据库 		
			//mtree.listAndInsSql(tnsneirongDao,tnDao,0, "1.0.0.0", 0,"fileweizhi");
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
	}
	
	
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
	private String getTitleLvl(XWPFDocument doc, XWPFParagraph para) {
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
	public void listAndInsSql(List<TreeNode> list,String wzlx,String wzversion,String sz){
       for(TreeNode item:list){
       	TreeNodeSjk tn = new TreeNodeSjk();
       	//tn.setVersion(wzversion);			
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			//tn.setQbneirong(item.getData().getJSONArray("qbneirong").toString());
			//tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
           tn.setRootid(item.getId());            
           //tn.setId(-1);
           System.out.println(tn.toString());
           tnDao.save(tn);
           System.out.println(item.getId() + "," + item.getData().toJSONString());
           if(item.nodes.size() == 0){
               continue;
           }else {
           	listAndInsSql(item.nodes,wzlx,wzversion,sz);
           }
           System.out.println();
       }
   }
	/**
	 * 得到当前传入paragraph的最大标题度
	 
	private int getMaxBiaoTiDuanWei(JSONArray jsonDuanArray) {
		int DuanLuoDingDuanWei = 1;
		if (null == jsonDuanArray || 0 == jsonDuanArray.size()) {
			return DuanLuoDingDuanWei;
		}
		for (int j = 0; j < jsonDuanArray.size(); j++) {
           JSONObject obj = (JSONObject)jsonDuanArray.get(j);
           if(null!= obj.get("biaoti") && (int)(obj.get("biaoti"))>=0) {
           	if(DuanLuoDingDuanWei < (int)(obj.get("biaoti"))) {
           		DuanLuoDingDuanWei = (int)(obj.get("biaoti"));
           	}
           }	            
		}
		return DuanLuoDingDuanWei;
	}
	/**
	 * 得到当前传入paragraph的最小标题度
	 
	private int getMinBiaoTiDuanWei(JSONArray jsonDuanArray) {
		int DuanLuoDingDuanWei = 1;
		if (null == jsonDuanArray || 0 == jsonDuanArray.size()) {
			return DuanLuoDingDuanWei;
		}
		for (int j = 0; j < jsonDuanArray.size(); j++) {
           JSONObject obj = (JSONObject)jsonDuanArray.get(j);
           if(null!= obj.get("biaoti") && (int)(obj.get("biaoti"))>=0) {
           	DuanLuoDingDuanWei = (int)(obj.get("biaoti"));
           	return DuanLuoDingDuanWei;
           }	            
		}
		return DuanLuoDingDuanWei;
	}
	*/
	
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
	        	//mtree.addright((int)(obj1.get("biaoti")) - 1, obj1);
				//System.out.println("btneirong  1  " + obj1.getString("btneirong"));
	        	//mtree.addright(obj1);
				mtree.addTreeNodeByStack(obj1.getIntValue("biaoti") - 1,obj1);
	        	jsonDuanArray.remove(obj1);
	        	if (obj1.getString("btneirong").equals("自来水")) {
	        		System.out.println("挂上一次");
	        	}
	        	diGuiQiu(mtree, jsonDuanArray);	
	        	break;     	
	        }
			
		}		
		return mtree;
	}
}
