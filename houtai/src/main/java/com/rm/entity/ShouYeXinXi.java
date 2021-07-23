package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name="t_shouyexinxi")
public class ShouYeXinXi {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 	

    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(length=50)
	private String sz;
	private int szid;
	@Column(length=200)
	private String zsd;
	private int zsdid;
	
	public int getSzid() {
		return szid;
	}
	public void setSzid(int szid) {
		this.szid = szid;
	}
	public int getZsdid() {
		return zsdid;
	}
	public void setZsdid(int zsdid) {
		this.zsdid = zsdid;
	}
	@Column
	private Date lrsj;
	@Column(length=50)
	private String xinxiyuanleixing;	
	private int xinxiyuanid;
	
	@Column(length=1)
    private String yxbz;	
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
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
	public String getXinxiyuanleixing() {
		return xinxiyuanleixing;
	}
	public void setXinxiyuanleixing(String xinxiyuanleixing) {
		this.xinxiyuanleixing = xinxiyuanleixing;
	}
	public int getXinxiyuanid() {
		return xinxiyuanid;
	}
	public void setXinxiyuanid(int xinxiyuanid) {
		this.xinxiyuanid = xinxiyuanid;
	}
	
	
	@Column(length=200)
	private String biaoti;
	
	public ShouYeXinXi(int szid,String sz, String biaoti, 
			int zsdid,String zsd, Date lrsj, String xinxiyuanleixing, int xinxiyuanid,String yxbz) {
		super();
		this.szid = szid;
		this.sz = sz;
		this.zsdid = zsdid;
		this.biaoti = biaoti;
		this.zsd = zsd;
		this.lrsj = lrsj;
		this.xinxiyuanleixing = xinxiyuanleixing;
		this.xinxiyuanid = xinxiyuanid;
		this.yxbz= yxbz;
	}
	
	public ShouYeXinXi(int szid,String sz, String biaoti, 
			Date lrsj, String xinxiyuanleixing, int xinxiyuanid,String yxbz) {
		super();
		this.szid = szid;
		this.sz = sz;
		this.biaoti = biaoti;
		this.lrsj = lrsj;
		this.xinxiyuanleixing = xinxiyuanleixing;
		this.xinxiyuanid = xinxiyuanid;
		this.yxbz= yxbz;
	}
	
	public String getBiaoti() {
		return biaoti;
	}
	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}
	public ShouYeXinXi() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	
	
	
	
	
}
