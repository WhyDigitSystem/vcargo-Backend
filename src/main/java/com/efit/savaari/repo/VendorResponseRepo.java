package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.IndentsVO;
import com.efit.savaari.entity.VendorResponseVO;

@Repository
public interface VendorResponseRepo extends JpaRepository<VendorResponseVO, Long>{

	List< VendorResponseVO> findByIndentsVO(IndentsVO indentsVO);

}
