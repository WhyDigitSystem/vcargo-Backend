package com.efit.savaari.repo;

import java.math.BigDecimal;
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

    @Query(nativeQuery = true, value = "select a.* from fuel a where   a.orgid=?1 ")
	List<FuelVO> getFuelByOrgId(Long orgId);
	
	 @Query(nativeQuery = true, value = "select * from fuel a where a.orgid=?1  ORDER BY a.fuelid DESC LIMIT 5")
	 List<FuelVO> findByOrgId(Long orgId);

	 @Query(value = "SELECT SUM(quantity) FROM fuel WHERE orgid = ?1 and active=1", nativeQuery = true)
	 BigDecimal getTotalFuel(Long orgId);

	 @Query(value = "select sum(cost) from fuel where orgid=?1 and active=1", nativeQuery = true)
	 BigDecimal gettotalFuelAmount(Long orgId);
}
