package com.efit.savaari.repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.FuelVO;

@Repository
public interface FuelRepo extends JpaRepository<FuelVO, Long> {

    List<FuelVO> findByVehicleId(Long vehicleId);

    List<FuelVO> findByDriverId(Long driverId);

    @Query(nativeQuery = true, value = "select a.* from fuel a where   a.vehicle=:vehicleId",
    		countQuery = "select a.* from fuel a where   a.vehicle=:vehicleId")
	Page<FuelVO> getFuelByVehicle(Long vehicleId, Pageable pageable);

    @Query(nativeQuery = true, value = "select a.* from fuel a where   a.orgid=?1 ")
	List<FuelVO> getFuelByOrgId(Long orgId);
	
	 @Query(nativeQuery = true, value = "select * from fuel a where a.orgid=?1  ORDER BY a.fuelid DESC LIMIT 5")
	 List<FuelVO> findByOrgId(Long orgId);

	 @Query(value = """
			 SELECT SUM(quantity)
			 FROM fuel
			 WHERE orgid=:orgId
			 AND active=1
			 AND (
			     :fromDate IS NULL
			     OR fueldate BETWEEN :fromDate AND :toDate
			 )
			 """, nativeQuery = true)
			 BigDecimal getTotalFuel(
			         @Param("orgId") Long orgId,
			         @Param("fromDate") String fromDate,
			         @Param("toDate") String toDate);
	 @Query(value = """
			 SELECT SUM(cost)
			 FROM fuel
			 WHERE orgid=:orgId
			 AND active=1
			 AND (
			     :fromDate IS NULL
			     OR fueldate BETWEEN :fromDate AND :toDate
			 )
			 """, nativeQuery = true)
			 BigDecimal getTotalFuelAmount(
			         @Param("orgId") Long orgId,
			         @Param("fromDate") String fromDate,
			         @Param("toDate") String toDate);

//	 List<FuelVO> findByOrgIdAndCreatedOnBetween(Long orgId, LocalDate localDate,
//			LocalDate localDate2);

	 @Query(value = "SELECT * FROM fuel " +
		        "WHERE orgid = :orgId " +
		        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
		        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
		        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
		        nativeQuery = true)
		List<FuelVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		        @Param("orgId") Long orgId,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate);

	 @Query(value =
		        "SELECT IFNULL(SUM(cost),0) " +
		        "FROM fuel " +
		        "WHERE orgid=:orgId " +
		        "AND fueldate=CURDATE()",
		        nativeQuery = true)
		BigDecimal getTodayFuelAmount(@Param("orgId") Long orgId);
	 
	 @Query(value =
		        "SELECT IFNULL(SUM(cost),0) " +
		        "FROM fuel " +
		        "WHERE orgid=:orgId " +
		        "AND fueldate=DATE_SUB(CURDATE(),INTERVAL 1 DAY)",
		        nativeQuery = true)
		BigDecimal getYesterdayFuelAmount(@Param("orgId") Long orgId);
	 
	 @Query(value =
		        "SELECT " +
		        "fueldate AS fuelDate, " +
		        "SUM(cost) AS amount " +
		        "FROM fuel " +
		        "WHERE orgid=:orgId " +
		        "AND fueldate BETWEEN DATE_SUB(CURDATE(),INTERVAL 6 DAY) " +
		        "AND CURDATE() " +
		        "GROUP BY fueldate " +
		        "ORDER BY fueldate",
		        nativeQuery = true)
		List<Map<String,Object>> getWeekFuelSummary(@Param("orgId") Long orgId);
	 
	 @Query(value =
		        "SELECT " +
		        "CONCAT('Week ', WEEK(fueldate,1)-WEEK(DATE_FORMAT(fueldate,'%Y-%m-01'),1)+1) AS week, " +
		        "SUM(cost) AS amount " +
		        "FROM fuel " +
		        "WHERE orgid=:orgId " +
		        "AND MONTH(fueldate)=MONTH(CURDATE()) " +
		        "AND YEAR(fueldate)=YEAR(CURDATE()) " +
		        "GROUP BY week " +
		        "ORDER BY MIN(fueldate)",
		        nativeQuery = true)
		List<Map<String,Object>> getMonthFuelSummary(@Param("orgId") Long orgId);

	 
}
