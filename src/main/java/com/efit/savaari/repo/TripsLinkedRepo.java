package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.TripsLinkedVO;

@Repository
public interface TripsLinkedRepo extends JpaRepository<TripsLinkedVO, Long>{

	List<TripsLinkedVO> findByIndentsVO(IndentsVO indentsVO);

}
