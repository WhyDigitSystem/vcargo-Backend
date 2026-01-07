package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.entity.FuelMasterVO;

@Service
public interface FuelMasterService {

    FuelMasterVO createFuelEntry(FuelMasterDTO dto);

    List<FuelMasterVO> getFuelByVehicle(Long vehicleId);

    List<FuelMasterVO> getFuelByDriver(Long driverId);

    FuelMasterVO getFuelById(Long fuelId);

    void deleteFuelEntry(Long fuelId);
}
