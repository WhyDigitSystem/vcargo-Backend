package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.MaintenanceVO;

@Repository
public interface MaintenanceRepo extends JpaRepository<MaintenanceVO, Long> {

    List<MaintenanceVO> findByVehicleId(Long vehicleId);

    List<MaintenanceVO> findByStatus(String status);
}