package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.TripInvoiceVO;

public interface TripInvoiceRepo extends JpaRepository<TripInvoiceVO, Long> {

	Page<TripInvoiceVO> getTripInvoiceByOrgId(Long orgId, Pageable pageable);

}


