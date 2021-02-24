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
@Table(name="t_examans")
public class ExamAnsDa {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
	@Column
    private Integer szid;
	
    public Integer getSzid() {
		return szid;
	}

	public void setSzid(Integer szid) {
		this.szid = szid;
	}

	@Column
    private Integer examqueid;

	@Column(length=2400)
    private String ans;

	public Integer getExamqueid() {
		return examqueid;
	}

	public void setExamqueid(Integer examqueid) {
		this.examqueid = examqueid;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	} 
    
	@Column(length=2400)
    private String que;

	public String getQue() {
		return que;
	}

	public void setQue(String que) {
		this.que = que;
	}
	
	@Column(length=2400)
    private String jiexi;

	public String getJiexi() {
		return jiexi;
	}

	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}
	
	@Column(length=100)
    private String zzd;
     
        
    @Column(length=1)
    private String yxbz;	
    
	@ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "atcexamqueid",referencedColumnName = "id")
  	private AtcSjk atcSjk;
	
    public AtcSjk getAtcSjk() {
		return atcSjk;
	}

	public void setAtcSjk(AtcSjk atcSjk) {
		this.atcSjk = atcSjk;
	}

	@Column
    private Date lrsj;

	public String getZzd() {
		return zzd;
	}

	public void setZzd(String zzd) {
		this.zzd = zzd;
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
		
}
