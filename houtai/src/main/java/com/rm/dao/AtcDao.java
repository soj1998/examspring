package com.rm.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.TreeNodeSjk;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface AtcDao extends JpaRepository<TreeNodeSjk, Integer>{

	 @Query(value = "select * from treenodesjk where parentid = :pid",nativeQuery = true)
	 public List<TreeNodeSjk> getTreeByParentid(@Param("pid") int parentid);
	 
	 @Query(value = "select * from treenodesjk where rootid = :rid",nativeQuery = true)
	 public List<TreeNodeSjk> getTreeByRootid(@Param("rid") int rootid);
	 
}
