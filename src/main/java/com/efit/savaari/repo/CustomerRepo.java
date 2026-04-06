package com.efit.savaari.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long> {

	@Query(value = "select * FROM customer where customerid=?1", nativeQuery = true)
	Optional<CustomerVO> getCustomerById(Long id);

	@Query(value = "SELECT c.* " +
	        "FROM customer c " +
	        "WHERE (:branchCode IS NULL OR c.branchcode = :branchCode) " +
	        "AND c.orgid = :orgId",
	        nativeQuery = true)
	List<CustomerVO> getCustomerByOrgId(
	        @Param("branchCode") String branchCode,
	        @Param("orgId") Long orgId
	);


	@Query(value = "SELECT c.customercode, c.customername " + "FROM customer c "
			+ "WHERE (?1 IS NULL OR c.branchcode = ?1) and c.orgid=?2", nativeQuery = true)
	List<Object[]> getCustomerNameByOrgId(String branchCode, Long orgId);
	
	@Query(
	        value = "SELECT b.valuedescription, b.valuecode " +
	                "FROM listofvalues a " +
	                "JOIN listofvalues1 b ON a.listofvaluesid = b.listofvaluesid " +
	                "WHERE a.orgid = ?1 AND a.listdescription = ?2",
	        nativeQuery = true
	)
	List<Object[]> getValueDescriptionByListOfValues(Long orgId, String listDescription);

	@Query(value = "SELECT customercode FROM customer ORDER BY customerid DESC LIMIT 1", nativeQuery = true)
	String getLastCustomerCode();


}
