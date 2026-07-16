package com.efit.savaari.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TyreMasterVO;

@Repository
public interface TyreMasterRepo extends JpaRepository<TyreMasterVO, Long> {

   

	@Query(nativeQuery = true, value = "SELECT a.* FROM tyremaster a WHERE a.orgid = :orgId")
	List<TyreMasterVO> getTyreByOrgId(@Param("orgId") Long orgId);

    @Query(nativeQuery = true,   value = "SELECT * FROM tyremaster a WHERE a.orgid = ?1 ORDER BY a.tyreid DESC LIMIT 5")
	 List<TyreMasterVO> findByOrgId(Long orgId);

    @Query(value = """
    		SELECT COUNT(*)
    		FROM tyremaster
    		WHERE orgid=:orgId
    		AND (
    		    :fromDate IS NULL
    		    OR purchasedate BETWEEN :fromDate AND :toDate
    		)
    		""", nativeQuery = true)
    		Long getTyresPurchased(
    		        @Param("orgId") Long orgId,
    		        @Param("fromDate") String fromDate,
    		        @Param("toDate") String toDate);

//	List<TyreMasterVO> findByOrgIdAndCreatedOnBetween(Long orgId, LocalDate localDate, LocalDate localDate2);

	@Query(value = "SELECT * FROM tyremaster " +
	        "WHERE orgid = :orgId " +
	        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
	        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
	        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
	        nativeQuery = true)
	List<TyreMasterVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
	        @Param("orgId") Long orgId,
	        @Param("fromDate") String fromDate,
	        @Param("toDate") String toDate);
	
    
}
