package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripsVO;

@Repository
public interface TripsRepo extends JpaRepository<TripsVO, Long>{

	@Query(
		    value = "SELECT t.* FROM trips t " +
		            "WHERE (:branchCode IS NULL OR t.branchcode = :branchCode) AND t.orgid = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.lrnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.route) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.orgin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.drivernumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.drivername) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.eta) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.tatdays) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehicletonnagecapacity) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehiclesqftcapacity) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.materialsqft) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.weight) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    countQuery =
		            "SELECT COUNT(*) FROM trips t " +
		            "WHERE (:branchCode IS NULL OR t.branchcode = :branchCode) AND t.orgid = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.lrnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehiclenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.route) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.orgin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.drivernumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.drivername) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.eta) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.tatdays) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehicletonnagecapacity) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.vehiclesqftcapacity) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.materialsqft) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.weight) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    nativeQuery = true
		)
		Page<TripsVO> getTripsByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId,
		        @Param("search") String search,
		        Pageable pageable
		);

	@Query(value = "SELECT t.tripsid ,t.lrnumber,t.vehiclenumber,t.orgin,t.destination,t.status  " + "FROM trips t "
			+ "WHERE (?1 IS NULL OR t.branchcode = ?1 ) and t.orgid=?2 ", nativeQuery = true)
	List<Object[]> getTripDetailsListByOrgId(String branchCode, Long orgId);

}
