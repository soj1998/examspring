package com.rm.czentity;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.TnsQbNeiRong;
import com.rm.entity.TreeNode;
import com.rm.entity.TreeNodeSjk;


public class CzTreeNode {
	
    private TreeNode root = new TreeNode(0);    //树的根节点
    public int identifying = 1;  //用于记录树上的节点
    public int index = 0;		//用于遍历树的指针路过节点的个数
    //获取根节点
    public TreeNode getRoot(){
        return this.root;
    }
    public int getRight(int parentId,List<TreeNode> list) {
    	int rightId = 0;
    	for(TreeNode item:list){    		
        	if(item.getData().getShort("biaoti") == parentId){
        		rightId = item.getId();
        	}        	
        }    	
    	return rightId;
    }
    //添加方法重载
    public void addright(JSONObject data){
        this.addright(data.getIntValue("biaoti") - 1,data,this.getRoot().nodes);
    }
    //添加方法重载
    public void addright(int parentId,JSONObject data){
        this.addright(parentId,data,this.getRoot().nodes);
    }
    //先向树的右节点添加
    public void addright(int parentId,JSONObject data,List<TreeNode> list){
        if(parentId==0){
        	TreeNode newNode = new TreeNode(identifying++,parentId,data);
            this.root.nodes.add(newNode);
        }else {
            if(list.size()==0){
                return;
            }          
            for(TreeNode item:list){
            	//int rightId = getRight(parentId,list);
            	int rightId = 0;
            	for(TreeNode item1:list){
            		if(item1.getData().getIntValue("biaoti") == parentId){
                		rightId = item1.getId();                		
                	}
                }            	
            	if(item.getId() == rightId){            		
                	TreeNode newNode = new TreeNode(identifying++, rightId, data);
                    item.nodes.add(newNode);
                    return;
                }else {
                	addright(parentId,data,item.nodes);
                }
            }
        }        
    }
    //遍历方法的重载
    public List<TreeNode> getTree(){
        return this.getRoot().nodes;
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
            System.out.println(item.getId() + "," + item.getData().toJSONString());
            if(item.nodes.size() == 0){
                continue;
            }else {
                list(item.nodes);
            }
            System.out.println();
        }
    }
    public TreeNode minRightNode(List<TreeNode> list,int dqId){
    	index++;  //遍历次数，用于退出循环
        if(index == identifying){
            return null;
        }
        for(TreeNode item:this.getRoot().nodes){        	
            if(item.nodes.size() == 0){
                continue;
            } else if(item.getId() < dqId){
            	minRightNode(item.nodes,dqId);
            } else if(item.getId() == dqId){
            	return item;
            }           
        }
        return null;
    }  
    public List<TreeNode> returnList(){
    	return this.getRoot().nodes;
    }
    
    //遍历TreeNode并插入
    //遍历方法的重载
    public void listAndInsSql(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao,String wzlx,String wzversion,String sz){
        this.listAndInsSql(tnsneirongDao,tnDao,this.getRoot().nodes,wzlx,wzversion,sz);
    }
    //循环Tree
    public void listAndInsSql(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao, List<TreeNode> list,String wzlx,String wzversion,String sz){
        index++;  //遍历次数，用于退出循环
        if(index == identifying){
            return;
        }        
        for(TreeNode item:list){
        	TreeNodeSjk tn = new TreeNodeSjk();
        	tn.setAtclx(wzlx);
        	tn.setSz(sz);
            tn.setVersion(wzversion);			
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			//tn.setQbneirong(item.getData().getJSONArray("qbneirong").toString());
			JSONArray qbnr = item.getData().getJSONArray("qbneirong");
			Set<TnsQbNeiRong> tnsqbnr = new HashSet<TnsQbNeiRong>();
			qbnr.forEach(e ->{
				JSONObject js = (JSONObject)e;
				tnsqbnr.add(new TnsQbNeiRong(js.getString("neirong"),js.getInteger("hangshu"),tn));
			});
			//tn.setQbneirong(tnsqbnr);
			tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
            tn.setRootid(item.getId());   
            tn.setParentid(item.getParentId());
            //tn.setId(-1);
            //System.out.println(tn.toString());
            tnDao.save(tn);
            tnsneirongDao.saveAll(tnsqbnr);
            //System.out.println(item.getId() + "," + item.getData().toJSONString());
            if(item.nodes.size() == 0){
                continue;
            }else {
            	listAndInsSql(tnsneirongDao,tnDao,item.nodes,wzlx,wzversion,sz);
            }
            System.out.println();
        }
    }
    /**
    //添加方法重载
    public void addleft(int parentId,String data){
        this.addleft(parentId,data,this.getRoot().nodes);
    }
    //先向树的左节点添加
    public void addleft(int parentId,String data,List<TreeNode> list){
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
                	addleft(parentId,data,item.nodes);
                }
            }
            //System.out.println("1  " + list.size());
        }        
    }
    //插入一个child节点到当前节点中   
    public void addChildNode(TreeNode treeNode) {  
        this.addleft(treeNode.getId(),treeNode.getData());  
    } 
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
    找到树的最左边的节点
    public:
    void findBottomLeftValue(TreeNode* root, int& maxDepth, int& leftVal, int depth) {
        if (root == NULL) {
            return;
        }
        //Go to the left and right of each node 
        findBottomLeftValue(root->left, maxDepth, leftVal, depth+1);
        findBottomLeftValue(root->right, maxDepth, leftVal, depth+1);

        //Update leftVal and maxDepth
        if (depth > maxDepth) {
            maxDepth = depth;
            leftVal = root->val;
        }
    }

    //Entry function
    int findBottomLeftValue(TreeNode* root) {
        int maxDepth = 0;
        //Initialize leftVal with root's value to cover the edge case with single node
        int leftVal = root->val;
        findBottomLeftValue(root, maxDepth, leftVal, 0);
        return leftVal;
    }
    */
}
