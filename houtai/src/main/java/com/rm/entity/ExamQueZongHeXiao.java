package com.rm.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rm.util.StringUtil;

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

	@Column(length=1200)
    private String examans; 
    
    
    @Column(length=1200)
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

	public ExamQueZongHeXiao(String examque, String examans, String examanal, ExamQueZongHeDa examQueZongHeDa) {
		super();
		this.examque = examque;
		this.examans = examans;
		this.examanal = examanal;
		this.examQueZongHeDa = examQueZongHeDa;
	}

	public ExamQueZongHeXiao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamQueZongHeXiao(Map<Integer,String> examque, Map<Integer,String> examans, Map<Integer,String> examanal, ExamQueZongHeDa examQueZongHeDa) {
		super();
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(timu));
		list.add(daan);
		list.add(jiexi);
		this.examque = StringUtil.getMapString(examque,list);
		this.examans = StringUtil.getMapString(examans,list);
		this.examanal = StringUtil.getMapString(examanal,list);
		this.examQueZongHeDa = examQueZongHeDa;
	}
	@Transient
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	//private String zsd = "【知识点】";
	@Transient
	private String daan = "【答案】";	
	@Transient
	private String jiexi = "【解析】";
	
}
