package com.rm.util.file;



import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.entity.AtcSjk;
import com.rm.entity.BiaoTi;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamDaan;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamZsd;
import com.rm.entity.lieju.Sz;
import com.rm.entity.lieju.WenZhangLeiXing;
import com.rm.service.impl.FindServiceImpl;
import com.rm.service.impl.SaveServiceImpl;
import com.rm.util.StringUtil;



public class SzExamFileSaveSql {

	
    private String wdjs = "【结束】";
	
	private int xitishuzifenzugeshu = 100;
	private String[] xitishuzifenzufuhao = StringUtil.getXiTiShuZiFenZuFuHao();
	
	private static final Logger LOG = LoggerFactory.getLogger(SzExamFileSaveSql.class);
    
	
	private String jsonarrjieduan = "a1";
		
	private void saveExamChoi(SaveServiceImpl examQueService, ExamQue examQue, JSONArray map) {
		JSONArray sortedJsonArray = StringUtil.getJSONArraySorted(map);
        for(Object mapping:sortedJsonArray){
        	JSONObject jone = (JSONObject)mapping;
        	String a = jone.getString("neirong");
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
	
	private void saveExamDaan(SaveServiceImpl examQueService, ExamQue examQue, JSONArray map) {
		try{
			String daan1 = StringUtil.getJSONArrayString(map,StringUtil.getXiTiDaan());
			if (StringUtil.isNotEmpty(daan1)) {
				ExamDaan daan2 = new ExamDaan(daan1,examQue.getId()); 
				examQueService.saveExamDaan(daan2);
			}
        }catch (Exception e){
            LOG.error("添加saveExamDaan 失败!"+e.getMessage());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
        }
	}
	
	
	//有问题 有答案就行
	private boolean getPanDuanXinXiQuan(JSONArray crArray) {
		boolean rs= false;
		boolean timuyou = false;
		boolean timuyouwai = false;
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
				String gets = jb.getString("neirong");
				if(!timuyou) {
					for(String timu : StringUtil.getXiTiLeiXingZw()) {
						if (gets.indexOf(timu)>= 0) {
							timuyou = true;
							break;
						}
					}
				}
				if(!timuyouwai) {
					for(String timu : StringUtil.getXiTiDaan()) {
						if (gets.indexOf(timu)>= 0) {
							timuyouwai = true;
							break;
						}
					}
				}
			}
			if (timuyou && timuyouwai) {
				rs = true;
				break;
			}
		}
		return rs;
	}
		
    
	private boolean getpanDuanZonghe(JSONArray crArray,String[] zongheti) {
		boolean rs= false;
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			if (rs) {
				break;
			}
			if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
				String gets = jb.getString("neirong");
				for (String a: zongheti) {
					if (gets.indexOf(a)>= 0) {
						rs = true;
						break;
					}
				}				
			}
		}
		return rs;
	}
	
	private boolean getpanDuanXuanXiang(JSONArray crArray,String[] danxuan) {
		boolean rs= false;
		boolean tc = false;
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
				String gets = jb.getString("neirong");
				for (String d : danxuan) {
					if (gets.indexOf(d)>= 0) {
						tc = true;
						break;
					}
				}
			}
			if(tc) {
				rs = true;
				break;
			} 
		}
		return rs;
	}
	private String laiyuan = "【来源】";
	private String biaoti = "【标题】";
	private String riqi = "【日期】";
	private String xilie = "【系列】";
	private int[] getpanDaDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
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
						boolean t = false;
						for (String a : zsd) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
							szbz = 1;
						}					
					}
				}
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						boolean t = false;
						for (String a : sz) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
							ksd = jb.getIntValue("hangshu");
						}						
						if (szbz == 0 && gets.indexOf(laiyuan)>= 0) {
							jsd = jb.getIntValue("hangshu");
							if (jsd-ksd > 0) {
								jsd = jsd -1;
							}
							return new int[] {ksd,jsd};
						}
						boolean t1 = false;
						for (String a : zsd) {
							if (gets.indexOf(a)>= 0) {
								t1 = true;
								break;
							}
						}
						if (szbz == 1 && t1) {
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
						boolean t = false;
						for (String a : zsd) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
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
						boolean t = false;
						for (String a : zsd) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
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
	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
		int ksd = 0;
		int jsd = 0;		
		switch(pdKey) {
			case "sz":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						boolean t = false;
						for (String a : sz) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
							ksd = jb.getIntValue("hangshu");
						}
						for(String tm:zsd) {
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
			case "zsd":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						boolean t = false;
						for (String a : zsd) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
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
						boolean t = false;
						for (String a : StringUtil.getXiTiZongHeTi()) {
							if (gets.indexOf(a)>= 0) {
								t = true;
								break;
							}
						}
						if (t) {
							ksd = jb.getIntValue("hangshu");
						}
						for(String tm:StringUtil.getXiTiLeiXingZw()) {
							boolean t1 = false;
							for (String a : StringUtil.getXiTiZongHeTi()) {
								if (gets.indexOf(a)>= 0) {
									t1 = true;
									break;
								}
							}
							if(t1) {
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
						for (String ab:StringUtil.getXiTiLeiXingZwYouXuanXiangZw()) {
							if (gets.indexOf(ab)>= 0) {
								sfcz = true;
								break;
							}	
						}						
					}
				}
				
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");						
						for(String tm:StringUtil.getXiTiLeiXingZw()) {
							if (ksd > 0) {
								break;
							}
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
							boolean t = false;
							for (String a : daan) {
								if (gets.indexOf(a)>= 0) {
									t = true;
									break;
								}
							}
							if (t) {
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
						for (String abc: StringUtil.getXiTiLeiXingZwYouXuanXiangZw()) {
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
							for(String tm:StringUtil.getXuanXiangBzTou()) {
								if (ksd > 0) {
									break;
								}
								if (gets.indexOf(tm)>= 0) {
									ksd = jb.getIntValue("hangshu");
									break;
								}
							}
							boolean t = false;
							for (String a : daan) {
								if (gets.indexOf(a)>= 0) {
									t = true;
									break;
								}
							}
							if (t) {								
								jsd = jb.getIntValue("hangshu");
								if (jsd-ksd > 0) {
									jsd = jsd -1;
								}
								return new int[] {ksd,jsd};
							}
						}
					}
				} else {
					ksd = maxhangshu + 1;
				}
				break;
			}
			case "daan":{
				for (Object fd:crArray) {
					JSONObject jb = (JSONObject)fd;
					if(StringUtil.isNotEmpty(jb.getString("neirong"))) {
						String gets = jb.getString("neirong");
						boolean t1 = false;
						boolean t2 = false;
						for (String a : daan) {
							if (gets.indexOf(a)>= 0) {
								t1 = true;
								break;
							}
						}
						for (String a : jiexi) {
							if (gets.indexOf(a)>= 0) {
								t2 = true;
								break;
							}
						}
						if (t1) {
							ksd = jb.getIntValue("hangshu");
						}
						if (t2) {							
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
						boolean t2 = false;
						for (String a : jiexi) {
							if (gets.indexOf(a)>= 0) {
								t2 = true;
								break;
							}
						}
						if (t2) {
							ksd = jb.getIntValue("hangshu");
						}
						ksd = ksd==0?maxhangshu + 1:ksd;
					}
				}
				break;
			}
		}		
		jsd = jsd==0?maxhangshu:jsd;
		return new int[] {ksd,jsd};
	}
	
	
	
	public JSONArray diGuiHz(int hs,JSONArray rsArray,JSONArray csArray,String[] panduanchuanru) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = hs;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			String d = obj1.get("neirong").toString();
			String d1 = StringUtil.removeTailSpace(d);
			if(StringUtil.isNotEmpty(d1))
	        {
				jarray.add(obj1);
				String[] timuzu = panduanchuanru; //StringUtil.getXiTiLeiXingZw();
				boolean panduandao = false;
				for (String abc : timuzu) {
					int zhaodao1 = d1.indexOf(abc);
					int zhaodao2 = abc.indexOf(d1);
					if (zhaodao1 >= 0 || zhaodao2 >= 0) {
						panduandao = true;
						break;
					}
				}
				if(panduandao || d.indexOf(wdjs) >= 0
						|| i == csArray.size() - 1) {
					if((panduandao || i == csArray.size() - 1) && i!=hs) {
						if (i != csArray.size() - 1) {
							jarray.remove(jarray.size()-1);
						}
						if (j.size() >= 1) {
							rsArray.add(j);
						}
						diGuiHz(i,rsArray,csArray,panduanchuanru);
						break;
					}
					if (jarray.size() > 0) {
						j.put(jsonarrjieduan, jarray);
					}
					continue;
				}				
	        }			
			if (i == csArray.size() - 1) {
				return rsArray;
			}
		}
		return rsArray;
	}
	
	
	
	
	
	
	
	
	private String[] sz = StringUtil.getZhuanLanXueKe();
	private String[] zsd = StringUtil.getXiTiZhiShiDian();
	private String zhengwen = "【正文】";
	private String[] daan = StringUtil.getXiTiDaan();	
	private String[] jiexi = StringUtil.getXiTiJieXi();
	//一个整体的存取
	public String asoneinsertToSql(HttpServletRequest request,FindServiceImpl findService,SaveServiceImpl examQueService,
			String picurl,String fileweizhi) {
			//,int shuizhong,String wzlaiyuan,int danduchongfu) {
		//存入试题和存入文章是不一样的 
		//存入试题 不搞有效标志 不搞关联 单独存的时候设为空
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		//多选 判断 单选 以外的题 放到 examansda 里面
		String jilulog = fileweizhi.substring(0,fileweizhi.lastIndexOf("szexam") + 7)
				+ "szexamlog.txt";		
		FileXiangGuan fileXiangGuan = new FileXiangGuan();
		JSONArray jsonDuanArray = fileXiangGuan.transFiletoList(fileweizhi,request,picurl);
		Date lrsj = Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant());
		AtcSjk atcSjk = new AtcSjk();		
		atcSjk.setFileweizhi(fileweizhi);
		atcSjk.setWzlxid(WenZhangLeiXing.SHITI.getIndex());
		atcSjk.setLrsj(lrsj);
		atcSjk.setWzlxid(WenZhangLeiXing.SHITI.getIndex());
		atcSjk.setYxbz("Y");
		AtcSjk fid = examQueService.saveAtcSjk(atcSjk);
		if (fid  == null  ) {
			LOG.info("---文档保存错误不能继续搞了");	
			return "文档保存错误不能继续搞了";
		}
		int kashihangshu = fileXiangGuan.diGuiHzKaiShiHangShu(jsonDuanArray, StringUtil.getKaiShiBiaoZHi());
		JSONArray yuanshiarray = new JSONArray();
		for(Object one:jsonDuanArray){
			JSONObject one1 = (JSONObject)one;
        	int hs = one1.getIntValue("hangshu");		        	
        	if (hs >= kashihangshu) {
        		yuanshiarray.add(one1);
        	}
        }
		JSONArray hzarray = new JSONArray();
		fileXiangGuan.diGuiHzXin(kashihangshu,hzarray,yuanshiarray,sz);
		boolean biaotione = false;
		for (Object fd2:hzarray) {
			JSONObject jb2 = (JSONObject)fd2;
			JSONArray arr2 = jb2.getJSONArray(StringUtil.getJianGeBiaoZHi());//a1 al 简直坑爹
			int maxhangshu2  = 0;
			for (Object a1:arr2) {
				JSONObject b1 = (JSONObject)a1;
				if(b1.getIntValue("hangshu")>= maxhangshu2) {
					maxhangshu2 = b1.getIntValue("hangshu");
				}
			}
			int[] sz_qz = getpanDaDuanGuiShuDian("sz",arr2,maxhangshu2);
			int[] zsd_qz = getpanDaDuanGuiShuDian("zsd",arr2,maxhangshu2);
			int[] laiyuan_qz = getpanDaDuanGuiShuDian("laiyuan",arr2,maxhangshu2);
			int[] biaoti_qz = getpanDaDuanGuiShuDian("biaoti",arr2,maxhangshu2);
			int[] riqi_qz = getpanDaDuanGuiShuDian("riqi",arr2,maxhangshu2);
			int[] xilie_qz = getpanDaDuanGuiShuDian("xilie",arr2,maxhangshu2);
			int[] zhengwen_qz = getpanDaDuanGuiShuDian("zhengwen",arr2,maxhangshu2);
			JSONArray szlist = fileXiangGuan.getGuiShu(sz_qz,arr2);
			JSONArray zsdlist = fileXiangGuan.getGuiShu(zsd_qz,arr2);
			JSONArray laiyuanlist = fileXiangGuan.getGuiShu(laiyuan_qz,arr2);
			JSONArray biaotilist = fileXiangGuan.getGuiShu(biaoti_qz,arr2);
			JSONArray riqilist = fileXiangGuan.getGuiShu(riqi_qz,arr2);
			JSONArray xilielist = fileXiangGuan.getGuiShu(xilie_qz,arr2);
			JSONArray zwlist = fileXiangGuan.getGuiShu(zhengwen_qz,arr2);
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
			} else {
				LOG.error("zsd2为空" + zsd2);
				continue;
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
			lrsj = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			Date lrsj2 = null!=wddate?wddate:lrsj;
			//atcSjk.setLrsj(lrsj2);
			//atcSjk.setFileweizhi(fileweizhi);
			String sz1 = StringUtil.getJSONArrayString(szlist, StringUtil.getZhuanLanXueKe());
			Sz sza =findService.getSzByMc(sz1);
			if (null == sza) {
				LOG.info("---学科错误，无法对应输入的学科" + sz1);
				continue;
			}
			String ly = StringUtil.getJSONArrayString(laiyuanlist, StringUtil.getZhuanLanLaiYuan());
			String xl = StringUtil.getJSONArrayString(xilielist, StringUtil.getZhuanLanXiLie());
			BiaoTi biaoti = new BiaoTi(biaoti2,ly,xl,lrsj2,"Y",sza.getId());
			if (!biaotione) {
				BiaoTi biaoti21 = examQueService.saveBiaoTi(biaoti);
				if (biaoti21 == null ) {
					LOG.info("---标题保存错误，不继续了");
					continue;
				} else {
					biaoti = biaoti21;
				}
			}
			JSONArray hzarray3 = new JSONArray();
			for(Object mapping1:zwlist){
				JSONObject mapping = (JSONObject)mapping1;
	        	String a1 = mapping.getString("neirong");
	        	a1 = a1.replace(zhengwen, "");
	        	if (StringUtil.isEmpty(a1)) {
	        		continue;
	        	}
	        	hzarray3.add(mapping1);
	        }
			if (hzarray3 == null || hzarray3.size() <= 0) {
				LOG.error("试题添加失败,没搞对" + biaoti2);
				continue;
			}
			String[] timuleix = StringUtil.getXiTiLeiXingZw();			
			JSONArray hzarray4 = new JSONArray();
			fileXiangGuan.diGuiHzXin(0,hzarray4,hzarray3,timuleix);
			for (Object fd:hzarray4) { 
				JSONObject jb3 = (JSONObject)fd;
				JSONArray arr3 = jb3.getJSONArray(jsonarrjieduan);
				JSONArray hzarray2 = new JSONArray();
				JSONArray hzarray5 = new JSONArray();//hzarray2 的信息加工后填到hzarray5
				String[] timufenge = StringUtil.getXiTiShuZiFenZu(xitishuzifenzugeshu, xitishuzifenzufuhao);
				fileXiangGuan.diGuiHzXin(0,hzarray2,arr3,timufenge);
				String timuxuanding = "";
				boolean timuxuandinga = false;
				for (Object fd3:arr3) {
					JSONObject jb1 = (JSONObject)fd3;
					String arr1 = jb1.getString("neirong");
					String arr21 = StringUtil.myTrim(arr1);
					if (timuxuandinga) {
						break;
					}
					if (StringUtil.isNotEmpty(arr21)) {
						for (String a :timuleix) {
							String ab = a.replaceAll("【", "");
							ab = ab.replaceAll("】", "");
							if (a.indexOf(arr21) >= 0 
									|| arr21.indexOf(a) >= 0
									|| arr21.indexOf(ab) >= 0) {
								timuxuanding = a;
								timuxuandinga = true;
								break;
							}
						}
					}
				}
				
				JSONArray timulist2,xuanxianglist,daanlist,jiexilist;
				if(StringUtil.isNotEmpty(timuxuanding)) {
					for (int i =0;i<hzarray2.size();i++){//Object a2:hzarray2) {
						//加入题目类型 和知识点
						JSONObject jsona2 = hzarray2.getJSONObject(i);
						JSONArray arrxiao = jsona2.getJSONArray(jsonarrjieduan);
						if (null != arrxiao && arrxiao.size() > 0) {
							Object ab = arrxiao.get(0);
							JSONObject abo = (JSONObject)ab;
							String a = timuxuanding;
							int a1 = abo.getIntValue("hangshu");
							JSONArray arrxiaoxin = new JSONArray();
							JSONObject t1 = new JSONObject();
							t1.put("hangshu", a1);
							t1.put("neirong", a);
							arrxiaoxin.add(t1);
							//第一次 不赋值  前面根据单选多选 已经搞了第一个值了
							for (int j = 0 ; j<arrxiao.size(); j++) {
								if (i==0 && j==0 ) {
									continue;
								}
								arrxiaoxin.add((JSONObject)arrxiao.get(j));
							}
							JSONObject zuizhong = new JSONObject();
							zuizhong.put(jsonarrjieduan, arrxiaoxin);
							hzarray5.add(zuizhong);
						}
					}
				}
				int biaotixh = 0;
				if (hzarray5 != null && hzarray5.size() > 0) {
					for (Object fd4:hzarray5) {
						JSONObject jb = (JSONObject)fd4;
						JSONArray arr = jb.getJSONArray(jsonarrjieduan);	
						//先进行是否是综合题目的判断 非综合题进入非综合题
						if (!getPanDuanXinXiQuan(arr)) {
							//信息全不全 有题目类型 答案之一就行
							LOG.info("信息不全，跳过了"); 
							JSONObject ab = (JSONObject)arr.get(0);
							FileXiangGuan.writeLogToFile(jilulog, ab.getString("hangshu") + "   " + ab.getString("neirong"));
							continue;
						}
						if(getpanDuanZonghe(arr,StringUtil.getXiTiZongHeTi())) {
							
						}
						else if (null!= arr && arr.size() > 0) {
							int maxhangshu  = 0;
							for (Object a1:arr) {
								JSONObject b1 = (JSONObject)a1;
								if(b1.getIntValue("hangshu")>= maxhangshu) {
									maxhangshu = b1.getIntValue("hangshu");
								}
							}
							int[] timu_qz2 = getpanDuanGuiShuDian("timu",arr,maxhangshu);
							int[] xuanxiang_qz = getpanDuanGuiShuDian("xuanxiang",arr,maxhangshu);
							int[] daan_qz = getpanDuanGuiShuDian("daan",arr,maxhangshu);
							int[] jiexi_qz = getpanDuanGuiShuDian("jiexi",arr,maxhangshu);
							timulist2 = fileXiangGuan.getGuiShu(timu_qz2,arr);
							xuanxianglist = fileXiangGuan.getGuiShu(xuanxiang_qz,arr);
							daanlist = fileXiangGuan.getGuiShu(daan_qz,arr);
							jiexilist = fileXiangGuan.getGuiShu(jiexi_qz,arr);
							ExamQue examQue = null;
							biaotixh++;
							if(getpanDuanXuanXiang(arr,StringUtil.getXiTiLeiXingZwYouXuanXiangZw()))
							{
								examQue = new ExamQue(fid,sza.getId(),exzsd1,biaoti.getId(),biaotixh,timulist2,"Y",daanlist,jiexilist);
								try{
									ExamQue examQue2 = examQueService.saveExamQue(examQue);
									if (null == examQue2) {
										LOG.error("添加ExamQue 失败!,问题已存在");
										continue;
									}
									if(!getpanDuanXuanXiang(arr,StringUtil.getXiTiLeiXingZwYouXuanXiangZWZhiShiPanDuan())) {
										saveExamChoi(examQueService,examQue2,xuanxianglist); 
									}
						        }catch (Exception e){
						            LOG.error("添加ExamQue 失败!"+e.getMessage());
						        }
							} else {
								examQue = new ExamQue(fid,sza.getId(),exzsd1,biaoti.getId(),biaotixh,timulist2,"Y",null,jiexilist);
								try{
									ExamQue examQue2 = examQueService.saveExamQue(examQue);
									if (null == examQue2) {
										LOG.error("添加ExamQue 失败!,问题已存在");
										continue;
									}
									saveExamDaan(examQueService,examQue2,daanlist); 
						        }catch (Exception e){
						            LOG.error("添加ExamQue 失败!"+e.getMessage());
						        }
							}
						}			
					}
				}
			}
			LOG.info("根据学科分组，循环了一遍");
		}
		LOG.info(hzarray.size() + ",从正文开始后的hzarray大小，添加完毕");
		String rs1 = "";
		return "ok" + rs1;
	}
}
