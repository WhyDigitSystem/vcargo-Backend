package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.TimeLineVO;

@Repository
public interface TimeLineRepo extends JpaRepository<TimeLineVO, Long>{

	List<TimeLineVO> findByIndentsVO(IndentsVO indentsVO);


	
}
