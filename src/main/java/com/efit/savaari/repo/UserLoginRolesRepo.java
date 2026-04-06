package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.UserLoginRolesVO;


public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

//	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

//	UserLoginRolesVO findByUserVO_EmployeeCodeAndUserVO_OrgId(String employeeCode, Long orgId);

}