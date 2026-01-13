package com.efit.savaari.repo;

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

	@Query(value = "SELECT t.* FROM tvehicle t "
			+ "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And t.orgid = :orgId "
			+ "AND ( " + "   :search IS NULL OR :search = '' OR "
			+ "   LOWER(t.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.type) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.model) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.capacity) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.driver) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.driverphone) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.currentlocation) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.permittype) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.ownername) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   CAST(t.year AS CHAR) LIKE CONCAT('%', :search, '%') OR "
			+ "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ ")", countQuery = "SELECT COUNT(*) FROM tvehicle t "
					+ "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And t.orgid = :orgId "
					+ "AND ( " + "   :search IS NULL OR :search = '' OR "
					+ "   LOWER(t.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.type) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.model) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.capacity) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.driver) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.driverphone) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.currentlocation) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.permittype) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.ownername) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   CAST(t.year AS CHAR) LIKE CONCAT('%', :search, '%') OR "
					+ "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) " + ")", nativeQuery = true)
	Page<TvehicleVO> getTvehiclesByOrgId(@Param("branchCode") String branchCode, @Param("orgId") Long orgId,
			@Param("search") String search, Pageable pageable);

	Optional<TvehicleVO> findByOrgIdAndVehicleNumber(Long orgId, String vehicleNumber);

}
