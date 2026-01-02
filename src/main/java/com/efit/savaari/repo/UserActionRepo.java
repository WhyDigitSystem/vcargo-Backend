package com.efit.savaari.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.UserActionVO;



@Repository
public interface UserActionRepo extends JpaRepository<UserActionVO, Long>{

}
