package com.rm.dynamicsource;

public class DataSourceContextHolder {
	private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void setDataSource(String type) {
        holder.set(type);
    }

    public static String getDataSource() {
        String lookUpKey = holder.get();
        System.out.println("*********");
        System.out.println(lookUpKey + "*********");
        System.out.println("*********");
        return lookUpKey == null ? "masterDataSource" : lookUpKey;
    }

    public static void clear() {
        holder.remove();
    }
}
