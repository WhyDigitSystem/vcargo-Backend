package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.TripsDocumentsVO;

@Repository
public interface TripsDocumentsRepo extends JpaRepository<TripsDocumentsVO, Long>{

	List<TripsDocumentsVO> findByIndentsVO(IndentsVO indentsVO);

}
