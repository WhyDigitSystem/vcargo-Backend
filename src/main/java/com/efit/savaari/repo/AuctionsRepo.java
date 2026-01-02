package com.efit.savaari.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.AuctionsVO;

@Repository
public interface AuctionsRepo extends JpaRepository<AuctionsVO, Long> {

	@Query(
		    value =
		        "SELECT a.* FROM auctions a " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR a.branchcode = :branchCode) AND a.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(a.auctionstype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.loading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.unloading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.vehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.vehiclequantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.loadingdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.loadingtime) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.unloadingdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.duration AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.startdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.enddate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.material) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.materialquantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.materialweight AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.weightunit) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.dimension AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.transportertag) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.numtransporter AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.excludetransporters) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.shortcuts) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.bidtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.minbiddifference AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.allowedbids) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.maxprice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.minprice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.additionalcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    countQuery =
		        "SELECT COUNT(*) FROM auctions a " +
		        "WHERE (:branchCode IS NULL OR :branchCode = '' OR a.branchcode = :branchCode) AND a.orgid = :orgId " +
		        "AND ( " +
		        "   :search IS NULL OR :search = '' OR " +
		        "   LOWER(a.auctionstype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.loading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.unloading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.vehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.vehiclequantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.loadingdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.loadingtime) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.unloadingdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.duration AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.startdate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.enddate AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.material) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.materialquantity AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   CAST(a.materialweight AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.weightunit) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.dimension AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.transportertag) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.numtransporter AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.excludetransporters) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.shortcuts) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.bidtype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   CAST(a.minbiddifference AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
		        "   LOWER(a.allowedbids) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.maxprice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.minprice) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.additionalcharges) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.branchcode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "   LOWER(a.branch) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        ") ",
		    nativeQuery = true
		)
		Page<AuctionsVO> getAuctionsByOrgId(
		        @Param("branchCode") String branchCode,
		         @Param("orgId") Long orgId, @Param("search") String search,
		        Pageable pageable
		);

	@Query(
		    value =
		        "SELECT " +
		        "    a.auctionsid, " +
		        "    a.auctionstype, " +
		        "    a.roundtrip, " +
		        "    a.customgeofence, " +
		        "    a.loading, " +
		        "    a.unloading, " +
		        "    a.vehicle, " +
		        "    a.vehiclequantity, " +
		        "    a.loadingdate, " +
		        "    a.loadingtime, " +
		        "    a.unloadingdate, " +
		        "    a.duration, " +
		        "    a.startdate, " +
		        "    a.enddate, " +
		        "    a.material, " +
		        "    a.materialquantity, " +
		        "    a.materialweight, " +
		        "    a.weightunit, " +
		        "    a.dimension, " +
		        "    a.transportertag, " +
		        "    a.numtransporter, " +
		        "    a.excludetransporters, " +
		        "    a.shortcuts, " +
		        "    a.terms, " +
		        "    a.documents, " +
		        "    a.organizationname, " +
		        "    a.bidtype, " +
		        "    a.minbiddifference, " +
		        "    a.allowedbids, " +
		        "    a.maxprice, " +
		        "    a.minprice, " +
		        "    a.additionalcharges, " +
		        "    a.active, " +
		        "    a.createdby, " +
		        "    a.modifiedby, " +
		        "    a.orgid, " +
		        "    a.branchcode, " +
		        "    a.branch, " +
		        "    a.screencode, " +
		        "    a.screenname, " +

		        // quote status
		        "    CASE WHEN EXISTS ( " +
		        "        SELECT 1 FROM quote q " +
		        "        WHERE q.userid = :userid AND q.auctionsid = a.auctionsid " +
		        "    ) THEN 1 ELSE 0 END AS status, " +

		        // auction running status
		        "    CASE " +
		        "        WHEN NOW() < a.startdate THEN 0 " +
		        "        WHEN NOW() BETWEEN a.startdate AND DATE_ADD(a.startdate, INTERVAL a.duration MINUTE) THEN 1 " +
		        "        ELSE 2 " +
		        "    END AS auctionstatus " +

		        "FROM auctions a " +
		        "JOIN users u ON u.userid = :userid " +
		        "LEFT JOIN vendor v ON v.vendorid = u.vendorid " +

		        "WHERE a.active = 1 " +

		        "AND ( " +
		        "       u.orgid IS NULL " +               // open transporter
		        "       OR (v.organization IS NOT NULL " + // included transporter
		        "           AND v.orgid = a.orgid " +
		        "           AND a.transportertag LIKE CONCAT('%', v.organization, '%') " +
		        "       ) " +
		        ") " +

		        "AND ( " +                                // not excluded
		        "       a.excludetransporters IS NULL " +
		        "       OR v.organization IS NULL " +
		        "       OR a.excludetransporters NOT LIKE CONCAT('%', v.organization, '%') " +
		        ") " +

		        // search filter
		        "AND ( " +
		        "       :search IS NULL OR :search = '' OR " +
		        "       LOWER(a.auctionstype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.loading)      LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.unloading)    LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.vehicle)       LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.material)      LIKE LOWER(CONCAT('%', :search, '%')) " +
		        "    )",
		    
		    countQuery =
		        "SELECT COUNT(*) " +
		        "FROM auctions a " +
		        "JOIN users u ON u.userid = :userid " +
		        "LEFT JOIN vendor v ON v.vendorid = u.vendorid " +

		        "WHERE a.active = 1 " +

		        "AND ( " +
		        "       u.orgid IS NULL " +
		        "       OR (v.organization IS NOT NULL " +
		        "           AND v.orgid = a.orgid " +
		        "           AND a.transportertag LIKE CONCAT('%', v.organization, '%') " +
		        "       ) " +
		        ") " +

		        "AND ( " +
		        "       a.excludetransporters IS NULL " +
		        "       OR v.organization IS NULL " +
		        "       OR a.excludetransporters NOT LIKE CONCAT('%', v.organization, '%') " +
		        ") " +

		        "AND ( " +
		        "       :search IS NULL OR :search = '' OR " +
		        "       LOWER(a.auctionstype) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.loading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.unloading) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.vehicle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		        "       LOWER(a.material) LIKE LOWER(CONCAT('%', :search, '%')) " +
		        "    )",
		    nativeQuery = true
		)
		Page<Object[]> getAuctionsReportByOrgId(
		        @Param("userid") Long userId,
		        @Param("search") String search,
		        Pageable pageable
		);


	@Query(value = 
		       "SELECT * FROM auctions " +
		       "WHERE auctionsid = :id " +
		       "AND active = 1 " +
		       "AND STR_TO_DATE(REPLACE(enddate,'T',' '), '%Y-%m-%d %H:%i:%s') >= NOW()",
		       nativeQuery = true)
		AuctionsVO validateAuctionActiveAndNotExpired(@Param("id") Long id);

}
