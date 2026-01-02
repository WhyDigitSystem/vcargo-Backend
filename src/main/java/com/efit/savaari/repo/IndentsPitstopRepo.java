package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsPitstopVO;
import com.efit.savaari.entity.IndentsVO;

@Repository
public interface IndentsPitstopRepo extends JpaRepository<IndentsPitstopVO, Long>{

	List< IndentsPitstopVO> findByIndentsVO(IndentsVO indentsVO);

}
