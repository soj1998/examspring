package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_examquesjk")
public class ExamQue {
 
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

	@Column(length=100)
    private String zzd;
     
    @Column(length=600)
    private String examque;
     
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZzd() {
		return zzd;
	}

	public void setZzd(String zzd) {
		this.zzd = zzd;
	}

	public String getExamque() {
		return examque;
	}

	public void setExamque(String examque) {
		this.examque = examque;
	}

	public String getExamans() {
		return examans;
	}

	public void setExamans(String examans) {
		this.examans = examans;
	}

	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	public String getExamanal() {
		return examanal;
	}

	public void setExamanal(String examanal) {
		this.examanal = examanal;
	}

	@Column(length=300)
    private String examans; 
    
    @Column(length=50)
    private String examtype; 
    
    @Column(length=600)
    private String examanal;
    
    
}
