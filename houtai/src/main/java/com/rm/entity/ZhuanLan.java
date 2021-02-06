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
@Table(name="t_examchoisjk")
public class ZhuanLan {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    @Column(length=1000)
    private String zlduanluo;
    
    @Column(columnDefinition="tinyint default -1")
    private int btid; //是标题的话 存入-1
     
    public int getBtid() {
		return btid;
	}

	public void setBtid(int btid) {
		this.btid = btid;
	}

	@ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "zhuanlanctoatcid",referencedColumnName = "id")
  	private AtcSjk atcSjk;

	public ZhuanLan(int btid, String zlduanluo, AtcSjk atcSjk) {
		super();
		this.zlduanluo = zlduanluo;
		this.btid = btid;
		this.atcSjk = atcSjk;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		

	
	public String getZlduanluo() {
		return zlduanluo;
	}

	public void setZlduanluo(String zlduanluo) {
		this.zlduanluo = zlduanluo;
	}

	

	public ZhuanLan() {
		super();
		// TODO Auto-generated constructor stub
	}

	
     
}
