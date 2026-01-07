package com.efit.savaari.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelMasterDTO;
import com.efit.savaari.entity.FuelMasterVO;
import com.efit.savaari.repo.FuelMasterRepo;

@Service
public class FuelMasterServiceImpl implements FuelMasterService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FuelMasterServiceImpl.class);

    @Autowired
    private FuelMasterRepo fuelMasterRepo;

    @Override
    @Transactional
    public FuelMasterVO createFuelEntry(FuelMasterDTO dto) {

        FuelMasterVO fuel = new FuelMasterVO();

        fuel.setVehicleId(dto.getVehicleId());
        fuel.setDriverId(dto.getDriverId());
        fuel.setFuelType(dto.getFuelType());
        fuel.setQuantity(dto.getQuantity());
        fuel.setCost(dto.getCost());
        fuel.setOdometerReading(dto.getOdometerReading());
        fuel.setPreviousOdometer(dto.getPreviousOdometer());
        fuel.setStation(dto.getStation());

        // Convert String → LocalDate / LocalTime
        fuel.setDate(LocalDate.parse(dto.getDate()));
        fuel.setTime(LocalTime.parse(dto.getTime()));

        fuel.setReceiptNumber(dto.getReceiptNumber());
        fuel.setNotes(dto.getNotes());

        LOGGER.info("Saving fuel entry for vehicle {}", dto.getVehicleId());

        return fuelMasterRepo.save(fuel);
    }

    @Override
    public List<FuelMasterVO> getFuelByVehicle(Long vehicleId) {
        return fuelMasterRepo.findByVehicleId(vehicleId);
    }

    @Override
    public List<FuelMasterVO> getFuelByDriver(Long driverId) {
        return fuelMasterRepo.findByDriverId(driverId);
    }

    @Override
    public FuelMasterVO getFuelById(Long fuelId) {
        return fuelMasterRepo.findById(fuelId).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteFuelEntry(Long fuelId) {
        fuelMasterRepo.deleteById(fuelId);
    }
}
