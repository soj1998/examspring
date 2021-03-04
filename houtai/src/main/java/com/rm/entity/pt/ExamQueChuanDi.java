package com.rm.entity.pt;


import java.util.List;



public class ExamQueChuanDi {
 
	
    

		

	
    private String que;
    
   
	

 
    

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

	

    
	
    private String ans; 
    
	
    public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	
    private String examtype; 
    
    
    private String jiexi;
    
   
	
	public String getJiexi() {
		return jiexi;
	}

	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}

	private List<String> xuanxiang;

	public List<String> getXuanxiang() {
		return xuanxiang;
	}

	public void setXuanxiang(List<String> xuanxiang) {
		this.xuanxiang = xuanxiang;
	}
    
}
