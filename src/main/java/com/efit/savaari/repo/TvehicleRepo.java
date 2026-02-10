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


	@Query(value = "SELECT\r\n"
			+ "    COUNT(DISTINCT CASE \r\n"
			+ "        WHEN v.active = 'MAINTENANCE' \r\n"
			+ "        THEN v.tvehicleid \r\n"
			+ "    END) AS maintenance_vehicle_count,\r\n"
			+ "\r\n"
			+ "    COUNT(DISTINCT CASE \r\n"
			+ "        WHEN v.active = 'ACTIVE'\r\n"
			+ "         AND t.tripid IS NOT NULL\r\n"
			+ "        THEN v.tvehicleid\r\n"
			+ "    END) AS ontrip_vehicle_count,\r\n"
			+ "\r\n"
			+ "    COUNT(DISTINCT CASE \r\n"
			+ "        WHEN v.active = 'ACTIVE'\r\n"
			+ "         AND t.tripid IS NULL\r\n"
			+ "        THEN v.tvehicleid\r\n"
			+ "    END) AS active_vehicle_count\r\n"
			+ "\r\n"
			+ "FROM tvehicle v\r\n"
			+ "LEFT JOIN trip t\r\n"
			+ "    ON t.vehicle = v.tvehicleid\r\n"
			+ "   AND t.status IN ('scheduled','started')\r\n"
			+ "   AND t.orgid = v.orgid\r\n"
			+ "WHERE v.orgid = ?1",
	        nativeQuery = true)
	List<Object[]> getAllDashBoardVehicleDetails(Long orgId);



	
	
	    
}
