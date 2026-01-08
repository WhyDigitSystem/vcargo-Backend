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
import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.service.MaintenanceService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController extends BaseController {

    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody MaintenanceDTO dto) {

        Map<String, Object> map = new HashMap<>();

        MaintenanceVO m = maintenanceService.saveOrUpdate(dto);

        map.put(CommonConstant.STRING_MESSAGE,
                dto.getMaintenanceId() == null
                    ? "Maintenance created successfully"
                    : "Maintenance updated successfully");
        map.put("maintenance", m);

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @GetMapping("/byVehicle")
    public ResponseEntity<ResponseDTO> getByVehicle(@RequestParam Long vehicleId) {

        Map<String, Object> map = new HashMap<>();
        map.put("maintenanceList", maintenanceService.getByVehicle(vehicleId));

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam Long maintenanceId) {

        maintenanceService.delete(maintenanceId);
        return ResponseEntity.ok(createServiceResponse(new HashMap<>()));
    }
}

