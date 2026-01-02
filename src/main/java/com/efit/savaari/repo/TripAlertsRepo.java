package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.AuctionsVO;
import com.efit.savaari.entity.TripAlertsVO;

@Repository
public interface TripAlertsRepo extends JpaRepository<TripAlertsVO, Long> {

	@Query(
		    value =
		        "SELECT t.* FROM tripalerts t " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) AND t.orgid = :orgId  " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(t.tripsid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.vehicleid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.driverid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(t.type) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.extra) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(t.fromdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.todate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",

		    countQuery =
		        "SELECT COUNT(*) FROM tripalerts t " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) AND t.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(t.tripsid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.vehicleid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.driverid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(t.type) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.extra) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(t.fromdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(t.todate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    nativeQuery = true
		)
		Page<TripAlertsVO> getTripAlertsByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId,
		        @Param("search") String search,
		        Pageable pageable
		);


}
