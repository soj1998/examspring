package com.rm.dao.linshi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.linshi.Article;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ArticleDao extends JpaRepository<Article, Integer>{

	 @Query(value = "select * from article where author_id = :aid",nativeQuery = true)
	 public List<Article> findArticleByAuthorid(@Param("aid") int aid);
	 
	 
}
