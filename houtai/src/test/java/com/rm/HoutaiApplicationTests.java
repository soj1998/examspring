package com.rm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import com.rm.dao.BookDao;
import com.rm.dao.QueandAnsDao;
import com.rm.dao.XueKeDao;
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
		System.out.println("开始啦");
		//HWPFDocument document = null;
		try {
            //List<String> list = new ArrayList<>();
            InputStream is = new FileInputStream(new File("E:\\234.doc"));  //需要将文件路更改为word文档所在路径。
            POIFSFileSystem fs = new POIFSFileSystem(is);
            HWPFDocument document = new HWPFDocument(fs);
            Range range = document.getRange();
            CharacterRun run1 = null;//用来存储第一行内容的属性
            CharacterRun run2 = null;//用来存储第二行内容的属性
            
            int q=1;
            for (int i = 0; i < range.numParagraphs()-1; i++) {
                Paragraph para1 = range.getParagraph(i);// 获取第i段
                Paragraph para2 = range.getParagraph(i+1);// 获取第i段
                int t=i;              //记录当前分析的段落数                
                String paratext1 = para1.text().trim().replaceAll("\r\n", "");   //当前段落和下一段
                String paratext2 = para2.text().trim().replaceAll("\r\n", "");
                System.out.println("开始啦"+paratext2);
                run1=para1.getCharacterRun(0);
                run2=para2.getCharacterRun(0);
                if (paratext1.length() > 0&&paratext2.length() > 0) {
                        //这个if语句为的是去除大标题，连续三个段落字体大小递减就跳过
                        if(run1.getFontSize()>run2.getFontSize()&&run2.getFontSize()>range.getParagraph(i+2).getCharacterRun(0).getFontSize()) {
                            continue;
                        }                        
                        //连续两段字体格式不同
                        if(run1.getFontSize()>run2.getFontSize()) {
                            
                            String content=paratext2;
                            run1=run2;  //从新定位run1  run2
                            run2=range.getParagraph(t+2).getCharacterRun(0);
                            t=t+1;
                            while(run1.getFontSize()==run2.getFontSize()) {
                                //连续的相同
                                content+=range.getParagraph(t+1).text().trim().replaceAll("\r\n", "");
                                run1=run2;
                                run2=range.getParagraph(t+2).getCharacterRun(0);
                                t++;
                            }
                            
                            if(paratext1.indexOf("HYPERLINK")==-1&&content.indexOf("HYPERLINK")==-1) {
                                System.out.println(q+"标题"+paratext1+"\t内容"+content);
                                i=t;
                                q++;
                            }
                                
                        }
                }
            }
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	System.out.println("结束啦");
        }
    }
	
	@Test
    public void test2(){
		System.out.println("开始啦");
		InputStream is = null;
		XWPFDocument doc = null;
		try {
			is = new FileInputStream("E:\\234.docx");
			doc = new XWPFDocument(is);
			// 获取段落
			List<XWPFParagraph> paras = doc.getParagraphs();
			for (XWPFParagraph para : paras) {
				// System.out.println(para.getCTP());//得到xml格式
				System.out.println(para.getStyleID());// 段落级别
				System.out.println(para.getParagraphText());// 段落内容
				String titleLvl = getTitleLvl(doc, para);// 获取段落级别
				System.out.println("1----:"+titleLvl);
				if ("a5".equals(titleLvl) || "HTML".equals(titleLvl) || "".equals(titleLvl) || null == titleLvl) {
					titleLvl = "8";
				}
				System.out.println(titleLvl + "-----");// 0,1,2
				if (!"8".equals(titleLvl)) {
					System.out.println(titleLvl + "====" + para.getParagraphText());
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
	      * Word中的大纲级别，可以通过getPPr().getOutlineLvl()直接提取，但需要注意，Word中段落级别，通过如下三种方式定义： 
	      *  1、直接对段落进行定义； 
	      *  2、对段落的样式进行定义； 
	      *  3、对段落样式的基础样式进行定义。 
	      *  因此，在通过“getPPr().getOutlineLvl()”提取时，需要依次在如上三处读取。
	      * @param doc
	      * @param para
	      * @return
    */
	private static String getTitleLvl(XWPFDocument doc, XWPFParagraph para) {
		String titleLvl = "";
		try {
			// 判断该段落是否设置了大纲级别
			if (para.getCTP().getPPr().getOutlineLvl() != null) {
				// System.out.println("getCTP()");
				// System.out.println(para.getParagraphText());
				// System.out.println(para.getCTP().getPPr().getOutlineLvl().getVal());
				return String.valueOf(para.getCTP().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式是否设置了大纲级别
			if (doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl() != null) {
				// System.out.println("getStyle");
				// System.out.println(para.getParagraphText());
				// System.out.println(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String.valueOf(
						doc.getStyles().getStyle(para.getStyle()).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			// 判断该段落的样式的基础样式是否设置了大纲级别
			if (doc.getStyles().getStyle(doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal())
					.getCTStyle().getPPr().getOutlineLvl() != null) {
				// System.out.println("getBasedOn");
				// System.out.println(para.getParagraphText());
				String styleName = doc.getStyles().getStyle(para.getStyle()).getCTStyle().getBasedOn().getVal();
				// System.out.println(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
				return String
						.valueOf(doc.getStyles().getStyle(styleName).getCTStyle().getPPr().getOutlineLvl().getVal());
			}
		} catch (Exception e) {

		}

		try {
			if (para.getStyleID() != null) {
				return para.getStyleID();
			}
		} catch (Exception e) {

		}

		return titleLvl;
	}
	
	
	
}
