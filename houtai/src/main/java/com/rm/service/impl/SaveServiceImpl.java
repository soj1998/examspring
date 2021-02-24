package com.rm.service.impl;

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
	
	public ExamZsd saveExamZsd(ExamZsd examZsd) {
		List<ExamZsd> zsdList =  examZsdDao.getZsdByNeiRong(examZsd.getNeirong());
		if (zsdList.size() > 0) {
			ExamZsd fu = zsdList.get(0);
			examZsd.setSjid(fu.getId());
			examZsd.setJibie(fu.getJibie() +1);
		} else {
			examZsd.setSjid(-1L);
			examZsd.setJibie(1);
		}
		ExamZsd rs = examZsdDao.save(examZsd);
		return rs;
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
