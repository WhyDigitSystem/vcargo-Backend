package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.RolesPermissionHeaderVO;
import com.efit.savaari.entity.RolesPermissionVO;

@Repository
public interface RolePermissionRepo extends JpaRepository<RolesPermissionVO, Long> {

	List<RolesPermissionVO> findByRolesPermissionHeaderVO(RolesPermissionHeaderVO vo);


}