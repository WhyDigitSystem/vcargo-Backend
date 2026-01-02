package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.DriverVO;

@Repository
public interface DriverRepo extends JpaRepository<DriverVO, Long> {

    boolean existsByDriverNumberAndOrgId(String driverNumber, Long orgId);

    @Query(
    	    value = "SELECT d.* FROM driver d " +
    	            "WHERE (:branchCode IS NULL OR d.branchcode = :branchCode) And d.orgid = :orgId  " +
    	            "AND (" +
    	            "   :search IS NULL OR :search = '' OR " +
    	            "   LOWER(d.drivernumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.drivername) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.branchcode) LIKE LOWER(CONCAT('%', :search, '%'))" +
    	            ")",
    	    countQuery =
    	            "SELECT COUNT(*) FROM driver d " +
    	            "WHERE (:branchCode IS NULL OR d.branchcode = :branchCode) And d.orgid = :orgId " +
    	            "AND (" +
    	            "   :search IS NULL OR :search = '' OR " +
    	            "   LOWER(d.drivernumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.drivername) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.branch) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
    	            "   LOWER(d.branchcode) LIKE LOWER(CONCAT('%', :search, '%'))" +
    	            ")",
    	    nativeQuery = true
    	)
    	Page<DriverVO> getAllDriver(
    	        @Param("branchCode") String branchCode,
    	        @Param("orgId") Long orgId,
    	        @Param("search") String search,
    	        Pageable pageable
    	);



}
