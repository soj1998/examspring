package com.rm.entity;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	private int Id;    //节点Id
    private String data; //节点数据
    //private int HangShu; //章节目录 重要的是行数 id是自增的无法对应 

	public List<TreeNode> nodes = new ArrayList<TreeNode>(); //多个子节点，利用List实现
    public TreeNode(int Id){
        this.Id = Id;
    }
    public TreeNode(int Id,String data){
        this.Id = Id;
        this.data = data;
    }
    
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    /*
    public TreeNode(int Id, int HangShu, String data){
        this.Id = Id;
        this.data = data;
        this.HangShu = HangShu;
    }
    public int getHangShu() {
		return HangShu;
	}
	public void setHangShu(int hangShu) {
		HangShu = hangShu;
	}
	*/
}
