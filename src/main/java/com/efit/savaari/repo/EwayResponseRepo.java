package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.ErrorListVO;
import com.efit.savaari.entity.EwayResponseVO;

@Repository
public interface EwayResponseRepo extends JpaRepository<EwayResponseVO, Long> {

	@Query(nativeQuery = true, value = "select a.* from errorlist a where a.errorcode in(?1)")
	List<ErrorListVO> getErrorDetails(String l);

}
