package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorVO;

@Repository
public interface VendorRepo extends JpaRepository<VendorVO, Long> {

	@Query(value = "SELECT * FROM vendor v " +
	        "WHERE v.active = true " +
	        "AND (:branchCode IS NULL OR v.branchcode = :branchCode) " +
	        "AND v.orgid = :orgId " +
	        "AND (" +   // <-- VERY IMPORTANT PARENTHESES START
	        "LOWER(v.vendorcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.organization) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.address) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocemail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.primaryemail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.primaryphonenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.approvalstatus) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.tags) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.vendortype) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",

	countQuery = "SELECT COUNT(*) FROM vendor v " +
	        "WHERE v.active = true " +
	        "AND (:branchCode IS NULL OR v.branchcode = :branchCode) " +
	        "AND v.orgid = :orgId " +
	        "AND (" +
	        "LOWER(v.vendorcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.organization) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.address) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocemail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.pocnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.primaryemail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.primaryphonenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.approvalstatus) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.tags) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "LOWER(v.vendortype) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")", 
	nativeQuery = true)
	Page<VendorVO> getVendorByOrgId(@Param("branchCode") String branchCode,
	                                @Param("orgId") Long orgId,
	                                @Param("search") String search,
	                                Pageable pageable);


	@Query(value = "select * from vendor a where a.vendorid=?1 ", nativeQuery = true)
	VendorVO getVendorById(Long id);

	@Query(value = "SELECT v.organization ,v.vendorcode,v.vendorid " + "FROM vendor v "
			+ "WHERE (?1 IS NULL OR v.branchcode = ?1 ) and v.orgid=?2 ", nativeQuery = true)
	List<Object[]> getOrganizationAndCodeList(String branchCode, Long orgId);

	VendorVO findByPrimaryEmail(String email);

	VendorVO findByVendorTypeAndId(String type,Long id);

//	@Query("SELECT v FROM VendorVO v WHERE v.email = :email")
//	Optional<UserVO> findByEmail(String email); // WRONG RETURN TYPE

//	Optional<VendorVO> findByVendorCode(String vendorCode);

//	@Query("SELECT v FROM VendorVO v " +
//		       "LEFT JOIN FETCH v.vendorUsersVO " +
//		       "LEFT JOIN FETCH v.vendorDetailsVO " +
//		       "WHERE v.id = :id")
//		VendorVO findByIdWithDetailsAndUsers(@Param("id") Long id);

}
