package com.rm.entity;

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
    
    private Integer guanlianId;
    
    public Integer getGuanlianId() {
		return guanlianId;
	}
	public void setGuanlianId(Integer guanlianId) {
		this.guanlianId = guanlianId;
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
    
     
     
}
