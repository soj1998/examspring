package com.rm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rm.dao.AtcSjkDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamQueDao;
import com.rm.entity.ExamQue;
import com.rm.service.SzExamService;

@Service("examService")
public class SzExamServiceImpl implements SzExamService  {
	@Resource
    private AtcSjkDao atcSjkDao; 	
	@Resource
    private ExamQueDao examQueDao;
	@Resource
    private ExamChoiDao examChoiDao;
	@Override
	public ExamQue save(ExamQue examQue) {
		ExamQue rs = examQueDao.save(examQue);
		return rs;
	}

}
