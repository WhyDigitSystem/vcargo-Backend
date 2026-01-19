package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CompanyProfileVO;

@Repository
public interface CompanyProfileRepo extends JpaRepository<CompanyProfileVO, Long>{

	@Query(nativeQuery = true, value = "select a.* from companyprofile a where a.orgid=?1", countQuery = "select a.* from trip a where a.orgid=:orgId")
	Page<CompanyProfileVO> getAllCompanyProfileByOrgId(Long orgId, Pageable pageable);


}
