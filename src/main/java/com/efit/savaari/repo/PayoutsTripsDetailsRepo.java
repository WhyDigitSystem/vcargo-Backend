package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PayoutTripsDetailsVO;
import com.efit.savaari.entity.PayoutsVO;

@Repository
public interface PayoutsTripsDetailsRepo extends JpaRepository<PayoutTripsDetailsVO, Long>{

	List< PayoutTripsDetailsVO> findByPayoutsVO(PayoutsVO payoutsVO);

}
