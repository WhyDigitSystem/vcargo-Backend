package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.FuelVO;

@Repository
public interface FuelRepo extends JpaRepository<FuelVO, Long> {

    List<FuelVO> findByVehicleId(Long vehicleId);

    List<FuelVO> findByDriverId(Long driverId);

    @Query(nativeQuery = true, value = "select a.* from fuel a where   a.vehicle=:vehicleId",
    		countQuery = "select a.* from fuel a where   a.vehicle=:vehicleId")
	Page<FuelVO> getFuelByVehicle(Long vehicleId, Pageable pageable);

    @Query(nativeQuery = true, value = "select a.* from fuel a where   a.orgid=:orgId",
    		countQuery = "select a.* from fuel a where   a.orgid=:orgId")
	Page<FuelVO> getFuelByOrgId(Long orgId, Pageable pageable);
}
