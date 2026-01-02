package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorDetailsVO;
import com.efit.savaari.entity.VendorVO;

@Repository
public interface VendorDetailsRepo extends JpaRepository<VendorDetailsVO, Long>{

	List<VendorDetailsVO> findByVendorVO(VendorVO vendorVO);

}
