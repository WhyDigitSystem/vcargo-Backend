package com.efit.savaari.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.EwayBillResponseVO;

@Repository
public interface EwayBillResponseRepo extends JpaRepository<EwayBillResponseVO, Long> {

	@Query(nativeQuery = true, value = "select productname,productdesc,hsncode,quantity,qtyunit,cgstrate,sgstrate,igstrate,cessrate,cessnonadvol,taxableamount from EWAYBILL_REQUEST where docno=?1\r\n"
			+ "group by productname,productdesc,hsncode,quantity,qtyunit,cgstrate,sgstrate,igstrate,cessrate,cessnonadvol,taxableamount")
	List<Object[]> getItemListDetails(String docId);

	@Query(nativeQuery = true, value = "SELECT a.user_name, a.gstin, a.CLIENT_ID, a.CLIENT_SECRET, a.AUTHTOKEN, a.SEK \r\n"
			+ "            FROM einvoiceheader a, EWAYBILL_REQUEST b \r\n"
			+ "           WHERE a.GSTIN = b.FROMGSTIN AND b.docno =?1 \r\n"
			+ "           GROUP BY a.user_name, a.gstin, a.CLIENT_ID, a.CLIENT_SECRET, a.AUTHTOKEN, a.SEK")
	Set<Object[]> getEwayHeaderDetails(String docId);

	@Query(nativeQuery = true, value = "select docno,docdate from EWAYBILL_REQUEST where genewaybill='T' and eapicall='F'  group by docno,docdate")
	List<Object[]> getPendingEwayNonIRNDetails();

}
