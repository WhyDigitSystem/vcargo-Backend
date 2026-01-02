package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripsPitStopVO;
import com.efit.savaari.entity.TripsVO;

@Repository
public interface TripsPitStopRepo extends JpaRepository<TripsPitStopVO, Long>{

	List< TripsPitStopVO> findByTripsVO(TripsVO tripsVO);

}
