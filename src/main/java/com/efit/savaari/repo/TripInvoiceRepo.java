package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.efit.savaari.entity.TripInvoiceVO;

public interface TripInvoiceRepo extends JpaRepository<TripInvoiceVO, Long> {

	Page<TripInvoiceVO> getTripInvoiceByOrgId(Long orgId, Pageable pageable);

	@Query(nativeQuery = true, value = "select * from tripinvoice a where a.orgid=?1  ORDER BY a.invoiceid DESC LIMIT 5")
	List<TripInvoiceVO> findByOrgId(Long orgId);

	@Query(value = "SELECT * FROM tripinvoice " +
	        "WHERE orgid = :orgId " +
	        "AND STR_TO_DATE(createdon, '%d-%m-%Y %h:%i:%s %p') " +
	        "BETWEEN STR_TO_DATE(:fromDate, '%d-%m-%Y %h:%i:%s %p') " +
	        "AND STR_TO_DATE(:toDate, '%d-%m-%Y %h:%i:%s %p')",
	        nativeQuery = true)
	List<TripInvoiceVO> findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
	        @Param("orgId") Long orgId,
	        @Param("fromDate") String fromDate,
	        @Param("toDate") String toDate);

	
}
