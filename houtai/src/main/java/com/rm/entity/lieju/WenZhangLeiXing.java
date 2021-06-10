package com.rm.entity.lieju;


/*
 * param 1 基础
 * param 2 专栏
 * param 3 试题
 * */
public enum WenZhangLeiXing {
	JICHU("基础", 1), ZHUANLAN("专栏", 2), SHITI("试题", 3);  
    // 成员变量  
    private String name;  
    private int index;  
    // 构造方法  
    private WenZhangLeiXing(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(int index) {  
        for (WenZhangLeiXing c : WenZhangLeiXing.values()) {  
            if (c.getIndex() == index) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    } 
}
