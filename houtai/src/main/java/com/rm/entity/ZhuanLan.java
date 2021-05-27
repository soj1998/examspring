package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_zhuanlan")
public class ZhuanLan {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    //段落存为json bt xl hangshu neirong
	@Column(length=1000)
    private String zlduanluo;
    
	@Column(length=1000)
    private String zlxilie;
	
    public String getZlxilie() {
		return zlxilie;
	}

	public void setZlxilie(String zlxilie) {
		this.zlxilie = zlxilie;
	}



	@Column(columnDefinition="int default -1")
    private int btid; //是标题的话 存入-1
     
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

	@Column(length=200)
    private String zlzsd;

	public String getZlzsd() {
		return zlzsd;
	}

	public void setZlzsd(String zlzsd) {
		this.zlzsd = zlzsd;
	}



	@Column(length=1000)
    private String wzlaiyuan;

	@ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "zhuanlanctoatcid",referencedColumnName = "id")
  	private AtcSjk atcSjk;

	public ZhuanLan(int btid, String zlduanluo, String xilie,AtcSjk atcSjk) {
		super();
		this.zlduanluo = zlduanluo;
		this.atcSjk = atcSjk;
		this.btid = btid;
		this.zlxilie = xilie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(length=16000) 
	//英文63325 utf-8 21812 gbk 32766
	//这都是一共的 21812 - 1000 -1000 - 1000 - 200
    private String zlzhengge;	    
	
	
	public String getZlzhengge() {
		return zlzhengge;
	}

	public void setZlzhengge(String zlzhengge) {
		this.zlzhengge = zlzhengge;
	}

	public int getBtid() {
		return btid;
	}

	public void setBtid(int btid) {
		this.btid = btid;
	}

	public AtcSjk getAtcSjk() {
		return atcSjk;
	}

	public void setAtcSjk(AtcSjk atcSjk) {
		this.atcSjk = atcSjk;
	}

	public String getZlduanluo() {
		return zlduanluo;
	}

	public void setZlduanluo(String zlduanluo) {
		this.zlduanluo = zlduanluo;
	}

	@Column(columnDefinition="int default -1")
    private int hangshu; 

	public ZhuanLan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZhuanLan(String yxbz,int btid, int hangshu,String zlduanluo, String xilie,AtcSjk atcSjk) {
		super();
		this.zlduanluo = zlduanluo;
		this.atcSjk = atcSjk;
		this.btid = btid;
		this.zlxilie = xilie;
		this.hangshu = hangshu;
		this.yxbz =yxbz;
	}

	public ZhuanLan(String yxbz,int szid,int btid, Date riqi,String laiyuan,String xilie, String zhengge) {
		super();
		this.szid = szid;
		this.btid = btid;
		this.zlxilie = xilie;
		this.wzlaiyuan = laiyuan;
		this.zlzhengge = zhengge;
		this.lrsj = riqi;
		this.yxbz = yxbz;
	}
	
	public ZhuanLan(String yxbz,int szid,int btid, Date riqi,String laiyuan,String zhengge) {
		super();	
		this.szid = szid;
		this.btid = btid;
		this.wzlaiyuan = laiyuan;
		this.zlzhengge = zhengge;
		this.lrsj = riqi;
		this.yxbz = yxbz;
	}
	
	public int getHangshu() {
		return hangshu;
	}

	public void setHangshu(int hangshu) {
		this.hangshu = hangshu;
	}
	
	
     
}
