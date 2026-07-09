package com.efit.savaari.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.TvehicleVO;

@Repository
public interface TvehicleRepo extends JpaRepository<TvehicleVO, Long> {

	@Query(value = "SELECT t.* FROM tvehicle t " +
	        "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) " +
	        "AND t.orgid = :orgId",
	        nativeQuery = true)
	List<TvehicleVO> getTvehiclesByOrgId(@Param("branchCode") String branchCode,
	                                     @Param("orgId") Long orgId);


//	Optional<TvehicleVO> findByOrgIdAndVehicleNumber(Long orgId, String vehicleNumber);

//	Optional<TvehicleVO> findByOrgIdAndVehicleId(Long orgId, String vehicleId);


//	Optional<TvehicleVO> findByOrgIdAndTvehicleId(Long orgId, String vehicleId);

	Optional<TripInvoiceVO> findByOrgIdAndId(Long orgId, String vehicleId);


	@Query(value = """
			SELECT
			    COUNT(DISTINCT CASE
			        WHEN v.active = 'MAINTENANCE'
			        THEN v.tvehicleid
			    END) AS maintenance_vehicle_count,

			    COUNT(DISTINCT CASE
			        WHEN v.active = 'ACTIVE'
			         AND t.tripid IS NOT NULL
			        THEN v.tvehicleid
			    END) AS ontrip_vehicle_count,

			    COUNT(DISTINCT CASE
			        WHEN v.active = 'ACTIVE'
			         AND t.tripid IS NULL
			        THEN v.tvehicleid
			    END) AS active_vehicle_count

			FROM tvehicle v

			LEFT JOIN trip t
			       ON t.vehicle = v.tvehicleid
			      AND t.status IN ('scheduled','started')
			      AND t.orgid = v.orgid
			      AND (
			            :fromDate IS NULL
			            OR DATE(
			                STR_TO_DATE(t.createdon,'%d-%m-%Y %h:%i:%s %p')
			            ) BETWEEN :fromDate AND :toDate
			      )

			WHERE v.orgid = :orgId
			  AND (
			        :fromDate IS NULL
			        OR DATE(
			            STR_TO_DATE(v.createdon,'%d-%m-%Y %h:%i:%s %p')
			        ) BETWEEN :fromDate AND :toDate
			      )
			""", nativeQuery = true)
			List<Object[]> getAllDashBoardVehicleDetails(
			        @Param("orgId") Long orgId,
			        @Param("fromDate") String fromDate,
			        @Param("toDate") String toDate);


	boolean existsByVehicleNumberAndOrgId(String vehicleNumber, Long orgId);


	boolean existsByChassisNumberAndOrgId(String chassisNo, Long orgId);


	boolean existsByEngineNumberAndOrgId(String engineNo, Long orgId);



	
	
	    
}
