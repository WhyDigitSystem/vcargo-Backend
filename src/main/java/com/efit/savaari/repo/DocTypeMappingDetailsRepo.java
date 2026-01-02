package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.DocTypeMappingDetailsVO;

public interface DocTypeMappingDetailsRepo extends JpaRepository<DocTypeMappingDetailsVO, Long> {

//	DocTypeMappingDetailsVO findByBranchAndScreenCode(String branch, String screenCode);

	DocTypeMappingDetailsVO findByBranchCodeAndScreenCode(String branchCode, String screenCode);


}