package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.TripInvoiceVO;

public interface TripInvoiceRepo extends JpaRepository<TripInvoiceVO, Long> {

}
