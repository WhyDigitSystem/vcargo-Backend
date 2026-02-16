package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CompanyAddressVO;
import com.efit.savaari.entity.CompanyProfileVO;

@Repository
public interface CompanyAddressRepo extends JpaRepository<CompanyAddressVO, Long>{

	List<CompanyAddressVO> findByCompanyProfileVO(CompanyProfileVO vo);

}
