package com.rm.control;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rm.dao.XueKeDao;
import com.rm.entity.XueKe;
@RequestMapping(value="/xuekeerji")
@RestController
public class XueKeErJiController {
	@Resource
    private XueKeDao xkDao;     
    
    @RequestMapping(value="/listall")
    public List<XueKe> listall(){    	
        List<XueKe> list_glx=xkDao.findAll();
        return list_glx;
    }
    
}