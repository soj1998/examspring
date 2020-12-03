package com.rm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_myatc1")
public class TreeNodeSjk {
 
    @Id
    @GeneratedValue
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

	public String getQbneirong() {
		return qbneirong;
	}

	public void setQbneirong(String qbneirong) {
		this.qbneirong = qbneirong;
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
    private Integer biaoti;
    
    @Column(length=100)
    private String btneirong;
    
    @Column(length=20000)
    private String qbneirong;
    @Column
    private Date lrsj;
    
    public String getAtclx() {
        return atclx;
    }

	@Override
	public String toString() {
		return "TreeNodeSjk [id=" + id + ", atclx=" + atclx + ", sz=" + sz + ", version=" + version + ", rootid="
				+ rootid + ", biaoti=" + biaoti + ", btneirong=" + btneirong + ", qbneirong=" + qbneirong + ", lrsj="
				+ lrsj + "]";
	}
    
}
