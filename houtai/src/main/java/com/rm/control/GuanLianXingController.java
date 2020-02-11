package com.rm.control;

import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.rm.dao.GuanLianXingDao;
import com.rm.dao.IguanLianXingSpecDao;
import com.rm.entity.GuanLianXing;
@RequestMapping(value="/book")
@RestController
public class GuanLianXingController {
	private static final Logger LOG = LoggerFactory.getLogger(GuanLianXingController.class);
    @Resource
    private GuanLianXingDao glxDao;
    
    
    
    @RequestMapping(value="/cs")
    public String list11(){
    	return "aa";
    	
    }
    
    @RequestMapping(value="/listqian5")
    public List<GuanLianXing> list(GuanLianXing gl){
    	ExampleMatcher matcher = ExampleMatcher.matching()
    			.withMatcher("userId",m->m.exact()); 
    	Example<GuanLianXing> example=Example .of(gl,matcher);
        List<GuanLianXing> list_glx=glxDao.findAll(example);
        LOG.info("---");
        return list_glx;
    }
    @RequestMapping(value="/listcs")
    public String list1() {
    	List<GuanLianXing> gll=glxDao.findAll();
        for(GuanLianXing g:gll) {
        	LOG.info(g.getXueKe());
        }
        return "111";
	}
	 public static void main(String[] args) {
		 Specification<GuanLianXing> spec = new Specification<GuanLianXing>() {
	            @Override
	            public Predicate toPredicate(Root<GuanLianXing> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                //1.获取比较的属性
	                Path<Object> custId = root.get("userId");//查询的式属性名，不是表的字段名
	                //2.构造查询条件  ：    select * from cst_customer where cust_id = 3
	                /**
	                 * 第一个参数：需要比较的属性（path对象）
	                 * 第二个参数：当前需要比较的取值
	                 */
	                Predicate predicate = cb.equal(custId, 1);//进行精准的匹配  （比较的属性，比较的属性的取值）
	                return predicate;
	            }
	        };
	        GuanLianXingController gx=new GuanLianXingController();
	        List<GuanLianXing> gll=gx.glxDao.findAll();
	        for(GuanLianXing g:gll) {
	        	LOG.info(g.getXueKe());
	        }
	       
	}
}