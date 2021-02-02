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
@Table(name="t_examquezhxiao")
public class ExamQueZongHeXiao {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
     
    @Column(length=600)
    private String examque;    
  
 
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	

	public String getExamanal() {
		return examanal;
	}

	public void setExamanal(String examanal) {
		this.examanal = examanal;
	}

	@Column(length=300)
    private String examans; 
    
    
    @Column(length=600)
    private String examanal;
    
    @ManyToOne(targetEntity = ExamQueZongHeDa.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "examquezongheda",referencedColumnName = "id")
  	private ExamQueZongHeDa examQueZongHeDa;


	public ExamQueZongHeDa getExamQueZongHeDa() {
		return examQueZongHeDa;
	}

	public void setExamQueZongHeDa(ExamQueZongHeDa examQueZongHeDa) {
		this.examQueZongHeDa = examQueZongHeDa;
	}
    
}
