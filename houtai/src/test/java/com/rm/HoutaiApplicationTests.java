package com.rm;

import static org.mockito.Mockito.lenient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.rm.dao.BookDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.XueKeDao;
import com.rm.entity.QueandAns;
import com.rm.entity.QuesUserXuekeInfo;
import com.rm.entity.XueKeBaoCun;


@SpringBootTest
class HoutaiApplicationTests {
	@Resource
    private BookDao bookDao;
	@Resource
    private XueKeDao xkDao;   
	@Resource
    private QueandAnsDao qaDao;  
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		List<String> abc=xkDao.getXueKeNative();
		abc.forEach((item) -> LOG.info(item+"1"));
		LOG.info("-----------------");
		abc=xkDao.getErJiFenLeiByXueKeNative("啊啊啊");
		abc.forEach((item) -> LOG.info(item+"2"));
		LOG.info("-----------------");
		List<XueKeBaoCun> aaa=xkDao.getZhangJieByXueKeNative("啊啊啊", "阿萨大大");
		aaa.forEach((item) -> LOG.info(item.getZhang()+" 2 "+item.getJie()));
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		
	}
	

	
	@Test
    public void test(){
		LOG.info("------------");
		List<Object> aa=qaDao.getXueKeByQuesNative();
		LOG.info("------------");
		LOG.info("------------"+aa.size());
		aa.forEach(item->{
			LOG.info("------------");
			Object[] rowArray = (Object[]) item;
		    Map<String, Object> mapArr = new HashMap<String, Object>();
		    LOG.info("");
		    for(int i=0;i<rowArray.length;i++) {
		    	LOG.info("----"+rowArray[i].toString());
		    }
			});
    }
}
