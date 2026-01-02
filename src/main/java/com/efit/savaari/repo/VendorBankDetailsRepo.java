package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorBankDetailsVO;
import com.efit.savaari.entity.VendorVO;

@Repository
public interface VendorBankDetailsRepo extends JpaRepository<VendorBankDetailsVO, Long> {

	List< VendorBankDetailsVO> findByVendorVO(VendorVO vendorVO);

}
