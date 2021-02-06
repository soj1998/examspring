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
@Table(name="t_atcsjk")
public class AtcSjk implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id; 	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	
	
	@Column
    private Date lrsj;
		
	
	public Date getLrsj() {
		return lrsj;
	}

	public void setLrsj(Date lrsj) {
		this.lrsj = lrsj;
	}



	@Column
    private int wzlxid;
     
    @Column
    private int szid;
     
    public int getWzlxid() {
		return wzlxid;
	}

	public void setWzlxid(int wzlxid) {
		this.wzlxid = wzlxid;
	}

	public int getSzid() {
		return szid;
	}

	public void setSzid(int szid) {
		this.szid = szid;
	}



	@Column(length=10)
    private String version;
    
    
    @Column(length=200)
    private String fileweizhi;
     
    
    public String getFileweizhi() {
		return fileweizhi;
	}

	public void setFileweizhi(String fileweizhi) {
		this.fileweizhi = fileweizhi;
	}

	@Column(length=1)
    private String yxbz;
	
	

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}


	@Column(length=5000)
    private String wzjiagou;

	@Column(length=1000)
    private String wzlaiyuan;



	public String getWzlaiyuan() {
		return wzlaiyuan;
	}

	public void setWzlaiyuan(String wzlaiyuan) {
		this.wzlaiyuan = wzlaiyuan;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWzjiagou() {
		return wzjiagou;
	}

	public void setWzjiagou(String wzjiagou) {
		this.wzjiagou = wzjiagou;
	}
	
	

	

	
    
}
