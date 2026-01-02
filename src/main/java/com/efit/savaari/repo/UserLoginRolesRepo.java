package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.UserLoginRolesVO;
import com.efit.savaari.entity.UserVO;


public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

//	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

//	UserLoginRolesVO findByUserVO_EmployeeCodeAndUserVO_OrgId(String employeeCode, Long orgId);

}