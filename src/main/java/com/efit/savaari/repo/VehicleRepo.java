package com.efit.savaari.repo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.dto.TripDashboardDTO;
import com.efit.savaari.entity.FuelVO;
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
	
	@Query(value = "SELECT COUNT(*) FROM tvehicle WHERE orgid = ?1 AND active = 'ACTIVE'", nativeQuery = true)
	Long getActiveVehicleCount(Long orgId);

	@Query(value = "SELECT COUNT(*) FROM tvehicle WHERE orgid = ?1 AND active = 'MAINTENANCE'", nativeQuery = true)
	Number getMaintenanceVehicleCount(Long orgId);

	@Query(value = "select Count(*) from maintenance where completeddate > current_date() and orgid=?1", nativeQuery = true)
	Number getUpcomingMaintenanceVehicle(Long orgId);

	@Query(value = "select sum(estimatedcost) from maintenance where completeddate < current_date() and orgid=?1", nativeQuery = true)
	BigDecimal getmaintenanceCost(Long orgId);

}
