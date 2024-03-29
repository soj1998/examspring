package com.rm.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
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
import com.rm.dao.ZhuanLanDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.BiaoTi;
import com.rm.entity.ExamAnsDa;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamChoiZongHe;
import com.rm.entity.ExamDaan;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamQueZongHeXiao;
import com.rm.entity.ExamUser;
import com.rm.entity.ExamZsd;
import com.rm.entity.ShouYeXinXi;
import com.rm.entity.ZhuanLan;
import com.rm.util.SimCalculator;
import com.rm.util.StringUtil;


@Service
public class SaveServiceImpl{
	@Resource
    private AtcSjkDao atcSjkDao; 	
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
    private ExamAnsDaDao examAnsDaDao;
	@Resource
    private ExamZsdDao examZsdDao;
	
	@Resource
    private ExamUserDao examUserDao;
	@Resource
    private ZhuanLanDao zhuanLanDao;
	@Resource
    private BiaoTiDao biaoTiDao;
	@Resource
    private ShouYeXinXiDao shouYeXinXiDao;
	private static final Logger LOG = LoggerFactory.getLogger(SaveServiceImpl.class);
	
	public AtcSjk saveAtcSjk(AtcSjk atcSjk) {		    	
		AtcSjk rs = atcSjkDao.save(atcSjk);
		return rs;
	}
	
	
	public BiaoTi saveBiaoTi(BiaoTi atcSjk) {		    	
		BiaoTi rs = biaoTiDao.save(atcSjk);
		return rs;
	}
	public ExamQue saveExamQue(ExamQue examQue) {
		SimCalculator sc=new SimCalculator();
		List<ExamQue> szall=examQueDao.findAll();
    	for(ExamQue b:szall) {    		
			double bl=sc.calculate(b.getQue(), examQue.getQue(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
    			return null;  			
    		}
    	}    	
		ExamQue rs = examQueDao.save(examQue);
		return rs;
	}
	
	public ExamZsd saveExamZsd(List<ExamZsd> examzsdzu) {
		List<ExamZsd> rs = new ArrayList<ExamZsd>();
		int sjid = -1;
		//知识点 一个也没有 每一个都要存一下
		//知识点 前一个没有 后面的有 不保存
		//知识点 前一个有 后面的保存
		int yigemeiyou = 0;
		for(int i = 0; i < examzsdzu.size(); i++) {
			ExamZsd dq = examzsdzu.get(i);
			List<ExamZsd> zsdList =  examZsdDao.getZsdByNeiRong(dq.getNeirong());
			if (zsdList.size() > 0) {
				yigemeiyou++;
				if (yigemeiyou <= i) {
					//这是前一个没有，后一个有了
					LOG.error("1   是前一个没有，后一个有了"+dq.getNeirong());
					return null;
				}
			}			
		}
		if (yigemeiyou ==0) {
			int qianyigeid = -1;
			for(int i = 0; i < examzsdzu.size(); i++) {
				ExamZsd dq = examzsdzu.get(i);
				int jibie = i;
				dq.setSjid(qianyigeid);
				dq.setJibie(jibie);
				if(dq.getNeirong().length() > 50) {
					LOG.error("1   "+dq.getNeirong());
					continue;
				}
				ExamZsd rs1 = examZsdDao.save(dq);
				qianyigeid = rs1.getId();
				rs.add(rs1);
			}
		}else {
			for(int i = 0; i < examzsdzu.size(); i++) {
				ExamZsd dq = examzsdzu.get(i);
				int jibie = i;			
				List<ExamZsd> zsdList =  examZsdDao.getZsdByNeiRong(dq.getNeirong());
				if (zsdList.size() > 0) {
					ExamZsd fu = zsdList.get(0);
					sjid = fu.getId();
					jibie = fu.getJibie() + 1;
					if (i == examzsdzu.size() - 1) {
						return fu;
					}
					continue;
				}
				if(dq.getNeirong().length() > 50) {
					LOG.error("2   "+dq.getNeirong());
					continue;
				}
				dq.setSjid(sjid);
				dq.setJibie(jibie);
				ExamZsd rs1 = examZsdDao.save(dq);
				rs.add(rs1);
			}
		}
		if (rs.size() > 0) {
			rs.sort((x,y) -> {
			  if (x.getJibie() >= y.getJibie())
	              return 1; //-1 降序 1 升序
	          else
	              return -1;
	        });
			return rs.get(rs.size() - 1);
		}
		return null;
		
	}
	
	public ExamZsd saveZsdEnd(String zsd1) {
		//搞知识点 知识点搞分级 是***
		String[] zsdzu = zsd1.split("\\*\\*\\*");
		List<ExamZsd> zsdzulist = new ArrayList<ExamZsd>();
		for (String azsdzu : zsdzu) {
			ExamZsd exzsd = new ExamZsd();
			exzsd.setNeirong(StringUtil.myTrim(azsdzu));
			zsdzulist.add(exzsd);
		}
		ExamZsd exzsd1 = saveExamZsd(zsdzulist);		
		return exzsd1;
	}
	
	public ExamAnsDa saveExamAnsDa(ExamAnsDa eExamAnsDa) {
		SimCalculator sc=new SimCalculator();
		List<ExamAnsDa> szall=examAnsDaDao.findAll();
    	for(ExamAnsDa b:szall) {    		
			double bl=sc.calculate(b.getQue(), eExamAnsDa.getQue(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
    			return null;  			
    		}
    	}    	
    	ExamAnsDa rs = examAnsDaDao.save(eExamAnsDa);
		return rs;
	}
	
	public ExamChoi saveExamChoi(ExamChoi examChoi) {		    	
		ExamChoi rs = examChoiDao.save(examChoi);
		return rs;
	}
	
	public ExamDaan saveExamDaan(ExamDaan examDaan) {		    	
		ExamDaan rs = examDaanDao.save(examDaan);
		return rs;
	}

	public ExamQueZongHeDa saveExamQueZongHeDa(ExamQueZongHeDa examQueZongHeDa) {
		SimCalculator sc=new SimCalculator();
		List<ExamQueZongHeDa> szall=examQueZongHeDaDao.findAll();
    	for(ExamQueZongHeDa b:szall) {    		
			double bl=sc.calculate(b.getExamque(), examQueZongHeDa.getExamque(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
    			return null;  			
    		}
    	}    	
		ExamQueZongHeDa rs = examQueZongHeDaDao.save(examQueZongHeDa);
		return rs;
	}
	
	public ExamQueZongHeXiao saveExamQueZongHeXiao(ExamQueZongHeXiao examQueZongHeXiao) {		    	
		ExamQueZongHeXiao rs = examQueZongHeXiaoDao.save(examQueZongHeXiao);
		return rs;
	}
	
	public ExamChoiZongHe saveExamChoiZongHe(ExamChoiZongHe examChoiZongHe) {		    	
		ExamChoiZongHe rs = examChoiZongHeDao.save(examChoiZongHe);
		return rs;
	}
	
	public ZhuanLan saveZhuanLan(ZhuanLan zhuanLan) {
		SimCalculator sc=new SimCalculator();
		List<ZhuanLan> szall=zhuanLanDao.findAll();
    	for(ZhuanLan b:szall) {    		
			double bl=sc.calculate(b.getZlzhengge(), zhuanLan.getZlzhengge(), 400);    
    		if(bl>0.8) {
    			LOG.info("already have same zhuanlan");
    			return null;  			
    		}
    	}
		ZhuanLan rs = zhuanLanDao.save(zhuanLan);
		return rs;
	}
	
	public ShouYeXinXi saveShouYeXinXi(ShouYeXinXi zhuanLan) {
		ShouYeXinXi rs = shouYeXinXiDao.save(zhuanLan);
		return rs;
	}
	
	public void delShouYeXinXi(int syid, String xinxiyuan) {
		/**ShouYeXinXi rs = shouYeXinXiDao.getShouYeByXinXiYuan(xinxiyuan,syid);
		if (rs != null) {
			shouYeXinXiDao.deleteById(rs.getId());
		}**/
		LOG.info("no do anything");
	}
	
	
	public ExamUser saveExamUser(ExamUser examUser) {
		//判断要不要加1
		ExamUser zl = new ExamUser();
		zl.setExamque(examUser.getExamque());
		zl.setUserid(examUser.getUserid());	
		Example<ExamUser> example2 = Example.of(zl);	
		List<ExamUser> rslist =  examUserDao.findAll(example2);
		if (rslist != null && rslist.size() > 0) {
			ExamUser rs1 = rslist.get(0);
			examUser.setShuliang(rs1.getShuliang() + 1);
			examUser.setId(rs1.getId());
		} else {
			examUser.setShuliang(1);
		}
		ExamUser rs = examUserDao.save(examUser);
		return rs;
	}
}
