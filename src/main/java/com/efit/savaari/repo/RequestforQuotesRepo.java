package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RequestforQuotesVO;

@Repository
public interface RequestforQuotesRepo extends JpaRepository<RequestforQuotesVO, Long>{

	@Query(
		    value =
		        "SELECT r.* FROM requestforquotes r " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR r.branchcode = :branchCode) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(r.rfqdetails) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.natureofcontract) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.material) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(r.contractstartdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(r.contractduration) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.vendortags) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.numtransporter) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.additionalcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    countQuery =
		        "SELECT COUNT(*) FROM requestforquotes r " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR r.branchcode = :branchCode) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(r.rfqdetails) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.natureofcontract) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.material) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(r.contractstartdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(r.contractduration) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.vendortags) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.numtransporter) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.additionalcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(r.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    nativeQuery = true
		)
		Page<RequestforQuotesVO> getRequestforQuotesByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("search") String search,
		        Pageable pageable
		);

}
