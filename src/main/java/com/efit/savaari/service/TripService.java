package com.efit.savaari.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.entity.TripVO;

@Service
public interface TripService {

    TripVO saveOrUpdate(TripDTO dto);

    List<TripVO> getByVehicle(Long vehicleId);

    List<TripVO> getByDriver(Long driverId);

    void delete(Long tripId);
}

