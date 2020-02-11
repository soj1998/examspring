package com.rm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class ExamUser {
 
    @Id
    @GeneratedValue
    private Integer id;
     
    @Column(length=40)
    private String userName;
     
    @Column(length=40)
    private String userPassword;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
   
     
}
