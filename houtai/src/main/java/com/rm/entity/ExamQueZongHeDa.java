package com.rm.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
@Table(name="t_examquezhda")
public class ExamQueZongHeDa {
 
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

	
     
    @Column(length=600)
    private String examque;
    
    @Column(length=1)
    private String yxbz;
	
    @ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "atcexamquezonghedaid",referencedColumnName = "id")
  	private AtcSjk atcSjk;
	
    @Column
    private Date lrsj;
		
	
	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}
	
	@ManyToOne(targetEntity = ExamZsd.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "examquezhdazsdid",referencedColumnName = "id")
  	private ExamZsd examZsd;
	
	public ExamZsd getExamZsd() {
		return examZsd;
	}

	public void setExamZsd(ExamZsd examZsd) {
		this.examZsd = examZsd;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
 
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
	
	public void setExamque(Map<Integer,String> examque) {
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(timu));
		list.add(zsd_1);
		this.examque = StringUtil.getMapString(examque,list);
	}

	

	public ExamQueZongHeDa() {
		super();
	}

	@Transient
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	@Transient
	private String zsd_1 = "【知识点】";
	//private String daan = "【答案】";	
	//private String jiexi = "【解析】";
	public ExamQueZongHeDa(AtcSjk fid,Integer szid, ExamZsd examZsd1, Map<Integer,String> examque, String yxbz, Date lrsj) {
		super();
		this.atcSjk = fid;
		this.szid = szid;
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(timu));
		list.add(zsd_1);
		this.examZsd = examZsd1;
		this.examque = StringUtil.getMapString(examque,list);
		this.yxbz = yxbz;
		this.lrsj = lrsj;
	}


    
    
}
