package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.VendorUsersVO;
import com.efit.savaari.entity.VendorVO;

@Repository
public interface VendorUsersRepo extends JpaRepository<VendorUsersVO, Long>{

	List<VendorUsersVO> findByVendorVO(VendorVO vendorVO);


}
