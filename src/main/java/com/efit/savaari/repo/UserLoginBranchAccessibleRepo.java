package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.UserLoginBranchAccessibleVO;


public interface UserLoginBranchAccessibleRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
