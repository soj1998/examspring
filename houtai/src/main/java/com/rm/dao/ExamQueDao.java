package com.rm.dao;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamQue;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamQueDao extends JpaRepository<ExamQue, Integer>{
	@Query(value = "select * from t_examquesjk",nativeQuery = true)
	public List<ExamQue> buyaoyonggetExamQueList(@Param("startIndex") int sindex,@Param("pageSize") int pages);
}
