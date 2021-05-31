package com.rm.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_zhuanlanzg")
public class ZhuanLanZhengGe {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    
    
	@Column(length=300)
    private String zlxilie;
	
    public String getZlxilie() {
		return zlxilie;
	}

	public void setZlxilie(String zlxilie) {
		this.zlxilie = zlxilie;
	}

	@Column(length=1)
    private String yxbz;
	
	@Column
    private int szid;
	
	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}

	public int getSzid() {
		return szid;
	}

	public void setSzid(int szid) {
		this.szid = szid;
	}

	public String getWzlaiyuan() {
		return wzlaiyuan;
	}

	public void setWzlaiyuan(String wzlaiyuan) {
		this.wzlaiyuan = wzlaiyuan;
	}

	@Column
    private Date lrsj;
		

	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	private ExamZsd exzsd;

	public ExamZsd getExzsd() {
		return exzsd;
	}

	public void setExzsd(ExamZsd exzsd) {
		this.exzsd = exzsd;
	}



	@Column(length=300)
    private String wzlaiyuan;

		

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=10000) 
	//英文63325 utf-8 21812 gbk 32766
	//这都是一共的 21812 - 1000 -1000 - 1000 - 200
    private String zlzhengge;	    
	
	
	public String getZlzhengge() {
		return zlzhengge;
	}

	public void setZlzhengge(String zlzhengge) {
		this.zlzhengge = zlzhengge;
	}
	@Column(length=9000) 
	private String zlzhenggetxt;
	
	public String getZlzhenggetxt() {
		return zlzhenggetxt;
	}

	public void setZlzhenggetxt(String zlzhenggetxt) {
		this.zlzhenggetxt = zlzhenggetxt;
	}

	
	public ZhuanLanZhengGe() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	/*
	 * 搞单个专栏的时候用，且有系列
	 * */
	public ZhuanLanZhengGe(String yxbz,int szid,int btid, Date riqi,String laiyuan,String xilie, String zhengge, String zhenggetxt,ExamZsd exzsd) {
		super();
		this.szid = szid;
		this.zlxilie = xilie;
		this.wzlaiyuan = laiyuan;
		this.zlzhengge = zhengge;
		this.lrsj = riqi;
		this.yxbz = yxbz;
		this.zlzhenggetxt = zhenggetxt;
		this.exzsd = exzsd;
	}
	/*
	 * 搞单个专栏的时候用，没有系列
	 * */
	public ZhuanLanZhengGe(String yxbz,int szid,int btid, Date riqi,String laiyuan,String zhengge, String zhenggetxt,ExamZsd exzsd) {
		super();	
		this.szid = szid;
		this.wzlaiyuan = laiyuan;
		this.zlzhengge = zhengge;
		this.lrsj = riqi;
		this.yxbz = yxbz;
		this.zlzhenggetxt = zhenggetxt;
		this.exzsd = exzsd;
	}
	
	
	
     
}
