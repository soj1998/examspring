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
            	//System.out.println("here" + item.getId() + "," + parentId);
                if(item.getId() == parentId){  //找到父节点
                	//System.out.println("jinlaile");
                	TreeNode newNode = new TreeNode(identifying++, data);
                    item.nodes.add(newNode); //节点添加
                    break;
                }else {
                    add(parentId,data,item.nodes);
                }
            }
            //System.out.println("1  " + list.size());
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
            System.out.println(item.getId() + "," + item.getData());
            if(item.nodes.size() == 0){
                continue;
            }else {
                list(item.nodes);
            }
            System.out.println("\t");
        }
    }   
    public List<TreeNode> returnList(){
    	return this.getRoot().nodes;
    }
    
    /* 插入一个child节点到当前节点中 */  
    public void addChildNode(TreeNode treeNode) {  
        this.add(treeNode.getId(),treeNode.getData());  
    }  
    /**
    public void initChildList() {  
        if (childList == null)  
            childList = new ArrayList<TreeNode>();  
    }  
    
  
    public boolean isValidTree() {  
        return true;  
    }  
    
    // 返回当前节点的父辈节点集合   
    public List<TreeNode> getElders() {  
        List<TreeNode> elderList = new ArrayList<TreeNode>();  
        TreeNode parentNode = this.getParentNode(); //参考代码就是返回parentid 
        if (parentNode == null) {  
            return elderList;  
        } else {  
            elderList.add(parentNode);  
            elderList.addAll(parentNode.getElders());  
            return elderList;  
        }  
    }  
  
    // 返回当前节点的晚辈集合   
    public List<TreeNode> getJuniors() {  
        List<TreeNode> juniorList = new ArrayList<TreeNode>();  
        List<TreeNode> childList = this.getChildList();  
        if (childList == null) {  
            return juniorList;  
        } else {  
            int childNumber = childList.size();  
            for (int i = 0; i < childNumber; i++) {  
                TreeNode junior = childList.get(i);  
                juniorList.add(junior);  
                juniorList.addAll(junior.getJuniors());  
            }  
            return juniorList;  
        }  
    }  
  
    // 返回当前节点的孩子集合   
    public List<TreeNode> getChildList() {  
        return childList;  
    }  
  
    // 删除节点和它下面的晚辈   
    public void deleteNode() {  
        TreeNode parentNode = this.getParentNode();  
        int id = this.getSelfId();  
  
        if (parentNode != null) {  
            parentNode.deleteChildNode(id);  
        }  
    }  
  
    // 删除当前节点的某个子节点   
    public void deleteChildNode(int childId) {  
        List<TreeNode> childList = this.getChildList();  
        int childNumber = childList.size();  
        for (int i = 0; i < childNumber; i++) {  
            TreeNode child = childList.get(i);  
            if (child.getSelfId() == childId) {  
                childList.remove(i);  
                return;  
            }  
        }  
    }  
  
    // 动态的插入一个新的节点到当前树中  
    public boolean insertJuniorNode(TreeNode treeNode) {  
        int juniorParentId = treeNode.getParentId();  
        if (this.parentId == juniorParentId) {  
            addChildNode(treeNode);  
            return true;  
        } else {  
            List<TreeNode> childList = this.getChildList();  
            int childNumber = childList.size();  
            boolean insertFlag;  
  
            for (int i = 0; i < childNumber; i++) {  
                TreeNode childNode = childList.get(i);  
                insertFlag = childNode.insertJuniorNode(treeNode);  
                if (insertFlag == true)  
                    return true;  
            }  
            return false;  
        }  
    }  
  
    // 找到一颗树中某个节点 
    public TreeNode findTreeNodeById(int id) {  
        if (this.selfId == id)  
            return this;  
        if (childList.isEmpty() || childList == null) {  
            return null;  
        } else {  
            int childNumber = childList.size();  
            for (int i = 0; i < childNumber; i++) {  
                TreeNode child = childList.get(i);  
                TreeNode resultNode = child.findTreeNodeById(id);  
                if (resultNode != null) {  
                    return resultNode;  
                }  
            }  
            return null;  
        }  
    }  
    */
}
