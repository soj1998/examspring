package com.rm.entity.pt;

public class SyXinXiSort{    	
	private int idxh;
	private int id;
	private String xinxiyuan;
	
	SyXinXiSort(int idxh,int id,String xinxiyuan) {
		this.idxh = idxh;
		this.id = id;
		this.xinxiyuan = xinxiyuan;
	}

	public int getIdxh() {
		return idxh;
	}

	public void setIdxh(int idxh) {
		this.idxh = idxh;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getXinxiyuan() {
		return xinxiyuan;
	}

	public void setXinxiyuan(String xinxiyuan) {
		this.xinxiyuan = xinxiyuan;
	}
	
	
}