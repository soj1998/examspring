package com.rm.entity.lieju;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_wzlx")
public class Wzlx {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column(length=100)
    private String wzlxmc;
     
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
	public String getWzlxmc() {
		return wzlxmc;
	}
	public void setWzlxmc(String wzlxmc) {
		this.wzlxmc = wzlxmc;
	}
    
	public Wzlx(int id,String wzlxmc) {
		super();
		if(id!= -1) {
			this.id = id;
		}
		this.wzlxmc = wzlxmc;
	}
	public Wzlx() {
		super();
	} 
     
}
