package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorGstVO;
import com.efit.savaari.entity.VendorVO;

@Repository
public interface VendorGstRepo extends JpaRepository<VendorGstVO, Long>{

	List< VendorGstVO> findByVendorVO(VendorVO vendorVO);

}
