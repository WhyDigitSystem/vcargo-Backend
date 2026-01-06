package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TvehicleDocumentsVO;
import com.efit.savaari.entity.TvehicleVO;

@Repository
public interface TvehicleDocumentsRepo extends JpaRepository<TvehicleDocumentsVO, Long> {

//	List< TvehicleDocumentsVO> findByTvehicleVO(TvehicleVO vo);

	List<TvehicleDocumentsVO> findByTvehicleAndDocumentType(TvehicleVO vehicle, String documentType);

	void deleteByTvehicleAndDocumentType(TvehicleVO vehicle, String documentType);

}
