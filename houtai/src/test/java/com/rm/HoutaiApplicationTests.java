package com.rm;




import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.rm.dao.AtcSjkDao;
import com.rm.dao.BookDao;
import com.rm.dao.ExamAnsDaDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamChoiZongHeDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.ExamQueZongHeDaDao;
import com.rm.dao.ExamQueZongHeXiaoDao;
import com.rm.dao.ExamUserDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.ZhuanLanDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.dao.sys.SzDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.entity.ExamUser;
import com.rm.entity.pt.ExamQueChuanDi;
import com.rm.service.impl.FindServiceImpl;
import com.rm.service.impl.SaveServiceImpl;
import com.rm.util.file.FileXiangGuan;
import com.rm.util.file.SheetResult;
import com.rm.util.file.WriteExcel;


@EnableTransactionManagement
@SpringBootTest
class HoutaiApplicationTests {
	@Resource
    private BookDao bookDao;
	@Resource
    private XueKeDao xkDao;   
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
	@Resource
    private ExamUserDao examUserDao;
	@Resource 
    private FindServiceImpl findservice;
	@Resource 
    private SaveServiceImpl saveservice;
    //mvn 安装命令mvn clean package -DskipTests
	
	

	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
	public static final String APP_ID = "16781535";
    public static final String API_KEY = "hUqtyvI4ip03GS8ehcpI3hRX";
    public static final String SECRET_KEY = "DIM7drbNHoGlhLXjD7AMf9uD3bYlSNhN";
	@Test
	void contextLoads() throws JSONException {
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        
        
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("language_type", "CHN_ENG");
	    options.put("detect_direction", "true");
	    options.put("detect_language", "true");
	    options.put("probability", "true");
	    
	    
	    // 参数为本地图片路径
	    String jilulog = "D:\\szexamlog.txt";
	    
	    String path = "C:\\Users\\ad\\Desktop\\图片2\\3";		//要遍历的路径
		File file = new File(path);		//获取其file对象
		File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
		List<File> ls = new ArrayList<File>();
		for(File f:fs){	
			ls.add(f);
			String fn = f.getName();
			int dian = fn.indexOf(".");
			int dian2 = 2;
			if (dian==23) 
				dian2 = 3;
			String fnc = fn.substring(20,20+ dian2);
			System.out.println(fn + "  " + fn.substring(20) + "  " + dian);
			System.out.println(fnc);
		}
		//if (1==1) {
			//return;
		//}
		Collections.sort(ls, new Comparator<File>() {
            //升序排序
            public int compare(File o1, File o2) {
            	String fn = o1.getName();
    			int dian = fn.indexOf(".");
    			int dian2 = 2;
    			if (dian==23) 
    				dian2 = 3;
    			String fnc = fn.substring(20,20+ dian2);
    			int a = Integer.parseInt(fnc);
    			String fn1 = o2.getName();
    			int dian1 = fn1.indexOf(".");
    			int dian21 = 2;
    			if (dian1==23) 
    				dian21 = 3;
    			String fnc1 = fn1.substring(20,20+ dian21);
    			int b = Integer.parseInt(fnc1);
    			return a - b;
            }
        });
		
		for(File f:ls){					//遍历File[]数组
			if(!f.isDirectory())		//若非目录(即文件)，则打印
			{
				System.out.println(f.getAbsolutePath());
				String image = f.getAbsolutePath();//"D:\\图4.png";
			    org.json.JSONObject res = client.basicGeneral(image, options);
			    //System.out.println(res.toString(2));
			    org.json.JSONArray a = res.getJSONArray("words_result");
			    
			    for (Object one : a ) {
			    	org.json.JSONObject one1 = (org.json.JSONObject) one;
			    	//System.out.println("111"  + one1.getString("words"));
			    	FileXiangGuan.writeLogToFile(jilulog, one1.getString("words"));
			    }
			}	
		}
		
	    
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		LOG.info("123".substring(1,3));
	}
	

	@Transactional
	@Test
    public void test() throws IOException{
		String a ="AB";
		String[] ab = a.split("");
		for(String a1: ab) {
			System.out.println(a1);
		}
		System.out.println(String.join("|", ab));
    }
	
