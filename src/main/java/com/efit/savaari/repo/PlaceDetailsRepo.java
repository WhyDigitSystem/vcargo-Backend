package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.PlaceDetailsVO;

@Repository
public interface PlaceDetailsRepo extends JpaRepository<PlaceDetailsVO, Long>{

	PlaceDetailsVO findByPlaceAndOrgIdAndBranchCode(String place, Long orgId, String branchCode);

	boolean existsByPlaceAndOrgIdAndBranchCode(String place, Long orgId, String branchCode);

	@Query(
		    value = "SELECT p.* FROM placedetails p " +
		            "WHERE (?1 IS NULL OR p.branchcode = ?1) " +
		            "AND (" +
		            "   ?2 IS NULL OR ?2 = '' OR " +
		            "   LOWER(p.place) LIKE LOWER(CONCAT('%', ?2, '%')) " +
		            ")",
		    countQuery =
		            "SELECT COUNT(*) FROM placedetails p " +
		            "WHERE (?1 IS NULL OR p.branchcode = ?1) " +
		            "AND (" +
		            "   ?2 IS NULL OR ?2 = '' OR " +
		            "   LOWER(p.place) LIKE LOWER(CONCAT('%', ?2, '%')) " +
		            ")",
		    nativeQuery = true
		)
		Page<PlaceDetailsVO> getPlaceDetailsByOrgId(String branchCode, String search, Pageable pageable);
	

}
