package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.MaintenanceVO;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceVO, Long> {

 

	
	 @Query(nativeQuery = true, value = "select a.* from maintenance a where a.orgid=:orgId",countQuery = "select a.* from maintenance a where a.orgid=:orgId")
	Page<MaintenanceVO> getMaintenanceByOrgId(Long orgId, Pageable pageable);
}