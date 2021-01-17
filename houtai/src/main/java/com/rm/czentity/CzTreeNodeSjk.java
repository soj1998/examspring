package com.rm.czentity;




import java.util.List;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.TreeNodeSjk;


public class CzTreeNodeSjk {
	
	public List<TreeNodeSjk> diGuiQiu(int rid, int atcid,List<TreeNodeSjk> trs,TreeNodeSjkDao tnDao) {
		if (null == trs ||trs.size() == 0) {
			trs = tnDao.getTreeByRootid(rid,atcid);;
		}
		List<TreeNodeSjk> temp = tnDao.getTreeByParentid(rid,atcid);
		if (null == temp ||temp.size() == 0) {
			return trs;
		}
		for (TreeNodeSjk s:temp) {
			trs.add(s);
			diGuiQiu(s.getRootid(),atcid,trs,tnDao);
		}
		return trs;
	}
}
