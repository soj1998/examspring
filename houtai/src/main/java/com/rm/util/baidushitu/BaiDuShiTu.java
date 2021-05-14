package com.rm.util.baidushitu;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

public class BaiDuShiTu {
	public static final String APP_ID = "16781535";
    public static final String API_KEY = "hUqtyvI4ip03GS8ehcpI3hRX";
    public static final String SECRET_KEY = "DIM7drbNHoGlhLXjD7AMf9uD3bYlSNhN";
	
    public static JSONObject contextLoads(String filename) throws JSONException {
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
	    String image = filename;//"D:\\图4.jpg";
	    JSONObject res = client.basicGeneral(image, options);
	    System.out.println(res.toString(2));
	    return res;
	}
}
