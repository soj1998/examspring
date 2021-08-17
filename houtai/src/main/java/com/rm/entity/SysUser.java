package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_sysuser")
public class SysUser {
 
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private int uid;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public SysUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SysUser(int uid) {
		super();
		this.uid = uid;
	}
	
	
	
    
    
   
     
}
