package com.efit.savaari.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.entity.TripDocumentVO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.entity.TripWaypointVO;
import com.efit.savaari.repo.TripRepo;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepo tripRepo;

    @Override
    @Transactional
    public TripVO saveOrUpdate(TripDTO dto) {

        TripVO trip;

        if (dto.getTripId() != null) {
            trip = tripRepo.findById(dto.getTripId())
                    .orElseThrow(() -> new RuntimeException("Trip not found"));
            trip.getWaypoints().clear();
            trip.getDocuments().clear();
        } else {
            trip = new TripVO();
        }

        trip.setCustomerId(dto.getCustomerId());
        trip.setVehicleId(dto.getVehicleId());
        trip.setDriverId(dto.getDriverId());
        trip.setSource(dto.getSource());
        trip.setDestination(dto.getDestination());
        trip.setDistance(dto.getDistance());
        trip.setEstimatedDuration(dto.getEstimatedDuration());
        trip.setStartDate(LocalDate.parse(dto.getStartDate()));
        trip.setStartTime(LocalTime.parse(dto.getStartTime()));
        trip.setEndDate(dto.getEndDate() != null && !dto.getEndDate().isEmpty()
                ? LocalDate.parse(dto.getEndDate()) : null);
        trip.setEndTime(dto.getEndTime() != null && !dto.getEndTime().isEmpty()
                ? LocalTime.parse(dto.getEndTime()) : null);
        trip.setStatus(dto.getStatus());
        trip.setTripType(dto.getTripType());
        trip.setGoodsType(dto.getGoodsType());
        trip.setGoodsWeight(dto.getGoodsWeight());
        trip.setGoodsValue(dto.getGoodsValue());
        trip.setTripCost(dto.getTripCost());
        trip.setRevenue(dto.getRevenue());
        trip.setProfit(dto.getProfit());
        trip.setFuelCost(dto.getFuelCost());
        trip.setTollCharges(dto.getTollCharges());
        trip.setOtherExpenses(dto.getOtherExpenses());
        trip.setNotes(dto.getNotes());

        trip.setWaypoints(dto.getWaypoints().stream().map(w -> {
            TripWaypointVO wp = new TripWaypointVO();
            wp.setLocation(w.getLocation());
            wp.setSequenceNo(w.getSequenceNo());
            wp.setTripVO(trip);
            return wp;
        }).toList());

        trip.setDocuments(dto.getDocuments().stream().map(d -> {
            TripDocumentVO doc = new TripDocumentVO();
            doc.setDocumentType(d.getDocumentType());
            doc.setDocument(d.getDocument());
            doc.setTripVO(trip);
            return doc;
        }).toList());

        return tripRepo.save(trip);
    }

    @Override
    public List<TripVO> getByVehicle(Long vehicleId) {
        return tripRepo.findByVehicleId(vehicleId);
    }

    @Override
    public List<TripVO> getByDriver(Long driverId) {
        return tripRepo.findByDriverId(driverId);
    }

    @Override
    @Transactional
    public void delete(Long tripId) {
        tripRepo.deleteById(tripId);
    }
}
