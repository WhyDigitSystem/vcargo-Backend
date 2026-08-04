package com.efit.savaari.repo;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripVO;

@Repository
public interface TripRepo extends JpaRepository<TripVO, Long> {

	@Query(nativeQuery = true, value = "select * from trip  where orgid=?1")
	List<TripVO> getTripByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from trip a where a.orgid=?1  ORDER BY a.tripid DESC LIMIT 5")
    List<TripVO> findByOrgId(Long orgId);


	@Modifying
    @Transactional
    @Query("UPDATE TripVO t SET t.tripStartTime = CURRENT_TIMESTAMP, t.status = 'STARTED' WHERE t.id = :id")
    int updateTripStart(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE TripVO t SET t.tripEndTime = CURRENT_TIMESTAMP, t.status = 'COMPLETED' WHERE t.id = :id")
    int updateTripEnd(@Param("id") Long id);

    @Query(value = """
    		SELECT COUNT(*)
    		FROM trip
    		WHERE orgid=:orgId
    		AND active=1
    		AND (
    		    :fromDate IS NULL
    		    OR startdate BETWEEN :fromDate AND :toDate
    		)
    		""", nativeQuery = true)
    		Long getTotalCount(
    		        @Param("orgId") Long orgId,
    		        @Param("fromDate") String fromDate,
    		        @Param("toDate") String toDate);

    @Query(value = """
    	    SELECT COUNT(*)
    	    FROM tdriver
    	    WHERE orgid = :orgId
    	      AND status = 'ONTRIP'
    	      AND active = 1
    	      AND (
    	            :fromDate IS NULL
    	            OR DATE(
    	                STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p')
    	            ) BETWEEN :fromDate AND :toDate
    	      )
    	    """, nativeQuery = true)
    	Long getOnTripDriverCount(
    	        @Param("orgId") Long orgId,
    	        @Param("fromDate") String fromDate,
    	        @Param("toDate") String toDate);

//	List<TripVO> findByOrgIdAndCreatedOnBetween(Long orgId, LocalDate localDate,
//			LocalDate localDate2);
	
	@Query(value = "SELECT * FROM trip " +
	        "WHERE orgid = :orgId " +
	        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
	        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
	        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
	        nativeQuery = true)
	List<TripVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
	        @Param("orgId") Long orgId,
	        @Param("fromDate") String fromDate,
	        @Param("toDate") String toDate);

	@Query(value =
	        "SELECT COUNT(*) AS completedTrips " +
	        "FROM trip " +
	        "WHERE orgid=:orgId " +
	        "AND cancel=0 " +
	        "AND UPPER(status)='COMPLETED' " +
	        "AND startdate=CURDATE()",
	        nativeQuery = true)
	Map<String,Object> getTodayCompletedTrips(@Param("orgId") Long orgId);


	@Query(value =
		    "SELECT " +
		    "DATE(t.startdate) AS tripDate, " +
		    "COUNT(*) AS completedTrips, " +
		    "(SELECT COUNT(*) " +
		    " FROM tvehicle v " +
		    " WHERE v.orgid=:orgId " +
		    " AND v.cancel=0 " +
		    " AND v.active='ACTIVE') AS totalActiveVehicles " +
		    "FROM trip t " +
		    "WHERE t.orgid=:orgId " +
		    "AND t.cancel=0 " +
		    "AND UPPER(t.status)='COMPLETED' " +
		    "AND t.startdate BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 DAY) AND CURDATE() " +
		    "GROUP BY DATE(t.startdate) " +
		    "ORDER BY DATE(t.startdate)",
		    nativeQuery = true)
		List<Map<String,Object>> getWeekCompletedTrips(@Param("orgId") Long orgId);
	
	
	@Query(value =
		    "SELECT " +
		    "weekNo, " +
		    "COUNT(*) AS completedTrips, " +
		    "(SELECT COUNT(*) " +
		    " FROM tvehicle v " +
		    " WHERE v.orgid=:orgId " +
		    " AND v.cancel=0 " +
		    " AND v.active='ACTIVE') AS totalActiveVehicles " +
		    "FROM ( " +
		    "   SELECT FLOOR((DAY(startdate)-1)/7)+1 AS weekNo " +
		    "   FROM trip " +
		    "   WHERE orgid=:orgId " +
		    "   AND cancel=0 " +
		    "   AND UPPER(status)='COMPLETED' " +
		    "   AND YEAR(startdate)=YEAR(CURDATE()) " +
		    "   AND MONTH(startdate)=MONTH(CURDATE()) " +
		    ") x " +
		    "GROUP BY weekNo " +
		    "ORDER BY weekNo",
		    nativeQuery = true)
		List<Map<String,Object>> getMonthCompletedTrips(@Param("orgId") Long orgId);
	
	
	@Query(value =
	        "SELECT COUNT(*) " +
	        "FROM tvehicle " +
	        "WHERE orgid=:orgId " +
	        "AND cancel=0 " +
	        "AND active='ACTIVE'",
	        nativeQuery = true)
	Long getTotalActiveVehicles(@Param("orgId") Long orgId);
	
}
