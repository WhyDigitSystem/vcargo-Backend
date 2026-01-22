package com.efit.savaari.repo;

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

	@Query(value = "SELECT t.* FROM tdriver t "
			+ "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And" + " t.orgid = :orgId "
			+ "AND ( " + "   :search IS NULL OR :search = '' OR "
			+ "   LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.email) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.licensenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.aadharnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.address) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.status) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.experience) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.salary) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.assignedvehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.currentlocation) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.bloodgroup) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.emergencycontact) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.performance) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   CAST(t.joineddate AS CHAR) LIKE CONCAT('%', :search, '%') OR "
			+ "   CAST(t.lasttrip AS CHAR) LIKE CONCAT('%', :search, '%') OR "
			+ "   LOWER(t.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.modifiedby) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   CAST(t.orgid AS CHAR) LIKE CONCAT('%', :search, '%') OR "
			+ "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) "
			+ ")", countQuery = "SELECT COUNT(*) FROM tdriver t "
					+ "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) And t.orgid = :orgId  "
					+ "AND ( " + "   :search IS NULL OR :search = '' OR "
					+ "   LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.email) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.licensenumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.aadharnumber) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.address) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.status) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.experience) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.salary) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.assignedvehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.currentlocation) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.bloodgroup) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.emergencycontact) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.performance) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   CAST(t.joineddate AS CHAR) LIKE CONCAT('%', :search, '%') OR "
					+ "   CAST(t.lasttrip AS CHAR) LIKE CONCAT('%', :search, '%') OR "
					+ "   LOWER(t.createdby) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.modifiedby) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   CAST(t.orgid AS CHAR) LIKE CONCAT('%', :search, '%') OR "
					+ "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR "
					+ "   LOWER(t.branchname) LIKE LOWER(CONCAT('%', :search, '%')) " + ")", nativeQuery = true)
	Page<TdriverVO> getTdriverByOrgId(@Param("branchCode") String branchCode, @Param("orgId") Long orgId,
			@Param("search") String search, Pageable pageable);
	 
	@Query(value = " SELECT\r\n"
			+ "		      SUM(CASE WHEN status = 'ACTIVE' THEN 1 ELSE 0 END)   AS activeCount,\r\n"
			+ "		      SUM(CASE WHEN status = 'INACTIVE' THEN 1 ELSE 0 END) AS inactiveCount,\r\n"
			+ "		      SUM(CASE WHEN status = 'leave' THEN 1 ELSE 0 END)    AS leaveCount\r\n"
			+ "		    FROM tdriver\r\n"
			+ "		    WHERE orgid = ?1 AND active = 1", nativeQuery = true)
		DriverStatusCountProjection getDriverStatusCounts(Long orgId);

//	Optional<TdriverVO> findByOrgIdAndPhone(Long orgId, String driverNumber);



	Optional<TdriverVO> findByOrgIdAndId(Long orgId, String driverId);

	@Modifying
	@Query("UPDATE TdriverVO d SET d.status=:status WHERE d.id=:id")
	int updateDriverStatus(@Param("id") Long id, @Param("status") String status);

}
