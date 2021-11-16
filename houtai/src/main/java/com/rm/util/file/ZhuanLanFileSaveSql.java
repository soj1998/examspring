package com.rm.util.file;



import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.BiaoTi;
import com.rm.entity.ExamZsd;
import com.rm.entity.ZhuanLan;
import com.rm.entity.lieju.Sz;
import com.rm.service.impl.SaveServiceImpl;
import com.rm.util.StringUtil;


public class ZhuanLanFileSaveSql {

	
	private String sz = "【学科】";
	private String zsd = "【知识点】";
	private String laiyuan = "【来源】";
	private String biaoti = "【标题】";
	private String riqi = "【日期】";
	private String xilie = "【系列】";
	private String zhengwen = "【正文】";
	
	private static final Logger LOG = LoggerFactory.getLogger(ZhuanLanFileSaveSql.class);
    
	//一个整体的存取
	public void asoneinsertToSql(HttpServletRequest request,SaveServiceImpl examQueService,AtcSjkDao atcSjkDao,SzDao szDao,ZhuanLanDao zhuanLanDao,
			String picurl,String fileweizhi,int wzlx) {
		//存入试题和存入文章是不一样的 
		//存入试题 不搞有效标志 不搞关联 单独存的时候设为空
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		
		FileXiangGuan fileXiangGuan = new FileXiangGuan();
		JSONArray jsonDuanArray = fileXiangGuan.transFiletoList(fileweizhi,request,picurl);
		JSONArray hzarray = new JSONArray();
		AtcSjk atcSjk = new AtcSjk();		
		atcSjk.setFileweizhi(fileweizhi);
		atcSjk.setWzlxid(wzlx);
		int atcid = atcSjkDao.save(atcSjk).getId();
		if (atcid <0 ) {
			LOG.info("---文档保存错误不能继续搞了" + atcid);	
			return;
		}
		int kashihangshu = fileXiangGuan.diGuiHzKaiShiHangShu(jsonDuanArray, StringUtil.getKaiShiBiaoZHi());
		fileXiangGuan.diGuiHz(kashihangshu,hzarray,jsonDuanArray,sz);
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
			int[] zsd_qz = getpanDuanGuiShuDian("zsd",arr,maxhangshu);
			int[] laiyuan_qz = getpanDuanGuiShuDian("laiyuan",arr,maxhangshu);
			int[] biaoti_qz = getpanDuanGuiShuDian("biaoti",arr,maxhangshu);
			int[] riqi_qz = getpanDuanGuiShuDian("riqi",arr,maxhangshu);
			int[] xilie_qz = getpanDuanGuiShuDian("xilie",arr,maxhangshu);
			int[] zhengwen_qz = getpanDuanGuiShuDian("zhengwen",arr,maxhangshu);
			JSONArray szlist = getGuiShu(sz_qz,arr);
			JSONArray zsdlist = getGuiShu(zsd_qz,arr);
			JSONArray laiyuanlist = getGuiShu(laiyuan_qz,arr);
			JSONArray biaotilist = getGuiShu(biaoti_qz,arr);
			JSONArray riqilist = getGuiShu(riqi_qz,arr);
			JSONArray xilielist = getGuiShu(xilie_qz,arr);
			JSONArray zwlist = getGuiShu(zhengwen_qz,arr);
			String riqi2 = StringUtil.getJSONArrayString(riqilist, StringUtil.getZhuanLanRiQi());
			String zsd2 = StringUtil.getJSONArrayString(zsdlist, StringUtil.getXiTiZhiShiDian());
			String biaoti2 = StringUtil.getJSONArrayString(biaotilist, StringUtil.getXiTiBiaoTi());
			riqi2 = riqi2.replace("年", "-").replace("月", "-").replace("日", "");
			//搞下知识点
			ExamZsd exzsd1 = null;
			if (StringUtil.isNotEmpty(zsd2)) {
				exzsd1 = examQueService.saveZsdEnd(zsd2);
				if (null == exzsd1) {
					LOG.error("添加ExamZsd 失败!,zsd没搞对" + zsd2);
					continue;
				}
			}
			Date wddate = null;
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				LocalDate wddate2 = LocalDate.parse(riqi2, dateTimeFormatter);
				ZonedDateTime zonedDateTime = wddate2.atStartOfDay(ZoneId.systemDefault());
				wddate = Date.from(zonedDateTime.toInstant());
			} catch (DateTimeException e) {
				LOG.info("---时间转换错误，跳过，输入的时间" + riqi2);				
				e.printStackTrace();
				continue;
			}
			Date lrsj = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			Date lrsj2 = null!=wddate?wddate:lrsj;
			//atcSjk.setLrsj(lrsj2);
			//atcSjk.setFileweizhi(fileweizhi);
			String sz1 = StringUtil.getJSONArrayString(szlist, StringUtil.getZhuanLanXueKe());
			Sz sza =szDao.findSzBymc(sz1);
			if (null == sza) {
				LOG.info("---学科错误，无法对应输入的学科" + sz1);
				continue;
			}
			
			String ly = StringUtil.getJSONArrayString(laiyuanlist, StringUtil.getZhuanLanLaiYuan());
			String xl = StringUtil.getJSONArrayString(xilielist, StringUtil.getZhuanLanXiLie());
			BiaoTi biaoti = new BiaoTi(biaoti2,ly,xl,lrsj2,"Y",sza.getId());
			
