package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CompanyBankDetailsVO;
import com.efit.savaari.entity.CompanyProfileVO;

@Repository
public interface CompanyBankDetailsRepo extends JpaRepository< CompanyBankDetailsVO, Long> {

	List<CompanyBankDetailsVO> findByCompanyProfileVO(CompanyProfileVO vo);

}
