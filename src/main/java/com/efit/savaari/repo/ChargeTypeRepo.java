package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.ChargeTypeVO;

@Repository
public interface ChargeTypeRepo extends JpaRepository<ChargeTypeVO, Long>{

	@Query(value =
	        "SELECT c.* FROM chargetype c " +
	        "WHERE (:branchCode IS NULL OR :branchCode = '' OR c.branchcode = :branchCode) AND c.orgId = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(c.chargetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.unit) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ") ",

	        countQuery =
	        "SELECT COUNT(*) FROM chargetype c " +
	        "WHERE (:branchCode IS NULL OR :branchCode = '' OR c.branchcode = :branchCode) AND c.orgId = :orgId " +
	        "AND (" +
	        "   :search IS NULL OR :search = '' OR " +
	        "   LOWER(c.chargetype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.unit) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
	        "   LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        ")",
	        nativeQuery = true)
	Page<ChargeTypeVO> getChargeTypeByOrgId(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId,
	        @Param("search") String search,
	        Pageable pageable
	);

	
	@Query(value =
	        "SELECT c.chargetypeid, c.chargetype FROM chargetype c " +
	        "WHERE (:branchCode IS NULL OR :branchCode='' OR c.branchcode = :branchCode) and c.orgid = :orgId " +
	        "AND (:search IS NULL OR :search='' OR LOWER(c.chargetype) LIKE LOWER(CONCAT('%', :search, '%'))) ",

	        countQuery =
	        "SELECT COUNT(*) FROM chargetype c " +
	        "WHERE (:branchCode IS NULL OR :branchCode='' OR c.branchcode = :branchCode) and c.orgid = :orgId " +
	        "AND (:search IS NULL OR :search='' OR LOWER(c.chargetype) LIKE LOWER(CONCAT('%', :search, '%'))) ",
	        nativeQuery = true)
	Page<Object[]> getChargeTypeList(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId, @Param("search") String search,
	        Pageable pageable
	);




}
