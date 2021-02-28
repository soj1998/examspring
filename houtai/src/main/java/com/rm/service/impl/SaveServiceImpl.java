package com.rm.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ExamAnsDaDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.ExamZsdDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.ExamAnsDa;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamChoiZongHe;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamQueZongHeXiao;
import com.rm.entity.ExamZsd;
import com.rm.util.SimCalculator;


@Service
public class SaveServiceImpl{
	@Resource
    private AtcSjkDao atcSjkDao; 	
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
    private ExamAnsDaDao examAnsDaDao;
	@Resource
    private ExamZsdDao examZsdDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(SaveServiceImpl.class);
	
	public AtcSjk saveAtcSjk(AtcSjk atcSjk) {		    	
		AtcSjk rs = atcSjkDao.save(atcSjk);
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
		Long sjid = -1L;
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
					return null;
				}
			}			
		}
		if (yigemeiyou ==0) {
			Long qianyigeid = -1L;
			for(int i = 0; i < examzsdzu.size(); i++) {
				ExamZsd dq = examzsdzu.get(i);
				int jibie = i;
				dq.setSjid(qianyigeid);
				dq.setJibie(jibie);
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
}
