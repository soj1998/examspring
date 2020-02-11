package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.entity.Book;
import com.rm.entity.QueandAns;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface QueandAnsDao extends JpaRepository<QueandAns, Integer>{

	
 
}
