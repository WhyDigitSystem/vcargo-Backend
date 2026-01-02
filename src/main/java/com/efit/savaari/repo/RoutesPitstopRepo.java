package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RoutesPitstopVO;
import com.efit.savaari.entity.RoutesVO;

@Repository
public interface RoutesPitstopRepo extends JpaRepository<RoutesPitstopVO, Long>{

	List<RoutesPitstopVO> findByRoutesVO(RoutesVO routesVO);


}