	@Test
    public void test2() throws IOException{
		
		List<JSONObject> rs2 = findservice.getSuijiTiByUser(1,2);
    	List<ExamQueChuanDi> rs1 = new ArrayList<ExamQueChuanDi>();
    	int shezhiid = 1;
		for (JSONObject examQue1: rs2) {
			ExamQue examQue = examQue1.getObject("examque", ExamQue.class);
			ExamQueChuanDi examcd = new ExamQueChuanDi();
			examcd.setQue(examQue.getQue());
			examcd.setExamtype(examQue.getExamtype());
			List<ExamChoi> zbchoidel = findservice.getExamChoiListByQue(examQue.getId());
			List<String> xxs = new ArrayList<>();
			for (ExamChoi xx:zbchoidel) {
				xxs.add(xx.getXuanxiang());
			}
			examcd.setXuanxiang(xxs);
			examcd.setAns(examQue.getAns());
			examcd.setJiexi(examQue.getJiexi());
			examcd.setZsdneirong(examQue.getExamZsd().getNeirong());
			examcd.setId(shezhiid);
			shezhiid++;
			rs1.add(examcd);
			ExamUser examuser = examQue1.getObject("user", ExamUser.class);
			saveservice.saveExamUser(examuser);
		}
    	
		Workbook wb = WriteExcel.createWorkBook(WriteExcel.XLSX);
		SheetResult sh = new SheetResult();
		List<List<String>> ab0 = new ArrayList<List<String>>();
		for (ExamQueChuanDi cd: rs1) {
			List<String> ab1 = new ArrayList<String>();
			String wentilx = cd.getExamtype();
			String wentileixing = "daiding";
			switch (wentilx) {
				case "danxuan":wentileixing = "单选题";break;
				case "duoxuan":wentileixing = "多选题";break;
				case "panduan":wentileixing = "判断题";break;
			}
			ab1.add(wentileixing);
			String que = cd.getQue().length() >= 500 ? cd.getQue().substring(0,499): cd.getQue();
			ab1.add(que);
			String ans = cd.getAns();
			if (wentilx.equals("panduan")) {
				if ("对".equals(ans) || "正确".equals(ans)) {
					ans = "A";
				}
				if ("错".equals(ans) || "错误".equals(ans)) {
					ans = "B";
				}
			}
			if (wentilx.equals("duoxuan")) {
				String[] ans1 = ans.split("");
				ans = String.join("|", ans1);
			}
			ab1.add(ans);
			String jiex = cd.getJiexi().length() >= 500 ? cd.getJiexi().substring(0,499): cd.getJiexi();
			ab1.add(jiex);
			double fenshu = 1;
			switch (wentilx) {
				case "danxuan":fenshu = 1.5;break;
				case "duoxuan":fenshu = 2.5;break;
				case "panduan":fenshu = 2;break;
			}
			ab1.add("");
			ab1.add(String.valueOf(fenshu));
			List<String> xuanx = cd.getXuanxiang();
			Collections.sort(xuanx, new Comparator<String>() {
	            @Override
	            public int compare(String a, String b) {
	            	return a.compareTo(b);
	            }
	        });
			for(String aString : xuanx) {
				String xs = aString;
				if (aString.indexOf("A.") == 0) {
					xs = aString.replaceFirst("A.", "");
				}
				if (aString.indexOf("B.") == 0) {
					xs = aString.replaceFirst("B.", "");
				}
				if (aString.indexOf("C.") == 0) {
					xs = aString.replaceFirst("C.", "");
				}
				if (aString.indexOf("D.") == 0) {
					xs = aString.replaceFirst("D.", "");
				}
				if (aString.indexOf("E.") == 0) {
					xs = aString.replaceFirst("E.", "");
				}
				if (aString.indexOf("F.") == 0) {
					xs = aString.replaceFirst("F.", "");
				}
				ab1.add(xs);
			}
			if (wentilx.equals("panduan")) {
				ab1.add("正确");
				ab1.add("错误");
			}
			//ab1.add(cd.getZsdneirong());
			ab0.add(ab1);
		}
		sh.setDataList(ab0);
		WriteExcel.writeDataToExcel(wb, "sheet1", "d:\\1.xlsx", sh);
		System.out.println("113   113");
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