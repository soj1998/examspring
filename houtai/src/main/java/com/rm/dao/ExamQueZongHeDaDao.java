package com.rm.dao;



import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamQueZongHeDa;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamQueZongHeDaDao extends JpaRepository<ExamQueZongHeDa, Integer>{
	@Query(value = "select * from t_examquezhda where szid = :sid and yxbz ='Y' order by id",nativeQuery = true)
	public List<ExamQueZongHeDa> getexamansdazhanshi(Pageable pageable,@Param("sid") int ssid);
	
}
