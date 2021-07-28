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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

	
	@ManyToOne(targetEntity = ExamZsd.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "examquezsdid",referencedColumnName = "id")
  	private ExamZsd examZsd;
	
    public ExamZsd getExamZsd() {
		return examZsd;
	}

	public void setExamZsd(ExamZsd examZsd) {
		this.examZsd = examZsd;
	}


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

	
	@Column
	private int biaotiid; 

	public int getBiaotiid() {
		return biaotiid;
	}

	public void setBiaotiid(int biaotiid) {
		this.biaotiid = biaotiid;
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
    
    //级别默认为1 为0的则是综合题的小题 sjid去找综合题的大题
    @Column(columnDefinition="int default 1")
    private int jibie;
    @Column(columnDefinition="int default -1")
	private int sjid;
    
	public int getJibie() {
		return jibie;
	}

	public void setJibie(int jibie) {
		this.jibie = jibie;
	}

	public int getSjid() {
		return sjid;
	}

	public void setSjid(int sjid) {
		this.sjid = sjid;
	}

	public ExamQue(Integer szid, String examque, String yxbz, String examans, String examtype,
			String examanal,int biaotiid) {
		super();
		this.szid = szid;
		this.que = examque;
		this.yxbz = yxbz;
		this.ans = examans;
		this.examtype = examtype;
		this.jiexi = examanal;
		this.biaotiid = biaotiid;
	}
	
	@Transient
	private String[] xitishuzifenzufuhao = StringUtil.getXiTiShuZiFenZuFuHao();
	@Transient
	private int tihuantouweishu = StringUtil.getXiTiTiHuanTouWeiShu();
	
	public ExamQue(AtcSjk fid,Integer szid, ExamZsd examZsd, int biaotiid,Map<Integer,String> examque, String yxbz, Map<Integer,String> examans,
			Map<Integer,String> examanal) {
		super();
		this.atcSjk = fid;
		this.szid = szid;
		this.examZsd = examZsd;
		this.biaotiid = biaotiid;
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(StringUtil.getXiTiLeiXingZw()));
		String[] timufenge = StringUtil.getXiTiShuZiFenZu(StringUtil.getXiTiShuZiFenZuGeShu(), xitishuzifenzufuhao);
		list.addAll(Arrays.asList(timufenge));
		this.que = StringUtil.getMapStringTiHuanTou(examque,list,tihuantouweishu);
		this.yxbz = yxbz;
		this.ans = examans != null ? StringUtil.getMapString(examans,StringUtil.getXiTiDaan()) : null;
		this.setWentiLeiXing(examque);
		this.jiexi = StringUtil.getMapString(examanal,StringUtil.getXiTiJieXi());
	}
	
	public ExamQue(AtcSjk fid,Integer szid, ExamZsd examZsd, int biaotiid,JSONArray examque, String yxbz, JSONArray examans,
			JSONArray examanal) {
		super();
		this.atcSjk = fid;
		this.szid = szid;
		this.examZsd = examZsd;
		this.biaotiid = biaotiid;
		List<String> list=new ArrayList<String>();
		list.addAll(Arrays.asList(StringUtil.getXiTiLeiXingZw()));
		String[] timufenge = StringUtil.getXiTiShuZiFenZu(StringUtil.getXiTiShuZiFenZuGeShu(), xitishuzifenzufuhao);
		list.addAll(Arrays.asList(timufenge));
		this.que = StringUtil.getJSONArrayString(examque,list,tihuantouweishu);
		this.yxbz = yxbz;
		this.ans = examans != null ? StringUtil.getJSONArrayString(examans,StringUtil.getXiTiDaan()) : null;
		this.setWentiLeiXing(examque);
		this.jiexi = StringUtil.getJSONArrayString(examanal,StringUtil.getXiTiJieXi());
	}
	@Transient
	private String zsd = "【知识点】";

	
	public String getJiexi() {
		return jiexi;
	}

	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}

	private void setWentiLeiXing(Map<Integer,String> map) {
		String rs = "weizhi";
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
            //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            String a = entry.getValue();
            this.setExamtype(StringUtil.transExamXiTiLeiXing(a));
			return;
        }		
		this.setExamtype(rs);
	}	
	private void setWentiLeiXing(JSONArray map) {
		String rs = "weizhi";
		for (Object entry : map) {
			JSONObject one = (JSONObject) entry;
            String a = one.getString("neirong");
            this.setExamtype(StringUtil.transExamXiTiLeiXing(a));
			return;
        }		
		this.setExamtype(rs);
	}
	
	public ExamQue() {
		super();
	}

	
    
}
