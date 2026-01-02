package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CustomerBookingRequestVO;

@Repository
public interface CustomerBookingRequestRepo extends JpaRepository<CustomerBookingRequestVO, Long>{
	
	@Query(
		    value = "SELECT c.* FROM customerbookingrequest c " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR c.branchcode = :branchCode) And  c.orgId = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(c.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   c.noofvehicles LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(c.ordertype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.servicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.orderingparty) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.billtoparty) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.docketno) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   c.orgid LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(c.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.branchname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.remarks) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ") " ,
		    countQuery =
		            "SELECT COUNT(*) FROM customerbookingrequest c " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR c.branchcode = :branchCode) And  c.orgId = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(c.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   c.noofvehicles LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(c.ordertype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.servicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.orderingparty) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.materialtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.billtoparty) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.docketno) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   c.orgid LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(c.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.branchname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.status) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(c.remarks) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		            
		    nativeQuery = true
		)
		Page<CustomerBookingRequestVO> getCustomerBookingRequestByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId,
		        @Param("search") String search,
		        Pageable pageable
		);



}
