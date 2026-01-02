package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PayoutsDocumentVO;
import com.efit.savaari.entity.PayoutsVO;

@Repository
public interface PayoutsDocumentRepo extends JpaRepository<PayoutsDocumentVO, Long>{

	List< PayoutsDocumentVO> findByPayoutsVO(PayoutsVO payoutsVO);

}
