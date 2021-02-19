package com.rm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamQueDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.util.SimCalculator;


@Service
public class SzExamServiceImpl{
	@Resource
    private AtcSjkDao atcSjkDao; 	
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(SzExamServiceImpl.class);
	
	public ExamQue saveExamQue(ExamQue examQue) {
		SimCalculator sc=new SimCalculator();
		List<ExamQue> szall=examQueDao.findAll();
    	for(ExamQue b:szall) {    		
			double bl=sc.calculate(b.getExamque(), examQue.getExamque(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
    			return null;  			
    		}
    	}    	
		ExamQue rs = examQueDao.save(examQue);
		return rs;
	}
	
	public ExamChoi saveExamChoi(ExamChoi examChoi) {		    	
		ExamChoi rs = examChoiDao.save(examChoi);
		return rs;
	}

}
