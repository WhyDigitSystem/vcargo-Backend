package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TrackingTokenVO;

@Repository
public interface TrackingTokenRepo extends JpaRepository<TrackingTokenVO, Long> {

    TrackingTokenVO findTopByOrderByIdDesc();   // get latest token


}
