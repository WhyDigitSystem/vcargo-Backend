package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripVO;

@Repository
public interface TripRepo extends JpaRepository<TripVO, Long> {

	@Query(nativeQuery = true, value = "select a.* from trip a where a.orgid=:orgId", countQuery = "select a.* from trip a where a.orgid=:orgId")
	Page<TripVO> getTripByOrgId(Long orgId, Pageable pageable);

	@Query(nativeQuery = true, value = "select * from trip a where a.orgid=?1  ORDER BY a.tripid DESC LIMIT 5")
    List<TripVO> findByOrgId(Long orgId);
}
