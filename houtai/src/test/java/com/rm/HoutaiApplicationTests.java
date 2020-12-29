package com.rm;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.rm.dao.QueandAnsDao;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.dao.XueKeDao;
import com.rm.dao.linshi.ArticleDao;
import com.rm.dao.linshi.AuthorDao;
import com.rm.entity.TnsQbNeiRong;
import com.rm.entity.TreeNode;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.linshi.Article;
import com.rm.entity.linshi.Author;



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
	
	private static final Logger LOG = LoggerFactory.getLogger(HoutaiApplicationTests.class);
    
	@Test
	void contextLoads() {
		LOG.info("aaaaaaaabbbbbbbb");
		JSONObject a = new JSONObject();
		a.put("aaa","ddd");
		a.put("aaa","ccc");
		LOG.info("aaaaaaaa"+a.getString("aaa") + "".equals(a.getString("ab"))
				+ "   "+(null==a.getString("ab")));
	}
	

	@Test
	@Transactional(rollbackOn = Exception.class)
    @Rollback(value = false)//如果设置为fasle，即使发生异常也不会回滚
	void ceshi1() {
		
	}
	

	
	@Test
    public void test(){
		System.out.println("开始啦");
		//HWPFDocument document = null;
		try {
			List<TreeNodeSjk> bkall=tnDao.findAll();
	    	for(TreeNodeSjk b:bkall) {
	    			LOG.info("--- "+b.getAtclx());
	    	}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	System.out.println("结束啦");
        }
    }
	@Test
    public void testa(){
		System.out.println("开始啦");
		//HWPFDocument document = null;
		try {
			TreeNodeSjk tn = new TreeNodeSjk();
			tn.setAtclx("zsd");
			tn.setBiaoti(2);
			tn.setBtneirong("1");			
			tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
			tn.setRootid(3);
            tn.setSz("xfs");
            tn.setVersion("1.0.0.1");
            //tn.setId(-1);
            System.out.println(tn.toString());
            tnDao.save(tn);
            Set<TnsQbNeiRong> qbneirongset = new HashSet<TnsQbNeiRong>();
			TnsQbNeiRong tn1 = new TnsQbNeiRong("a1",1,tn);
			TnsQbNeiRong tn2 = new TnsQbNeiRong("a2",2,tn);
			qbneirongset.add(tn1);
			qbneirongset.add(tn2);				
            tnsneirongDao.saveAll(qbneirongset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	System.out.println("结束啦");
        }
    }
	

	@Test
	public void insertOneToMany(){
		Author author = new Author();
		author.setName("周晨曦4");
		List<Article> list = new ArrayList<Article>();
		for(int i =0; i<10; i++ ){
			Article article = new Article();
			article.setTitle("标题:" + i);
			article.setContent("内容:" + i);
			// 记住一定要添加关系实体对象，否在关联字段为null
			article.setAuthor(author);
			list.add(article);			
		}
		
		// author.setArticleList(list);
		authorDao.save(author);
		list.forEach(e ->{
			articleDao.save(e);
		});
		List<Author> ab = authorDao.findAll();
		ab.forEach(e->{
			List<Article> a = articleDao.findArticleByAuthorid(e.getId().intValue());
			System.out.println(a.get(0).getAuthor());
		});

		System.out.println("aa");
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
							) {						
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
			CzTreeNode mtree = new CzTreeNode();
			diGuiQiu(mtree, zhengLiArray);	
			//mtree.list();
			//list并且插入到数据库 		
			mtree.listAndInsSql(tnsneirongDao,tnDao,"zsd", "1.0.0.0", "zzs");
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
        	tn.setAtclx(wzlx);
        	tn.setSz(sz);
            tn.setVersion(wzversion);			
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			//tn.setQbneirong(item.getData().getJSONArray("qbneirong").toString());
			tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
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
	 * 用递归，求节点
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
				System.out.println("btneirong  1  " + obj1.getString("btneirong"));
	        	mtree.addright(obj1);
	        	jsonDuanArray.remove(obj1);	
	        	diGuiQiu(mtree, jsonDuanArray);	
	        	break;     	
	        }
			
		}		
		return mtree;
	}
	
	
}
