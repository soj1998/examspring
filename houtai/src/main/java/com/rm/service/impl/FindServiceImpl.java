package com.rm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
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
import com.rm.dao.ZhuanLanDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.ExamZsd;


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
    private ZhuanLanDao zhuanLanDao; 
	
	private static final Logger LOG = LoggerFactory.getLogger(FindServiceImpl.class);
	
	public List<ExamZsd> findExamZsdYi(ExamZsd examZsd) {		    	
		List<ExamZsd> rs = examZsdDao.getZsdYiJi();
		return rs;
	}
	
	public List<JSONObject> findYiYouWZGeShu(int szid, int wzlxid) {
		AtcSjk atcSjk = new AtcSjk();
		atcSjk.setSzid(szid);
		atcSjk.setWzlxid(wzlxid);
		Example<AtcSjk> example = Example.of(atcSjk);
		Long examsl = atcSjkDao.count(example);
		return null;
	}
}
