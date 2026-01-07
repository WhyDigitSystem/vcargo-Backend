package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TyreMasterVO;

@Repository
public interface TyreMasterRepo extends JpaRepository<TyreMasterVO, Long> {

    List<TyreMasterVO> findByVehicleId(Long vehicleId);

    List<TyreMasterVO> findByStatus(String status);
}
