package com.efit.savaari.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.UserVO;
@Repository
public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUserNameOrEmail(String userName, String email);

	@Query("select a from UserVO a where a.userName=?1")
	UserVO findByUserName(String userName);

	@Query(value = "select u from UserVO u where u.id =?1")
	UserVO getUserById(Long usersId);


	UserVO findByUserNameOrEmailOrMobileNo(String userName, String userName2, String userName3);

	@Query(value = "select u from UserVO u where u.orgId =?1")
	List<UserVO> findAllByOrgId(Long orgId);

//	boolean existsByUserNameOrEmailOrMobileNo(String userName, String email, String email2);

//	UserVO findByEmployeeCodeAndOrgId(String employeeCode, Long orgId);

	boolean existsByUserName(String userName);
	
	
//	@Query(
//		    value = "SELECT " +
//		            " userid, employeename, employeecode, email, department, designation, " +
//		            " branch, branchcode, active, status, usertype, orgid " +
//		            " FROM users " +
//		            " WHERE orgid = :orgId " +
//		            "   AND (:branchCode IS NULL OR branchcode = :branchCode) " +
//		            "   AND ( " +
//		            "        LOWER(employeename) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(employeecode) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(email) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(department) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(designation) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "   ) " +
//		            " ORDER BY employeename ASC",
//		    countQuery = "SELECT COUNT(*) FROM users " +
//		            " WHERE orgid = :orgId " +
//		            "   AND (:branchCode IS NULL OR branchcode = :branchCode) " +
//		            "   AND ( " +
//		            "        LOWER(employeename) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(employeecode) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(email) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(department) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "     OR LOWER(designation) LIKE LOWER(CONCAT('%', :search, '%')) " +
//		            "   )",
//		    nativeQuery = true
//		)
//		Page<Map<String, Object>> getUsersByOrgId(
//		        @org.springframework.data.repository.query.Param("orgId") Long orgId,
//		        @org.springframework.data.repository.query.Param("branchCode") String branchCode,
//		        @org.springframework.data.repository.query.Param("search") String search,
//		        org.springframework.data.domain.Pageable pageable
//		);




//    @Query(value = " SELECT COUNT(*) " +
//            " FROM users " +
//            " WHERE orgid = ?1 " +
//            "   AND (?2 IS NULL OR branchcode = ?2) " +
//            "   AND ( " +
//            "        LOWER(employeename) LIKE LOWER(CONCAT('%', ?3, '%')) " +
//            "     OR LOWER(employeecode) LIKE LOWER(CONCAT('%', ?3, '%')) " +
//            "     OR LOWER(email) LIKE LOWER(CONCAT('%', ?3, '%')) " +
//            "     OR LOWER(department) LIKE LOWER(CONCAT('%', ?3, '%')) " +
//            "     OR LOWER(designation) LIKE LOWER(CONCAT('%', ?3, '%')) " +
//            "   ) ",
//            nativeQuery = true)
//    Long getTotalUserCount(Long orgId, String branchCode, String search);

	@Query(value = "SELECT u.* FROM users u " +
	        "WHERE  " +
	        " (:branchCode IS NULL OR u.branchcode = :branchCode) " +
	        "AND ( :search  Is NULL OR :search = '' OR (LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.mobileno) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.type) LIKE LOWER(CONCAT('%', :search, '%')) ))",
	        
	       countQuery = "SELECT COUNT(*) FROM users u " +
	        "WHERE " +
	        " (:branchCode IS NULL OR u.branchcode = :branchCode) " +
	        "AND ( :search  Is NULL OR :search = '' OR (LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.mobileno) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.organizationname) LIKE LOWER(CONCAT('%', :search, '%')) " +
	        "     OR LOWER(u.type) LIKE LOWER(CONCAT('%', :search, '%')) )) ",
	        
	        nativeQuery = true)
	Page<Map<String, Object>> getAllUsersList(
	        @Param("branchCode") String branchCode,
	        @Param("search") String search,
	        org.springframework.data.domain.Pageable pageable
	);


	Optional<UserVO> findByEmail(String email);

	UserVO findByIdAndUserName(Long id, String userName);

	@Query(nativeQuery = true, value = "select u.userid from users u left join vendor v on v.vendorid = u.vendorid left join  auctions a on a.orgid=v.orgid and a.orgid=?1  where   u.orgid IS NULL\r\n"
			+ "       OR (\r\n"
			+ "            v.organization IS NOT NULL\r\n"
			+ "            AND v.orgid = a.orgid\r\n"
			+ "            AND a.transportertag LIKE CONCAT('%', v.organization, '%')\r\n"
			+ "          ) group by u.userid")
	List<Long> findEligibleUserForQuotes(Long orgId);


//	UserVO findByUserNameAndUsersId(String userName, Long usersId);



	
	


}