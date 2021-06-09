package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_shouyexinxi")
public class ShouYeXinXi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	@Column(length=50)
	private String sz;
	private int szid;
	@Column(length=200)
	private String zsd;
	private Long zsdid;
	
	public int getSzid() {
		return szid;
	}
	public void setSzid(int szid) {
		this.szid = szid;
	}
	public Long getZsdid() {
		return zsdid;
	}
	public void setZsdid(Long zsdid) {
		this.zsdid = zsdid;
	}
	@Column
	private Date lrsj;
	@Column(length=50)
	private String xinxiyuanleixing;	
	private Long xinxiyuanid;
	
	@Column(length=1)
    private String yxbz;	
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getXinxiyuanid() {
		return xinxiyuanid;
	}
	public void setXinxiyuanid(Long xinxiyuanid) {
		this.xinxiyuanid = xinxiyuanid;
	}
	public ShouYeXinXi(int szid,String sz, long zsdid,String zsd, Date lrsj, String xinxiyuanleixing, Long xinxiyuanid,String yxbz) {
		super();
		this.szid = szid;
		this.sz = sz;
		this.zsdid = zsdid;
		this.zsd = zsd;
		this.lrsj = lrsj;
		this.xinxiyuanleixing = xinxiyuanleixing;
		this.xinxiyuanid = xinxiyuanid;
		this.yxbz= yxbz;
	}
	public ShouYeXinXi() {
		super();
		// TODO Auto-generated constructor stub
	} 
	
	
	
	
	
	
}
