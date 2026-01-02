package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efit.savaari.entity.VehicleTypeVO;

public interface VehicleTypeRepo extends JpaRepository<VehicleTypeVO, Long>{
	boolean existsByVehicleTypeAndOrgId(String vehicleType, Long orgId);
	boolean existsByVehicleTypeAndOrgIdAndIdNot(String vehicleType, Long orgId, Long id);
	
	@Query("SELECT v FROM VehicleTypeVO v " +
		       "WHERE (:branchCode IS NULL OR v.branchCode = :branchCode) AND v.orgId = :orgId " +
		       "AND (:search IS NULL OR " +
		       "LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :search, '%')))")
		Page<VehicleTypeVO> getAllVehicleType(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId,
		        @Param("search") String search,
		        Pageable pageable);
	
	@Query(value = "SELECT c.vehicleType " +
            "FROM vehicletype c " +
            "WHERE (?1 IS NULL OR c.branchcode = ?1) and c.orgid=?2",
    nativeQuery = true)
List<String> getVehicleTypeListByOrgId(String branchCode, Long orgId);

	

}
