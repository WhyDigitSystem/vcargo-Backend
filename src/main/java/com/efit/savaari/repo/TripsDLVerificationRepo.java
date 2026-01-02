package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripsDLVerificationVO;
import com.efit.savaari.entity.TripsVO;

@Repository
public interface TripsDLVerificationRepo extends JpaRepository<TripsDLVerificationVO, Long>{

	List< TripsDLVerificationVO> findByTripsVO(TripsVO tripsVO);

}
