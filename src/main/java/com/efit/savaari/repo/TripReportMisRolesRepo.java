package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripReportMisRolesVO;
import com.efit.savaari.entity.TripReportMisVO;

@Repository 
public interface TripReportMisRolesRepo extends JpaRepository<TripReportMisRolesVO, Long>{

	List< TripReportMisRolesVO> findByTripReportMisVO(TripReportMisVO vo);

}
