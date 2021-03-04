package com.rm.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ExamAnsDaDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.ExamZsdDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.entity.ExamAnsDa;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamZsd;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.ZhuanLan;
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
	
	private static final Logger LOG = LoggerFactory.getLogger(FindServiceImpl.class);
	
	public List<ExamZsd> findExamZsdYi(ExamZsd examZsd) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdYiJi();
		return rs;
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
	
	public Long getJiChuShuLiang(int szid) {
		TreeNodeSjk twz = new TreeNodeSjk();
		twz.setSzid(szid);
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
			for (ExamQue examQue: rsa) {
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
				rs.add(examcd);
			}
			for (ExamAnsDa examQue: rsb) {
				ExamQueChuanDi examcd = new ExamQueChuanDi();
				examcd.setQue(examQue.getQue());
				examcd.setExamtype(examQue.getExamtype());
				examcd.setAns(examQue.getAns());
				examcd.setJiexi(examQue.getJiexi());
				rs.add(examcd);
			}
			return rs;
		}
		return null;
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
	
}
