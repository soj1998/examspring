package com.rm.dao;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamChoi;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamChoiDao extends JpaRepository<ExamChoi, Integer>{
	@Query(value = "select * from t_examchoisjk where examquechoisjkid = :wid",nativeQuery = true)
	public List<ExamChoi> getExamChoiListByQue(@Param("wid") int wid);
}
