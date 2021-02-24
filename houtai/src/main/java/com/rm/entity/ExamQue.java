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
    private String que;
    
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

	public String getQue() {
		return que;
	}

	public void setQue(String que) {
		this.que = que;
	}

	

	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	

    
	@Column(length=100)
    private String ans; 
    
	
    public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	@Column(length=50)
    private String examtype; 
    
    @Column(length=1200)
    private String jiexi;
    
   
    
	public ExamQue(Integer szid, String zzd, String examque, String yxbz, String examans, String examtype,
			String examanal) {
		super();
		this.szid = szid;
		this.zzd = zzd;
		this.que = examque;
		this.yxbz = yxbz;
		this.ans = examans;
		this.examtype = examtype;
		this.jiexi = examanal;
	}

	public ExamQue(AtcSjk fid,Integer szid, Map<Integer,String> zzd, Map<Integer,String> examque, String yxbz,Date lrsj, Map<Integer,String> examans,
			Map<Integer,String> examanal) {
		super();
		this.atcSjk = fid;
		this.szid = szid;
		this.zzd = StringUtil.getMapString(zzd,zsd);
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(StringUtil.getXiTiLeiXingZw()));
		this.que = StringUtil.getMapString(examque,list);
		this.yxbz = yxbz;
		this.lrsj = lrsj;
		this.ans = StringUtil.getMapString(examans,daan);
		this.setWentiLeiXing(examque);
		this.jiexi = StringUtil.getMapString(examanal,jiexi2);
	}
	@Transient
	private String zsd = "【知识点】";
	@Transient
	private String daan = "【答案】";
	@Transient
	private String jiexi2 = "【解析】";
	
	public String getJiexi() {
		return jiexi;
	}

	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}

	private void setWentiLeiXing(Map<Integer,String> map) {
		String rs = "weizhi";
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            String a = entry.getValue();
            this.setExamtype(StringUtil.transExamXiTiLeiXing(a));
			return;
        }		
		this.setExamtype(rs);
	}	
	
	public ExamQue() {
		super();
	}

	
    
}
