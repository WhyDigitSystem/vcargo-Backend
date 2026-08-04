package com.efit.savaari.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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



//
//	@Query("""
//			SELECT v
//			FROM TvehicleVO v
//			WHERE v.orgId = :orgId
//			AND (:vehicleNumber IS NULL OR v.vehicleNumber = :vehicleNumber)
//			AND v.insuranceExpiry BETWEEN :fromDate AND :toDate
//			AND v.active = 'ACTIVE'
//			ORDER BY v.insuranceExpiry
//			""")
//			List<TvehicleVO> findInsuranceExpiryWeek(
//			        @Param("orgId") Long orgId,
//			        @Param("vehicleNumber") String vehicleNumber,
//			        @Param("fromDate") LocalDate fromDate,
//			        @Param("toDate") LocalDate toDate);
//
//	@Query("""
//			SELECT v
//			FROM TvehicleVO v
//			WHERE v.orgId = :orgId
//			AND (:vehicleNumber IS NULL OR v.vehicleNumber = :vehicleNumber)
//			AND v.insuranceExpiry BETWEEN :fromDate AND :toDate
//			AND v.active = 'ACTIVE'
//			ORDER BY v.insuranceExpiry
//			""")
//			List<TvehicleVO> findInsuranceExpiryMonth(
//			        @Param("orgId") Long orgId,
//			        @Param("vehicleNumber") String vehicleNumber,
//			        @Param("fromDate") LocalDate fromDate,
//			        @Param("toDate") LocalDate toDate);
//
//	@Query("""
//			SELECT v
//			FROM TvehicleVO v
//			WHERE v.orgId = :orgId
//			AND (:vehicleNumber IS NULL OR v.vehicleNumber = :vehicleNumber)
//			AND v.insuranceExpiry < :today
//			AND v.active = 'ACTIVE'
//			ORDER BY v.insuranceExpiry
//			""")
//			List<TvehicleVO> findInsuranceExpired(
//			        @Param("orgId") Long orgId,
//			        @Param("vehicleNumber") String vehicleNumber,
//			        @Param("today") LocalDate today);
//
//
//	
	
	@Query("""
			SELECT v
			FROM TvehicleVO v
			WHERE v.orgId=:orgId
			AND v.active='ACTIVE'
			""")
			List<TvehicleVO> getInsuranceDashboard(
			        @Param("orgId") Long orgId);

	@Query("""
			SELECT v
			FROM TvehicleVO v
			WHERE v.orgId=:orgId
			AND v.active='ACTIVE'
			""")
			List<TvehicleVO> getFitnessDashboard(
			        @Param("orgId") Long orgId);
	
	
	@Query("""
			SELECT v
			FROM TvehicleVO v
			WHERE v.orgId=:orgId
			AND v.active='ACTIVE'
			""")
			List<TvehicleVO> getPucDashboard(
			        @Param("orgId") Long orgId);


	@Query(value =
		    "SELECT " +
		    "SUM(CASE WHEN v.fitnessexpiry < CURDATE() THEN 1 ELSE 0 END) AS critical, " +

		    "SUM(CASE WHEN DATEDIFF(v.fitnessexpiry, CURDATE()) BETWEEN 0 AND 7 THEN 1 ELSE 0 END) AS high, " +

		    "SUM(CASE WHEN DATEDIFF(v.fitnessexpiry, CURDATE()) BETWEEN 8 AND 15 THEN 1 ELSE 0 END) AS medium, " +

		    "SUM(CASE WHEN DATEDIFF(v.fitnessexpiry, CURDATE()) BETWEEN 16 AND 30 THEN 1 ELSE 0 END) AS low, " +

		    "COUNT(*) AS totalActiveVehicles, " +

		    "SUM(CASE " +
		    "       WHEN v.fitnessexpiry < CURDATE() " +
		    "       OR DATEDIFF(v.fitnessexpiry, CURDATE()) BETWEEN 0 AND 30 " +
		    "       THEN 1 " +
		    "       ELSE 0 " +
		    "END) AS totalExpiringVehicles " +

		    "FROM tvehicle v " +
		    "WHERE v.orgid = :orgId " +
		    "AND v.cancel = 0 " +
		    "AND v.active = 'ACTIVE'",
		    nativeQuery = true)
		Map<String, Object> getExpirySummaryDashboard(@Param("orgId") Long orgId);





//	@Query("""
//			SELECT v
//			FROM TvehicleVO v
//			WHERE v.orgId=:orgId
//			AND v.active='ACTIVE'
//			""")
//			List<TvehicleVO> getPermitDashboard(
//			        @Param("orgId") Long orgId);



}
