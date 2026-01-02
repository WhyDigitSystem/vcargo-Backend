package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripGeofenceAlertsVO;

@Repository
public interface TripGeofenceAlertsRepo extends JpaRepository<TripGeofenceAlertsVO,Long>{

	@Query(
		    value =
		        "SELECT t.* FROM tripgeoferencealerts t " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And t.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(t.tag) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.vehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.geofence) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.timestamp) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    countQuery =
		        "SELECT COUNT(*) FROM tripgeoferencealerts t " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And t.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(t.tag) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.vehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.geofence) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.timestamp) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        ") ",
		    nativeQuery = true
		)
		Page<TripGeofenceAlertsVO> getTripGeofenceAlertsByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId,
		        @Param("search") String search,
		        Pageable pageable
		);

}
