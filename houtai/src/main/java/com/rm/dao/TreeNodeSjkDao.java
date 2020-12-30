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
public interface TreeNodeSjkDao extends JpaRepository<TreeNodeSjk, Integer>{
/**
	@Query(value = "from GuanLianXing where userId = :userId")
    public List<Book> findGuanLianXingByUserId(@Param("userId") int useId);
 
 @Query(value = "update GuanLianXing set userId = :userId where id = :id")
 @Modifying
 public Integer updateUserIdById(@Param("id") int id,@Param("userId") int userId);

 @Query(value = "select * from t_guanlianxing where user_id = :userId",nativeQuery = true)
  public List<Book> findGuanLianXingByUserIdNative(@Param("userId") int useId);
*/
	//找到树节点的最左边的节点 也就是相同parentid 下的 rootid 最小的
	@Query(value = "select min(rootid) from treenodesjk where parentid = :pid",nativeQuery = true)
	public int getMinRootidByParentid(@Param("pid") int parentid);
	
	//根据父节点属于的层级找到下属的节点
	@Query(value = "select * from treenodesjk where parentid = :pid",nativeQuery = true)
	public List<TreeNodeSjk> getTreeByParentid(@Param("pid") int parentid);
	 
	@Query(value = "select * from treenodesjk where rootid = :rid",nativeQuery = true)
	public List<TreeNodeSjk> getTreeByRootid(@Param("rid") int rootid);
	 
	
}
