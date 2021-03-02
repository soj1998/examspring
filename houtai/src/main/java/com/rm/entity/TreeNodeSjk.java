package com.rm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="t_treenodesjk")
public class TreeNodeSjk implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; 
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getRootid() {
		return rootid;
	}

	public void setRootid(Integer rootid) {
		this.rootid = rootid;
	}

	public Integer getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(Integer biaoti) {
		this.biaoti = biaoti;
	}

	public String getBtneirong() {
		return btneirong;
	}

	public void setBtneirong(String btneirong) {
		this.btneirong = btneirong;
	}	
	
	@Column
    private int szid;

	public int getSzid() {
		return szid;
	}

	public void setSzid(int szid) {
		this.szid = szid;
	}
	
	@Column(length=1)
    private String yxbz;
	
	

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	@Override
	public String toString() {
		return "TreeNodeSjk [id=" + id + ", rootid=" + rootid + ", parentid=" + parentid + ", biaoti=" + biaoti
				+ ", hangshu=" + hangshu + ", btneirong=" + btneirong + ", atcSjk=" + atcSjk + "]";
	}


	@Column
    private Integer rootid;
    
    @Column
    private Integer parentid;
    
    //@Column(columnDefinition = "boolean default 0")
    //private boolean firstnode;   
	

	@Column
    private Integer biaoti;
    
	@Column
    private Integer hangshu;
	
    public Integer getHangshu() {
		return hangshu;
	}

	public void setHangshu(Integer hangshu) {
		this.hangshu = hangshu;
	}

	@Column(length=100)
    private String btneirong;
    
    //@Column(length=20000)
    //private String qbneirong;
    
    

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	@ManyToOne(targetEntity = AtcSjk.class,fetch = FetchType.EAGER)
  	@JoinColumn(name = "atctreenodesjkid",referencedColumnName = "id")
  	private AtcSjk atcSjk;
	//配置老师和学生一对多
    /**
     *                     
     *  1. 注解配置多表关系
     *  2. 声明关系
     *  3. 配置外键,或者中间表
     *  OneToMany配置一对多
     *      targetEntity设置对应的实体类的类型
     *  JoinColumn 配置外键
     *      name:外键的名称,
     *      referencedColumnName参照的主表的主键字段名称
     
    @OneToMany(targetEntity = TnsQbNeiRong.class)
    @JoinColumn(name = "neirong_atc_id",referencedColumnName = "tnssjk_id")
    private Set<TnsQbNeiRong> qbneirong = new HashSet<>();
	
	
	@OneToMany(mappedBy="treeNodeSjk",fetch = FetchType.EAGER)
	private Set<TnsQbNeiRong> qbneirong;

	public Set<TnsQbNeiRong> getQbneirong() {
		return qbneirong;
	}

	public void setQbneirong(Set<TnsQbNeiRong> qbneirong) {
		this.qbneirong = qbneirong;
	}
     */



	public TreeNodeSjk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TreeNodeSjk(Integer szid1, String yxbz1, Integer rootid, Integer parentid, Integer biaoti, Integer hangshu, String btneirong,
			AtcSjk atcSjk) {
		super();
		this.szid = szid1;
		this.yxbz = yxbz1;
		this.rootid = rootid;
		this.parentid = parentid;
		this.biaoti = biaoti;
		this.hangshu = hangshu;
		this.btneirong = btneirong;
		this.atcSjk = atcSjk;
	}

	public AtcSjk getAtcSjk() {
		return atcSjk;
	}

	public void setAtcSjk(AtcSjk atcSjk) {
		this.atcSjk = atcSjk;
	}

	

	


	
    
}
