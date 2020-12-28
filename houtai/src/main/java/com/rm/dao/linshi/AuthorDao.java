package com.rm.dao.linshi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.TreeNodeSjk;
import com.rm.entity.linshi.Author;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface AuthorDao extends JpaRepository<Author, Integer>{

	 @Query(value = "select * from author where parentid = :pid",nativeQuery = true)
	  public List<TreeNodeSjk> getTreeByParentid(@Param("pid") int parentid);
	 
	 
}
