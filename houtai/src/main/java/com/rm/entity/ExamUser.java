package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_examuser")
public class ExamUser {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column
    private int userid;
     
    @Column
    private int examid;
    
    @Column
    private int shuliang;
    
    @Column(length=1200)
    private String examque;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getExamid() {
		return examid;
	}

	public void setExamid(int examid) {
		this.examid = examid;
	}

	public int getShuliang() {
		return shuliang;
	}

	public void setShuliang(int shuliang) {
		this.shuliang = shuliang;
	}

	public String getExamque() {
		return examque;
	}

	public void setExamque(String examque) {
		this.examque = examque;
	}

	public ExamUser(int userid, int examid, int shuliang, String examque) {
		super();
		this.userid = userid;
		this.examid = examid;
		this.shuliang = shuliang;
		this.examque = examque;
	}

	public ExamUser() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
   
     
}
