package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsVO;

@Repository
public interface IndentsRepo extends JpaRepository<IndentsVO, Long>{

	@Query(
	        value = "SELECT i.* FROM indents i " +
	                "WHERE (:branchCode IS NULL OR i.branchcode = :branchCode) And i.orgid = :orgId  " +
	                "AND (" +
	                "   :search IS NULL OR :search = '' OR " +
	                "   LOWER(i.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.vechiletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.weight) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.numberofvehicles) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.customerrate) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.vendorratepervehicles) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.extrainfo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.ordertype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.dockerno) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.triptype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) " +
	                ")",
	        countQuery =
	                "SELECT COUNT(*) FROM indents i " +
	                "WHERE (:branchCode IS NULL OR i.branchcode = :branchCode) And i.orgid = :orgId " +
	                "AND (" +
	                "   :search IS NULL OR :search = '' OR " +
	                "   LOWER(i.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.vechiletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.weight) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.numberofvehicles) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.customerrate) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.vendorratepervehicles) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.extrainfo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.ordertype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.dockerno) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.triptype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	                "   LOWER(i.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) " +
	                ")",
	        nativeQuery = true
	)
	Page<IndentsVO> getIndentsByOrgId(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId,
	        @Param("search") String search,
	        Pageable pageable
	);


}
