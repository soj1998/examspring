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
@Table(name="t_examchoizh")
public class ExamChoiZongHe {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    @Column(length=200)
    private String xuanxiang;
     
    @ManyToOne(targetEntity = ExamQueZongHeXiao.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "examquezhchoiid",referencedColumnName = "id")
  	private ExamQueZongHeXiao examQueZongHeXiao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getXuanxiang() {
		return xuanxiang;
	}

	public void setXuanxiang(String xuanxiang) {
		this.xuanxiang = xuanxiang;
	}

	public ExamQueZongHeXiao getExamQueZongHeXiao() {
		return examQueZongHeXiao;
	}

	public void setExamQueZongHeXiao(ExamQueZongHeXiao examQueZongHeXiao) {
		this.examQueZongHeXiao = examQueZongHeXiao;
	}

	
     
     
}
