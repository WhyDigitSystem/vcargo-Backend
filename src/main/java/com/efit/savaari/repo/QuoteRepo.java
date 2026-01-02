package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.AuctionsVO;
import com.efit.savaari.entity.QuoteVO;
import com.efit.savaari.entity.UserVO;

@Repository
public interface QuoteRepo extends JpaRepository<QuoteVO, Long> {

	@Query(
		    value =
		        "SELECT q.* FROM quote q " +
		        "WHERE ( q.userid = :userid) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(q.quoteamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(q.additionalnotes) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    countQuery =
		        "SELECT COUNT(*) FROM quote q " +
		        "WHERE ( q.userid = :userid) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(q.quoteamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(q.additionalnotes) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    nativeQuery = true
		)
		Page<QuoteVO> getQuoteByUserId(
		    @Param("userid") Long userId,
		    @Param("search") String search,
		    Pageable pageable
		);

	QuoteVO findByIdAndAuctionId(Long id, Long auctionId);

	List<QuoteVO> findByAuctionId(Long auctionId);

	QuoteVO findTopByAuctionIdOrderByQuoteAmountDesc(Long auctionId);
	QuoteVO findTopByAuctionIdOrderByQuoteAmountAsc(Long auctionId);

	@Query(
		    value =
		        "SELECT q.* FROM quote q join auctions a on a.auctionsid=q.auctionsid " +
		        "WHERE ( a.userid = :userid) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(q.quoteamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(q.additionalnotes) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    countQuery =
		        "SELECT COUNT(*) FROM quote q " +
		        "LEFT JOIN auctions a ON q.auctionid = a.auctionsid " +
		        "WHERE ( a.userid = :userid) " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   CAST(q.quoteamount AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(q.additionalnotes) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.termsandconditions) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(q.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ")",
		    nativeQuery = true
		)
	Page<QuoteVO> getUserAuctionsQuoteByOrgId(
		    @Param("userid") Long userId,
		    @Param("search") String search,
		    Pageable pageable
		);


	List<QuoteVO> findAllByAuction(AuctionsVO auctionVO);

	List<QuoteVO> findAllByAuctionAndUserNot(AuctionsVO auctionVO, UserVO userVO);


}
