package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorInvoiceTripsDetailsVO;

@Repository
public interface VendorInvoiceTripsDetailsRepo extends JpaRepository<VendorInvoiceTripsDetailsVO, Long>{

	List<VendorInvoiceTripsDetailsVO> findByVendorInvoiceVO_Id(Long id);

}
