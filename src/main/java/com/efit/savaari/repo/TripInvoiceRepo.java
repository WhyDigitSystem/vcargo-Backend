package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.VehicleVO;

public interface TripInvoiceRepo extends JpaRepository<TripInvoiceVO, Long> {

}


