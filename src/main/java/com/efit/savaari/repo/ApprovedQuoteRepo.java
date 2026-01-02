package com.efit.savaari.repo;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.ApprovedQuoteVO;

@Repository
public interface ApprovedQuoteRepo extends JpaRepository<ApprovedQuoteVO, Long> {

	@Query(
		    value = "SELECT \r\n"
		    		+ "            ac.auctionsid,\r\n"
		    		+ "            ac.auctionstype,\r\n"
		    		+ "            ac.loading,\r\n"
		    		+ "            ac.unloading,\r\n"
		    		+ "            ac.additionalcharges,\r\n"
		    		+ "            ac.allowedbids,\r\n"
		    		+ "            ac.bidtype,\r\n"
		    		+ "            ac.dimension,\r\n"
		    		+ "            ac.material,\r\n"
		    		+ "            ac.materialquantity,\r\n"
		    		+ "            ac.materialweight,\r\n"
		    		+ "            ac.maxprice,\r\n"
		    		+ "            ac.minbiddifference,\r\n"
		    		+ "            ac.minprice,\r\n"
		    		+ "            ac.shortcuts,\r\n"
		    		+ "            ac.unloadingdate,\r\n"
		    		+ "            ac.vehicle,\r\n"
		    		+ "            ac.vehiclequantity,\r\n"
		    		+ "            ac.weightunit,\r\n"
		    		+ "            \r\n"
		    		+ "            av.organizationname AS approvedVendor,\r\n"
		    		+ "            a.approvedamount,\r\n"
		    		+ "            \r\n"
		    		+ "            mn.organizationname AS minVendor,\r\n"
		    		+ "            a.minquotedamount,\r\n"
		    		+ "            \r\n"
		    		+ "            mx.organizationname AS maxVendor,\r\n"
		    		+ "            a.maxquotedamount\r\n"
		    		+ "            \r\n"
		    		+ "        FROM approvedquote a\r\n"
		    		+ "        LEFT JOIN users av ON a.approveduserid = av.userid\r\n"
		    		+ "        LEFT JOIN users mn ON a.minquoteduserid = mn.userid\r\n"
		    		+ "        LEFT JOIN users mx ON a.maxquoteduserid = mx.userid\r\n"
		    		+ "        LEFT JOIN auctions ac \r\n"
		    		+ "            ON a.auctionsid = ac.auctionsid \r\n"
		    		+ "           AND ac.orgid = a.orgid\r\n"
		    		+ "        WHERE a.orgid = :orgid",
		    countQuery = "SELECT COUNT(*)\r\n"
		    		+ "        FROM approvedquote a\r\n"
		    		+ "        LEFT JOIN auctions ac \r\n"
		    		+ "            ON a.auctionsid = ac.auctionsid \r\n"
		    		+ "           AND ac.orgid = a.orgid\r\n"
		    		+ "        WHERE a.orgid = :orgid",
		    nativeQuery = true
		)
		Page<Map<String, Object>> getApprovedQuotesByOrg(
		        @Param("orgid") Long orgid,
		        Pageable pageable);

}
