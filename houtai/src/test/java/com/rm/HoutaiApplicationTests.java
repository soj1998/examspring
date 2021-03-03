package com.rm;




import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.BookDao;
import com.rm.dao.ExamAnsDaDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.linshi.Author;


@EnableTransactionManagement
@SpringBootTest
class HoutaiApplicationTests {
	@Resource
    private BookDao bookDao;
	@Resource
    private XueKeDao xkDao;   
	@Resource
    private QueandAnsDao qaDao;
	@Resource
    private TreeNodeSjkDao tnDao; 
	@Resource
    private TnsQbNeiRongDao tnsneirongDao;
	@Resource
    private AuthorDao authorDao;
	@Resource
    private ArticleDao articleDao;
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
    private ExamAnsDaDao examAnsDaDao;
	@Resource
    private AtcSjkDao atcSjkDao;
	@Resource 
    private ZhuanLanDao zhuanLanDao;
	@Resource 
    private SzDao szDao;
	

	
	

	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		List<Integer> intlist = new ArrayList<Integer>();
		intlist.add(1);
		intlist.add(4);
		intlist.add(3);
		intlist.add(6);
		intlist.add(1);
		for (int a : intlist) {
			System.out.println("1    " + a);
		}
		intlist.sort(Comparator.naturalOrder());
		for (int a : intlist) {
			System.out.println("2    " + a);
		}
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		LOG.info("111");
	}
	

	@Transactional
	@Test
    public void test(){
		for (int i = 1;i<10;i++) {
			if (i!=5) {
				Author au = new Author();
				au.setName("abcd" + i);
				authorDao.save(au);
			}
			if (i==5) {
				Author au = new Author();
				au.setName("abcd885444555552225544455214452144111444");
				authorDao.save(au);
			}
		}		
    }
	
	

	
	
	
	 
	
	
	
}


/**

private JSONArray diGuiHuiZong(int hs,JSONArray rsArray,JSONArray csArray) {
	if (null == rsArray) {
		rsArray = new JSONArray();			
	}
	JSONObject j = new JSONObject();
	JSONArray xuanxarray = new JSONArray();
	StringBuilder strb = new StringBuilder();
	for (int i = hs;i<csArray.size();i++) {
		JSONObject obj1 = (JSONObject)csArray.get(i);
		if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
        {
			String d = obj1.get("neirong").toString();
			strb.append(d);
			if(d.indexOf("【知识点】") == 0) {
				if(StringUtil.isNotEmpty(j.getString("zsd"))) {
					rsArray.add(j);
					diGuiHuiZong(i,rsArray,csArray);
					break;
				}
				//j.put("zsd", d.substring(5));
				continue;
			}
			if(d.indexOf("【单选题】") == 0) {
				j.put("tmlx", "danxuan");
				j.put("zsd", strb.substring(5));
				strb = new StringBuilder();
				j.put("wenti", d.substring(5));
				continue;
			}
			if(d.indexOf("【多选题】") == 0) {
				j.put("tmlx", "duoxuan");
				j.put("zsd", strb.substring(5));
				strb = new StringBuilder();
				j.put("wenti", d.substring(5));
				continue;
			}				
			if(d.indexOf("【计算题】") == 0) {
				j.put("tmlx", "jisuan");
				j.put("zsd", strb.substring(5));
				strb = new StringBuilder();
				j.put("wenti", d.substring(5));
				continue;
			}
			if(d.indexOf("【综合题】") == 0) {
				j.put("tmlx", "zonghe");
				j.put("zsd", strb.substring(5));
				strb = new StringBuilder();
				j.put("wenti", d.substring(5));
				continue;
			}
			if(d.indexOf("【答案】") == 0) {
				if(xuanxarray.size()>0) {
					j.put("xuanx", xuanxarray);
				}
				j.put("daan", d.substring(4));
				continue;
			}
			if(d.indexOf("【解析】") == 0) {
				j.put("jiexi", d.substring(4));
				continue;
			}
			if(StringUtil.isNotEmpty(j.getString("tmlx")) && j.getString("tmlx").indexOf("xuan") > 0) {
				j.put("wenti", strb.substring(5));
				strb = new StringBuilder();
				for (String xx:xuanxiang) {
					if(d.indexOf(xx)>=0) {
						JSONObject j1 = new JSONObject();
						j1.put("xuanx", d);
						xuanxarray.add(j1);
						break;
					}
				}
			}
        }
		if (i == csArray.size() - 1) {
			return rsArray;
		}
	}
	return rsArray;
}
private JSONArray diGuiGao(JSONArray rsArray,JSONArray csArray) {
	if (null == csArray ||csArray.size() == 0) {
		return rsArray;
	}
	for (Object obj :csArray) {
		JSONObject obj1 = (JSONObject)obj;
		if(StringUtil.isNotEmpty(obj1.get("neirong").toString()))
        {
			String d = obj1.get("neirong").toString();
			ExamChoi examChoi = new ExamChoi();
			ExamQue examQue = new ExamQue();
			switch (d.substring(0, 4)) {
				case "【知识点" :
					examQue.setZzd(d.substring(5));
					break;
				case "【单选题" :
					break;
				case "【多选题" :
					break;
				case "【计算题" :
					break;
				case "【综合题" :
					break;
				case "【答案】" :
					break;
				case "【解析】" :
					break;
			}
        	csArray.remove(obj1);
        	diGuiGao(csArray, rsArray);	
        	break;     	
        }
		
	}
	return rsArray;
}
**/