package com.rm.util.file;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.entity.AtcSjk;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamChoiZongHe;
import com.rm.entity.ExamDaan;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamQueZongHeXiao;
import com.rm.entity.ExamZsd;
import com.rm.entity.ShouYeXinXi;
import com.rm.entity.lieju.WenZhangLeiXing;
import com.rm.service.impl.FindServiceImpl;
import com.rm.service.impl.SaveServiceImpl;
import com.rm.util.StringUtil;



public class SzExamFileSaveSql {

	
    private String[] zsd = StringUtil.getXiTiZhiShiDian();
    private String[] biaoti = StringUtil.getXiTiBiaoTi();	
	private String wdjs = "【结束】";
	private String[] daan = StringUtil.getXiTiDaan();	
	private String[] jiexi = StringUtil.getXiTiJieXi();
	
	private int xitishuzifenzugeshu = 100;
	private String[] xitishuzifenzufuhao = StringUtil.getXiTiShuZiFenZuFuHao();
	
	private static final Logger LOG = LoggerFactory.getLogger(SzExamFileSaveSql.class);
    
	
	private String jsonarrjieduan = "a1";
	private String[] getZongheti() {
		String[] zongheti = Arrays.copyOf(StringUtil.getXiTiZhiShiDian(), StringUtil.getXiTiZhiShiDian().length + 1);
		zongheti[StringUtil.getXiTiZhiShiDian().length] = "【综合题】";
		return zongheti;
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
	
	private void saveExamDaan(SaveServiceImpl examQueService, ExamQue examQue, Map<Integer,String> map) {
		try{
			String daan1 = StringUtil.getMapString(map,StringUtil.getXiTiDaan());
			if (StringUtil.isNotEmpty(daan1)) {
				ExamDaan daan2 = new ExamDaan(daan1,examQue.getId()); 
				examQueService.saveExamDaan(daan2);
			}
        }catch (Exception e){
            LOG.error("添加saveExamDaan 失败!"+e.getMessage());
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			            
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
					for(String timu : StringUtil.getChuTiMuWaiXinXiQuan()) {
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
	
	private int[] getpanDuanGuiShuDian(String pdKey,JSONArray crArray,int maxhangshu) {
		int ksd = 0;
		int jsd = 0;		
		switch(pdKey) {
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
	private Map<Integer,String> getGuiShu(int[]qizhi,JSONArray crArray) {
		Map<Integer,String> rs = new HashMap<Integer,String>();
		int qi = qizhi[0];
		int zhi = qizhi[1];
		for (Object fd:crArray) {
			JSONObject jb = (JSONObject)fd;
			int a = jb.getIntValue("hangshu");
			if (a >= qi && a <= zhi) {
				Map<Integer,String> hashmap = new HashMap<Integer,String>();
				hashmap.put(a, jb.getString("neirong"));
				rs.put(a, jb.getString("neirong"));
			}
		}
		return rs;
	}
	
	
	private JSONArray diGuiHz(int hs,JSONArray rsArray,JSONArray csArray,String[] panduanchuanru) {
		if (null == rsArray) {
			rsArray = new JSONArray();			
		}
		JSONObject j = new JSONObject();
		JSONArray jarray = new JSONArray();
		for (int i = hs;i<csArray.size();i++) {
			JSONObject obj1 = (JSONObject)csArray.get(i);
			String d = obj1.get("neirong").toString();
			String d1 = StringUtil.myTrim(d);
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
						j.put(jsonarrjieduan, jarray);
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
	public String asoneinsertToSql(FindServiceImpl findService,SaveServiceImpl examQueService,
			String fileweizhi,int shuizhong,String wzlaiyuan,int danduchongfu) {
		//存入试题和存入文章是不一样的 
		//存入试题 不搞有效标志 不搞关联 单独存的时候设为空
		//对数据的长度 最好是有个更为友好的提示 比如 题目的长度 答案 或者解析的长度
		//对上次提交的数据 可以批量删除
		//增加题目的验证 已经有的题目就不存了
		//多选 判断 单选 以外的题 放到 examansda 里面
		String jilulog = fileweizhi.substring(0,fileweizhi.lastIndexOf("szexam") + 7)
				+ "szexamlog.txt";
		FileXiangGuan fileXiangGuan = new FileXiangGuan();
		AtcSjk asjk =new AtcSjk();
		asjk.setFileweizhi(fileweizhi);
		Date lrsj = Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant());
		asjk.setLrsj(lrsj);
		asjk.setSzid(shuizhong);
		asjk.setWzlxid(WenZhangLeiXing.SHITI.getIndex());
		asjk.setWzlaiyuan(wzlaiyuan);
		asjk.setYxbz("Y");
		AtcSjk fid = examQueService.saveAtcSjk(asjk);
		JSONArray yuanshiarray =fileXiangGuan.transFiletoList(fileweizhi);
		JSONArray hzarray = new JSONArray();
		//分析题目 是每个题目都有知识点 题型 选项 答案 解析 还有有一个 然后是题号
		//用答案和答案之间是否有知识点 题型来断定
		int panduantixingpaibu = danduchongfu;
		int meigetidouyouzsdtixing = 1;
		int zhiyouyigezsdleixing = 2;
		if(panduantixingpaibu==meigetidouyouzsdtixing) {
			diGuiHz(0,hzarray,yuanshiarray,zsd);
		}
		JSONArray hzarray3 = new JSONArray();
		//标题只搞一次		
		String mybiaoti = "";
		Map<Integer,String> mybiaotihashmap = new HashMap<Integer,String>();
		boolean mybiaotib = false;
		for (Object fd:yuanshiarray) { 
			JSONObject jb = (JSONObject)fd;
			String arr1 = jb.getString("neirong");
			String arr2 = StringUtil.myTrim(arr1);
			if (mybiaotib) {
				break;
			}
			for (String a : biaoti) {
				if (arr2.indexOf(a) >= 0) {
					mybiaotib = true;
					mybiaoti = arr2;
					mybiaotihashmap.put(1, arr2);
					break;
				}
			}
		}
		mybiaoti = StringUtil.getMapString(mybiaotihashmap, StringUtil.getXiTiBiaoTi());
		//检测标题 不能为空 不能和系统内重复
		if (StringUtil.isEmpty(mybiaoti)) {
			LOG.error("标题不能为空");
			return "标题不能为空";
		}		
		int ashul =findService.getXiTiShuLiang(mybiaoti);
		if (ashul > 0) {
			LOG.error("标题不能重复");
			return "系统内已有一样的标题";
		}		
		//标题搞完
		//知识点外面预留
		ExamZsd exzsdzuizhong = new ExamZsd();
		if(panduantixingpaibu==zhiyouyigezsdleixing) {
			String[] timuleix = StringUtil.getXiTiLeiXingZw();
			//这种文件知识点只能在开头设置一次
			String myzsd = "";
			boolean myzsdb = false;
			for (Object fd:yuanshiarray) { 
				JSONObject jb = (JSONObject)fd;
				String arr1 = jb.getString("neirong");
				String arr2 = StringUtil.myTrim(arr1);
				if (myzsdb) {
					break;
				}
				for (String a : zsd) {
					if (arr2.indexOf(a) >= 0) {
						myzsdb = true;
						myzsd = arr2;
						break;
					}
				}
			}
			diGuiHz(0,hzarray,yuanshiarray,timuleix);			
			for (Object fd:hzarray) { 
				JSONObject jb = (JSONObject)fd;
				JSONArray arr = jb.getJSONArray(jsonarrjieduan);
				JSONArray hzarray2 = new JSONArray();
				String[] timufenge = StringUtil.getXiTiShuZiFenZu(xitishuzifenzugeshu, xitishuzifenzufuhao);
				diGuiHz(0,hzarray2,arr,timufenge);
				String timuxuanding = "";
				boolean timuxuandinga = false;
				for (Object fd2:arr) {
					JSONObject jb1 = (JSONObject)fd2;
					String arr1 = jb1.getString("neirong");
					String arr2 = StringUtil.myTrim(arr1);
					if (timuxuandinga) {
						break;
					}
					if (StringUtil.isNotEmpty(arr2)) {
						for (String a :timuleix) {
							if (a.indexOf(arr2) > 0) {
								timuxuanding = a;
								timuxuandinga = true;
								break;
							}
						}
					}
				}
				if(StringUtil.isNotEmpty(timuxuanding)) {
					for (Object a2:hzarray2) {
						//加入题目类型 和知识点
						JSONObject jsona2 = (JSONObject)a2;
						JSONArray arrxiao = jsona2.getJSONArray(jsonarrjieduan);
						if (null != arrxiao && arrxiao.size() > 0) {
							Object ab = arrxiao.get(0);
							JSONObject abo = (JSONObject)ab;
							String a = timuxuanding + abo.getString("neirong");
							int a1 = abo.getIntValue("hangshu");
							JSONArray arrxiaoxin = new JSONArray();
							JSONObject zsd = new JSONObject();
							zsd.put("hangshu", -2);
							//知识点的分类
							//对大规模的单选多选 就不能在每个题目上搞知识点了
							zsd.put("neirong", myzsd);
							arrxiaoxin.add(zsd);
							JSONObject t1 = new JSONObject();
							t1.put("hangshu", a1);
							t1.put("neirong", a);
							arrxiaoxin.add(t1);
							for (int i = 1 ; i<arrxiao.size(); i++) {
								arrxiaoxin.add((JSONObject)arrxiao.get(i));
							}
							JSONObject zuizhong = new JSONObject();
							zuizhong.put(jsonarrjieduan, arrxiaoxin);
							hzarray3.add(zuizhong);
						}
					}
				}
			}			
		}
		Map<Integer,String> zsdlist,timulist,xuanxianglist,daanlist,jiexilist;
		Map<Integer,String> zhzsdlist,zhdatimulist,zhtimulist,zhxuanxianglist,zhdaanlist,zhjiexilist;
		if(panduantixingpaibu == zhiyouyigezsdleixing && hzarray3.size() > 0) {
			hzarray = hzarray3;
		}
		for (Object fd:hzarray) {
			JSONObject jb = (JSONObject)fd;
			JSONArray arr = jb.getJSONArray(jsonarrjieduan);	
			//先进行是否是综合题目的判断 非综合题进入非综合题
			if (!getPanDuanXinXiQuan(arr)) {
				//信息全不全 有题目类型 有知识点或答案之一就行
				LOG.info("信息不全，跳过了"); 
				JSONObject ab = (JSONObject)arr.get(0);
				FileXiangGuan.writeLogToFile(jilulog, ab.getString("hangshu") + "   " + ab.getString("neirong"));
				continue;
			}
			if(getpanDuanZonghe(arr,StringUtil.getXiTiZongHeTi())) {
				JSONArray hzzharray = new JSONArray();
				diGuiHzZhsy(0,hzzharray,arr);
				ExamQueZongHeDa examQueda = new ExamQueZongHeDa();
				boolean dadecunhao = false;
				for (Object fd1:hzzharray) {
					JSONObject jb1 = (JSONObject)fd1;
					JSONArray arr1 = jb1.getJSONArray(jsonarrjieduan);
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
					String azsd = StringUtil.getMapString(zhzsdlist,StringUtil.getXiTiZhiShiDian());
					String[] zsdzu = azsd.split("\\*\\*\\*");
					List<ExamZsd> zsdzulist = new ArrayList<ExamZsd>();
					for (String azsdzu : zsdzu) {
						ExamZsd exzsd = new ExamZsd();
						exzsd.setNeirong(StringUtil.myTrim(azsdzu));
						zsdzulist.add(exzsd);
					}
					ExamZsd exzsd1 = examQueService.saveExamZsd(zsdzulist);
					if (null == exzsd1) {
						LOG.error("添加ExamAnsDa 失败!,zsd没搞对");
						FileXiangGuan.writeLogToFile(jilulog, azsd);
						continue;
					}
					if(zhdatimulist.size()>0) {
						examQueda = new ExamQueZongHeDa(fid,shuizhong,exzsd1,zhdatimulist,"Y",lrsj);
						try{
							ExamQueZongHeDa examQueda2 = examQueService.saveExamQueZongHeDa(examQueda);
							if(null == examQueda2) {
								LOG.error("添加ExamQueZongHeDa 失败!,问题已存在");
								continue;
							}
							dadecunhao = true;
				        }catch (Exception e){
				            LOG.error("添加examQueZongHeDaDao 失败!"+e.getMessage());      
				        }
					}
				}
				if (dadecunhao) {
					for (Object fd1:hzzharray) {
						JSONObject jb1 = (JSONObject)fd1;
						JSONArray arr1 = jb1.getJSONArray(jsonarrjieduan);
						if(getpanDuanZonghe(arr1,getZongheti())) {
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
				        }						
						saveExamZongHeXiaoChoi(examQueService,examQueXiao,zhxuanxianglist); 
					}
				}
			}
			else if (null!= arr && arr.size() > 0) {
				//if(getpanDuanXuanXiang(arr,StringUtil.getXiTiLeiXingZwYouXuanXiangZw())) {
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
				String azsd = StringUtil.getMapString(zsdlist,StringUtil.getXiTiZhiShiDian());
				String[] zsdzu = azsd.split("\\*\\*\\*");
				List<ExamZsd> zsdzulist = new ArrayList<ExamZsd>();
				for (String azsdzu : zsdzu) {
					ExamZsd exzsd = new ExamZsd();
					exzsd.setNeirong(StringUtil.myTrim(azsdzu));
					zsdzulist.add(exzsd);
				}
				exzsdzuizhong = examQueService.saveExamZsd(zsdzulist);
				if (null == exzsdzuizhong) {
					LOG.error("添加ExamQueXuanXiang 失败!,zsd没搞对");
					FileXiangGuan.writeLogToFile(jilulog, azsd);
					continue;
				}
				ExamQue examQue = null;
				if(getpanDuanXuanXiang(arr,StringUtil.getXiTiLeiXingZwYouXuanXiangZw()))
				{
					examQue = new ExamQue(fid,shuizhong,exzsdzuizhong,mybiaoti,timulist,"Y",lrsj,daanlist,jiexilist);
					try{
						ExamQue examQue2 = examQueService.saveExamQue(examQue);
						if (null == examQue2) {
							LOG.error("添加ExamQue 失败!,问题已存在");
							continue;
						}
						saveExamChoi(examQueService,examQue2,xuanxianglist); 
			        }catch (Exception e){
			            LOG.error("添加ExamQue 失败!"+e.getMessage());
			        }
				} else {
					examQue = new ExamQue(fid,shuizhong,exzsdzuizhong,mybiaoti,timulist,"Y",lrsj,null,jiexilist);
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
		String rs1 = "";
		System.out.println(hzarray.size());
		String wzlx1 = WenZhangLeiXing.SHITI.getName();
		String szmc = findService.getSz(shuizhong).getSzmc();
		ShouYeXinXi syxx =new ShouYeXinXi(shuizhong,szmc,
				mybiaoti,exzsdzuizhong.getId(),exzsdzuizhong.getNeirong(),
				lrsj,wzlx1,-1,-1,"Y");
		ShouYeXinXi syxx1 =examQueService.saveShouYeXinXi(syxx);
		if (syxx1 != null) {
			rs1 = "shouyexinxi ok";
		} else {
			rs1 = "problem,maybe alreay exists";
		}
		return "ok" + rs1;
	}
}
