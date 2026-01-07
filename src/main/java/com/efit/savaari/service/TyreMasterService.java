package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TyreMasterDTO;
import com.efit.savaari.entity.TyreMasterVO;

@Service
public interface TyreMasterService {

    TyreMasterVO saveOrUpdateType(TyreMasterDTO dto);

    List<TyreMasterVO> getByVehicle(Long vehicleId);

    List<TyreMasterVO> getByStatus(String status);

    void deleteType(Long typeId);
}
