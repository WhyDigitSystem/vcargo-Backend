package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RoutesPetrolPumpsVO;
import com.efit.savaari.entity.RoutesVO;

@Repository
public interface RoutesPetrolPumpsRepo extends JpaRepository<RoutesPetrolPumpsVO, Long>{

	List<RoutesPetrolPumpsVO> findByRoutesVO(RoutesVO routesVO);

}
