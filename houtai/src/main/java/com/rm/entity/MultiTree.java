package com.rm.entity;

import java.util.List;

public class MultiTree {
    private TreeNode root = new TreeNode(0);    //树的根节点
    public int identifying = 1;  //用于记录树上的节点
    public int index = 0;		//用于遍历树的指针路过节点的个数
    //获取根节点
    public TreeNode getRoot(){
        return this.root;
    }
    //添加方法重载
    public void add(int parentId,String data){
        this.add(parentId,data,this.getRoot().nodes);
    }
    //添加
    public void add(int parentId,String data,List<TreeNode> list){
        if(parentId==0){	//如果父节点Id为0
        	TreeNode newNode = new TreeNode(identifying++,data);
            this.root.nodes.add(newNode);
        }else {  //判空
            if(list.size()==0){
                return;
            }
            for(TreeNode item:list){
                if(item.getId() == parentId){  //找到父节点
                	TreeNode newNode = new TreeNode(identifying++, data);
                    item.nodes.add(newNode); //节点添加
                    break;
                }else {
                    add(parentId,data,item.nodes);
                }
            }
        }

    }
    //遍历方法的重载
    public void list(){
        this.list(this.getRoot().nodes);
    }
    //循环Tree
    public void list(List<TreeNode> list){
        index++;  //遍历次数，用于退出循环
        if(index == identifying){
            return;
        }
        for(TreeNode item:list){
            System.out.println(item.getData());
            if(item.nodes.size() == 0){
                continue;
            }else {
                list(item.nodes);
            }
            System.out.println("\t");
        }
    }

}
