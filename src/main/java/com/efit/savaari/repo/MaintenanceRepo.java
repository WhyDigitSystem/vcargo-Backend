package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.MaintenanceVO;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceVO, Long> {

	
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1")
	List<MaintenanceVO> getMaintenanceByOrgId(Long orgId);

	 
	 @Query(nativeQuery = true, value = "select * from maintenance a where a.orgid=?1  ORDER BY a.maintenanceid DESC LIMIT 5")
	 List<MaintenanceVO> findByOrgId(Long orgId);
	 
}