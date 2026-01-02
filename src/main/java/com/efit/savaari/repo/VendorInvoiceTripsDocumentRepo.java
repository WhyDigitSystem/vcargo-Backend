package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorInvoiceTripsDocumentVO;

@Repository
public interface VendorInvoiceTripsDocumentRepo extends JpaRepository<VendorInvoiceTripsDocumentVO, Long>{

	List<VendorInvoiceTripsDocumentVO> findByVendorInvoiceVO_Id(Long id);

}
