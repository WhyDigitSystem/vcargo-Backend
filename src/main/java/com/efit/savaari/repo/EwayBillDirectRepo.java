package com.efit.savaari.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.EwayBillDirectVO;

@Repository
public interface EwayBillDirectRepo extends JpaRepository<EwayBillDirectVO, Long > {

	@Query(nativeQuery = true, value = "select * from EWAYBILL_REQUEST where docno in(?1)")
	List<EwayBillDirectVO> getDocidDetails(List<String> docId);

}
