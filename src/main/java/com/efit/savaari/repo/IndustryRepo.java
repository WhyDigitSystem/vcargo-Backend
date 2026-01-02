package com.efit.savaari.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndustryVO;

@Repository
public interface IndustryRepo extends JpaRepository<IndustryVO, Long> {

	@Query(value = "SELECT r.*\r\n" + "FROM industry r\r\n" + "WHERE (:orgId IS NULL OR r.orgid = :orgId)    \r\n"
			+ "  AND (\r\n" + "        :search IS NULL OR :search = '' OR (\r\n"
			+ "		   LOWER(r.industryname)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.industrycode)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.phonenumber)              LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.email)          LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "        ))", countQuery = "SELECT count(*)\r\n" + "FROM industry r\r\n"
					+ "WHERE (:orgId IS NULL OR r.orgid = :orgId)    \r\n" + "  AND (\r\n"
					+ "        :search IS NULL OR :search = '' OR (\r\n"
					+ "		   LOWER(r.industryname)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.industrycode)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.phonenumber)              LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.email)          LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "        ))", nativeQuery = true)
	Page<IndustryVO> getAllIndustryByOrgId(@Param("orgId") Long orgId, @Param("search") String search,
			Pageable pageable);

	@Query(nativeQuery = true, value = "select * from industry where industryid=?1   ")
	IndustryVO getIndustryById(Long id);

	IndustryVO findByEmail(String email);
	
	IndustryVO findByIndustryTypeAndId(String type,Long id);

}
