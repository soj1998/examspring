package com.rm.czentity;



import java.util.ArrayList;
import java.util.List;
import com.rm.dao.TreeNodeSjkDao;
import com.rm.entity.TreeNodeSjk;


public class CzTreeNodeSjk {
	
	public static List<TreeNodeSjk> diGuiQiu(int rid, List<TreeNodeSjk> trs,TreeNodeSjkDao tnDao) {
		if (null == trs ||trs.size() == 0) {
			trs = new ArrayList<TreeNodeSjk>();
		}
		List<TreeNodeSjk> temp = tnDao.getTreeByParentid(rid);
		if (null == temp ||temp.size() == 0) {
			return trs;
		}
		for (TreeNodeSjk s:temp) {
			trs.add(s);
			diGuiQiu(s.getRootid(),trs,tnDao);
		}
		return trs;
	}
}
