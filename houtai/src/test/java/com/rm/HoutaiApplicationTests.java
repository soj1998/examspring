package com.rm;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;

import com.rm.control.GuanLianXingController;
import com.rm.dao.BookDao;
import com.rm.dao.GuanLianXingDao;
import com.rm.entity.Book;
import com.rm.entity.GuanLianXing;

@SpringBootTest
class HoutaiApplicationTests {
	@Resource
    private GuanLianXingDao glxDao;
	@Resource
    private BookDao bookDao;
     
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		
	}
	@Test
	void ceshi() {
		LOG.info("1111");
		List<GuanLianXing> ls=glxDao.findGuanLianXingByUserId(2);
		for(GuanLianXing gl:ls) {
			LOG.info("  --"+gl.getXueKe());
		}
	}

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		LOG.info("1111");
		List<GuanLianXing> ls=glxDao.findGuanLianXingByUserId(2);
		for(GuanLianXing gl:ls) {
			LOG.info("  --"+gl.getUserId()+"  "+gl.getXueKe());					
		}
		glxDao.updateUserIdById(1, 3);	
		LOG.info("1111");
		ls=glxDao.findGuanLianXingByUserId(3);
		for(GuanLianXing gl:ls) {
			LOG.info("  --"+gl.getUserId()+"  "+gl.getXueKe());						
		}
	}
	
	@Test
	void ceshi2() {
		LOG.info("1111");
		List<GuanLianXing> ls=glxDao.findGuanLianXingByUserIdNative(3);
		for(GuanLianXing gl:ls) {
			LOG.info("  --"+gl.getXueKe());
		}
	}
	
	@Test
    public void test(){
        Specification<GuanLianXing> specification = new Specification<GuanLianXing>()
        {
           /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		@Override
			public Predicate toPredicate(Root<GuanLianXing> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				//分别构造各个单属性的过滤条件
				Predicate namePredicate = criteriaBuilder.like(root.get("xue_ke"), "政%");
                Predicate agePredicate = criteriaBuilder.ge(root.get("userid"), 3);//大于等于

                //组合成最终的过滤条件
                Predicate predicate = criteriaBuilder.and(namePredicate, agePredicate);
                return namePredicate;
			}
        };
        LOG.info("1111");
        //查询
        List<GuanLianXing> users = glxDao.findAll(specification);
        LOG.info("2222");
        users.forEach(new Consumer<GuanLianXing>() {
            @Override
            public void accept(GuanLianXing user) {
                System.out.println(user.getZhang()+" "+user.getXueKe());
            }
        });
    }
}
