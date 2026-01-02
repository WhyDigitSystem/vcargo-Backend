package com.efit.savaari.repo;

import java.util.List;
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

	@Query(value = "SELECT c.*\r\n"
			+ "FROM customer c\r\n"
			+ "WHERE (:branchCode IS NULL OR c.branchcode = :branchCode)\r\n"
			+ "  AND c.orgid = :orgId\r\n"
			+ "  AND (\r\n"
			+ "        :search IS NULL\r\n"
			+ "     OR :search = ''\r\n"
			+ "     OR LOWER(c.customercode) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.customername) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.gstnumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.pannumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.phonenumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.pocemail) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.pocname) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.pocnumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "     OR LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "      )",

			countQuery = "SELECT COUNT(*)\r\n"
					+ "FROM customer c\r\n"
					+ "WHERE (:branchCode IS NULL OR c.branchcode = :branchCode)\r\n"
					+ "  AND c.orgid = :orgId\r\n"
					+ "  AND (\r\n"
					+ "        :search IS NULL\r\n"
					+ "     OR :search = ''\r\n"
					+ "     OR LOWER(c.customercode) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.customername) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.gstnumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.pannumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.phonenumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.pocemail) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.pocname) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.pocnumber) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "     OR LOWER(c.branch) LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "      )\r\n"
					+ "",

			nativeQuery = true)
	Page<CustomerVO> getCustomerByOrgId(@Param("branchCode") String branchCode,@Param("orgId") Long orgId, @Param("search") String search,
			Pageable pageable);

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


}
