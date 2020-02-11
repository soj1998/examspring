package com.rm.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_userlog")
public class UserLogs {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    private Integer userId;
    
    private Integer login;
    
    private Double useTime;
    
    private Date lastLogin;
    
    private Integer lastQueandAnsId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public Double getUseTime() {
		return useTime;
	}

	public void setUseTime(Double useTime) {
		this.useTime = useTime;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getLastQueandAnsId() {
		return lastQueandAnsId;
	}

	public void setLastQueandAnsId(Integer lastQueandAnsId) {
		this.lastQueandAnsId = lastQueandAnsId;
	}
    
    
   
     
}
