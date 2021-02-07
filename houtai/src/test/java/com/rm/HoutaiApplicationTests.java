package com.rm;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.czentity.CzTreeNode;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.BookDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamChoiZongHe;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamQueZongHeXiao;
import com.rm.entity.TreeNode;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.ZhuanLan;
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
	@Resource
    private ExamQueZongHeDaDao examQueZongHeDaDao;
	@Resource
    private ExamQueZongHeXiaoDao examQueZongHeXiaoDao;
	@Resource
    private ExamChoiZongHeDao examChoiZongHeDao;
	@Resource
    private AtcSjkDao atcSjkDao;
	@Resource 
    private ZhuanLanDao zhuanLanDao;
	
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	private String[] xuanxiang = new String[] {"A.","B.","C.","D.","E.","F.","G.","H."};
	private String zsd = "【知识点】";
	private String wdjs = "【结束】";
	private String zongheti = "【综合题】";
	private String daan = "【答案】";	
	private String jiexi = "【解析】";
	
	
	private String szbz = "【税种】";
	private String laiyuan = "【来源】";
	private String shijian = "【日期】";
	private String xilie = "【系列】";
	private String zhengwen = "【正文】";
	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		LOG.info("【解析】aaaaaaaabbbbbbbb" + "\n" + "aaa");
		LOG.info(sources.length() + "");
		for (int i = 0;i<15;i++) {
			LOG.info(StringUtil.getRandomString(10));
		}
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		
	}
	

	
	@Test
    public void test(){
		//已经存入的数据 高一列存下来 还要搞一列记入时间
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("d:\\学研社-zzs-zl.docx");
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
					jsonDuanArray.add(jsonDuan);	
				}		
			}
			JSONArray hzarray = new JSONArray();
			diGuiHz(0,hzarray,jsonDuanArray);
			Map<Integer,String> szlist,laiyuanlist,shijianlist,xilielist,zwlist;
			for (Object fd:hzarray) {
				JSONObject jb = (JSONObject)fd;
				JSONArray arr = jb.getJSONArray("al");
				//先进行是否是综合题目的判断 非综合题进入非综合题				
				int maxhangshu  = 0;
				for (Object a1:arr) {
					JSONObject b1 = (JSONObject)a1;
					if(b1.getIntValue("hangshu")>= maxhangshu) {
						maxhangshu = b1.getIntValue("hangshu");
					}
				}
				int[] sz_qz = getpanDuanGuiShuDian("sz",arr,maxhangshu);
				int[] laiyuan_qz = getpanDuanGuiShuDian("laiyuan",arr,maxhangshu);
				int[] riqi_qz = getpanDuanGuiShuDian("riqi",arr,maxhangshu);
				int[] xilie_qz = getpanDuanGuiShuDian("xilie",arr,maxhangshu);
				int[] zhengwen_qz = getpanDuanGuiShuDian("zhengwen",arr,maxhangshu);
				szlist = getGuiShu(sz_qz,arr);
				laiyuanlist = getGuiShu(laiyuan_qz,arr);
				shijianlist = getGuiShu(riqi_qz,arr);
				xilielist = getGuiShu(xilie_qz,arr);
				zwlist = getGuiShu(zhengwen_qz,arr);
				Date lrsj = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
				AtcSjk atcSjk = new AtcSjk();
				atcSjk.setLrsj(lrsj);
				atcSjk.setSzid(3);
				atcSjk.setWzlxid(4);
				atcSjk.setWzlaiyuan("abc");
				Long atcid = atcSjkDao.save(atcSjk).getId();
				atcSjk.setId(atcid);				
				List<String> mapzhuan = new ArrayList<String>();
				mapzhuan.add(xilie);
				String xl = StringUtil.getMapString(xilielist, mapzhuan);
				List<Map.Entry<Integer,String>> listmap = new ArrayList<Map.Entry<Integer,String>>(zwlist.entrySet());
				int minhstimu  = 0;
				for(Map.Entry<Integer,String> mapping:listmap){
		        	String a1 = mapping.getValue();
		        	a1 = a1.replace(zhengwen, "");
		        	int hs = mapping.getKey();
		        	minhstimu = minhstimu == 0? hs: minhstimu;
		        	if (StringUtil.isNotEmpty(a1)) {
		        		minhstimu = minhstimu <= hs? hs: minhstimu;
		        	}
		        }
				try{
					int btid = -1;
					for(Map.Entry<Integer,String> mapping:listmap){
			        	String a1 = mapping.getValue();
			        	a1 = a1.replace(zhengwen, "");
			        	int hs = mapping.getKey();		        	
			        	if (StringUtil.isNotEmpty(a1)) {
			        		if(hs == minhstimu) {
			        			ZhuanLan zlan = new ZhuanLan(-1,hs,a1,xl,atcSjk);
			        			btid = zhuanLanDao.save(zlan).getId();
			        		}
			        	}
			        }
					if(btid !=-1) {
						for(Map.Entry<Integer,String> mapping:listmap){
				        	String a1 = mapping.getValue();
				        	a1 = a1.replace(zhengwen, "");
				        	int hs = mapping.getKey();		        	
				        	if (StringUtil.isNotEmpty(a1)) {
				        		if(hs != minhstimu) {
				        			ZhuanLan zlan = new ZhuanLan(btid,hs,a1,xl,atcSjk);
				        			zhuanLanDao.save(zlan).getId();
				        		}
				        	}
				        }
					}				
		        }catch (Exception e){
		            LOG.error("添加examQueDao 失败!"+e.getMessage());
		        }
			}
			System.out.println(hzarray.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
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
        		try{
        			examChoiDao.save(examChoi);
		        }catch (Exception e){
		            LOG.error("添加examChoiDao 失败!"+e.getMessage());
		            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
		        }
        	}
        }
	}
	
	private void saveExamZongHeXiaoChoi(ExamQueZongHeXiao examQueZongHeXiao, Map<Integer,String> map) {
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
        		ExamChoiZongHe examChoi = new ExamChoiZongHe(a,examQueZongHeXiao);
        		try{
        			examChoiZongHeDao.save(examChoi);
		        }catch (Exception e){
		            LOG.error("添加examChoiZongHeDao 失败!"+e.getMessage());
		            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
		        }
        	}
        }
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
	
	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
		int ksd = 0;
		int jsd = 0;		
		switch(pdKey) {
			case "sz":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(szbz)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(laiyuan)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}						
					}
				}
				break;
			}
			case "laiyuan":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(laiyuan)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(shijian)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}
					}
				}
				break;
			}
			case "shijian":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(shijian)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(xilie)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}
					}
				}
				break;
			}
			case "xilie":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(xilie)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(zhengwen)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}
					}
				}
				break;
			}			
			case "zhengwen":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zhengwen)>= 0) {
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
			if (qi>=0 && a >= qi && a <= zhi) {
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
				if(d.indexOf(szbz) >= 0 || i == csArray.size() - 1) {
					if(StringUtil.isNotEmpty(j.getString("zsd"))) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
						rsArray.add(j);
						diGuiHz(i,rsArray,csArray);
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


/**

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
**/