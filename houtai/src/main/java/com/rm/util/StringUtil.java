package com.rm.util;

public class StringUtil {

	public static Boolean isNotEmpty(String a) {
		if( a != null && !"".equals(a) && a.length()>0)
		{
			return true;
		}
		return false;
	}
	
	public static String isNUllGetSpace(String a) {
		if( a == null )
		{
			return "";
		}
		return a;
	}
}
