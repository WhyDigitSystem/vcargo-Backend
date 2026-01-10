package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TdriverDocumentsVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TvehicleDocumentsVO;
import com.efit.savaari.entity.TvehicleVO;

@Repository
public interface TdriverDocumentsRepo extends JpaRepository<TdriverDocumentsVO, Long>{

	List<TdriverDocumentsVO> findByTdriverVOAndDocumentType(TdriverVO vo, String documentType);


	void deleteByTdriverVOAndDocumentType(TdriverVO vo, String documentType);
}
