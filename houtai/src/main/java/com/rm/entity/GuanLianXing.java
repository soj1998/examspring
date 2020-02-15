package com.rm.entity;

import java.util.Date;

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
    
    private boolean yxbz;
    
    @Column(length=100)
    private String publicOrPrivate;
    
    private Date gongKaiYxqq;
    
    private Date gongKaiYxqz;
    
    public boolean isYxbz() {
		return yxbz;
	}
	public void setYxbz(boolean yxbz) {
		this.yxbz = yxbz;
	}
	public String getPublicOrPrivate() {
		return publicOrPrivate;
	}
	public void setPublicOrPrivate(String publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}
	public Date getGongKaiYxqq() {
		return gongKaiYxqq;
	}
	public void setGongKaiYxqq(Date gongKaiYxqq) {
		this.gongKaiYxqq = gongKaiYxqq;
	}
	public Date getGongKaiYxqz() {
		return gongKaiYxqz;
	}
	public void setGongKaiYxqz(Date gongKaiYxqz) {
		this.gongKaiYxqz = gongKaiYxqz;
	}
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
    
	private Date xgsj;

	public Date getXgsj() {
		return xgsj;
	}
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	} 
     
}
