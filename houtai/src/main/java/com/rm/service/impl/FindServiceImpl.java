package com.rm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.BiaoTiDao;
import com.rm.dao.ExamAnsDaDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamDaanDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.ExamUserDao;
import com.rm.dao.ExamZsdDao;
import com.rm.dao.ShouYeXinXiDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.BiaoTi;
import com.rm.entity.ExamAnsDa;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamDaan;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamUser;
import com.rm.entity.ExamZsd;
import com.rm.entity.ShouYeXinXi;
import com.rm.entity.SysUser;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.ZhuanLan;
import com.rm.entity.lieju.Sz;
import com.rm.entity.pt.ExamQueChuanDi;
import com.rm.util.StringUtil;


@Service
public class FindServiceImpl{
	@Resource
    private AtcSjkDao atcSjkDao; 
	@Resource
    private ExamAnsDaDao examAnsDaDao;
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	@Resource
    private ExamDaanDao examDaanDao;
	@Resource
    private ExamQueZongHeDaDao examQueZongHeDaDao;
	@Resource
    private ExamQueZongHeXiaoDao examQueZongHeXiaoDao;
	@Resource
    private ExamChoiZongHeDao examChoiZongHeDao;	
	@Resource
    private ExamZsdDao examZsdDao;	
	@Resource
    private TreeNodeSjkDao tnDao; 
	@Resource
    private ZhuanLanDao zhuanLanDao; 
	@Resource
    private ShouYeXinXiDao shouYeXinXiDao; 
	@Resource
    private BiaoTiDao biaotiDao;
	@Resource
    private SzDao szDao;
	
	@Resource
    private ExamUserDao examUserDao;
	private static final Logger LOG = LoggerFactory.getLogger(FindServiceImpl.class);
	
	public Sz getSz(int szid) {
		return szDao.findById(szid).get();
	}
	
	public Sz getSzByMc(String szmc) {
		return szDao.findSzBymc(szmc);
	}
	
	public ExamZsd getExamZsd(int szid) {
		//ExamZsd zl = new ExamZsd();
		//zl.setId((long)szid);;	
		//Example<ExamZsd> example2 = Example.of(zl);	
		//return examZsdDao.findOne(example2).get();
		return examZsdDao.findById(szid).get();
	}
	
