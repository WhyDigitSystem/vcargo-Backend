package com.efit.savaari.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TdriverDocumentsVO;
import com.efit.savaari.entity.TdriverVO;

@Repository
public interface TdriverDocumentsRepo extends JpaRepository<TdriverDocumentsVO, Long>{

	List<TdriverDocumentsVO> findByTdriverVOAndDocumentType(TdriverVO vo, String documentType);


	void deleteByTdriverVOAndDocumentType(TdriverVO vo, String documentType);


	List<TdriverDocumentsVO> findByTdriverVO(TdriverVO driver);


	@Modifying
	@Transactional
	@Query(value = "DELETE FROM tdriverdocuments WHERE tdriverid = :driverId", nativeQuery = true)
	void deleteByDriverId(@Param("driverId") Long driverId);
}
