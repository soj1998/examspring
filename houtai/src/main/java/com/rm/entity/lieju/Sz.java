package com.rm.entity.lieju;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_sz")
public class Sz {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column(length=100)
    private String szmc;
     
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
	public String getSzmc() {
		return szmc;
	}
	public void setSzmc(String szmc) {
		this.szmc = szmc;
	}
    
}
