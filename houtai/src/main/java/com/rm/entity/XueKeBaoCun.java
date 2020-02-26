package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_xueke")
public class XueKeBaoCun {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column(length=20)
    private String xueKe;
     
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
   
    @Column(length=30)
    private String erJiFenLei;

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
	 @Column(length=40)
	private String zhang;
	     
    @Column(length=40)
    private String jie;
}
