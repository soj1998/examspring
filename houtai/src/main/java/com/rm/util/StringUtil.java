package com.rm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class StringUtil {

	public static Boolean isNotEmpty(String a) {
		if( a == null || "".equals(a) && a.length()>0)
		{
			return false;
		}
		String b = myTrim(a);
		if( a != null && !"".equals(b) && b.length()>0)
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
    
    /**
     	* 对Map<Integer,String> 类型的map对，先排序 再取值
     	* 取值时，按照确定的数组替换为空
     	* 例如将“【单选题】”替换为空
     	* 将取到的值格式化为string输出
     * 
     */
    public static String getMapStringTiHuanTou(Map<Integer,String> map,List<String> tihuan,int toubuweizhi) {
		StringBuilder sb = new StringBuilder();		
		//这里将map.entrySet转换为List
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	String a = mapping.getValue();
        	if (StringUtil.isNotEmpty(a)) {
        		if(a.length() > toubuweizhi) {
        			String a1 = a.substring(0, toubuweizhi);
        			String a2 = a.substring(toubuweizhi, a.length());
        			for(String tm:tihuan) {
                		a1 = a1.replaceAll(tm, "");
        			}
        			a1 = myTrim(a1);
        			a2 = myTrim(a2);
        			if (StringUtil.isNotEmpty(a1) || StringUtil.isNotEmpty(a2)) {
                		sb = sb.append(a1).append(a2);
                	}
        		}else {
        			sb = sb.append(a);
        		}
        	}        	
        } 
		return sb.toString();
	}
    
    public static String getMapString(Map<Integer,String> map,List<String> tihuan) {
		StringBuilder sb = new StringBuilder();		
		//这里将map.entrySet转换为List
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	String a = mapping.getValue();
        	for(String tm:tihuan) {
        		a = a.replaceAll(tm, "");
			}
        	if (StringUtil.isNotEmpty(a)) {
        		sb = sb.append(a);
        	}
        } 
		return sb.toString();
	}
    
    public static String getMapString(Map<Integer,String> map,String[] tihuan) {
		StringBuilder sb = new StringBuilder();		
		//这里将map.entrySet转换为List
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	String a1 = mapping.getValue();
        	for (String ati : tihuan) {
        		a1 = a1.replaceAll(ati, "");
        	}
        	String a = myTrim(a1);
        	if (StringUtil.isNotEmpty(a)) {
        		sb = sb.append(a);
        	}
        } 
		return sb.toString();
	}
    
    //jsonobject 格式 hangshu neirong
    public static String getJSONArrayString(JSONArray map,String[] tihuan) {
		StringBuilder sb = new StringBuilder();		
		//存放排序结果json数组
        JSONArray sortedJsonArray = new JSONArray();
        // 用于排序的list
        List<JSONObject> list = new ArrayList<JSONObject>();
        //遍历待排序的json数组，并将数据放入list
        for (int i = 0; i < map.size(); i++) {
            list.add(map.getJSONObject(i));
        }
        //然后通过比较器来实现排序
        
        Collections.sort(list, new Comparator<JSONObject>() {
            //排序字段
            private static final String KEY_NAME1 = "hangshu";
            private static final String KEY_NAME2 = "neirong";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA1 = new String();
                String valA2 = new String();
                String valB1 = new String();
                String valB2 = new String();
                try {
                    valA1 = a.getString(KEY_NAME1);
                    valA2 = b.getString(KEY_NAME1);
                    valB1 = a.getString(KEY_NAME2);
                    valB2 = b.getString(KEY_NAME2);
                } catch (JSONException e) {
                    System.out.println(e);
                }
                // 设置排序规则
                int i = valA1.compareTo(valA2);
                if (i == 0) {
                    int j = valB1.compareTo(valB2);
                    return j;
                }
                return i;
            }
        });
      //将排序后结果放入结果jsonArray
        for (int i = 0; i < map.size(); i++) {
            sortedJsonArray.add(list.get(i));
            String a1 = list.get(i).getString("neirong");
            for (String ati : tihuan) {
        		a1 = a1.replaceAll(ati, "");
        	}
        	String a = myTrim(a1);
        	if (StringUtil.isNotEmpty(a)) {
        		sb = sb.append(a);
        	}
        }
        return sb.toString();
	}
    
    public static JSONArray getJSONArraySorted(JSONArray cr) {
        JSONArray sortedJsonArray = new JSONArray();
        // 用于排序的list
        List<JSONObject> list = new ArrayList<JSONObject>();
        //遍历待排序的json数组，并将数据放入list
        for (int i = 0; i < cr.size(); i++) {
            list.add(cr.getJSONObject(i));
        }
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<JSONObject>() {
            //排序字段
            private static final String KEY_NAME1 = "hangshu";
            private static final String KEY_NAME2 = "neirong";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA1 = new String();
                String valA2 = new String();
                String valB1 = new String();
                String valB2 = new String();
                try {
                    valA1 = a.getString(KEY_NAME1);
                    valA2 = b.getString(KEY_NAME1);
                    valB1 = a.getString(KEY_NAME2);
                    valB2 = b.getString(KEY_NAME2);
                } catch (JSONException e) {
                    System.out.println(e);
                }
                // 设置排序规则
                int i = valA1.compareTo(valA2);
                if (i == 0) {
                    int j = valB1.compareTo(valB2);
                    return j;
                }
                return i;
            }
        });
      
        for (int i = 0; i < list.size(); i++) {
            sortedJsonArray.add(list.get(i));
        }
        return sortedJsonArray;
    }
    public static JSONArray getJSONArraySortedDesc(JSONArray cr) {
        JSONArray sortedJsonArray = new JSONArray();
        // 用于排序的list
        List<JSONObject> list = new ArrayList<JSONObject>();
        //遍历待排序的json数组，并将数据放入list
        for (int i = 0; i < cr.size(); i++) {
            list.add(cr.getJSONObject(i));
        }
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<JSONObject>() {
            //排序字段
            private static final String KEY_NAME1 = "hangshu";
            private static final String KEY_NAME2 = "neirong";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA1 = new String();
                String valA2 = new String();
                String valB1 = new String();
                String valB2 = new String();
                try {
                    valA1 = a.getString(KEY_NAME1);
                    valA2 = b.getString(KEY_NAME1);
                    valB1 = a.getString(KEY_NAME2);
                    valB2 = b.getString(KEY_NAME2);
                } catch (JSONException e) {
                    System.out.println(e);
                }
                // 设置排序规则
                int i = valA2.compareTo(valA1);
                if (i == 0) {
                    int j = valB2.compareTo(valB1);
                    return j;
                }
                return i;
            }
        });
      
        for (int i = 0; i < list.size(); i++) {
            sortedJsonArray.add(list.get(i));
        }
        return sortedJsonArray;
    }
    
  //jsonobject 格式 hangshu neirong
    public static String getJSONArrayString(JSONArray map,String[] tihuan,int toubuweizhi) {
		StringBuilder sb = new StringBuilder();		
		//存放排序结果json数组
        JSONArray sortedJsonArray = getJSONArraySorted(map);
      
        for (int i = 0; i < map.size(); i++) {
        	JSONObject jone = (JSONObject)sortedJsonArray.get(i);
            String a12 = jone.getString("neirong");
            for (String ati : tihuan) {
        		a12 = a12.replaceAll(ati, "");
        	}
        	String a = myTrim(a12);
        	if (StringUtil.isNotEmpty(a)) {
        		
        		if(a.length() > toubuweizhi) {
        			String a1 = a.substring(0, toubuweizhi);
        			String a2 = a.substring(toubuweizhi, a.length());
        			for(String tm:tihuan) {
                		a1 = a1.replaceAll(tm, "");
        			}
        			a1 = myTrim(a1);
        			a2 = myTrim(a2);
        			if (StringUtil.isNotEmpty(a1) || StringUtil.isNotEmpty(a2)) {
                		sb = sb.append(a1).append(a2);
                	}
        		}else {
        			sb = sb.append(a);
        		}
            	
        	}
        }
        return sb.toString();
	}
    public static String getJSONArrayString(JSONArray map,List<String> tihuan,int toubuweizhi) {
		StringBuilder sb = new StringBuilder();		
		//存放排序结果json数组
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(getXiTiLeiXingZw()));
        JSONArray sortedJsonArray = getJSONArraySorted(map);      
        for (int i = 0; i < map.size(); i++) {
        	JSONObject jone = (JSONObject)sortedJsonArray.get(i);
            String a12 = jone.getString("neirong");
            for (String ati : list) {
            	if (a12.indexOf(ati)>=0) {
					//t1ci =true;
            		a12 = a12.replaceFirst(ati, "");
					break;
				}        		
        	}
        	String a = myTrim(a12);
        	if (StringUtil.isNotEmpty(a)) {
        		
        		if(a.length() > toubuweizhi) {
        			String a1 = a.substring(0, toubuweizhi);
        			String a2 = a.substring(toubuweizhi, a.length());
        			//boolean t1ci = false;
        			for(String tm:tihuan) {
        				//只替换一次
        				if (a1.indexOf(tm)>=0) {
        					//t1ci =true;
        					a1 = a1.replaceFirst(tm, "");
        					break;
        				}
                		
        			}
        			a1 = myTrim(a1);
        			a2 = myTrim(a2);
        			if (StringUtil.isNotEmpty(a1) || StringUtil.isNotEmpty(a2)) {
                		sb = sb.append(a1).append(a2);
                	}
        		}else {
        			sb = sb.append(a);
        		}
            	
        	}
        }
        return sb.toString();
	}
    /**
     * 生成六位随机数字字母组合的字符串
     * 
     */
    public static String getRandomString(int weishu) {
    	if (weishu < 6) {
    		weishu = 6;
    	}
    	String rs = "";
    	for (int i = 0; i <= 100; i++) 
    	{
    		String sources = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //一共36位 加上一些字母，就可以生成pc站的验证码了
    		Random rand = new Random();
    		StringBuffer flag = new StringBuffer();
    		for (int j = 0; j < 6; j++) 
    		{
    			flag.append(sources.charAt(rand.nextInt(36)) + "");
    		}
    		rs = flag.toString();
    	}
    	return rs;
    }
    
    /**
     * 去除string 头部空格 转char 用char iswhitespace判断
     * 
     * 
     **/
    
    public static boolean myIsWhitespace(char crstr) {
    	//全角空格（unicode = 12288 ），不间断空格（unicode=160）
    	boolean tb1 = Character.isWhitespace(crstr);
    	int a = (int)crstr;
		if (a==12288 || a==160) {
			tb1 = true;
		}
		return tb1;
    }
    
    public static String removeHeadSpace(String crstr) {
    	String rs = "";    	
    	char[] abcchar = crstr.toCharArray();
		StringBuilder str = new StringBuilder();
		boolean tb1 = false;
		if (abcchar.length > 0) {
			if(myIsWhitespace(abcchar[0])) {
				tb1 = true;
			}
		}
		for(int i =0;i<abcchar.length;i++) {
			if(tb1 && 
			   (myIsWhitespace(abcchar[i]))) {
				continue;
			}
			if(!myIsWhitespace(abcchar[i])) {
				tb1 = false;
			}
			str.append(abcchar[i]);
		}
		rs = str.toString();
    	return rs;
    }
    
    public static String removeTailSpace(String crstr) {
    	String rs = "";
    	char[] abcchar = crstr.toCharArray();
    	List<Character> abclist = new ArrayList<Character>();
    	for(char aa: abcchar) {
			abclist.add(aa);
		}
    	Collections.reverse(abclist);
    	List<Character> abclist2 = new ArrayList<Character>();
    	StringBuilder str = new StringBuilder();
		boolean tb1 = false;
		if (abclist.size() > 0) {
			if(myIsWhitespace(abclist.get(0))) {
				tb1 = true;
			}	
		}
		for(int i =0;i<abclist.size();i++) {
			Character abc = abclist.get(i);
			if(tb1 && myIsWhitespace(abc)) {
				continue;
			}
			if(!myIsWhitespace(abc)) {
				tb1 = false;
			} 
			if (abc != null ) {
				abclist2.add(abc);
			}
		}
		Collections.reverse(abclist2);
		for (char ab: abclist2) {
			str.append(ab);
		}
		rs = str.toString();
    	return rs;
    	
    }
    
    public static String myTrim(String crstr) {
    	String abc = removeHeadSpace(crstr);
    	String abc2 = removeTailSpace(abc);
    	return abc2;
    }
    
    public static List<String> getXinXiYuan() {
    	List<String> rs = new ArrayList<String>();
    	rs.add("选项试题");
    	rs.add("问答试题");
    	rs.add("专栏");
    	rs.add("大文章");
    	return rs;
    }
    
    /**
     *转换题目类型，方便保存
     *
     */
    public static String getUploadFiles() {
    	return "uploadfiles";
    }
    
    public static String getTempFiles() {
    	return "tempfiles";
    }
    
    public static String getPicFiles() {
    	return "picfiles";
    }
    
    public static String getKaiShiBiaoZHi() {
    	return "---------正式开始---------";
    }
    public static String getJianGeBiaoZHi() {
    	return "a1";
    }
    
    public static String[] getZhuanLanRiQi() {
    	return new String[] {"【日期】"};
    }
    
    public static String[] getZhuanLanShuiZhong() {
    	return new String[] {"【税种】"};
    }
    
    public static String[] getZhuanLanXueKe() {
    	return new String[] {"【学科】"};
    }
    
    public static String[] getZhuanLanLaiYuan() {
    	return new String[] {"【来源】"};
    }
    
    public static String[] getZhuanLanXiLie() {
    	return new String[] {"【系列】"};
    }
    
    public static String[] getChuTiMuWaiXinXiQuan() {
    	return new String[] {"【知识点】","【答案】"};
    }
    
    public static String[] getXiTiZhiShiDian() {
    	return new String[] {"【知识点】"};
    }
    
    public static String[] getXiTiBiaoTi() {
    	return new String[] {"【标题】"};
    }
    
    public static String[] getXiTiZongHeTi() {
    	return new String[] {"【综合题】"};
    }
    
    public static String[] getXiTiDaan() {
    	return new String[] {"【参考答案】","参考答案:","参考答案：","【答案】","答案："};
    }
    
    public static String[] getXiTiJieXi() {
    	return new String[] {"【答案解析】","答案解析:","答案解析：","解析：","【解析】"};
    }
    
    public static String[] getXiTiLeiXingZw() {
    	return new String[] {"【单选题】","【单项选择题】","【多选题】","【多项选择题】","【计算题】","【综合题】","【判断题】","【填空题】","【简答题】","【名词解释】"};
    }
    /*
            1.传入数字 返回类似1. 2. 3.或者 1, 2, 3,的字符串数组
            2.作为判断分隔的依据
     * */
    public static String[] getXiTiShuZiFenZu(int shuzishu,String[] zifu) {
    	List<String> a = new ArrayList<String>();
    	for (String zifua : zifu) {
	    	for (int i = shuzishu;i > 0;i--) {
	    		String ab = i + zifua;
	    		a.add(ab);
	    	}
    	}
    	return a.toArray(new String[a.size()]);
    }
    //每个单选不超过200题
    public static int getXiTiShuZiFenZuGeShu() {
    	return 200; //1. 2. 3. ... 100.
    }
    //只对前10个字符 替换 单选 多选 1. 2.
    public static int getXiTiTiHuanTouWeiShu() {
    	return 10; //1. 2. 3. ... 100.
    }
    
    public static String[] getXiTiShuZiFenZuFuHao() {
    	return new String[] {"."};
    }
    
    public static String getXiTiShuZiFuJieQu(String chuanru, int zifuweishu) {
    	int ac = 0;
    	for (String a : getXiTiShuZiFenZuFuHao()) {
    		int acd = chuanru.indexOf(a);
    		if (acd >= 0 && acd <= zifuweishu) {
    			ac = ac < acd ? acd : ac;
    		}
    	}
    	String a = ac > 0 ? chuanru.substring(0, ac) : "";
    	return a;
    }
    
    public static String[] getXiTiLeiXingZwYouXuanXiangZw() {
    	return new String[] {"【单选题】","【单项选择题】","【多选题】","【多项选择题】"}; //,"【判断题】"
    }
    
    public static String[] getXiTiLeiXingZwYouXuanXiangZWZhiShiPanDuan() {
    	return new String[] {"【判断题】"}; //,"【判断题】"
    }
    public static String[] getXiTiLeiXingZwYouXuanXiangYw() {
    	return new String[] {"danxuan","duoxuan","panduan"};
    }
    
    public static boolean panduanYouXuanXiangZw(String a) {
    	List<String> s = Arrays.asList(getXiTiLeiXingZwYouXuanXiangZw());
    	return s.contains(a);
    }
    
    public static boolean panduanYouXuanXiangYw(String a) {
    	List<String> s = Arrays.asList(getXiTiLeiXingZwYouXuanXiangYw());
    	return s.contains(a);
    }
    
    public static String[] getXiTiLeiXingYw() {
    	return new String[] {"danxuan","danxuan","duoxuan","duoxuan","jisuan","zonghe","panduan","tiankong","jianda","mcjieshi"};
    }
    
    public static String[] getXiTiLeiXingYwZhanShi() {
    	return new String[] {"weizhi", "danxuan", "duoxuan", "panduan", "weizhi", "jianda", "jisuan", "mcjieshi"};
    }
    
    public static String[] getXuanXiangBz() {
    	return new String[] {"A．","B．","C．","D．","E．","F．","G．","H．","A.","B.","C.","D.","E.","F.","G.","H."};
    }
    
    public static String[] getXuanXiangBzTou() {
    	return new String[] {"A．","A."};
    }
    
    public static String getPanDuanTiDaAn(String cr) {
    	String rs = "A";
    	switch (cr) {
    		case "正确": rs = "A";break;
    		case "对": rs = "A";break;
    		case "错误": rs = "B";break;
    		case "错": rs = "B";break;
    		default : rs = cr;
    	}
    	return rs;
    }
    
    public static String transExamXiTiLeiXing(String crstr) {
    	String rs ="weizhi";
    	String[] timu = getXiTiLeiXingZw();
    	for(int i = 0; i < timu.length -1; i++) {
			if (isNotEmpty(crstr) && crstr.indexOf(timu[i])>=0) {
				rs = getXiTiLeiXingYw()[i];
				break;
			}
		}
    	return rs;
    }
}