			BiaoTi biaoti21 = examQueService.saveBiaoTi(biaoti);
			if (biaoti21 == null ) {
				LOG.info("---标题保存错误，不继续了");
				continue;
			} else {
				biaoti = biaoti21;
			}
			
			//List<Map.Entry<Integer,String>> listmap = new ArrayList<Map.Entry<Integer,String>>(zwlist.entrySet());
			int minhstimu  = 0;
			for(Object mapping1:zwlist){
				JSONObject mapping = (JSONObject)mapping1;
	        	String a1 = mapping.getString("neirong");
	        	a1 = a1.replace(zhengwen, "");
	        	int hs = mapping.getIntValue("hangshu");		        	
	        	if (StringUtil.isNotEmpty(a1)) {
	        		minhstimu = minhstimu == 0? hs: minhstimu;
	        		minhstimu = minhstimu >= hs? hs: minhstimu;
	        	}
	        }
			try{
				int btid = -1;
				for(Object mapping1:zwlist){
					JSONObject mapping = (JSONObject)mapping1;
		        	String a1 = mapping.getString("neirong");
		        	a1 = a1.replace(zhengwen, "");
		        	if (StringUtil.isEmpty(a1)) {
		        		continue;
		        	}
		        	if (StringUtil.isNotEmpty(a1) && a1.length() > 1950) {
		        		LOG.info("段落信息超过1950太长啦，添加失败!");
		        		continue;
		        	}
		        	int hs = mapping.getIntValue("hangshu");
		        	if(btid !=-1) {
		        		break;
		        	}
		        	if (StringUtil.isNotEmpty(a1)) {
		        		if(hs == minhstimu) {
		        			ZhuanLan zlan = new ZhuanLan("Y",-1,hs,a1,xl,null);
		        			//zlan.setLrsj(lrsj2);
		        			//zlan.setWzlaiyuan(ly);
		        			//zlan.setSzid(sza.getId());
		        			//zlan.setYxbz("Y");
		        			zlan.setBiaoti(biaoti21.getId());
		        			zlan.setExzsdid(exzsd1.getId());
		        			btid = zhuanLanDao.save(zlan).getId();
		        			/**
		        			String wzlx1 = WenZhangLeiXing.ZHUANLAN.getName();
		        			if (exzsd1 != null) {
		        				zlan.setExzsdid(exzsd1.getId());
		        				btid = zhuanLanDao.save(zlan).getId();
			        			ShouYeXinXi syxx =new ShouYeXinXi(sza.getId(),sza.getSzmc(),
			        					biaoti2,exzsd1.getId(),exzsd1.getNeirong(),
			        					wddate,wzlx1,btid,-1,"Y");
			        			ShouYeXinXi syxx1 =examQueService.saveShouYeXinXi(syxx);
			        			if (syxx1 == null ) {
			        				LOG.info("添加ShouYeXinXi 失败!");
			        			}
		        			} else {
		        				btid = zhuanLanDao.save(zlan).getId();
		        				ShouYeXinXi syxx =new ShouYeXinXi(sza.getId(),sza.getSzmc(),
			        					biaoti2,
			        					wddate,wzlx1,btid,-1,"Y");
			        			ShouYeXinXi syxx1 =examQueService.saveShouYeXinXi(syxx);
			        			if (syxx1 == null ) {
			        				LOG.info("添加ShouYeXinXi 失败!");
			        			}
		        			}**/
		        		}
		        	}
		        	
		        }
				if(btid !=-1) {
					for(Object mapping1:zwlist){
						JSONObject mapping = (JSONObject)mapping1;
			        	String a1 = mapping.getString("neirong");
			        	a1 = a1.replace(zhengwen, "");
			        	if (StringUtil.isEmpty(a1)) {
			        		continue;
			        	}
			        	if (StringUtil.isNotEmpty(a1) && a1.length() > 1950) {
			        		LOG.info("段落信息超过1950太长啦，添加失败!");
			        		continue;
			        	}
			        	int hs = mapping.getIntValue("hangshu");		        	
			        	if (StringUtil.isNotEmpty(a1)) {
			        		if(hs != minhstimu) {
			        			ZhuanLan zlan = new ZhuanLan("Y",btid,hs,a1,xl,null);
			        			//zlan.setLrsj(lrsj2);
			        			//zlan.setWzlaiyuan(ly);
			        			//zlan.setSzid(sza.getId());
			        			//zlan.setYxbz("Y");
			        			//zlan.setExzsd(exzsd1);
			        			zhuanLanDao.save(zlan).getId();
			        		}
			        	}
			        }
				}
				
	        }catch (Exception e){
	            LOG.error("添加zhuanLanDao 失败!"+e.getMessage());
	        }
		}
		LOG.info(hzarray.size() + ",添加完毕");
	}

	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
		int ksd = 0;
		int jsd = 0;
		boolean czxl = false;
		switch(pdKey) {
			case "sz":{
				int szbz = 0;
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zsd)>= 0) {
							szbz = 1;
						}						
					}
				}
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(sz)>= 0) {
							ksd = jb.getIntValue("hangshu");
						}						
						if (szbz == 0 && gets.indexOf(laiyuan)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}
						if (szbz == 1 && gets.indexOf(zsd)>= 0) {
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
			case "zsd":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(zsd)>= 0) {
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
						if (gets.indexOf(zsd)>= 0) {
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
						if (gets.indexOf(biaoti)>= 0) {
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
			case "biaoti":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						if (gets.indexOf(biaoti)>= 0) {
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
	public Map<Integer,String> getGuiShu3(int[]qizhi,JSONArray crArray) {
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
	
}
