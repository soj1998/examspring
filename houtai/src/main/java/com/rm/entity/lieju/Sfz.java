package com.rm.entity.lieju;

public enum Sfz {
	ZZS("增值税", 1), QYSDS("企业所得税", 2), XFS("消费税", 3), GRSDS("个人所得税黄色", 4);  
    // 成员变量  
    private String name;  
    private int index;  
    // 构造方法  
    private Sfz(String name, int index) {  
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(int index) {  
        for (Sfz c : Sfz.values()) {  
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
