package com.rm.util;

public class PageZHUtil {

	public static int[] getStartIndex(int pageNum,int pageSize,int totalRecord) {
		int startIndex = 0;
		int rs_pageSize = pageSize;
		int totalPage = 0;
		if (totalRecord == 0 ) {
			totalRecord =1000;
		}
		if(totalRecord%pageSize==0){
            //说明整除，正好每页显示pageSize条数据，没有多余一页要显示少于pageSize条数据的
            totalPage = totalRecord / pageSize;
        }else{
            //不整除，就要在加一页，来显示多余的数据。
            totalPage = totalRecord / pageSize +1;
        }
        //开始索引
        startIndex = (pageNum-1)*pageSize;
        System.out.println(totalPage + "前台已经算好，不用再搞了");
		return new int[] {startIndex,rs_pageSize};
	}
	
	public static int[] getStartIndex(int pageNum,int pageSize) {
		int startIndex = 0;
		int rs_pageSize = pageSize;		
        startIndex = (pageNum-1)*pageSize;        
		return new int[] {startIndex,rs_pageSize};
	}
}
