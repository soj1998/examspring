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
import com.rm.czentity.CzTreeNodeSjk;
import com.rm.dao.AtcDao;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.AtcSjk;
import com.rm.entity.TnsQbNeiRong;
import com.rm.entity.TreeNodeSjk;

@RequestMapping(value="/atc")
@RestController
public class AtcController {
  
	@Resource
    private AtcDao atcDao;
	@Resource
    private AtcSjkDao atcSjkDao;  
	@Resource
    private TreeNodeSjkDao treeNodeSjkDao;
	
	@Resource
    private TnsQbNeiRongDao tnsQbNeiRongDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(AtcController.class);
	@CrossOrigin
	@RequestMapping(value="/gettree",method=RequestMethod.POST)
    public JSONArray gettree(@RequestParam("parentid") int pid,@RequestParam("atctreenodesjkid") int atcid){    	
        JSONArray rs = new JSONArray();
		List<TreeNodeSjk> list_glx=treeNodeSjkDao.getTreeByParentid(pid,atcid);
        list_glx.forEach(e -> {
        	List<TreeNodeSjk> list_glx2 = treeNodeSjkDao.getTreeByParentid(e.getRootid(),atcid);
        	JSONObject r1 =new JSONObject();        	
        	r1.put("yuantou", e);
        	r1.put("children", list_glx2);
        	System.out.println(e);
        	//System.out.println(list_glx2.get(0));
        	rs.add(r1);
        });        
        return rs;
    }
	
	
	@CrossOrigin
	@RequestMapping(value="/gettreebyid",method=RequestMethod.POST)
    public List<TreeNodeSjk> gettreebyid(@RequestParam("parentid") int pid,@RequestParam("atctreenodesjkid") int atcid){    	
        List<TreeNodeSjk> list_glx=treeNodeSjkDao.getTreeByParentid(pid,atcid);
        LOG.info(list_glx.size() + "");
        return list_glx;
    }
	
	@CrossOrigin
	@RequestMapping(value="/getatcidbyszwzlx",method=RequestMethod.POST)
    public List<AtcSjk> getatcidbyszwzlx(@RequestParam("szid") int szid,@RequestParam("wzlxid") int wzlxid){    	
        List<AtcSjk> list_glx=atcSjkDao.getBanbenBySzWzlx(szid,wzlxid);
        LOG.info(list_glx.size() + "");
        return list_glx;
    }
	
	@CrossOrigin
	@RequestMapping(value="/gettreeneirongbyid",method=RequestMethod.POST)
    public JSONObject gettreeneirongbyid(@RequestParam("parentid") int pid,@RequestParam("rootid") int rid,@RequestParam("atctreenodesjkid") int atcid){
		JSONObject r1 =new JSONObject();
		int minr = treeNodeSjkDao.getMinRootidByParentid(pid,atcid);
        List<TnsQbNeiRong> rs1 = new ArrayList<TnsQbNeiRong>();
        List<TreeNodeSjk> rs2 = new ArrayList<TreeNodeSjk>();
        if (minr == rid) {
			// List<TnsQbNeiRong> list_glx1=tnsQbNeiRongDao.getQbNrBySjktid(pid);
			// rs1.addAll(list_glx1);
			List<TreeNodeSjk> listbt=treeNodeSjkDao.getTreeByRootid(pid,atcid);
	        rs2.addAll(listbt);
		}		
		List<TreeNodeSjk> listbt2 = new ArrayList<TreeNodeSjk>();
		CzTreeNodeSjk czTreeNodeSjk = new CzTreeNodeSjk();
        List<TreeNodeSjk> listbt3 = czTreeNodeSjk.diGuiQiu(rid,atcid, listbt2, treeNodeSjkDao);
        rs2.addAll(listbt3);               
        r1.put("zhangjie", rs2);
        for(TreeNodeSjk ts:rs2) {
        	List<TnsQbNeiRong> list_glx1=tnsQbNeiRongDao.getQbNrBySjktid(ts.getId().intValue());
			rs1.addAll(list_glx1);
        }
        r1.put("neirong", rs1);   
        return r1;
    }
}