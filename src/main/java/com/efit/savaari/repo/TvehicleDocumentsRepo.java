package com.efit.savaari.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TvehicleDocumentsVO;
import com.efit.savaari.entity.TvehicleVO;

@Repository
public interface TvehicleDocumentsRepo extends JpaRepository<TvehicleDocumentsVO, Long> {

//	List< TvehicleDocumentsVO> findByTvehicleVO(TvehicleVO vo);

	List<TvehicleDocumentsVO> findByTvehicleAndDocumentType(TvehicleVO vehicle, String documentType);

	

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM tvehicledocuments WHERE tvehicleid = :vehicleId", nativeQuery = true)
	void deleteByVehicleId(@Param("vehicleId") Long vehicleId);



	List<TvehicleDocumentsVO> findByTvehicle(TvehicleVO vo);


}
