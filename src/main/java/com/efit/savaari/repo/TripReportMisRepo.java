package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripReportMisVO;

@Repository
public interface TripReportMisRepo extends JpaRepository<TripReportMisVO, Long> {

	@Query(
		    value = "SELECT t.* FROM tripreportmis t " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) " +
		            "AND ( " +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.refdoctype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.reporttype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.referencereport) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.isstandard) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.module) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.comment) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(t.addtotalrow AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(t.disabled AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    
		    countQuery =
		            "SELECT COUNT(*) FROM tripreportmis t " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) " +
		            "AND ( " +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.refdoctype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.reporttype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.referencereport) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.isstandard) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.module) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.comment) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   CAST(t.addtotalrow AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   CAST(t.disabled AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		            "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    
		    nativeQuery = true
		)
		Page<TripReportMisVO> getTripReportMisByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("search") String search,
		        Pageable pageable
		);

}
