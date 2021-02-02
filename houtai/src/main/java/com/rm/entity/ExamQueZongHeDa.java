package com.rm.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@Column(length=100)
    private String zzd;
     
    @Column(length=600)
    private String examque;
    
    @Column(length=1)
    private String yxbz;
	
	

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

	public String getZzd() {
		return zzd;
	}

	public void setZzd(String zzd) {
		this.zzd = zzd;
	}

	public void setZzd(Map<Integer,String> zsdd) {
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(timu));
		list.add(zsd_1);		
		this.zzd = StringUtil.getMapString(zsdd,list);
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

	public ExamQueZongHeDa(Integer szid, String zzd, String examque, String yxbz) {
		super();
		this.szid = szid;
		this.zzd = zzd;
		this.examque = examque;
		this.yxbz = yxbz;
	}

	public ExamQueZongHeDa() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Transient
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	@Transient
	private String zsd_1 = "【知识点】";
	//private String daan = "【答案】";	
	//private String jiexi = "【解析】";
	public ExamQueZongHeDa(Integer szid, Map<Integer,String> zsdd, Map<Integer,String> examque, String yxbz) {
		super();
		this.szid = szid;
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(timu));
		list.add(zsd_1);
		this.zzd = StringUtil.getMapString(zsdd,list);
		this.examque = StringUtil.getMapString(examque,list);
		this.yxbz = yxbz;
	}


    
    
}
