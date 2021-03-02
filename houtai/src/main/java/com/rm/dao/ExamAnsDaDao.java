package com.rm.dao;




import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamAnsDa;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamAnsDaDao extends JpaRepository<ExamAnsDa, Integer>{
	
	@Query(value = "select * from t_examansda where szid = :sid and examtype = :tmlx and yxbz ='Y' order by id",nativeQuery = true)
	public List<ExamAnsDa> getexamansdazhanshi(Pageable pageable,@Param("sid") int ssid,@Param("tmlx") String examtype);
	
}
