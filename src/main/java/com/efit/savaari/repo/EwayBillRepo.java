package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.EwayBillVO;

@Repository
public interface EwayBillRepo extends JpaRepository<EwayBillVO, Long> {

	EwayBillVO findByDocNo(String docId);
}
