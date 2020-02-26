package com.rm.control;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.QueandAnsDao;
import com.rm.entity.QueandAns;
import com.rm.util.SimCalculator;
@RequestMapping(value="/ques")
@RestController
public class QuesController {
  
	@Resource
    private QueandAnsDao qaDao;  
	
	
	private static final Logger LOG = LoggerFactory.getLogger(QuesController.class);
    @RequestMapping(value="/listall")
    public List<QueandAns> listall(){    	
        List<QueandAns> list_glx=qaDao.findAll();
        return list_glx;
    }
    /**
     * 添加图书
     * @param book
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(QueandAns qaa){
    	SimCalculator sc=new SimCalculator();
    	List<QueandAns> qaaall=qaDao.findAll();
    	for(QueandAns b:qaaall) {
    		double bl=sc.calculate(b.getQuestion(), qaa.getQuestion(), 40);    
    		if(bl>0.8) {
    			LOG.info("already have same question");
				return "存在相同的问题";    			
    		}
    	}
    	qaa.setUserId(0);
    	qaa.setLrxgsj(new Date());
    	qaDao.save(qaa);
    	LOG.info("question already save");
    	return "保存成功";
    }
    @RequestMapping(value="/getxuekebyques")
    public List<QueandAns> getXuekeByQues(){    	
        List<QueandAns> list_glx=qaDao.findAll();
        return list_glx;
    }
}