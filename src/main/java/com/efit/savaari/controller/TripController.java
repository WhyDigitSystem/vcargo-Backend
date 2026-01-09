package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.TripDTO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.service.TripService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/trip")
public class TripController extends BaseController {

    @Autowired
    private TripService tripService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody TripDTO dto) {

        Map<String, Object> map = new HashMap<>();

        TripVO trip = tripService.saveOrUpdate(dto);

        map.put(CommonConstant.STRING_MESSAGE,
                dto.getTripId() == null
                        ? "Trip created successfully"
                        : "Trip updated successfully");
        map.put("trip", trip);

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @GetMapping("/byVehicle")
    public ResponseEntity<ResponseDTO> byVehicle(@RequestParam Long vehicleId) {

        Map<String, Object> map = new HashMap<>();
        map.put("tripList", tripService.getByVehicle(vehicleId));

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @GetMapping("/byDriver")
    public ResponseEntity<ResponseDTO> byDriver(@RequestParam Long driverId) {

        Map<String, Object> map = new HashMap<>();
        map.put("tripList", tripService.getByDriver(driverId));

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam Long tripId) {

        tripService.delete(tripId);
        return ResponseEntity.ok(createServiceResponse(new HashMap<>()));
    }
}

