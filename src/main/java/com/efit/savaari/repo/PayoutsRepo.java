package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PayoutsVO;

@Repository
public interface PayoutsRepo extends JpaRepository<PayoutsVO, Long>{

	@Query(
		    value = "SELECT p.* FROM payouts p " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR p.branchcode = :branchCode) AND p.orgid = :orgId  " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(p.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.payoutstatus) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.purpose) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.approveby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.approveon) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.invoice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.invoicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(p.invoiceamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.quantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.totaladditionalcharges AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.totaltripcost AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(p.payoutreference) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.modifiedby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(p.orgid AS CHAR) LIKE CONCAT('%', :search, '%')" +
		            ") ",
		            
		    countQuery = 
		            "SELECT COUNT(*) FROM payouts p " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR p.branchcode = :branchCode) AND p.orgid = :orgId " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(p.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.payoutstatus) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.purpose) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.approveby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.approveon) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.invoice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.invoicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(p.invoiceamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.quantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.totaladditionalcharges AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(p.totaltripcost AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(p.payoutreference) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(p.modifiedby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(p.orgid AS CHAR) LIKE CONCAT('%', :search, '%')" +
		            ")",
		    nativeQuery = true
		)
		Page<PayoutsVO> getPayoutsByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("orgId") Long orgId, @Param("search") String search,
		        Pageable pageable
		);

}
