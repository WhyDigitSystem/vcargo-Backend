package com.efit.savaari.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.entity.TripVO;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceVO, Long> {

	
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1")
	List<MaintenanceVO> getMaintenanceByOrgId(Long orgId);

	 
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1  ORDER BY a.maintenanceid DESC LIMIT 5")
	 List<MaintenanceVO> findByOrgId(Long orgId);


//	 List<MaintenanceVO> findByOrgIdAndCreatedOnBetween(Long orgId, LocalDate localDate,
//			LocalDate localDate2);


	 @Query(value = "SELECT * FROM maintenance " +
		        "WHERE orgid = :orgId " +
		        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
		        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
		        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
		        nativeQuery = true)
		List<MaintenanceVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);

	 
	 
}