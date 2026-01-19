package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CompanyAddressVO;

@Repository
public interface CompanyAddressRepo extends JpaRepository<CompanyAddressVO, Long>{

}
