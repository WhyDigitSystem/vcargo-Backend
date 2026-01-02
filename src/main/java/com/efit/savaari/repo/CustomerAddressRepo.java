package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.CustomerAddressVO;
import com.efit.savaari.entity.CustomerVO;

@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddressVO, Long> {

	List< CustomerAddressVO> findByCustomerVO(CustomerVO vo);;

}
