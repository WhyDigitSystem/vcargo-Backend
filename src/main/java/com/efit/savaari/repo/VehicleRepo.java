package com.efit.savaari.repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VehicleVO;

@Repository
public interface VehicleRepo extends JpaRepository<VehicleVO, Long>{

	boolean existsByVehicleNumberAndOrgId(String vechicleNumber, Long orgId);

	@Query(
		    value = "SELECT v.* FROM vehicle v " +
		            "WHERE (:branchCode IS NULL OR v.branchcode = :branchCode) AND v.orgid = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(v.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%'))  " +		          
		            ")",
		    countQuery =
		            "SELECT COUNT(*) FROM vehicle v " +
		            "WHERE (:branchCode IS NULL OR v.branchcode = :branchCode) AND v.orgid = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(v.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%'))  " +
		 
		            ")",
		    nativeQuery = true
		)
		Page<VehicleVO> getAllVehicle(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId, @Param("search") String search,
		        Pageable pageable
		);


	Optional<VehicleVO> findByOrgIdAndVehicleNumber(Long orgId, String vehicle);
	
	@Query(value = """
		    SELECT COUNT(*)
		    FROM tvehicle
		    WHERE orgid = :orgId
		      AND active = 'ACTIVE'
		      AND (
		            :fromDate IS NULL
		            OR DATE(
		                STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p')
		            ) BETWEEN :fromDate AND :toDate
		      )
		    """, nativeQuery = true)
		Long getActiveVehicleCount(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);

	@Query(value = """
		    SELECT COUNT(*)
		    FROM tvehicle
		    WHERE orgid = :orgId
		      AND active = 'MAINTENANCE'
		      AND (
		            :fromDate IS NULL
		            OR DATE(
		                STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p')
		            ) BETWEEN :fromDate AND :toDate
		      )
		    """, nativeQuery = true)
		Long getMaintenanceVehicleCount(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);

	@Query(value = """
		    SELECT COUNT(*)
		    FROM maintenance
		    WHERE orgid = :orgId
		      AND (
		            :fromDate IS NULL
		            OR completeddate BETWEEN :fromDate AND :toDate
		      )
		    """, nativeQuery = true)
		Long getUpcomingMaintenanceVehicle(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);

	@Query(value = """
			SELECT SUM(estimatedcost)
			FROM maintenance
			WHERE orgid=:orgId
			AND (
			    :fromDate IS NULL
			    OR completeddate BETWEEN :fromDate AND :toDate
			)
			""", nativeQuery = true)
			BigDecimal getMaintenanceCost(
			        @Param("orgId") Long orgId,
			        @Param("fromDate") String fromDate,
			        @Param("toDate") String toDate);

	@Query(value = "SELECT vehiclenumber, orgid, insuranceexpiry, fitnessexpiry, nextservice\r\n"
			+ "		    FROM tvehicle\r\n"
			+ "		    WHERE active = 'ACTIVE'\r\n"
			+ "		      AND (\r\n"
			+ "		           DATEDIFF(insuranceexpiry, CURDATE()) BETWEEN 1 AND 30\r\n"
			+ "		        OR DATEDIFF(fitnessexpiry, CURDATE()) BETWEEN 1 AND 30\r\n"
			+ "		        OR DATEDIFF(nextservice, CURDATE()) BETWEEN 1 AND 30\r\n"
			+ "		      )", nativeQuery = true)
	List<Object[]> findVehiclesExpiringWithin30Days();

}
