package com.rm.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tnsqbneirong")
public class TnsQbNeiRong {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     
    @Column(length=10000)
    private String qbneirong;
    
    @Column
    private Integer hangshu;
    
    public Integer getHangshu() {
		return hangshu;
	}
	public void setHangshu(Integer hangshu) {
		this.hangshu = hangshu;
	}
	/**
     * 	1. 	多个学生对应一个老师
     *	2. 	注解形式配置多对一
     *  3.	配置表关系
     *  4.	配置外键
     
    @ManyToOne(targetEntity = TreeNodeSjk.class)
    @JoinColumn(name = "neirong_atc_id",referencedColumnName = "tnssjk_id")
    private TreeNodeSjk treenodesjk;
    */
    
    //多个工人---1个部门
  	@ManyToOne(fetch = FetchType.EAGER)
  	@JoinColumn(name = "treeNodeSjk_id",referencedColumnName = "id")
  	private TreeNodeSjk treeNodeSjk;

    
    public TreeNodeSjk getTreeNodeSjk() {
		return treeNodeSjk;
	}
	public void setTreeNodeSjk(TreeNodeSjk treeNodeSjk) {
		this.treeNodeSjk = treeNodeSjk;
	}
	public String getQbneirong() {
		return qbneirong;
	}
	public void setQbneirong(String qbneirong) {
		this.qbneirong = qbneirong;
	}
	
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }    
	public TnsQbNeiRong(String qbneirong, int hangshu,TreeNodeSjk treeNodeSjk) {
		super();
		this.qbneirong = qbneirong;
		this.hangshu = hangshu;
		this.treeNodeSjk = treeNodeSjk;
	}
	public TnsQbNeiRong() {
		super();
	}
    
}
