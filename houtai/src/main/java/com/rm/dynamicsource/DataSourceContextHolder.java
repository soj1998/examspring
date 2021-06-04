package com.rm.dynamicsource;

public class DataSourceContextHolder {
	private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void setDataSource(String type) {
        holder.set(type);
    }

    public static String getDataSource() {
    	//决定用master 还是slave
        String os = System.getProperty("os.name");
        //如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
        	//System.out.println("1------------");
        	DataSourceContextHolder.setDataSource("slaveDataSource");
        } else {  //linux 和mac
        	//System.out.println("2------------");
        	DataSourceContextHolder.setDataSource("masterDataSource");
        }
        String lookUpKey = holder.get();
        //System.out.println("*********");
        //System.out.println(lookUpKey + "*********");
        //System.out.println("*********");
        return lookUpKey == null ? "masterDataSource" : lookUpKey;
    }

    public static void clear() {
        holder.remove();
    }
}
