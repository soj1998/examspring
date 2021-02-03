package com.rm.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
     
    @Column(length=1200)
    private String examque;
    
    @Column(length=1)
    private String yxbz;
	
    @ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "atcexamqueid",referencedColumnName = "id")
  	private AtcSjk atcSjk;
	
    public AtcSjk getAtcSjk() {
		return atcSjk;
	}

	public void setAtcSjk(AtcSjk atcSjk) {
		this.atcSjk = atcSjk;
	}

	@Column
    private Date lrsj;
		
	
	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
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

	@Column(length=1200)
    private String examans; 
    
    @Column(length=50)
    private String examtype; 
    
    @Column(length=600)
    private String examanal;

	public ExamQue(Integer szid, String zzd, String examque, String yxbz, String examans, String examtype,
			String examanal) {
		super();
		this.szid = szid;
		this.zzd = zzd;
		this.examque = examque;
		this.yxbz = yxbz;
		this.examans = examans;
		this.examtype = examtype;
		this.examanal = examanal;
	}

	public ExamQue(AtcSjk fid,Integer szid, Map<Integer,String> zzd, Map<Integer,String> examque, String yxbz,Date lrsj, Map<Integer,String> examans,
			Map<Integer,String> examanal) {
		super();
		this.atcSjk = fid;
		this.szid = szid;
		this.zzd = getMapString(zzd);
		this.examque = getMapString(examque);
		this.yxbz = yxbz;
		this.lrsj = lrsj;
		this.examans = getMapString(examans);
		this.setWentiLeiXing(examque);
		this.examanal = getMapString(examanal);
	}
	@Transient
	private String[] timu = new String[] {"【单选题】","【多选题】","【计算题】","【综合题】","【判断题】"};
	@Transient
	private String[] timuleixing = new String[] {"danxuan","duoxuan","jisuan","zonghe","panduan"};
	@Transient
	private String zsd = "【知识点】";
	@Transient
	private String daan = "【答案】";
	@Transient
	private String jiexi = "【解析】";
	
	private void setWentiLeiXing(Map<Integer,String> map) {
		String rs = "weizhi";
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            String a = entry.getValue();
			for(int i = 0; i < timu.length; i++) {
				if (StringUtil.isNotEmpty(a) && a.indexOf(timu[i])>=0) {
					this.setExamtype(timuleixing[i]);
					return;
				}
			}
        }		
		this.setExamtype(rs);
	}
	
	private String getMapString(Map<Integer,String> map) {
		StringBuilder sb = new StringBuilder();		
		//这里将map.entrySet转换为List
        List<Map.Entry<Integer,String>> list = new ArrayList<Map.Entry<Integer,String>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            //升序排序
            public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,String> mapping:list){
        	String a = mapping.getValue();
        	for(String tm:this.timu) {
        		a = a.replaceAll(tm, "");
			}
        	a = a.replaceAll(this.zsd, "");
        	a = a.replaceAll(this.daan, "");
        	a = a.replaceAll(this.jiexi, "");
        	if (StringUtil.isNotEmpty(a)) {
        		sb = sb.append(a);
        	}
       } 
		return sb.toString();
	}
	
	public ExamQue() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
