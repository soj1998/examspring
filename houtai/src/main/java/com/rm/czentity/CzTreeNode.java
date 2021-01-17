package com.rm.czentity;



import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rm.dao.TnsQbNeiRongDao;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.AtcSjk;
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
            for(int i = list.size() -1;i>=0;i--)
            //for(TreeNode item:list)
            {
            	int rightId = getRight(parentId,list);
            	/**int rightId = 0;
            	for(TreeNode item1:list){
            		if(item1.getData().getIntValue("biaoti") == parentId){
                		rightId = item1.getId();                		
                	}            		
                }**/            	
            	if(list.get(i).getId() == rightId){            		
                	TreeNode newNode = new TreeNode(identifying++, rightId, data);
                	if(data.getString("btneirong").equals("自来水")){
                		System.out.println("2.rightId"+ rightId+",biaoti"+list.get(i).getData().getString("btneirong")+","+list.get(i).getId()+"," + data.getString("btneirong"));
                	}
                	list.get(i).nodes.add(newNode);
                	return;
                } else {
                	addright(parentId,data,list.get(i).nodes);
                }
            }
        }        
    }
    
  //先向树的右节点添加
    public void addright2(int parentId,JSONObject data,List<TreeNode> list){
    	this.addTreeNodeByStack(parentId,data);       
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
        	if(item.getData().getString("btneirong").equals("自来水")){
        		System.out.println(item.getId() + "," + item.getParentId() + "," + item.getData().getString("btneirong"));
        	}
            if(item.nodes.size() == 0){
                continue;
            }else {
                list(item.nodes);
            }
            //System.out.println();
        }
    }
    //非递归 栈压入遍历
    public void displayTreeByStack() {
    	TreeNode root = this.getRoot();
		if (root == null)
			System.out.println("空树");
		else {
			Stack<TreeNode> allNode = new Stack<TreeNode>();// 临时存储用栈
			allNode.push(root);// 压入根节点
			while (!allNode.isEmpty()) {// 只有栈不为空
				TreeNode n = (TreeNode) allNode.pop();// 弹出元素
				System.out.println(n.getData());
				if (n.nodes != null && n.nodes.size()> 0) {// 再遍历压入子树
					for (TreeNode tn : n.nodes)
						allNode.push(tn);
				}
			}
		}
	}
    
  //非递归 栈压入遍历
    public void addTreeNodeByStack(int parentId,JSONObject data) {
    	TreeNode root = this.getRoot();
		if (root == null)
			System.out.println("空树");
		else {
			Stack<TreeNode> allNode = new Stack<TreeNode>();// 临时存储用栈
			allNode.push(root);// 压入根节点
			while (!allNode.isEmpty()) {// 只有栈不为空				
				if(parentId==0){
		        	TreeNode newNode = new TreeNode(identifying++,parentId,data);
		            this.root = newNode;
		        	//this.root.nodes.add(newNode);
		            return;
		        }else {
		        	TreeNode n = (TreeNode) allNode.peek();
		        	int rightId = parentId;
	            	/**for(TreeNode item1:n.nodes){
	            		if(item1.getData().getIntValue("biaoti") == parentId){
	                		rightId = item1.getId();                		
	                	}            		
	                }**/
	            	if(n.getData().getIntValue("biaoti") == parentId){
                		rightId = n.getId(); 
                		TreeNode newNode = new TreeNode(identifying++, rightId, data);
	                	n.nodes.add(newNode);
	                	return;
                	} 
	            	/**if(n.getId() == rightId){            		
	                	TreeNode newNode = new TreeNode(identifying++, rightId, data);
	                	n.nodes.add(newNode);
	                	return;
	                }**/
		        }				
				TreeNode n = (TreeNode) allNode.pop();// 弹出元素
				// System.out.println(n.getData());
				if (n.nodes != null && n.nodes.size()> 0) {// 再遍历压入子树
					for (TreeNode tn : n.nodes) //这个操作是把n.nodes全部压入，并把最后压入的当做顶
						allNode.push(tn);
				}
			}
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
    public void listAndInsSql(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao,AtcSjk fid){
        this.listAndInsSql(tnsneirongDao,tnDao,this.getRoot().nodes,fid);
    }
    //循环Tree
    public void listAndInsSql(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao, List<TreeNode> list,AtcSjk fid){
        index++;  //遍历次数，用于退出循环
        if(index == identifying){
            return;
        }        
        for(TreeNode item:list){
        	TreeNodeSjk tn = new TreeNodeSjk();        	
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			tn.setHangshu(item.getData().getInteger("hangshu"));
			JSONArray qbnr = item.getData().getJSONArray("qbneirong");
			Set<TnsQbNeiRong> tnsqbnr = new HashSet<TnsQbNeiRong>();
			qbnr.forEach(e ->{
				JSONObject js = (JSONObject)e;
				tnsqbnr.add(new TnsQbNeiRong(js.getString("neirong"),js.getInteger("hangshu"),tn));
			});
			tn.setRootid(item.getId());   
            tn.setParentid(item.getParentId());
            tn.setAtcSjk(fid);
            //tn.setId(-1);
            //System.out.println(tn.toString());
            tnDao.save(tn);
            tnsneirongDao.saveAll(tnsqbnr);
            //System.out.println(item.getId() + "," + item.getData().toJSONString());
            if(item.nodes.size() == 0){
                continue;
            }else {
            	listAndInsSql(tnsneirongDao,tnDao,item.nodes,fid);
            }
            //System.out.println();
        }
    }
    
    public void listAndInsSql2(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao,int wzlx,String wzversion,int sz,String fileweizhi){
        this.listAndInsSql2(tnsneirongDao,tnDao,this.getRoot().nodes,wzlx,wzversion,sz,fileweizhi);
    }
    //循环Tree
    public void listAndInsSql2(TnsQbNeiRongDao tnsneirongDao,TreeNodeSjkDao tnDao, List<TreeNode> list,int wzlx,String wzversion,int sz,String fileweizhi){
        index++;  //遍历次数，用于退出循环
        if(index == identifying){
            return;
        }        
        for(TreeNode item:list){
        	TreeNodeSjk tn = new TreeNodeSjk();
        	//tn.setVersion(wzversion);
        	//tn.setFileweizhi(fileweizhi);
        	//tn.setWzlxid(wzlx);
        	//tn.setSzid(sz);
        	//tn.setYxbz("Y");
			tn.setBiaoti(item.getData().getInteger("biaoti"));
			tn.setBtneirong(item.getData().getString("btneirong"));
			tn.setHangshu(item.getData().getInteger("hangshu"));
			//tn.setQbneirong(item.getData().getJSONArray("qbneirong").toString());
			JSONArray qbnr = item.getData().getJSONArray("qbneirong");
			Set<TnsQbNeiRong> tnsqbnr = new HashSet<TnsQbNeiRong>();
			qbnr.forEach(e ->{
				JSONObject js = (JSONObject)e;
				tnsqbnr.add(new TnsQbNeiRong(js.getString("neirong"),js.getInteger("hangshu"),tn));
			});
			//tn.setQbneirong(tnsqbnr);
			//tn.setLrsj(Date.from(LocalDateTime.now().atZone( ZoneId.systemDefault()).toInstant()));
			//System.out.println("Rootid " + item.getId());
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
            	listAndInsSql2(tnsneirongDao,tnDao,item.nodes,wzlx,wzversion,sz,fileweizhi);
            }
            //System.out.println();
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
    	//找到树的最左边的节点
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
    // 多叉树遍历 非递归
    static void displayTreeByStack(treenode root) {
		if (root == null)
			System.out.println("空树");
		else {
			Stack allNode = new Stack();// 临时存储用栈
			allNode.push(root);// 压入根节点
			while (!allNode.isEmpty()) {// 只有栈不为空
				treenode n = (treenode) allNode.pop();// 弹出元素
				System.out.println(n.getData());
				if (n.getkNode() != null) {// 再遍历压入子树
					for (treenode tn : n.getkNode())
						allNode.push(tn);
				}
			}
		}
	}
    
    */
}
