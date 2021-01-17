package com.rm.dao;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	@Query(value = "select min(rootid) from t_treenodesjk where parentid = :pid and atctreenodesjkid = :atcid",nativeQuery = true)
	public int getMinRootidByParentid(@Param("pid") int parentid,@Param("atcid") int atcid);
	
	//根据父节点属于的层级找到下属的节点
	@Query(value = "select * from t_treenodesjk where parentid = :pid and atctreenodesjkid = :atcid",nativeQuery = true)
	public List<TreeNodeSjk> getTreeByParentid(@Param("pid") int parentid,@Param("atcid") int atcid);
	 
	@Query(value = "select * from t_treenodesjk where rootid = :rid and atctreenodesjkid = :atcid",nativeQuery = true)
	public List<TreeNodeSjk> getTreeByRootid(@Param("rid") int rootid,@Param("atcid") int atcid);
	 
	@Query(value = "select * from t_treenodesjk where szid = :szid and wzlxid = :wzlxid ORDER BY lrsj DESC limit 0,1",nativeQuery = true)
	public List<TreeNodeSjk> getBanbenBySzWzlx(@Param("szid") int szid,@Param("wzlxid") int wzlxid);
	
	@Transactional
	@Modifying
	@Query(value = "update t_treenodesjk set yxbz = 'N' where szid = :szid and wzlxid = :wzlxid",nativeQuery = true)
	public int updateBySzWzlx(@Param("szid") int szid,@Param("wzlxid") int wzlxid);
}
