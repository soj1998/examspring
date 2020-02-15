package com.rm.control;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.GuanLianXingDao;
import com.rm.entity.GuanLianXing;
@RequestMapping(value="/book")
@RestController
public class GuanLianXingController {
	private static final Logger LOG = LoggerFactory.getLogger(GuanLianXingController.class);
    @Resource
    private GuanLianXingDao glxDao;     
    
    @RequestMapping(value="/listqian5")
    public List<GuanLianXing> list(GuanLianXing gl){
    	ExampleMatcher matcher = ExampleMatcher.matching()
    			.withMatcher("userId",m->m.exact()); 
    	Example<GuanLianXing> example=Example .of(gl,matcher);
        List<GuanLianXing> list_glx=glxDao.findAll(example);
        LOG.info("---");
        return list_glx;
    }
    
}