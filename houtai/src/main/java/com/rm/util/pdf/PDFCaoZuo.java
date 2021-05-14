package com.rm.util.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import com.rm.util.DateTimeUtil;

public class PDFCaoZuo {
	
	//private static PDFEntity vo = new PDFEntity();
    
    // 原文链接：https://blog.csdn.net/qq_37022150/article/details/79486730
	public static String getPDFTxt(String inputFile){
        //创建文档对象
        PDDocument doc =null;
        String content="";
        try {
            //加载一个pdf对象
            doc =PDDocument.load(new File(inputFile));
            if( doc.isEncrypted() )
            {
                System.err.println( "Error: Document is encrypted with a password." );
                return "jiamile"; 
            }
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
	        if( document.isEncrypted() )
	        {
	            System.err.println( "Error: Document is encrypted with a password." );
	            return; 
	        }
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
	           * DPI是指每英寸的像素,也就是扫描精度,DPI越低,扫描的清晰度越低
	           *	 根据根据自己需求而定,我导出的图片是 927x1372
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
	
	
	
	



}
