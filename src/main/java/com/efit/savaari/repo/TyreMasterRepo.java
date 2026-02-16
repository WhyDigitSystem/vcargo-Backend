package com.efit.savaari.repo;

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

    @Query(nativeQuery = true,   value = "select count(*) from tyremaster where orgid=?1 ")
	Number getTyresPurchased(Long orgId);
    
}
