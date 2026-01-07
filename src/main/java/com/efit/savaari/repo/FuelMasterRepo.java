package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.FuelMasterVO;

@Repository
public interface FuelMasterRepo extends JpaRepository<FuelMasterVO, Long> {

    List<FuelMasterVO> findByVehicleId(Long vehicleId);

    List<FuelMasterVO> findByDriverId(Long driverId);
}
