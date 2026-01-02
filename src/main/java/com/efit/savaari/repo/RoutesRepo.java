package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RoutesVO;

@Repository
public interface RoutesRepo extends JpaRepository<RoutesVO, Long>{

	@Query(value =
	        "SELECT r.* FROM routes r " +
	        "WHERE (:branchCode IS NULL OR r.branchcode = :branchCode) And r.orgid = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(r.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.route) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.mileage) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.tat) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.branchname) LIKE LOWER(CONCAT('%', :search, '%'))  " +
	        ") " ,

	        countQuery =
	        "SELECT COUNT(*) FROM routes r " +
	        "WHERE (:branchCode IS NULL OR r.branchcode = :branchCode) And r.orgid = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(r.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.route) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.mileage) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.tat) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(r.branchname) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        nativeQuery = true)
	Page<RoutesVO> getRoutesByOrgId(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId, @Param("search") String search,
	        Pageable pageable
	);

	@Query(value = "SELECT c.origin ,c.destination " +
            "FROM routes c " +
            "WHERE (?1 IS NULL OR c.branchcode = ?1) and orgid=?2",
    nativeQuery = true)
	List<Object[]> getOriginAndDestinationList(String branchCode, Long orgId);



}
