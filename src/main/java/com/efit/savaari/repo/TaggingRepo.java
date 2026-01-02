package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TaggingVO;

@Repository
public interface TaggingRepo extends JpaRepository<TaggingVO, Long>{

	@Query(
		    value = "SELECT t.* FROM tagging t " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    
		    countQuery =
		            "SELECT COUNT(*) FROM tagging t " +
		            "WHERE (:branchCode IS NULL OR :branchCode = '' OR t.branchcode = :branchCode) " +
		            "AND (" +
		            "   :search IS NULL OR :search = '' OR " +
		            "   LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		            "   LOWER(t.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		            ")",
		    nativeQuery = true
		)
		Page<TaggingVO> getTaggingByOrgId(
		        @Param("branchCode") String branchCode,
		        @Param("search") String search,
		        Pageable pageable
		);

}