	public List<ExamZsd> findExamZsdYi(ExamZsd examZsd) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdYiJi();
		return rs;
	}
	
	public Long getShouYeXinXiShuLiang(int szid) {
		ShouYeXinXi zl = new ShouYeXinXi();
		zl.setSzid(szid);
		zl.setYxbz("Y");		
		Example<ShouYeXinXi> example2 = Example.of(zl);		
		Long a2 = shouYeXinXiDao.count(example2);
		LOG.info("13   " + a2);
		return a2;
	}	
	
	public Long getShouYeXinXiShuLiang() {
		ShouYeXinXi zl = new ShouYeXinXi();
		zl.setYxbz("Y");
		Example<ShouYeXinXi> example2 = Example.of(zl);		
		Long a2 = shouYeXinXiDao.count(example2);
		LOG.info("13   " + a2);
		return a2;
	}
	
	public List<ShouYeXinXi> getShouYeXinXiList(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ShouYeXinXi> list_glx=shouYeXinXiDao.getzlbybtidfuyi(pageRequest);
		return list_glx;
	}
	
	public Long getXiTiShuLiang(int szid) {
		ExamQue examQue = new ExamQue();
		examQue.setSzid(szid);
		examQue.setYxbz("Y");
		Example<ExamQue> example3 = Example.of(examQue);
		Long a3 = examQueDao.count(example3);
		LOG.info("14   " + a3);
		
		ExamQueZongHeDa examQueZongHeDa = new ExamQueZongHeDa();
		examQue.setSzid(szid);
		examQue.setYxbz("Y");
		Example<ExamQueZongHeDa> example4 = Example.of(examQueZongHeDa);
		Long a4 = examQueZongHeDaDao.count(example4);
		LOG.info("15   " + a4);
		
		ExamAnsDa examAnsDa = new ExamAnsDa();
		examAnsDa.setSzid(szid);
		examAnsDa.setYxbz("Y");
		Example<ExamAnsDa> example5 = Example.of(examAnsDa);
		Long a5 = examAnsDaDao.count(example5);
		LOG.info("16   " + a5);
		return a3 + a4 + a5;
	}
	
	public Long getXiTiShuLiang() {
		ExamQue examQue = new ExamQue();
		examQue.setYxbz("Y");
		Example<ExamQue> example3 = Example.of(examQue);
		Long a3 = examQueDao.count(example3);
		LOG.info("14   " + a3);
		
		ExamQueZongHeDa examQueZongHeDa = new ExamQueZongHeDa();
		examQue.setYxbz("Y");
		Example<ExamQueZongHeDa> example4 = Example.of(examQueZongHeDa);
		Long a4 = examQueZongHeDaDao.count(example4);
		LOG.info("15   " + a4);
		
		ExamAnsDa examAnsDa = new ExamAnsDa();
		examAnsDa.setYxbz("Y");
		Example<ExamAnsDa> example5 = Example.of(examAnsDa);
		Long a5 = examAnsDaDao.count(example5);
		LOG.info("16   " + a5);
		return a3 + a4 + a5;
	}
	
	public int getXiTiShuLiang(String biaoti) {
		ExamQue examQue = new ExamQue();
		examQue.setYxbz("Y");
		Example<ExamQue> example3 = Example.of(examQue);
		Long a3 = examQueDao.count(example3);
		LOG.info("14   " + a3);
		
		ExamAnsDa examAnsDa = new ExamAnsDa();
		examAnsDa.setBiaoti(biaoti);
		examAnsDa.setYxbz("Y");
		Example<ExamAnsDa> example5 = Example.of(examAnsDa);
		Long a5 = examAnsDaDao.count(example5);
		LOG.info("16   " + a5);
		return a3.intValue() + a5.intValue();
	}
	
	public List<ExamQue> getExamQueList(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamQue> list_glx=examQueDao.getall(pageRequest);
		return list_glx;
	}
	
	public List<ExamAnsDa> getExamDaList(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamAnsDa> list_glx=examAnsDaDao.getall(pageRequest);
		return list_glx;
	}
	
	public List<ExamQue> getExamQueListOrderBysj(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamQue> list_glx=examQueDao.getallorderbysj(pageRequest);
		return list_glx;
	}
	
	public List<ExamAnsDa> getExamDaListOrderBysj(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamAnsDa> list_glx=examAnsDaDao.getallorderbysj(pageRequest);
		return list_glx;
	}
	
	public Long getZhuanLanShuLiang(int szid) {
		ZhuanLan zl = new ZhuanLan();
		zl.setSzid(szid);
		zl.setYxbz("Y");
		ExampleMatcher matcher2 = ExampleMatcher.matching()
	            .withIgnorePaths("hangshu")
	            .withIgnorePaths("btid");
		Example<ZhuanLan> example2 = Example.of(zl, matcher2);		
		Long a2 = zhuanLanDao.count(example2);
		LOG.info("13   " + a2);
		return a2;
	}	
	
	public Long getZhuanLanShuLiang() {
		ZhuanLan zl = new ZhuanLan();
		zl.setYxbz("Y");
		ExampleMatcher matcher2 = ExampleMatcher.matching()
	            .withIgnorePaths("hangshu")
	            .withIgnorePaths("btid");
		Example<ZhuanLan> example2 = Example.of(zl, matcher2);		
		Long a2 = zhuanLanDao.count(example2);
		LOG.info("13   " + a2);
		return a2;
	}
	
	public List<ZhuanLan> getZhuanLanList(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlbybtidfuyi(pageRequest);
		return list_glx;
	}
	
	public List<ZhuanLan> getZhuanLanListOrderBysj(int pageNum,int pageSize) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ZhuanLan> list_glx=zhuanLanDao.getzlorderbysj(pageRequest);
		return list_glx;
	}
	
	public Long getJiChuShuLiang(int szid) {
		TreeNodeSjk twz = new TreeNodeSjk();
		twz.setSzid(szid);
		twz.setYxbz("Y");
		Example<TreeNodeSjk> example1 = Example.of(twz);
		Long a1 = tnDao.count(example1);
		LOG.info("12   " + a1);
		return a1;
	}
	
	public Long getJiChuShuLiang() {
		TreeNodeSjk twz = new TreeNodeSjk();
		twz.setYxbz("Y");
		Example<TreeNodeSjk> example1 = Example.of(twz);
		Long a1 = tnDao.count(example1);
		LOG.info("12   " + a1);
		return a1;
	}
	
	
	
	
	/**
	 * 根据索取题目全部、还是某类题型或知识点，以及pageNum pageSize得到返回的题目
	 * quanbu 就是全部题型
	 * danxuan 就是 单选 同样对 duoxuan panduan jianda mcjieshi zonghe
	 * 除上述之外的就是zsd 到examzsd库里面去找  以上全不是的 就返回空
	 * quanbu得到区间段 排序是 weizhi1 weizhi2 danxuan duoxuan panduan jianda jisuan mcjieshi
	 * 根据 pageNum 和 pageSize 得到 起 pageNum * pageSize 止为 pageNum * pageSize + pageSize
	 * 不考虑 根据pagesize 和pagenum来弄了  一次全都推到前台去 限值在不超过1000道题目的前提下
	 * 按照 weizhi1 weizhi2 weizhi3 danxuan duoxuan panduan jianda jisuan zonghe 排序
	 * 
	 */
	// private int xianzhitimu = 1000;
	public List<ExamQueChuanDi> getTiMuBySqtmSzid(int szid, String sqtm, int zuidashuliang){
		List<Integer> qujiand = gettotalsl(szid,sqtm);
		List<ExamQueChuanDi> rs = new ArrayList<ExamQueChuanDi>();
		List<ExamQue> rsa = new ArrayList<ExamQue>();
		List<ExamAnsDa> rsb = new ArrayList<ExamAnsDa>();
		if (null != qujiand) {
			int quzhizhongdian = StringUtil.getXiTiLeiXingYwZhanShi().length;
			for (int i = 0; i < qujiand.size() -1 ; i++) {
				int i1 = qujiand.get(i);
				if (i1 > zuidashuliang) {
					quzhizhongdian = i;
				}
			}
			for (int i = 0; i < quzhizhongdian; i++ ) {				
				String[] tmlx = StringUtil.getXiTiLeiXingYwZhanShi();
				if (i < 4) {
					List<ExamQue> rs1 = examQueDao.getallbytmleixing(szid, tmlx[i]);
					rsa.addAll(rs1);
				}
				if (i >= 4) {
					List<ExamAnsDa> rs1 = examAnsDaDao.getallbytmleixing(szid, tmlx[i]);
					rsb.addAll(rs1);					
				}
			}
			int shezhiid= 1;
			for (ExamQue examQue: rsa) {
				if ("danxuan".equals(examQue.getExamtype())) {
					//continue;
				}
				ExamQueChuanDi examcd = new ExamQueChuanDi();
				examcd.setQue(examQue.getQue());
				examcd.setExamtype(examQue.getExamtype());
				List<ExamChoi> zbchoidel = examChoiDao.getExamChoiListByQue(examQue.getId());
				List<String> xxs = new ArrayList<>();
				for (ExamChoi xx:zbchoidel) {
					xxs.add(xx.getXuanxiang());
				}
				examcd.setXuanxiang(xxs);
				examcd.setAns(examQue.getAns());
				examcd.setJiexi(examQue.getJiexi());
				examcd.setId(shezhiid);
				shezhiid++;
				rs.add(examcd);
			}
			for (ExamAnsDa examQue: rsb) {
				ExamQueChuanDi examcd = new ExamQueChuanDi();
				examcd.setQue(examQue.getQue());
				examcd.setExamtype(examQue.getExamtype());
				examcd.setAns(examQue.getAns());
				examcd.setJiexi(examQue.getJiexi());
				examcd.setId(shezhiid);
				shezhiid++;
				rs.add(examcd);
			}
			return rs;
		}
		return null;
	}
	
	
	public List<JSONObject> getTiMuForDingding(int szid,int userid,String tmlx,int zsdid,int sl,int slbeishu){
		if (sl < 0) {
			return new ArrayList<JSONObject>();
		}
		SysUser user = new SysUser(userid);
		List<ExamQue> danxuan = examQueDao.getallbytmleixingandzsd(szid, tmlx, zsdid);
		LOG.info(danxuan.size() + "  szid:" + szid + ",tmlx" + tmlx + ",zsdid" + zsdid);
		if(danxuan != null && danxuan.size() > 0)
			LOG.info(danxuan.get(0).getQue());
		List<JSONObject> danxuanrs = getExamQueOrder(sl,slbeishu,user,danxuan);
		return danxuanrs;
	}
	
	public List<ExamChoi> getExamChoiListByQue(int examqueid) {
		List<ExamChoi>  rs = new ArrayList<ExamChoi>();
		ExamQue eq = examQueDao.findById(examqueid).get();
		if (eq.getExamtype().equals("panduan")) {
			ExamChoi a = new ExamChoi("A.正确",eq);
			ExamChoi b = new ExamChoi("B.错误",eq);
			rs.add(a);
			rs.add(b);
		}
		if (eq.getExamtype().equals("danxuan") || eq.getExamtype().equals( "duoxuan")) {
			rs = examChoiDao.getExamChoiListByQue(examqueid);
		}
		Collections.sort(rs, new Comparator<ExamChoi>() {
            @Override
            public int compare(ExamChoi a, ExamChoi b) {
                String valA1 = new String();
                String valA2 = new String();
                try {
                    valA1 = a.getXuanxiang();
                    valA2 = b.getXuanxiang();
                } catch (JSONException e) {
                    System.out.println(e);
                }
                // 设置排序规则
                int i = valA1.compareTo(valA2);
                return i;
            }
        });
		return rs;
	}
	
	public ExamUser findExamUser(ExamUser examUser) {
		ExamUser rs1 =  examUserDao.findExamUser(examUser.getUserid(),examUser.getExamque());
		return rs1;
	}
	
	private List<JSONObject> getExamQueOrder(int sl,int beishu,SysUser user,List<ExamQue> list) {
		JSONArray rd = new JSONArray();
		for (ExamQue q: list) {
			JSONObject one = new JSONObject();
			one.put("examque", q);
			ExamUser zl = new ExamUser();
			zl.setUserid(user.getUid());
			zl.setExamque(q.getQue());
			zl.setExamid(q.getId());
			ExamUser zl2 = findExamUser(zl);
			if (zl2 != null) {
				one.put("user", zl2);
			} else {
				one.put("user", zl);
			}
			rd.add(one);
		}
		List<JSONObject> list2 = new ArrayList<JSONObject>();
        int rdsize = rd.size();
        for (int i = 0; i < rdsize; i++) {
        	JSONObject one2 =  rd.getJSONObject(i);
        	list2.add(one2);
        }
        Collections.sort(list2, new Comparator<JSONObject>() {
            //排序字段
            private static final String KEY_NAME1 = "user";

            @Override
            public int compare(JSONObject a, JSONObject b) {
            	ExamUser valA1 = new ExamUser();
            	ExamUser valA2 = new ExamUser();                
                try {	                	
                    valA1 = (ExamUser)a.get(KEY_NAME1);
                    valA2 = (ExamUser)b.get(KEY_NAME1);
                } catch (JSONException e) {
                    System.out.println(e);
                }
                // 设置排序规则
                if (valA1!= null && valA2 != null) {
	                int i = valA1.getShuliang();
	                int j = valA2.getShuliang();
	                return i-j;
                }
                return 0;
            }
        });
		List<JSONObject> rs = new ArrayList<JSONObject>();
		if (list2.size() >=sl && list2.size() > 0) {
			Set<Integer> rsshu = new HashSet<Integer>();
			int sla = sl;
			if (sl==0)
				sla=1;
			while (rsshu.size() < sla * beishu ) {
				Random random = new Random();
				int suiji = random.nextInt(list2.size());
				rsshu.add(suiji);
			}
			List<JSONObject> list3 = new ArrayList<JSONObject>();
			for (int i = 0; i < list2.size(); i++) {
				if (rsshu.contains(i)) {
					JSONObject one = (JSONObject)list2.get(i);
		        	list3.add(one);
				}
	        }
			Collections.sort(list3, new Comparator<JSONObject>() {
	            //排序字段
	            private static final String KEY_NAME1 = "user";

	            @Override
	            public int compare(JSONObject a, JSONObject b) {
	            	ExamUser valA1 = new ExamUser();
	            	ExamUser valA2 = new ExamUser();                
	                try {	                	
	                    valA1 = (ExamUser)a.get(KEY_NAME1);
	                    valA2 = (ExamUser)b.get(KEY_NAME1);
	                } catch (JSONException e) {
	                    System.out.println(e);
	                }
	                // 设置排序规则
	                if (valA1!= null && valA2 != null) {
		                int i = valA1.getShuliang();
		                int j = valA2.getShuliang();
		                return i-j;
	                }
	                return 0;
	            }
	        });
			for (int i = 0; i < sla * beishu; i++) {				
				ExamQue one = list3.get(i).getObject("examque", ExamQue.class);
				if(one.getQue().length() > 500) {
					continue;
				}
				rs.add(list3.get(i));
				if(rs.size() > sl) {
					break;
				}
	        }
		} else {
			for (JSONObject q : list2) {
				rs.add(q);
			}
		}
		return rs;
	}
	
	private List<Integer> gettotalsl(int szid, String tmsq) {
		JSONObject rs = new JSONObject();
		List<Integer> rs1 = new ArrayList<Integer>();
		if ("quanbu".equals(tmsq)) {
			rs.put("total", getXiTiShuLiang(szid).intValue());
			String[] tmlx = StringUtil.getXiTiLeiXingYwZhanShi();
			for (int i= 0;i < tmlx.length -1;i++) {
				if (i < 4) {
					int weizhi1 = examQueDao.gettmleixingsl(szid, tmlx[i]);
					rs.put(i + 1 + "", weizhi1);
				}
				if (i >= 4) {
					int weizhi1 = examAnsDaDao.gettmleixingsl(szid, tmlx[i]);
					rs.put(i + 1 + "", weizhi1);
				}
			}
			int weizhi1 = rs.getIntValue("1");			
			int danxsl = rs.getIntValue("2");
			int duoxsl = rs.getIntValue("3");
			int pdsl = rs.getIntValue("4");
			int weizhi2 = rs.getIntValue("5");
			int jdsl = rs.getIntValue("6");
			int jssl = rs.getIntValue("7");
			int mcsl = rs.getIntValue("8");
			int duan1 = weizhi1;
			int duan2 = weizhi1 + danxsl;
			int duan3 = weizhi1 + danxsl + duoxsl;
			int duan4 = weizhi1 + danxsl + duoxsl + pdsl;
			int duan5 = weizhi1 + danxsl + duoxsl + pdsl + weizhi2;
			int duan6 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl;
			int duan7 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl + jssl;
			int duan8 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl + jssl + mcsl;
			rs1.add(duan1); 
			rs1.add(duan2);
			rs1.add(duan3);
			rs1.add(duan4);
			rs1.add(duan5);
			rs1.add(duan6);
			rs1.add(duan7);
			rs1.add(duan8);
			// 正序naturalOrder1234 倒序reverseOrder 4321
			rs1.sort(Comparator.naturalOrder());
			return rs1;
		}
		if ("danxuan".equals(tmsq)) {
			
		}
		if ("duoxuan".equals(tmsq)) {
			
		}
		if ("jianda".equals(tmsq)) {
			
		}
		return null;
	}
		
	public JSONObject buyonggettotal(int szid, String tmsq) {
		JSONObject rs = new JSONObject();		
		if ("quanbu".equals(tmsq)) {
			rs.put("total", getXiTiShuLiang(szid).intValue());
			int weizhi1 = examQueDao.gettmleixingsl(szid, "weizhi");
			int weizhi2 = examAnsDaDao.gettmleixingsl(szid, "weizhi");
			int danxsl = examQueDao.gettmleixingsl(szid, "danxuan");
			int duoxsl = examQueDao.gettmleixingsl(szid, "duoxuan");
			int pdsl = examQueDao.gettmleixingsl(szid, "panduan");
			int jdsl = examAnsDaDao.gettmleixingsl(szid, "jianda");
			int jssl = examAnsDaDao.gettmleixingsl(szid, "jisuan");
			int mcsl = examAnsDaDao.gettmleixingsl(szid, "mcjieshi");
			rs.put("weizhi-quept", weizhi1);
			rs.put("weizhi-ansda", weizhi2);
			rs.put("danxuan", danxsl);
			rs.put("duoxuan", duoxsl);
			rs.put("panduan", pdsl);
			rs.put("jianda", jdsl);
			rs.put("jisuan", jssl);
			rs.put("mcjieshi", mcsl);			
			return rs;
		}
		if ("danxuan".equals(tmsq)) {
			
		}
		if ("duoxuan".equals(tmsq)) {
			
		}
		if ("jianda".equals(tmsq)) {
			
		}
		return null;
	}
	
	public List<Integer> buyonggetqujianduan (JSONObject rs, String tmsq) {
		List<Integer> rs1 = new ArrayList<Integer>();
		if ("quanbu".equals(tmsq)) {
			int total = rs.getIntValue("total");
			int weizhi1 = rs.getIntValue("weizhi-quept");
			int weizhi2 = rs.getIntValue("weizhi-ansda");
			int danxsl = rs.getIntValue("danxuan");
			int duoxsl = rs.getIntValue("duoxuan");
			int pdsl = rs.getIntValue("panduan");
			int jdsl = rs.getIntValue("jianda");
			int jssl = rs.getIntValue("jisuan");
			int mcsl = rs.getIntValue("mcjieshi");
			int duan1 = weizhi1;
			int duan2 = weizhi1 + weizhi2;
			int duan3 = weizhi1 + weizhi2 + danxsl;
			int duan4 = weizhi1 + weizhi2 + danxsl + duoxsl;
			int duan5 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl;
			int duan6 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl;
			int duan7 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl + jssl;
			int duan8 = weizhi1 + weizhi2 + danxsl + duoxsl + pdsl + jdsl + jssl + mcsl;
			LOG.info("是否相等，total：" + total +",duan8："+ duan8,"二者之差："+ (total - duan8));
			rs1.add(duan1); 
			rs1.add(duan2);
			rs1.add(duan3);
			rs1.add(duan4);
			rs1.add(duan5);
			rs1.add(duan6);
			rs1.add(duan7);
			rs1.add(duan8);
			// 正序naturalOrder1234 倒序reverseOrder 4321
			rs1.sort(Comparator.naturalOrder());
			return rs1;
		}
		return null;
	}
	
	public List<ExamQueChuanDi> getTiMuBySzid(int szid, int zuidashuliang){
		List<ExamQueChuanDi> rs = new ArrayList<ExamQueChuanDi>();
		List<ExamQue> rsa = new ArrayList<ExamQue>();
		String[] tmlx = StringUtil.getXiTiLeiXingYwZhanShi();
		for (int i = 0; i < tmlx.length; i++ ) {				
			List<ExamQue> rs1 = examQueDao.getallbytmleixing(szid, tmlx[i]);
			rsa.addAll(rs1);
		}
		int shezhiid= 1;
		for (ExamQue examQue: rsa) {
			if ("danxuan".equals(examQue.getExamtype())) {
				//continue;
			}
			ExamQueChuanDi examcd = new ExamQueChuanDi();
			examcd.setQue(examQue.getQue());
			examcd.setExamtype(examQue.getExamtype());
			List<ExamChoi> zbchoidel = examChoiDao.getExamChoiListByQue(examQue.getId());
			List<String> xxs = new ArrayList<>();
			for (ExamChoi xx:zbchoidel) {
				xxs.add(xx.getXuanxiang());
			}
			examcd.setXuanxiang(xxs);
			if (examQue.getAns() == null) {
				ExamDaan ans = examDaanDao.getExamDaanByQue(examQue.getId());
				examcd.setAns(ans.getDaan());
			} else {
				examcd.setAns(examQue.getAns());
			}
			examcd.setJiexi(examQue.getJiexi());
			examcd.setId(shezhiid);
			shezhiid++;
			rs.add(examcd);
		}			
		return rs;		
	}
	
	public List<ExamQueChuanDi> getTiMuByBiaoTi(int szid,int biaotiid){
		List<ExamQueChuanDi> rs = new ArrayList<ExamQueChuanDi>();
		List<ExamQue> rs1 = examQueDao.getallbybiaoti(szid, biaotiid);
		int shezhiid= 1;
		for (ExamQue examQue: rs1) {
			if ("danxuan".equals(examQue.getExamtype())) {
				//continue;
			}
			ExamQueChuanDi examcd = new ExamQueChuanDi();
			examcd.setQue(examQue.getQue());
			examcd.setExamtype(examQue.getExamtype());
			List<ExamChoi> zbchoidel = examChoiDao.getExamChoiListByQue(examQue.getId());
			Collections.sort(zbchoidel, new Comparator<ExamChoi>() {
	            @Override
	            public int compare(ExamChoi a, ExamChoi b) {
	                String valA1 = new String();
	                String valA2 = new String();
	                try {
	                    valA1 = a.getXuanxiang();
	                    valA2 = b.getXuanxiang();
	                } catch (JSONException e) {
	                    System.out.println(e);
	                }
	                // 设置排序规则
	                int i = valA1.compareTo(valA2);
	                return i;
	            }
	        });
			List<String> xxs = new ArrayList<>();
			for (ExamChoi xx:zbchoidel) {
				xxs.add(xx.getXuanxiang());
			}
			examcd.setXuanxiang(xxs);
			if (examQue.getAns() == null) {
				ExamDaan ans = examDaanDao.getExamDaanByQue(examQue.getId());
				if (ans != null )
					examcd.setAns(ans.getDaan());
			} else {
				examcd.setAns(examQue.getAns());
			}
			examcd.setJiexi(examQue.getJiexi());
			examcd.setId(shezhiid);
			shezhiid++;
			rs.add(examcd);
		}			
		return rs;		
	}
	
	
	public List<JSONObject> getSuijiTiByUser(int szid,int userid){
		//找到60道题，20单选 2党史 2大数据 5货劳 5企业 3个人 1土地 2其他 20多选 10判断同样
		//得到所有的知识点id 家里是9 大数据 8 其他税种 10 货物劳务 11  企业 12 个人 13 土增 14 两办 15 党史
		//单位笔记本 是 从32开始 家里从8开始
		int beishu = 5;
		String[] tmlx = new String[] {"danxuan","duoxuan","panduan"};
		//String[] tmlx = new String[] {"panduan"};
		JSONArray zsd = new JSONArray();
		//String[] zsdlx = new String[] {"qitasz","dashuju","huolao","qiye","geren","tuzeng","liangban","dangshi"};
		List<ExamZsd> zsdshuishou = getZsdByNeiRong("税收业务");
		int sjid= zsdshuishou.get(0).getId();
		List<ExamZsd> zsdjihe = getZsdXiaJi(sjid);
		for(ExamZsd tm : zsdjihe) {
			JSONObject zsdone = new JSONObject();
			zsdone.put("zsdid", tm.getId());
			int sl = 0;
			switch (tm.getNeirong()) {
				case "其他税种" : sl=2; break;
				case "大数据税收风险" : sl=2; break;
				case "货物劳务税" : sl=2; break;
				case "企业所得税" : sl=2; break;
				case "个人所得税" : sl=1; break;
				case "土地增值税" : sl=1; break;
				case "党史" : sl=0; break;
			}
			zsdone.put("zsdsl", sl);
			zsd.add(zsdone);
			LOG.info(tm.getNeirong() + "    " + tm.getId());
		}	
		JSONArray ab = new JSONArray();
		for(String tm : tmlx) {
			JSONObject a = new JSONObject();
			a.put("tmlx", tm);
			a.put("zsdid", zsd);
			ab.add(a);
		}
		List<JSONObject> rs = new ArrayList<JSONObject>();
		for (Object one : ab) {
			JSONObject jone = (JSONObject)one;
			String tm = jone.getString("tmlx");
			JSONArray jarr = jone.getJSONArray("zsdid");
			for (Object one1 : jarr) {
				JSONObject jone1 = (JSONObject)one1;
				int zsdid = jone1.getIntValue("zsdid");
				int zsdsl = jone1.getIntValue("zsdsl");
				//单位学科id 2 家里学科ID 1
				List<JSONObject> rs1 = getTiMuForDingding(szid, userid, tm, zsdid, zsdsl - 1 , beishu);
				rs.addAll(rs1);
			}
		}
		return rs;		
	}
	
	public int getTiMuByBiaoTiCount(int szid,String biaoti){
		return biaotiDao.getallbybiaoticount(szid, biaoti);		
	}
	
	public BiaoTi getBiaoTiById(int szid){
		return biaotiDao.findById(szid).get();		
	}
	
	public List<ExamQue> getExamQue(int pageNum, int pageSize, int szid) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamQue> list_glx=examQueDao.getallbyszid(szid,pageRequest);
		return list_glx;
	}
	
	public List<ExamQue> getExamQue(int pageNum, int pageSize, int szid, String timulx) {
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamQue> list_glx=examQueDao.getexamquezhanshi(pageRequest,szid,timulx);
		return list_glx;
	}
	
	public List<ExamAnsDa> getExamAnsDa(int pageNum, int pageSize, int szid, String timulx) {		    	
		Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize);
		List<ExamAnsDa> list_glx=examAnsDaDao.getexamansdazhanshi(pageRequest,szid,timulx);
		return list_glx;
	}	
	
	public List<ExamZsd> getZongHe(int szid) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdYiJi();
		return rs;
	}
	
	public List<ExamZsd> getZsdByNeiRong(String nr) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdByNeiRong(nr);
		return rs;
	}
	
	public List<ExamZsd> getZsdXiaJi(int sjid) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdXiaJi(sjid);
		return rs;
	}
	
	
}
