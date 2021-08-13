package com.rm.entity;

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

	public Integer getId() {
		return 1;
	}

	
   
    
   
     
}
