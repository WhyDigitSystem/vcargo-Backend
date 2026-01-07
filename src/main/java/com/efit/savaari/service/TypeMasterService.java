package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TypeMasterDTO;
import com.efit.savaari.entity.TypeMasterVO;

@Service
public interface TypeMasterService {

    TypeMasterVO saveOrUpdateType(TypeMasterDTO dto);

    List<TypeMasterVO> getByVehicle(Long vehicleId);

    List<TypeMasterVO> getByStatus(String status);

    void deleteType(Long typeId);
}
