package com.rm.entity;

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

	public ZhuanLan(int btid, int hangshu,String zlduanluo, String xilie,AtcSjk atcSjk) {
		super();
		this.zlduanluo = zlduanluo;
		this.atcSjk = atcSjk;
		this.btid = btid;
		this.zlxilie = xilie;
		this.hangshu = hangshu;
	}

	
	
	public int getHangshu() {
		return hangshu;
	}

	public void setHangshu(int hangshu) {
		this.hangshu = hangshu;
	}
	
	
     
}
