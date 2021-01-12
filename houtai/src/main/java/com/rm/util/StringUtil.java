package com.rm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	public static Boolean isNotEmpty(String a) {
		if( a != null && !"".equals(a) && a.length()>0)
		{
			return true;
		}
		return false;
	}
	
	public static String nullToKong(String a) {
		if( a == null )
		{
			return "";
		}
		return a;
	}
	
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}

	

	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
	

	public static List<String> filterWhite(List<String> list){
		List<String> resultList=new ArrayList<String>();
		for(String l:list){
			if(isNotEmpty(l)){
				resultList.add(l);
			}
		}
		return resultList;
	}

	public static String encodeStr(String str) {  
        if(str == null){
        	return null;
        }
		try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
	public static String mySubString(String a,int index){
		if (a.length()>index){
			return a.substring(0, index);
		}
		return a;
	}
	
	public static String getRootDir(HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("");
		System.out.println("ceshizhong1   "+path);
		path=path.substring(0,path.indexOf("houtai")-1);
		System.out.println("ceshizhong2   "+path);
		return path;
	}
	
	public static String getRootDir(HttpServletRequest request,String jiequmingzi) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("");
		System.out.println("webapp绝对路径   "+path);
		if (path.indexOf(jiequmingzi) <= 0) {
			throw new Exception("出现错误啦");
		}
		path=path.substring(0,path.indexOf(jiequmingzi)-1);
		System.out.println("上一层路径,保存的位置   "+path);
		return path;
	}
	
	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 依据原始文件名生成新文件名
	 * @return
	 */
	public static String getName(String fileName) {
		Random random = new Random();
		return fileName = "" + random.nextInt(10000)
				+ System.currentTimeMillis() + getFileExt(fileName);
	}

	/**
	 * 文件类型判断
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean checkFileType(String fileName,String[] allowFiles) {
		Iterator<String> type = Arrays.asList(allowFiles).iterator();
		while (type.hasNext()) {
			String ext = type.next();
			if (fileName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.toCharArray();
        float chLength = ch.length;
        float count = 0;
        //System.out.println(ch[0]+"--检查乱码开始");
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            //System.out.println(c+"--检查乱码");
            if (!Character.isLetterOrDigit(c)) {
                if (!isChineseOrSymbol(c)) {
                    count = count + 1;
                   // System.out.println(c+"--发现乱码");
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
    
    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChineseOrSymbol(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        if(isCnSymbol(ch)) return true;  
        if(isEnSymbol(ch)) return true;  
        if(ch=='\r'||ch=='\n')  return true; 
        if(0x2010 <= ch && ch <= 0x2017) return true;  
        if(0x2020 <= ch && ch <= 0x2027) return true;   
        if(0x2B00 <= ch && ch <= 0x2BFF) return true;   
        if(0xFF03 <= ch && ch <= 0xFF06) return true;   
        if(0xFF08 <= ch && ch <= 0xFF0B) return true;  
        if(ch == 0xFF0D || ch == 0xFF0F) return true;  
        if(0xFF1C <= ch && ch <= 0xFF1E) return true;  
        if(ch == 0xFF20 || ch == 0xFF65) return true;  
        if(0xFF3B <= ch && ch <= 0xFF40) return true;  
        if(0xFF5B <= ch && ch <= 0xFF60) return true;  
        if(ch == 0xFF62 || ch == 0xFF63) return true;  
        if(ch == 0x0020 || ch == 0x3000) return true;  
        return false;  

    }
    /**
     * 替换字符串中的乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static String replaceMessyCode(String strName) {
        
        char[] ch = strName.toCharArray();          
        //System.out.println(ch[0]+"--检查乱码开始");
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            /** 2. 放置分隔符:将不可见字符使用空格(32)替换 */  
            if (c <= 31)  
            {  
                c = 32;  
            } 

        }
        String result =  String.valueOf(ch).trim();        
        return result;
        
    }
    
    /**
     * 判断为空行
     *
     * @param strName 字符串
     * @return 是否是空行
     */
    public static boolean isReturnOrNewlien(String strName) {
    	if("".equals(strName.trim())) {
    		return true;
    	}
    	return false;
    }
    
    
    private static boolean isCnSymbol(char ch) {  
        if (0x3004 <= ch && ch <= 0x301C) return true;  
        if (0x3020 <= ch && ch <= 0x303F) return true;  
        return false;  
    }  
    private static boolean isEnSymbol(char ch){  
        
        if (ch == 0x40) return true;  
        if (ch == 0x2D || ch == 0x2F) return true;  
        if (0x23 <= ch && ch <= 0x26) return true;  
        if (0x28 <= ch && ch <= 0x2B) return true;          
        if (0x3C <= ch && ch <= 0x3E) return true;          
        if (0x5B <= ch && ch <= 0x60) return true;  
        if (0x7B <= ch && ch <= 0x7E) return true;  

        return false;  
      } 
}
