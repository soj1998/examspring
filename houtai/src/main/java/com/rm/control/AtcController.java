package com.rm.control;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.AtcDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.TnsQbNeiRong;
import com.rm.entity.TreeNodeSjk;

@RequestMapping(value="/atc")
@RestController
public class AtcController {
  
	@Resource
    private AtcDao atcDao;
	
	@Resource
    private TreeNodeSjkDao treeNodeSjkDao;
	
	@Resource
    private TnsQbNeiRongDao tnsQbNeiRongDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(AtcController.class);
	@CrossOrigin
	@RequestMapping(value="/gettree",method=RequestMethod.POST)
    public JSONArray listerji3(@RequestParam("parentid") int pid){    	
        JSONArray rs = new JSONArray();
		List<TreeNodeSjk> list_glx=treeNodeSjkDao.getTreeByParentid(pid);
        list_glx.forEach(e -> {
        	List<TreeNodeSjk> list_glx2 = treeNodeSjkDao.getTreeByParentid(e.getRootid());
        	JSONObject r1 =new JSONObject();        	
        	r1.put("yuantou", e);
        	r1.put("children", list_glx2);
        	System.out.println(e);
        	System.out.println(list_glx2.get(0));
        	rs.add(r1);
        });        
        return rs;
    }
	
	
	@CrossOrigin
	@RequestMapping(value="/gettreebyid",method=RequestMethod.POST)
    public List<TreeNodeSjk> listerji(@RequestParam("parentid") int pid){    	
        List<TreeNodeSjk> list_glx=treeNodeSjkDao.getTreeByParentid(pid);
        LOG.info(list_glx.size() + "");
        return list_glx;
    }
	
	@CrossOrigin
	@RequestMapping(value="/gettreeneirongbyid",method=RequestMethod.POST)
    public List<TnsQbNeiRong> listerji2(@RequestParam("parentid") int pid,@RequestParam("rootid") int rid){    	
        int minr = treeNodeSjkDao.getMinRootidByParentid(pid);
        List<TnsQbNeiRong> rs =new ArrayList<TnsQbNeiRong>();
        if (minr == rid) {
			List<TnsQbNeiRong> list_glx1=tnsQbNeiRongDao.getQbNrBySjktid(pid);
			rs.addAll(list_glx1);
		}
		List<TnsQbNeiRong> list_glx=tnsQbNeiRongDao.getQbNrBySjktid(rid);
        LOG.info(list_glx.size() + "");
        rs.addAll(list_glx);
        return rs;
    }
}