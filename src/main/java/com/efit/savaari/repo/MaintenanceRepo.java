package com.efit.savaari.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.MaintenanceVO;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceVO, Long> {

	
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1")
	List<MaintenanceVO> getMaintenanceByOrgId(Long orgId);

	 
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1  ORDER BY a.maintenanceid DESC LIMIT 5")
	 List<MaintenanceVO> findByOrgId(Long orgId);


//	 List<MaintenanceVO> findByOrgIdAndCreatedOnBetween(Long orgId, LocalDate localDate,
//			LocalDate localDate2);


	 @Query(value = "SELECT * FROM maintenance " +
		        "WHERE orgid = :orgId " +
		        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
		        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
		        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
		        nativeQuery = true)
		List<MaintenanceVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);




//	 @Query("""
//			 SELECT m
//			 FROM MaintenanceVO m
//			 WHERE m.orgId = :orgId
//	 		AND (:vehicleNumber IS NULL OR m.vehicle.vehicleNumber = :vehicleNumber)
//			 AND m.scheduledDate BETWEEN :fromDate AND :toDate
//			 AND LOWER(m.status) <> 'completed'
//			 ORDER BY m.scheduledDate
//			 """)
//			 List<MaintenanceVO> findWeekSchedule(
//			         @Param("orgId") Long orgId,
//			         @Param("vehicleNumber") String vehicleNumber,
//			         @Param("fromDate") LocalDate fromDate,
//			         @Param("toDate") LocalDate toDate);
//	 
//
//	 
//	 @Query("""
//			 SELECT m
//			 FROM MaintenanceVO m
//			 WHERE m.orgId = :orgId
//	 		AND (:vehicleNumber IS NULL OR m.vehicle.vehicleNumber = :vehicleNumber)
//			 AND m.scheduledDate BETWEEN :fromDate AND :toDate
//			 AND LOWER(m.status) <> 'completed'
//			 ORDER BY m.scheduledDate
//			 """)
//			 List<MaintenanceVO> findMonthSchedule(
//					  @Param("orgId") Long orgId,
//				         @Param("vehicleNumber") String vehicleNumber,
//				         @Param("fromDate") LocalDate fromDate,
//				         @Param("toDate") LocalDate toDate);
//	 
//	 @Query("""
//			 SELECT m
//			 FROM MaintenanceVO m
//			 WHERE m.orgId = :orgId
//	 		AND (:vehicleNumber IS NULL OR m.vehicle.vehicleNumber = :vehicleNumber)
//			 AND m.scheduledDate < :today
//			 AND LOWER(m.status) <> 'completed'
//			 ORDER BY m.scheduledDate
//			 """)
//			 List<MaintenanceVO> findExpiredSchedule(
//			         @Param("orgId") Long orgId,
//			         @Param("vehicleNumber") String vehicleNumber,
//			         @Param("today") LocalDate today);
//


	 @Query("""
			 SELECT m
			 FROM MaintenanceVO m
			 WHERE m.orgId=:orgId
			 AND LOWER(m.status)<>'completed'
			 """)
			 List<MaintenanceVO> getMaintenanceDashboard(
			         @Param("orgId") Long orgId);


	 @Query(value =
			    "SELECT " +
			    "v.vehiclenumber AS vehicle, " +
			    "m.title AS service, " +
			    "m.scheduleddate AS scheduledDate, " +
			    "DATEDIFF(m.scheduleddate, CURDATE()) AS daysLeft, " +
			    "CASE " +
			    "   WHEN DATEDIFF(m.scheduleddate, CURDATE()) <= 3 THEN 'High' " +
			    "   WHEN DATEDIFF(m.scheduleddate, CURDATE()) <= 7 THEN 'Medium' " +
			    "   ELSE 'Low' " +
			    "END AS priority " +
			    "FROM maintenance m " +
			    "LEFT JOIN tvehicle v ON v.tvehicleid = m.vehicle " +
			    "WHERE m.orgid = :orgId " +
			    "AND m.cancel = 0 " +
			    "AND LOWER(m.status) <> 'completed' " +
			    "AND m.scheduleddate BETWEEN CURDATE() " +
			    "AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) " +
			    "ORDER BY m.scheduleddate ASC",
			    nativeQuery = true)
			List<Map<String, Object>> getUpcomingMaintenance(@Param("orgId") Long orgId);

}