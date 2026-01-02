package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorRateVO;

@Repository
public interface VendorRateRepo extends JpaRepository<VendorRateVO, Long>{

	
	@Query(value =
	        "SELECT v.* FROM vendorrate v " +
	        "WHERE (:branchCode IS NULL OR v.branchcode = :branchCode) And v.orgid = :orgId  " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(v.state) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   DATE_FORMAT(v.effectivefrom, '%Y-%m-%d') LIKE CONCAT('%', :search, '%') OR " +
	        "   DATE_FORMAT(v.effectiveto, '%Y-%m-%d') LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.priority) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.rate) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.ratetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.weight) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.detentioncharge) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   v.ranks LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.unloadingcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.remarks) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.extracharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   v.orgid LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        
	        countQuery =
	        "SELECT COUNT(*) FROM vendorrate v " +
	        "WHERE (:branchCode IS NULL OR v.branchcode = :branchCode)  And v.orgid = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(v.state) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.vendor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.namingseries) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   DATE_FORMAT(v.effectivefrom, '%Y-%m-%d') LIKE CONCAT('%', :search, '%') OR " +
	        "   DATE_FORMAT(v.effectiveto, '%Y-%m-%d') LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.priority) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.origin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.destination) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.rate) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.vehicletype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.ratetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.weight) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.detentioncharge) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   v.ranks LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.unloadingcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.remarks) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.extracharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   v.orgid LIKE CONCAT('%', :search, '%') OR " +
	        "   LOWER(v.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(v.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        
	        nativeQuery = true)
	Page<VendorRateVO> getVendorRateByOrgId(
	        @Param("branchCode") String branchCode,
	         @Param("orgId") Long orgId, @Param("search") String search,
	        Pageable pageable
	);

	@Query(value = "SELECT v.vendor ,v.rate,v.origin,v.destination " +
            "FROM vendorrate v " +
            "WHERE (?1 IS NULL OR v.branchcode = ?1) and orgId=?2",
    nativeQuery = true)
	List<Object[]> getVendorRateAndOriginList(String branchCode, Long orgId);


}
