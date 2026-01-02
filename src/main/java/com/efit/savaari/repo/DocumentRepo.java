package com.efit.savaari.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.DocumentVO;

@Repository
public interface DocumentRepo extends JpaRepository<DocumentVO, Long> {

	@Query(value = "SELECT r.*\r\n" + "FROM document r\r\n"
			+ "WHERE (:orgId IS NULL OR r.orgid = :orgId) and (:userId IS NULL OR r.userid = :userId)    \r\n"
			+ "  AND (\r\n" + "        :search IS NULL OR :search = '' OR (\r\n"
			+ "		   LOWER(r.name)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.type)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.issuedate)              LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "          OR LOWER(r.userid)          LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
			+ "        ))", countQuery = "SELECT count(*)\r\n" + "FROM document r\r\n"
					+ "WHERE (:orgId IS NULL OR r.orgid = :orgId)  and (:userId IS NULL OR r.userid = :userId)   \r\n"
					+ "  AND (\r\n" + "        :search IS NULL OR :search = '' OR (\r\n"
					+ "		   LOWER(r.name)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.type)       LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.issuedate)              LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "          OR LOWER(r.userid)          LIKE LOWER(CONCAT('%', :search, '%'))\r\n"
					+ "        ))", nativeQuery = true)
	Page<DocumentVO> getAllDocumentByUserId(@Param("orgId") Long orgId, @Param("userId") Long userId,
			@Param("search") String search, Pageable pageable);

	@Query(nativeQuery = true, value = "select * from document where documentid=?1   ")
	DocumentVO getDocumentById(Long id);

	boolean existsByDocumentNoAndOrgId(String documentNo, Long orgId);

	@Query(nativeQuery = true, value = "select sum(totaldocument) totaldocument,sum(actives) actives,sum(expiringsoon) expiringsoon,sum(expired) expired,sum(verified) verified,sum(pending) pending  from(\r\n"
			+ "select count(*) totaldocument,0 actives,0 expiringsoon,0 expired,0 verified,0 pending from document where orgid=?1 and branchcode=?2 and userid=?3\r\n"
			+ "union \r\n"
			+ "select 0 totaldocument,count(*) actives,0 expiringsoon,0 expired,0 verified,0 pending from document where orgid=?1 and branchcode=?2 and userid=?3 and status='Active'\r\n"
			+ "union\r\n"
			+ "select 0 totaldocument,0 actives,count(*) expiringsoon,0 expired,0 verified,0 pending from document where orgid=?1 and branchcode=?2 and userid=?3 and status='Expiring Soon'\r\n"
			+ "union\r\n"
			+ "select 0 totaldocument,0 actives, 0 expiringsoon,count(*) expired,0 verified,0 pending from document where orgid=?1 and branchcode=?2 and userid=?3 and status='Expired'\r\n"
			+ "union \r\n"
			+ "select 0 totaldocument,0 actives, 0 expiringsoon,0 expired,count(*) verified,0 pending from document where orgid=?1 and branchcode=?2 and userid=?3 and varifiedstatus='Verified'\r\n"
			+ "union \r\n"
			+ "select 0 totaldocument,0 actives, 0 expiringsoon,0 expired,0 verified,count(*) pending from document where orgid=?1 and branchcode=?2 and userid=?3 and varifiedstatus='Pending'\r\n"
			+ ") a")
	Set<Object[]> getDocumentCount(Long orgId, String branchCode, Long userId);

	@Query(nativeQuery = true, value = "select sum(vehicledocument) vehicledocument,sum(driverdocument) driverdocument,sum(invoicedocument) invoicedocument,sum(insurancedocument) insurancedocument,sum(taxdocument) taxdocument,sum(routedocument) routedocument  from(\r\n"
			+ "select count(*) vehicledocument,0 driverdocument,0 invoicedocument,0 insurancedocument,0 taxdocument,0 routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='Vehicle RC'\r\n"
			+ "union \r\n"
			+ "select 0 vehicledocument,count(*) driverdocument,0 invoicedocument,0 insurancedocument,0 taxdocument,0 routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='Driver License'\r\n"
			+ "union\r\n"
			+ "select 0 vehicledocument,0 driverdocument,count(*) invoicedocument,0 insurancedocument,0 taxdocument,0 routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='invoice'\r\n"
			+ "union\r\n"
			+ "select 0 vehicledocument,0 driverdocument, 0 invoicedocument,count(*) insurancedocument,0 taxdocument,0 routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='Insurance Policy'\r\n"
			+ "union \r\n"
			+ "select 0 vehicledocument,0 driverdocument, 0 invoicedocument,0 insurancedocument,count(*) taxdocument,0 routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='Tax Receipt'\r\n"
			+ "union \r\n"
			+ "select 0 vehicledocument,0 driverdocument, 0 invoicedocument,0 insurancedocument,0 taxdocument,count(*) routedocument from document where orgid=?1 and branchcode=?2 and userid=?3 and type='Route Permit'\r\n"
			+ " ) a")
	Set<Object[]> getDocumentTypeCount(Long orgId, String branchCode, Long userId);
	
	@Query(nativeQuery = true, value = "select * from document where orgid=?1 and branchcode=?2 and userid=?3 and \r\n"
			+ "((associatedwith like concat('%',?4,'%') or associationtype like concat('%',?4,'%') or name like concat('%',?4,'%') or type like concat('%',?4,'%') or documentno like concat('%',?4,'%')\r\n"
			+ "or tags like concat('%',?4,'%')) or ?4 is null) and (status =?5 or 'ALL'=?5)  and (type=?6 or 'ALL'=?6)")
	List<DocumentVO> getAllDocumentList(Long orgId, String branchCode, Long userId,String search,String status,String type);

}
