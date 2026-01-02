package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorInvoiceVO;

@Repository
public interface VendorInvoiceRepo extends JpaRepository<VendorInvoiceVO, Long>{

	
	@Query(
		    value =
		        "SELECT v.* FROM vendorinvoice v " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR v.branchcode = :branchCode) And v.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(v.vendorinvoiceid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(v.invoicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(v.tripcost AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(v.totalamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(v.payableamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    countQuery =
		        "SELECT COUNT(*) FROM vendorinvoice v " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR v.branchcode = :branchCode) And v.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(v.vendorinvoiceid AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(v.invoicetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(v.tripcost AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(v.totalamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(v.payableamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    nativeQuery = true
		)
		Page<VendorInvoiceVO> getVendorInvoiceByOrgId(
		        @Param("branchCode") String branchCode,
		         @Param("orgId")  Long orgId, @Param("search") String search,
		        Pageable pageable
		);


}
