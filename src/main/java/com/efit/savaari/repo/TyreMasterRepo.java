package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TyreMasterVO;

@Repository
public interface TyreMasterRepo extends JpaRepository<TyreMasterVO, Long> {

   

    @Query(nativeQuery = true, value = "select a.* from tyremaster a where a.orgid=:orgId",countQuery = "select a.* from tyremaster a where a.orgid=:orgId")
	Page<TyreMasterVO> getTyreByOrgId(Long orgId, Pageable pageable);
}
