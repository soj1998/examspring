package com.rm.util.baidushitu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;
import com.rm.util.file.FileXiangGuan;

public class ShiTuFileUtil {
	public static final String APP_ID = "16781535";
    public static final String API_KEY = "hUqtyvI4ip03GS8ehcpI3hRX";
    public static final String SECRET_KEY = "DIM7drbNHoGlhLXjD7AMf9uD3bYlSNhN";
	public void getTxtFromImage(String outputfilename,String inputfiledir,int filenamepaixu,int sanweiorliangwei) {
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
	    String jilulog = outputfilename; //"D:\\szexamlog.txt";
	    
	    String path = inputfiledir;//"C:\\Users\\ad\\Desktop\\图片2\\3";		//要遍历的路径
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
			//String fnc = fn.substring(20,20+ dian2);
			String fnc = fn.substring(filenamepaixu,20+ dian2);
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
    			//String fnc = fn.substring(20,20+ dian2);
    			String fnc = fn.substring(filenamepaixu,filenamepaixu+ dian2);
    			int a = Integer.parseInt(fnc);
    			String fn1 = o2.getName();
    			int dian1 = fn1.indexOf(".");
    			int dian21 = 2;
    			if (dian1==sanweiorliangwei) //if (dian1==23) 
    				dian21 = 3;
    			String fnc1 = fn1.substring(filenamepaixu,filenamepaixu+ dian21);
    			int b = Integer.parseInt(fnc1);
    			return a - b;
            }
        });
		
		for(File f:ls){					//遍历File[]数组
			if(!f.isDirectory())		//若非目录(即文件)，则打印
			{
				System.out.println(f.getAbsolutePath());
				String image = f.getAbsolutePath();
			    JSONObject res = client.basicGeneral(image, options);
			    //System.out.println(res.toString(2));
			    JSONArray a = res.getJSONArray("words_result");
			    
			    for (Object one : a ) {
			    	JSONObject one1 = (JSONObject) one;
			    	//System.out.println("111"  + one1.getString("words"));
			    	FileXiangGuan.writeLogToFile(jilulog, one1.getString("words"));
			    }
			}	
		}
	}
}
