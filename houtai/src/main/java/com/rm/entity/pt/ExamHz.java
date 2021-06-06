package com.rm.entity.pt;

import java.util.Date;

import com.rm.entity.ExamZsd;

public class ExamHz {
 
	
    private Integer id;
    
	
    private Integer szid;
	
    public Integer getSzid() {
		return szid;
	}

	public void setSzid(Integer szid) {
		this.szid = szid;
	}	

	
    private String ans;
	

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	} 
    
	
    private String que;

	public String getQue() {
		return que;
	}

	public void setQue(String que) {
		this.que = que;
	}
	
	
    private String jiexi;

	public String getJiexi() {
		return jiexi;
	}

	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}
        
    

	
    private Date lrsj;

	
	

	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	
    private String examtype; 
	
	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	public ExamHz() {
		super();
	}
	
	private ExamZsd examZsd;
	
	public ExamZsd getExamZsd() {
		return examZsd;
	}

	public void setExamZsd(ExamZsd examZsd) {
		this.examZsd = examZsd;
	}
	
	public ExamHz(Integer szid, ExamZsd zzd1, String examque, Date lrsj, String examans,
			String examanal,String examtype) {
		super();
		this.szid = szid;
		this.examZsd = zzd1;
		this.que = examque;
		this.lrsj = lrsj;
		this.ans = examans;
		this.examtype = examtype;
		this.jiexi = examanal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
