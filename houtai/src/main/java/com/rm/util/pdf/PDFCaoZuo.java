package com.rm.util.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import com.rm.util.DateTimeUtil;

public class PDFCaoZuo {
	
	//private static PDFEntity vo = new PDFEntity();
    
    // 原文链接：https://blog.csdn.net/qq_37022150/article/details/79486730
	public static String ReaDPDF(String inputFile){
        //创建文档对象
        PDDocument doc =null;
        String content="";
        try {
            //加载一个pdf对象
            doc =PDDocument.load(new File(inputFile));
            //获取一个PDFTextStripper文本剥离对象  
            PDFTextStripper textStripper =new PDFTextStripper();
            content=textStripper.getText(doc);            
            //System.out.println("内容:"+content);
            System.out.println("全部页数"+doc.getNumberOfPages());  
            //关闭文档
            doc.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return content;
    }
	public static void getImgInPDF(String pdfPath,String imgPath){
	    PDDocument document = null;
	    try {
	        File pdfFile = new File(pdfPath);
	        //　加载pdf文档，在pdmodel包
	        document = PDDocument.load(pdfFile);
	        //　PDF文档总页数
	        //　PDF文档渲染对象，在rendering包
	        PDFRenderer renderer = new PDFRenderer(document);
	        String startDate = DateTimeUtil.getTimeNowString();
	        int pageCount = document.getNumberOfPages();
	        System.out.println("共 "+pageCount+" 页.");
	        for (int i = 0; i < pageCount; i++) {
	          /**
	           * renderImage(i,1.9f)
	           * 
	           * i: 指定页对象下标,从0开始,0即第一页
	           * 
	           * 1.9f:DPI值(Dots Per Inch),官方描述比例因子，其中1=72 DPI
	           *      DPI是指每英寸的像素,也就是扫描精度,DPI越低,扫描的清晰度越低
	           *      根据根据自己需求而定,我导出的图片是 927x1372
	           */
	            BufferedImage image = renderer.renderImage(i,1.9f);
	            // 导出图片命名为:0-n.jpeg
	            ImageIO.write(image, "JPEG", new File(imgPath+i+".jpeg"));
	            System.out.println("导出 "+imgPath+i+".jpeg...");
	        }
	        System.out.println("开始时间:"+startDate);
	        System.out.println("结束时间:"+ DateTimeUtil.getTimeNowString());
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	}
	
	public static void getTextFromPdf() throws IOException {
        String dest = "D:/test.pdf";

        PDDocument document = PDDocument.load(new File(dest));

        // 获取页码
        int pages = document.getNumberOfPages();
        System.out.println("总页：" + pages);

        PDFTextStripper2 stripper2 = new PDFTextStripper2();
        for (int i = 1; i <= pages; i++) {
            stripper2.setStartPage(i);
            stripper2.setEndPage(i);
            stripper2.getText(document);//读取当前页的全部内容

			//这里可以自己for循环处理，我为了过滤PDF page的头和页脚，直接读body
			//注意，body里面遇到表格，图片，会放到ls的最后面，方便处理
            List<List<TextPosition>> ll = stripper2.getCharactersByArticle();
//          List<TextPosition> ls = ll.get(0);//读取PDF page的header
            List<TextPosition> ls = ll.get(1);//读取pdf page的body内容
//          List<TextPosition> ls = ll.get(2);//读取PDF page的页码部分
            float y = 0;
            int buttom;//每行距离下面一行的距离
            StringBuffer sentence = new StringBuffer();
            for (TextPosition tp : ls) {
                String c = tp.getUnicode();

                //根据高度来判断是否是一句话
                if (y != tp.getY()) {
                    System.out.print(sentence.toString());

                    buttom = (int) (tp.getY() - y);
                    if (buttom > 11 || buttom < -10) {
                        System.out.println();
                    }
                    y = tp.getY();
                    sentence.setLength(0);
                }

                sentence.append(c);

                //特殊处理符号
                if (c.equals("•")) {
                    sentence.append(" ");
                }

                //遇到表格不打印出来
                if (sentence.toString().indexOf("表格 ") == 0 || sentence.toString().indexOf("Table ") == 0) {
                    break;
                }
            }
            if (sentence.length() > 0) {
                System.out.print(sentence.toString());
            }
        }

    }


}
