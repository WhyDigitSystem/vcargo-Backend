package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.entity.MaintenanceVO;

@Service
public interface MaintenanceService {

    MaintenanceVO saveOrUpdate(MaintenanceDTO dto);

    List<MaintenanceVO> getByVehicle(Long vehicleId);

    void delete(Long maintenanceId);
}