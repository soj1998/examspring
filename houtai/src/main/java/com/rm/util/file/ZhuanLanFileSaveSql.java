package com.rm.util.file;



import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.ZhuanLan;
import com.rm.entity.lieju.Sz;
import com.rm.util.StringUtil;


public class ZhuanLanFileSaveSql {

	
	private String sz = "【税种】";
	private String laiyuan = "【来源】";
	private String riqi = "【日期】";
	private String xilie = "【系列】";
	private String zhengwen = "【正文】";
	
	private static final Logger LOG = LoggerFactory.getLogger(ZhuanLanFileSaveSql.class);
    
	//一个整体的存取
	public void asoneinsertToSql( AtcSjkDao atcSjkDao,SzDao szDao,ZhuanLanDao zhuanLanDao,
			String fileweizhi,int wzlx) {
		//存入试题和存入文章是不一样的 
		//存入试题 不搞有效标志 不搞关联 单独存的时候设为空
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		
		FileXiangGuan fileXiangGuan = new FileXiangGuan();
		JSONArray jsonDuanArray = fileXiangGuan.transFiletoList(fileweizhi);
		JSONArray hzarray = new JSONArray();
		fileXiangGuan.diGuiHz(0,hzarray,jsonDuanArray,sz);
		for (Object fd:hzarray) {
			//Map<Integer,String> szlist = null;
			//Map<Integer,String> laiyuanlist1 = null;
			//Map<Integer,String> shijianlist = null;
			//Map<Integer,String> xilielist = null;
			//Map<Integer,String> zwlist = null;
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
			Map<Integer,String> szlist = getGuiShu(sz_qz,arr);
			Map<Integer,String> laiyuanlist = getGuiShu(laiyuan_qz,arr);
			Map<Integer,String> riqilist = getGuiShu(riqi_qz,arr);
			Map<Integer,String> xilielist = getGuiShu(xilie_qz,arr);
			Map<Integer,String> zwlist = getGuiShu(zhengwen_qz,arr);
			String riqi2 = StringUtil.getMapString(riqilist, riqi);
			riqi2 = riqi2.replace("年", "-").replace("月", "-").replace("日", "");
			Date wddate = null;
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				LocalDate wddate2 = LocalDate.parse(riqi2, dateTimeFormatter);
				ZonedDateTime zonedDateTime = wddate2.atStartOfDay(ZoneId.systemDefault());
				wddate = Date.from(zonedDateTime.toInstant());
			} catch (DateTimeException e) {
				LOG.info("---时间转换错误，跳过");				
				e.printStackTrace();
				continue;
			}
			Date lrsj = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			AtcSjk atcSjk = new AtcSjk();
			Date lrsj2 = null!=wddate?wddate:lrsj;
			atcSjk.setLrsj(lrsj2);
			atcSjk.setFileweizhi(fileweizhi);
			String sz1 = StringUtil.getMapString(szlist, sz);
			Sz sza =szDao.findSzBymc(sz1);
			if (null == sza) {
				LOG.info("---税种错误，无法对应");
				continue;
			}
			atcSjk.setSzid(sza.getId());
			atcSjk.setWzlxid(wzlx);
			String ly = StringUtil.getMapString(laiyuanlist, laiyuan);
			atcSjk.setWzlaiyuan(ly);
			atcSjk.setYxbz("Y");
			Long atcid = atcSjkDao.save(atcSjk).getId();
			atcSjk.setId(atcid);				
			String xl = StringUtil.getMapString(xilielist, xilie);
			List<Map.Entry<Integer,String>> listmap = new ArrayList<Map.Entry<Integer,String>>(zwlist.entrySet());
			int minhstimu  = 0;
			for(Map.Entry<Integer,String> mapping:listmap){
	        	String a1 = mapping.getValue();
	        	a1 = a1.replace(zhengwen, "");
	        	int hs = mapping.getKey();		        	
	        	if (StringUtil.isNotEmpty(a1)) {
	        		minhstimu = minhstimu == 0? hs: minhstimu;
	        		minhstimu = minhstimu >= hs? hs: minhstimu;
	        	}
	        }
			try{
				int btid = -1;
				for(Map.Entry<Integer,String> mapping:listmap){
		        	String a1 = mapping.getValue();
		        	a1 = a1.replace(zhengwen, "");
		        	int hs = mapping.getKey();
		        	if(btid !=-1) {
		        		break;
		        	}
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
	            LOG.error("添加zhuanLanDao 失败!"+e.getMessage());
	        }
		}
		LOG.info(hzarray.size() + "");
	}

	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
		int ksd = 0;
		int jsd = 0;
		boolean czxl = false;
		switch(pdKey) {
			case "sz":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(sz)>= 0) {
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
						if (gets.indexOf(riqi)>= 0) {
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
			case "riqi":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(riqi)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}
						if (gets.indexOf(xilie)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
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
			case "xilie":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(xilie)>= 0) {
							czxl = true;
						}						
					}
				}
				if (!czxl) {
					return new int[] {-1,-1};
				}
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
	
	
}
