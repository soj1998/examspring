package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_queans")
public class QueandAns {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column(length=400)
    private String question;
     
    @Column(length=400)
    private String choices;
    
    @Column(length=200)
    private String answers;
    
    @Column(length=500)
    private String analyse;
    
  	public String getZhongYaoXing() {
		return zhongYaoXing;
	}
	public void setZhongYaoXing(String zhongYaoXing) {
		this.zhongYaoXing = zhongYaoXing;
	}
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getChoices() {
		return choices;
	}
	public void setChoices(String choices) {
		this.choices = choices;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getAnalyse() {
		return analyse;
	}
	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
    
	private Integer xueKeId;
    
    public Integer getXueKeId() {
		return xueKeId;
	}
	public void setXueKeId(Integer xueKeId) {
		this.xueKeId = xueKeId;
	}

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
     
}
