package com.rm.entity.pt;

import java.util.Date;


public class SyXinXi {
    private int idxh;
	private int id;
	private String wzlx;
	private String sz;
	private String zsd;
	private Date lrsj;
	private String xinxiyuan;
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
	public String getWzlx() {
		return wzlx;
	}
	public void setWzlx(String wzlx) {
		this.wzlx = wzlx;
	}
	public String getSz() {
		return sz;
	}
	public void setSz(String sz) {
		this.sz = sz;
	}
	public String getZsd() {
		return zsd;
	}
	public void setZsd(String zsd) {
		this.zsd = zsd;
	}
	public Date getLrsj() {
		return lrsj;
	}
	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}
	public String getXinxiyuan() {
		return xinxiyuan;
	}
	public void setXinxiyuan(String xinxiyuan) {
		this.xinxiyuan = xinxiyuan;
	}
	
	public SyXinXi(int idxh, int id, String wzlx, String sz, String zsd, Date lrsj, String xinxiyuan) {
		super();
		this.idxh = idxh;
		this.id = id;
		this.wzlx = wzlx;
		this.sz = sz;
		this.zsd = zsd;
		this.lrsj = lrsj;
		this.xinxiyuan = xinxiyuan;
	}
	
	
}
