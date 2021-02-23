package com.rm.util.file;


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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.entity.AtcSjk;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamChoiZongHe;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamQueZongHeXiao;
import com.rm.service.impl.SaveServiceImpl;
import com.rm.util.StringUtil;


public class SzExamFileSaveSql {

	
	private String zsd = "【知识点】";
	private String wdjs = "【结束】";
	private String zongheti = "【综合题】";
	private String daan = "【答案】";	
	private String jiexi = "【解析】";
	
	private static final Logger LOG = LoggerFactory.getLogger(SzExamFileSaveSql.class);
    
	/**
	 1.把文章按照段落转化为jsonarry
	 * */
	public JSONArray fileToDuanLuo(String filePath){
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
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return jsonDuanArray;
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
	
	private void saveExamChoi(SaveServiceImpl examQueService, ExamQue examQue, Map<Integer,String> map) {
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
        			examQueService.saveExamChoi(examChoi);
		        }catch (Exception e){
		            LOG.error("添加examChoiDao 失败!"+e.getMessage());
		            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
		        }
        	}
        }
	}
	
	private void saveExamZongHeXiaoChoi(SaveServiceImpl examQueService,ExamQueZongHeXiao examQueZongHeXiao, Map<Integer,String> map) {
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
        			examQueService.saveExamChoiZongHe(examChoi);
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
			case "zsd":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zsd)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						for(String tm:StringUtil.getXiTiLeiXingZw()) {
							if (gets.indexOf(tm)>= 0) {
								jsd = jb.getIntValue("hangshu");
								if (jsd-ksd > 0) {
									jsd = jsd -1;
								}
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
						for(String tm:StringUtil.getXiTiLeiXingZw()) {
							if(tm.equals(zongheti)) {
								continue;
							}
							if (gets.indexOf(tm)>= 0) {
								jsd = jb.getIntValue("hangshu");
								if (jsd-ksd > 0) {
									jsd = jsd -1;
								}
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
						for(String tm:StringUtil.getXiTiLeiXingZw()) {
							if (gets.indexOf(tm)>= 0) {
								ksd = jb.getIntValue("hangshu");
								break;
							}
						}
						if (sfcz) {
							for(String tm:StringUtil.getXuanXiangBz()) {
								if (gets.indexOf(tm)>= 0) {
									jsd = jb.getIntValue("hangshu");
									if (jsd-ksd > 0) {
										jsd = jsd -1;
									}
									return new int[] {ksd,jsd};
								}
							}
						} else {
							if (gets.indexOf(daan)>= 0) {
								jsd = jb.getIntValue("hangshu");
								if (jsd-ksd > 0) {
									jsd = jsd -1;
								}
								return new int[] {ksd,jsd};
							}
						}
					}
				}
				break;
			}
			case "xuanxiang":{
				boolean sfcz = false;
				boolean sfcz1 =false;
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						for (String abc: StringUtil.getXiTiLeiXingZwYouXuanXiang()) {
							if (gets.indexOf(abc)>= 0) {
								sfcz1 = true;
								break;
							}
						}
						if (sfcz1) {
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
							if (gets.indexOf(StringUtil.getXuanXiangBz()[0])>= 0) {
								ksd = jb.getIntValue("hangshu");
							}
							if (gets.indexOf(daan)>= 0) {								
								jsd = jb.getIntValue("hangshu");
								if (jsd-ksd > 0) {
									jsd = jsd -1;
								}
							}
						}
					}
				} else {
					ksd = -1;
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
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
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
						ksd = ksd==0?-1:ksd;
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
				if(d.indexOf(zsd) >= 0 || d.indexOf(wdjs) >= 0
						|| i == csArray.size() - 1) {
					if(StringUtil.isNotEmpty(j.getString("zsd"))) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
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
				for(String tm:StringUtil.getXiTiLeiXingZw()) {
					if (d.indexOf(tm)>= 0 || i == csArray.size() - 1) {
						if(StringUtil.isNotEmpty(j.getString("zh"))) {
							if (i != csArray.size() - 1) {
								jarray.remove(jarray.size()-1);
							}
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
	
	
	
	
	//一个整体的存取
	public void asoneinsertToSql(SaveServiceImpl examQueService,
			String fileweizhi,int wzlx,int shuizhong,String wzlaiyuan) {
		//存入试题和存入文章是不一样的 
		//存入试题 不搞有效标志 不搞关联 单独存的时候设为空
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		AtcSjk asjk =new AtcSjk();
		asjk.setFileweizhi(fileweizhi);
		Date lrsj = Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant());
		asjk.setLrsj(lrsj);
		asjk.setSzid(shuizhong);
		asjk.setWzlxid(wzlx);
		asjk.setWzlaiyuan(wzlaiyuan);
		asjk.setYxbz("Y");
		AtcSjk fid = examQueService.saveAtcSjk(asjk);
		JSONArray yuanshiarray =fileToDuanLuo(fileweizhi);
		JSONArray hzarray = new JSONArray();
		diGuiHz(0,hzarray,yuanshiarray);
		Map<Integer,String> zsdlist,timulist,xuanxianglist,daanlist,jiexilist;
		Map<Integer,String> zhzsdlist,zhdatimulist,zhtimulist,zhxuanxianglist,zhdaanlist,zhjiexilist;
		for (Object fd:hzarray) {
			JSONObject jb = (JSONObject)fd;
			JSONArray arr = jb.getJSONArray("al");			
			//先进行是否是综合题目的判断 非综合题进入非综合题
			if(!getpanDuanZonghe(arr,zongheti)) {
				int maxhangshu  = 0;
				for (Object a1:arr) {
					JSONObject b1 = (JSONObject)a1;
					if(b1.getIntValue("hangshu")>= maxhangshu) {
						maxhangshu = b1.getIntValue("hangshu");
					}
				}
				int[] zsd_qz = getpanDuanGuiShuDian("zsd",arr,maxhangshu);
				int[] timu_qz = getpanDuanGuiShuDian("timu",arr,maxhangshu);
				int[] xuanxiang_qz = getpanDuanGuiShuDian("xuanxiang",arr,maxhangshu);
				int[] daan_qz = getpanDuanGuiShuDian("daan",arr,maxhangshu);
				int[] jiexi_qz = getpanDuanGuiShuDian("jiexi",arr,maxhangshu);
				zsdlist = getGuiShu(zsd_qz,arr);
				timulist = getGuiShu(timu_qz,arr);
				xuanxianglist = getGuiShu(xuanxiang_qz,arr);
				daanlist = getGuiShu(daan_qz,arr);
				jiexilist = getGuiShu(jiexi_qz,arr);
				ExamQue examQue = new ExamQue(fid,shuizhong,zsdlist,timulist,"Y",lrsj,daanlist,jiexilist);
				try{
					ExamQue examQue2 = examQueService.saveExamQue(examQue);
					if (null == examQue2) {
						LOG.error("添加ExamQue 失败!,问题已存在");
						continue;
					}
					saveExamChoi(examQueService,examQue2,xuanxianglist); 
		        }catch (Exception e){
		            LOG.error("添加examQueDao 失败!"+e.getMessage());
		            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
		        }				
			} else {
				JSONArray hzzharray = new JSONArray();
				diGuiHzZhsy(0,hzzharray,arr);
				ExamQueZongHeDa examQueda = new ExamQueZongHeDa();
				boolean dadecunhao = false;
				for (Object fd1:hzzharray) {
					JSONObject jb1 = (JSONObject)fd1;
					JSONArray arr1 = jb1.getJSONArray("al");
					int maxhangshu  = 0;
					for (Object a1:arr1) {
						JSONObject b1 = (JSONObject)a1;
						if(b1.getIntValue("hangshu")>= maxhangshu) {
							maxhangshu = b1.getIntValue("hangshu");
						}
					}
					int[] zsd_qz1 = getpanDuanGuiShuDian("zsd",arr1,maxhangshu);
					int[] datimu_qz1 = getpanDuanGuiShuDian("datimu",arr1,maxhangshu);
					zhzsdlist = getGuiShu(zsd_qz1,arr1);
					zhdatimulist = getGuiShu(datimu_qz1,arr1);
					if(zhdatimulist.size()>0) {
						examQueda = new ExamQueZongHeDa(fid,shuizhong,zhzsdlist,zhdatimulist,"Y",lrsj);
						try{
							ExamQueZongHeDa examQueda2 = examQueService.saveExamQueZongHeDa(examQueda);
							if(null == examQueda2) {
								LOG.error("添加ExamQueZongHeDa 失败!,问题已存在");
								continue;
							}
							dadecunhao = true;
				        }catch (Exception e){
				            LOG.error("添加examQueZongHeDaDao 失败!"+e.getMessage());
				            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
				        }
					}
				}
				if (dadecunhao) {
					for (Object fd1:hzzharray) {
						JSONObject jb1 = (JSONObject)fd1;
						JSONArray arr1 = jb1.getJSONArray("al");
						if(getpanDuanZonghe(arr1,zongheti)) {
							continue;
						}
						if(getpanDuanZonghe(arr1,zsd)) {
							continue;
						}
						int maxhangshu  = 0;
						for (Object a1:arr1) {
							JSONObject b1 = (JSONObject)a1;
							if(b1.getIntValue("hangshu")>= maxhangshu) {
								maxhangshu = b1.getIntValue("hangshu");
							}
						}
						int[] timu_qz1 = getpanDuanGuiShuDian("timu",arr1,maxhangshu);
						int[] xuanxiang_qz1 = getpanDuanGuiShuDian("xuanxiang",arr1,maxhangshu);
						int[] daan_qz1 = getpanDuanGuiShuDian("daan",arr1,maxhangshu);
						int[] jiexi_qz1 = getpanDuanGuiShuDian("jiexi",arr1,maxhangshu);
						zhtimulist = getGuiShu(timu_qz1,arr1);
						zhxuanxianglist = getGuiShu(xuanxiang_qz1,arr1);
						zhdaanlist = getGuiShu(daan_qz1,arr1);
						zhjiexilist = getGuiShu(jiexi_qz1,arr1);
						ExamQueZongHeXiao examQueXiao = new ExamQueZongHeXiao(zhtimulist,zhdaanlist,zhjiexilist,examQueda);
						try{
							examQueService.saveExamQueZongHeXiao(examQueXiao);
				        }catch (Exception e){
				            LOG.error("添加examQueZongHeXiaoDao 失败!"+e.getMessage());
				            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
				        }						
						saveExamZongHeXiaoChoi(examQueService,examQueXiao,zhxuanxianglist); 
					}
				}
			}
		}
		System.out.println(hzarray.size());
	}
}
