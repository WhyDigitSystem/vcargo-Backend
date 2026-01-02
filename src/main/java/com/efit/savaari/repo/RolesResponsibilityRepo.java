package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efit.savaari.entity.RolesResponsibilityVO;
import com.efit.savaari.entity.RolesVO;


public interface RolesResponsibilityRepo extends JpaRepository<RolesResponsibilityVO, Long>{

	List<RolesResponsibilityVO> findByRolesVO(RolesVO rolesVO);

}
