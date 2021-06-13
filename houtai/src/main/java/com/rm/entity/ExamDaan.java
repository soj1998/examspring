package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_examdaansjk")
public class ExamDaan {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    @Column(length=1200)
    private String daan;
     
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="examqueid")
	private Integer examquedaansjkid;
    
	

	public Integer getExamquedaansjkid() {
		return examquedaansjkid;
	}

	public void setExamquedaansjkid(Integer examquedaansjkid) {
		this.examquedaansjkid = examquedaansjkid;
	}

	public String getDaan() {
		return daan;
	}

	public void setDaan(String daan) {
		this.daan = daan;
	}
	
	public ExamDaan(String daan, Integer examquedaansjkid) {
		super();
		this.daan = daan;
		this.examquedaansjkid = examquedaansjkid;
	}

	public ExamDaan() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ExamChoi [id=" + id + ", daan=" + daan + ", examQue=" + examquedaansjkid + "]";
	}
     
     
}
