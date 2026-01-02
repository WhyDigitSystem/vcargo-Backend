package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PayoutsVO;
import com.efit.savaari.entity.PayoutsVendorDetailsVO;

@Repository
public interface PayoutsVendorDetailsRepo extends JpaRepository<PayoutsVendorDetailsVO, Long> {

	List< PayoutsVendorDetailsVO> findByPayoutsVO(PayoutsVO payoutsVO);

}
