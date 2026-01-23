package com.efit.savaari.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TdriverVO;

@Repository
public interface TdriverRepo extends JpaRepository<TdriverVO, Long> {

	@Query(value = "SELECT t.* FROM tdriver t " +
	        "WHERE (?1 IS NULL OR ?1 = '' OR t.branchcode = ?1) " +
	        "AND t.orgid = ?2",
	        nativeQuery = true)
	List<TdriverVO> getTdriverByOrgId( String branchCode,
	                                 Long orgId);

	 
	@Query(value = " SELECT SUM(CASE WHEN status = 'Active' THEN 1 ELSE 0 END)   AS activeCount,\r\n"
			+ "					SUM(CASE WHEN status = 'Inactive' THEN 1 ELSE 0 END) AS inactiveCount,\r\n"
			+ "				      SUM(CASE WHEN status = 'Leave' THEN 1 ELSE 0 END)    AS leaveCount\r\n"
			+ "				    FROM tdriver\r\n"
			+ "			    WHERE orgid = ?1 AND active = 1 ", nativeQuery = true)
		DriverStatusCountProjection getDriverStatusCounts(Long orgId);

//	Optional<TdriverVO> findByOrgIdAndPhone(Long orgId, String driverNumber);



	Optional<TdriverVO> findByOrgIdAndId(Long orgId, String driverId);

	@Modifying
	@Query("UPDATE TdriverVO d SET d.status=:status WHERE d.id=:id")
	int updateDriverStatus(@Param("id") Long id, @Param("status") String status);

}
