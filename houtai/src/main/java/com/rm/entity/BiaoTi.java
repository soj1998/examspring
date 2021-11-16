package com.rm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="t_biaoti")
public class BiaoTi implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 	

	
	@Column(length=150)
	private String biaoti;
	
	
	
	@Column(length=150)
	private String laiyuan;
	
	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

	public String getXilie() {
		return xilie;
	}

	public void setXilie(String xilie) {
		this.xilie = xilie;
	}

	@Column(length=150)
	private String xilie;

	@Column(length=1)
	private String yxbz;
	@Column
	private Date lrsj;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column
	private int szid;

	public int getSzid() {
		return szid;
	}

	public void setSzid(int szid) {
		this.szid = szid;
	}

	public BiaoTi(String biaoti, String laiyuan, String xilie,Date lrsj,String yxbz,int szid) {
		super();
		this.biaoti = biaoti;
		this.laiyuan = laiyuan;
		this.xilie = xilie;
		this.lrsj = lrsj;
		this.yxbz = yxbz;
		this.szid = szid;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}

	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	public BiaoTi() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	

	
    
}
