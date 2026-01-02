package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RoutesDetailsVO;
import com.efit.savaari.entity.RoutesVO;

@Repository
public interface RoutesDetailsRepo extends JpaRepository<RoutesDetailsVO, Long>{

	Iterable<? extends RoutesDetailsVO> findByRoutesVO(RoutesVO routesVO);

}
