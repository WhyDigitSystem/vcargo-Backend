package com.efit.savaari.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efit.savaari.entity.TypeMasterVO;

@Repository
public interface TypeMasterRepo extends JpaRepository<TypeMasterVO, Long> {

    List<TypeMasterVO> findByVehicleId(Long vehicleId);

    List<TypeMasterVO> findByStatus(String status);
}
