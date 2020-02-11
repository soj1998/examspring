package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_guanlianxing")
public class GuanLianXing {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    private Integer userId;
    
    public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(length=20)
    private String xueKe;
    
    @Column(length=30)
    private String erJiFenLei;
    
    @Column(length=60)
    private String zhang;
    
    @Column(length=60)
    private String jie;
    
    @Column(length=100)
    private String zhongYaoXing;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
	public String getXueKe() {
		return xueKe;
	}
	public void setXueKe(String xueKe) {
		this.xueKe = xueKe;
	}
	public String getErJiFenLei() {
		return erJiFenLei;
	}
	public void setErJiFenLei(String erJiFenLei) {
		this.erJiFenLei = erJiFenLei;
	}
	public String getZhang() {
		return zhang;
	}
	public void setZhang(String zhang) {
		this.zhang = zhang;
	}
	public String getJie() {
		return jie;
	}
	public void setJie(String jie) {
		this.jie = jie;
	}
	public String getZhongYaoXing() {
		return zhongYaoXing;
	}
	public void setZhongYaoXing(String zhongYaoXing) {
		this.zhongYaoXing = zhongYaoXing;
	}
    
     
}
