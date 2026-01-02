package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorInvoiceChargesVO;

@Repository
public interface VendorInvoiceChargesRepo extends JpaRepository<VendorInvoiceChargesVO, Long>{

	List<VendorInvoiceChargesVO> findByVendorInvoiceVO_Id(Long id);

}
