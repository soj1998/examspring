package com.rm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import com.rm.entity.ExamQue;
import com.rm.entity.ExamQueZongHeDa;
import com.rm.entity.ExamZsd;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.ZhuanLan;


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
