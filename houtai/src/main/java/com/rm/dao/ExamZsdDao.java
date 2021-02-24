package com.rm.dao;




import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamZsd;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamZsdDao extends JpaRepository<ExamZsd, Integer>{
	
	@Query(value = "select * from t_examquezsd where neirong= :nr",nativeQuery = true)
	public List<ExamZsd> getZsdByNeiRong(@Param("nr") String neirong);
	
	@Query(value = "select * from t_examquezsd where sjid= -1",nativeQuery = true)
	public List<ExamZsd> getZsdYiJi();
	
	@Query(value = "select * from t_examquezsd where sjid= :sid",nativeQuery = true)
	public List<ExamZsd> getZsdXiaJi(@Param("sid") int sid);
}
