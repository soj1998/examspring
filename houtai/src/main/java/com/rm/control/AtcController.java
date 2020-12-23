package com.rm.control;


import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.AtcDao;
import com.rm.entity.TreeNodeSjk;

@RequestMapping(value="/atc")
@RestController
public class AtcController {
  
	@Resource
    private AtcDao atcDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(AtcController.class);
	@RequestMapping(value="/gettreebyid",method=RequestMethod.GET)
    public List<TreeNodeSjk> listerji(@RequestParam("parentid") int pid){    	
        List<TreeNodeSjk> list_glx=atcDao.getTreeByParentid(pid);
        LOG.info(list_glx.size() + "");
        return list_glx;
    }
}