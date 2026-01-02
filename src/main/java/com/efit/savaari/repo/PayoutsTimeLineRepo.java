package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PayoutsTimeLineVO;
import com.efit.savaari.entity.PayoutsVO;

@Repository
public interface PayoutsTimeLineRepo extends JpaRepository< PayoutsTimeLineVO, Long>{

	List< PayoutsTimeLineVO> findByPayoutsVO(PayoutsVO payoutsVO);

}
