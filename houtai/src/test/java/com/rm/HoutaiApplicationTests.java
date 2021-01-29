package com.rm;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.czentity.CzTreeNode;
import com.rm.dao.BookDao;
import com.rm.dao.ExamChoiDao;
import com.rm.dao.ExamQueDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.entity.ExamChoi;
import com.rm.entity.ExamQue;
import com.rm.entity.TreeNode;
import com.rm.entity.TreeNodeSjk;


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
	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		LOG.info("【知识点关联】aaaaaaaabbbbbbbb");
		LOG.info("【单选题  】aaaaaaaabbbbbbbb".substring(0,7));
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		
	}
	

	
	@Test
    public void test(){
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("d:\\学研社-zzs-xt.docx");
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return;
			}
			JSONArray jsonDuanArray = new JSONArray();			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				JSONObject jsonDuan = new JSONObject();
				if("".equals(paras.get(i).getParagraphText())) {
					continue;
				}else {
					jsonDuan.put("neirong", paras.get(i).getParagraphText());
					jsonDuan.put("hangshu", i);
					String d = paras.get(i).getParagraphText().trim();
					ExamChoi examChoi = new ExamChoi();
					ExamQue examQue = new ExamQue();
					switch (d.substring(0, 3)) {
						
					}
					jsonDuanArray.add(jsonDuan);	
				}
							
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
	
	

	
	
	/**
	 *1.搞一个json 把读到的信息储存到mysql，信息包括多少段，多少章，多少节，多少目，
	 *章节目对应的段落数 多少空段落 对应的段落数。
	 *2.对每一个章节目 存到mysql 包括上下文和具体文字
	 *3.概括有个数字 对应着详细	
	 *4.用多叉树存储相关信息
	 *5.按多叉树的id存到mysql数据库
	 *6.建表 myacl article 
	 *  id 自增字段  文章类型  类型为 知识点，随笔，案例，前沿
	 *  涉及税种 增值税、消费税  版本 1.0.0.0 录入时间
	 *  rootid 多叉树id biaoti 标题段落   btneirong 标题内容
	 *  qbneirong 标题下的全部内容
	*/
	@Test
    public void test2(){
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("d:\\菜鸟税法.docx");
			doc = new XWPFDocument(is);
			//获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			int duanLuoZongshu = paras.size();
			if(duanLuoZongshu <= 0) {
				System.out.println("当前文档没有读取到的段落数");
				doc.close();
				return;
			}
			JSONArray jsonDuanArray = new JSONArray();			
			for (int i = 0;i < duanLuoZongshu ; i++) {
				JSONObject jsonDuan = new JSONObject();
				if("".equals(paras.get(i).getParagraphText())) {
					//jsonDuan.put("neirong", "");
					continue;
				}else {
					jsonDuan.put("neirong", paras.get(i).getParagraphText());
				}
				String titleLvl = getTitleLvl(doc, paras.get(i));// 获取段落级别
				jsonDuan.put("hangshu", i);
				switch(titleLvl) {
					case "0":
						jsonDuan.put("biaoti",1);
						break;
					case "1":
						jsonDuan.put("biaoti",2);
						break;
					case "2":
						jsonDuan.put("biaoti",3);
						break;
					case "3":
						jsonDuan.put("biaoti",4);
						break;
					case "4":
						jsonDuan.put("biaoti",5);
						break;
					case "5":
						jsonDuan.put("biaoti",6);
						break;
					case "6":
						jsonDuan.put("biaoti",7);
						break;
					case "7":
						jsonDuan.put("biaoti",8);
						break;
					case "8":
						jsonDuan.put("biaoti",9);
						break;
					default:
						jsonDuan.put("biaoti", 0);
						break;
				}
				//System.out.println("titleLvl:" + titleLvl + ",i-" + i+":"+paras.get(i).getParagraphText());
				jsonDuanArray.add(jsonDuan);
				/**
				System.out.println("1----:"+titleLvl);				
				if ("a5".equals(titleLvl) || "HTML".equals(titleLvl) || "".equals(titleLvl) || null == titleLvl) {
					titleLvl = "8";
				}
				//System.out.println(titleLvl + "-----");// 0,1,2
				if (!"8".equals(titleLvl)) {
					//System.out.println(titleLvl + "====" + para.getParagraphText());
				}
				*/
			}
			JSONArray zhengLiArray = new JSONArray();
			for(Object obj:jsonDuanArray) {
				JSONObject obj1 = (JSONObject)obj;
				JSONObject nobj = new JSONObject();
				if(obj1.getShort("biaoti")>0) {
					nobj.put("biaoti", obj1.getShort("biaoti"));
					nobj.put("btneirong", obj1.getString("neirong"));
					nobj.put("hangshu", obj1.getShort("hangshu"));
				}
				JSONArray zL2Array = new JSONArray();
				for(Object obj2:jsonDuanArray) {
					JSONObject obj3 = (JSONObject)obj2;
					if(obj3.getShort("biaoti") == 0
						&&
						obj3.getShort("hangshu") > obj1.getShort("hangshu")
					) 
					{						
						JSONObject nobj2 = new JSONObject();
						nobj2.put("neirong", obj3.getString("neirong"));
						nobj2.put("hangshu", obj3.getShort("hangshu"));
						zL2Array.add(nobj2);
					}
					if(obj3.getShort("biaoti") > 0
							&&
					   obj3.getShort("hangshu") > obj1.getShort("hangshu")) {
						break;
					}
				}
				if(null != nobj && nobj.size() > 0 )//&& null != zL2Array && zL2Array.size() > 0)
					nobj.put("qbneirong", zL2Array);
				if(null != nobj && nobj.size() > 0)
					zhengLiArray.add(nobj);
			}
			zhengLiArray.forEach(e->{
				JSONObject obj1 = (JSONObject)e;
				if (obj1.getString("btneirong").equals("自来水"))
				{
					//System.out.println("找到一个");
				}
			});
			CzTreeNode mtree = new CzTreeNode();
			diGuiQiu(mtree, zhengLiArray);
			//非递归 遍历
			//mtree.displayTreeByStack();
			//mtree.list();
			//list并且插入到数据库 		
			//mtree.listAndInsSql(tnsneirongDao,tnDao,0, "1.0.0.0", 0,"fileweizhi");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
      * Word中的大纲级别，可以通过getPPr().getOutlineLvl()直接提取，但需要注意，Word中段落级别，通过如下三种方式定义： 
      *  1、直接对段落进行定义； 
      *  2、对段落的样式进行定义； 
      *  3、对段落样式的基础样式进行定义。 
      *  因此，在通过“getPPr().getOutlineLvl()”提取时，需要依次在如上三处读取。
      * @param doc
      * @param para
      * @return
    */
	private String getTitleLvl(XWPFDocument doc, XWPFParagraph para) {
		String titleLvl = "";
		try {
			// 判断该段落是否设置了大纲级别
			if (para.getCTP().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getCTP()");
				 //System.out.println(para.getParagraphText());
				 //System.out.println(para.getCTP().getPPr().getOutlineLvl().getVal());
				return String.valueOf(para.getCTP().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式是否设置了大纲级别
			if (doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getStyle");
				 //System.out.println(para.getParagraphText());
				 //System.out.println(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String.valueOf(
						doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式的基础样式是否设置了大纲级别
			if (doc.getStyles().getStyle(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal())
					.getCTStyle().getPPr().getOutlineLvl() != null) {
				 //System.out.println("getBasedOn");
				 //System.out.println(para.getParagraphText());
				 String styleName = doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal();
				 //System.out.println(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String
						.valueOf(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			//System.out.println("getStyleID");
			if (para.getStyleID() != null) {
				//System.out.println("getStyleID" + 11);
				return para.getStyleID();
			}
		} catch (Exception e) {

		}

		return titleLvl;
	}
	public void listAndInsSql(List<TreeNode> list,String wzlx,String wzversion,String sz){
        for(TreeNode item:list){
        	TreeNodeSjk tn = new TreeNodeSjk();
        	//tn.setVersion(wzversion);			
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			//tn.setQbneirong(item.getData().getJSONArray("qbneirong").toString());
			//tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
            tn.setRootid(item.getId());            
            //tn.setId(-1);
            System.out.println(tn.toString());
            tnDao.save(tn);
            System.out.println(item.getId() + "," + item.getData().toJSONString());
            if(item.nodes.size() == 0){
                continue;
            }else {
            	listAndInsSql(item.nodes,wzlx,wzversion,sz);
            }
            System.out.println();
        }
    }
	/**
	 * 得到当前传入paragraph的最大标题度
	 
	private int getMaxBiaoTiDuanWei(JSONArray jsonDuanArray) {
		int DuanLuoDingDuanWei = 1;
		if (null == jsonDuanArray || 0 == jsonDuanArray.size()) {
			return DuanLuoDingDuanWei;
		}
		for (int j = 0; j < jsonDuanArray.size(); j++) {
            JSONObject obj = (JSONObject)jsonDuanArray.get(j);
            if(null!= obj.get("biaoti") && (int)(obj.get("biaoti"))>=0) {
            	if(DuanLuoDingDuanWei < (int)(obj.get("biaoti"))) {
            		DuanLuoDingDuanWei = (int)(obj.get("biaoti"));
            	}
            }	            
		}
		return DuanLuoDingDuanWei;
	}
	/**
	 * 得到当前传入paragraph的最小标题度
	 
	private int getMinBiaoTiDuanWei(JSONArray jsonDuanArray) {
		int DuanLuoDingDuanWei = 1;
		if (null == jsonDuanArray || 0 == jsonDuanArray.size()) {
			return DuanLuoDingDuanWei;
		}
		for (int j = 0; j < jsonDuanArray.size(); j++) {
            JSONObject obj = (JSONObject)jsonDuanArray.get(j);
            if(null!= obj.get("biaoti") && (int)(obj.get("biaoti"))>=0) {
            	DuanLuoDingDuanWei = (int)(obj.get("biaoti"));
            	return DuanLuoDingDuanWei;
            }	            
		}
		return DuanLuoDingDuanWei;
	}
	*/
	
	/**
	 *	用递归，求节点
	 */
	private CzTreeNode diGuiQiu(CzTreeNode mtree, JSONArray jsonDuanArray) {
		if (null == jsonDuanArray ||jsonDuanArray.size() == 0) {
			return mtree;
		}
		for (Object obj :jsonDuanArray) {
			JSONObject obj1 = (JSONObject)obj;
			if(null!= obj1.get("biaoti"))
	        {
	        	//mtree.addright((int)(obj1.get("biaoti")) - 1, obj1);
				//System.out.println("btneirong  1  " + obj1.getString("btneirong"));
	        	//mtree.addright(obj1);
				mtree.addTreeNodeByStack(obj1.getIntValue("biaoti") - 1,obj1);
	        	jsonDuanArray.remove(obj1);
	        	if (obj1.getString("btneirong").equals("自来水")) {
	        		System.out.println("挂上一次");
	        	}
	        	diGuiQiu(mtree, jsonDuanArray);	
	        	break;     	
	        }
			
		}		
		return mtree;
	}
	 
	
}
