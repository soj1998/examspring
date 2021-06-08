package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_ggxinxi")
public class GongGongXinXi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	@Column(length=50)
	private String sz;
	@Column(length=200)
	private String zsd;
	@Column
	private Date lrsj;
	@Column(length=50)
	private String xinxiyuanleixing;	
	private Long xinxiyuanid;
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
	public GongGongXinXi(String sz, String zsd, Date lrsj, String xinxiyuanleixing, Long xinxiyuanid) {
		super();
		this.sz = sz;
		this.zsd = zsd;
		this.lrsj = lrsj;
		this.xinxiyuanleixing = xinxiyuanleixing;
		this.xinxiyuanid = xinxiyuanid;
	} 
	
	
	
	
	
	
}
