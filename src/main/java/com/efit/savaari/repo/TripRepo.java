package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TripVO;

@Repository
public interface TripRepo extends JpaRepository<TripVO, Long> {

    List<TripVO> findByVehicleId(Long vehicleId);

    List<TripVO> findByDriverId(Long driverId);

    List<TripVO> findByStatus(String status);
}
