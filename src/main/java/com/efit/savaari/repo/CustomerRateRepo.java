package com.efit.savaari.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CustomerRateVO;

@Repository
public interface CustomerRateRepo extends JpaRepository<CustomerRateVO, Long>{


	@Query(value =
	        "SELECT c.* FROM customerrate c " +
	        "WHERE (:branchCode IS NULL OR c.branchcode = :branchCode) AND c.orgid = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(c.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.rate LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.ratetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.weight LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.orgid LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        
	        countQuery =
	        "SELECT COUNT(*) FROM customerrate c " +
	        "WHERE (:branchCode IS NULL OR c.branchcode = :branchCode) AND c.orgid = :orgId  " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(c.customer) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.rate LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.ratetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.weight LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   c.orgid LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(c.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        
	        nativeQuery = true)
	Page<CustomerRateVO> getCustomerRateByOrgId(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId,
	        @Param("search") String search,
	        Pageable pageable
	);

	
	@Query(value = "SELECT c.customer ,c.rate,c.origin,c.destination " +
            "FROM customerrate c " +
            "WHERE (?1 IS NULL OR c.branchcode = ?1) and c.orgid=?2",
    nativeQuery = true)
	List<Object[]> getCustomerRateAndOriginList(String branchCode, Long orgId);
	
	




	
}
