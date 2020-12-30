package com.rm.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="treenodesjk")
public class TreeNodeSjk implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSz() {
		return sz;
	}

	public void setSz(String sz) {
		this.sz = sz;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
	
	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}

	public void setAtclx(String atclx) {
		this.atclx = atclx;
	}

	@Column(length=20)
    private String atclx;
     
    @Column(length=20)
    private String sz;
     
    @Column(length=10)
    private String version;
    
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
    
    @Column
    private Date lrsj;
    
    public String getAtclx() {
        return atclx;
    }

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

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
	@Override
	public String toString() {
		return "TreeNodeSjk [id=" + id + ", atclx=" + atclx + ", sz=" + sz + ", version=" + version + ", rootid="
				+ rootid + ", parentid=" + parentid + ", biaoti=" + biaoti + ", btneirong=" + btneirong + ", qbneirong="
			    + ", lrsj=" + lrsj + "]";
	}

	
    
}
