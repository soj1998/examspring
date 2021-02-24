package com.rm.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="t_examquezsd")
public class ExamZsd implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 	

	private int jibie;
	
	@Column(length=50)
	private String neirong;
	
	private Long sjid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getJibie() {
		return jibie;
	}

	public void setJibie(int jibie) {
		this.jibie = jibie;
	}

	public String getNeirong() {
		return neirong;
	}

	public void setNeirong(String neirong) {
		this.neirong = neirong;
	}

	public Long getSjid() {
		return sjid;
	}

	public void setSjid(Long sjid) {
		this.sjid = sjid;
	}
	
	

	

	
    
}
