package com.efit.savaari.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.MaintenanceDTO;
import com.efit.savaari.entity.MaintenancePartVO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.repo.MaintenanceRepo;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepo maintenanceRepo;

    @Override
    @Transactional
    public MaintenanceVO saveOrUpdate(MaintenanceDTO dto) {

        MaintenanceVO m;

        if (dto.getMaintenanceId() != null) {
            m = maintenanceRepo.findById(dto.getMaintenanceId())
                    .orElseThrow(() -> new RuntimeException("Maintenance not found"));
            m.getParts().clear();
        } else {
            m = new MaintenanceVO();
        }

        m.setTitle(dto.getTitle());
        m.setVehicleId(dto.getVehicleId());
        m.setType(dto.getType());
        m.setStatus(dto.getStatus());
        m.setPriority(dto.getPriority());
        m.setScheduledDate(LocalDate.parse(dto.getScheduledDate()));
        m.setCompletedDate(
            dto.getCompletedDate() != null && !dto.getCompletedDate().isEmpty()
                ? LocalDate.parse(dto.getCompletedDate())
                : null
        );
        m.setOdometerReading(dto.getOdometerReading());
        m.setCost(dto.getCost());
        m.setEstimatedCost(dto.getEstimatedCost());
        m.setServiceCenter(dto.getServiceCenter());
        m.setMechanic(dto.getMechanic());
        m.setDescription(dto.getDescription());
        m.setNotes(dto.getNotes());

        List<MaintenancePartVO> parts = dto.getParts().stream().map(p -> {
            MaintenancePartVO part = new MaintenancePartVO();
            part.setName(p.getName());
            part.setQuantity(p.getQuantity());
            part.setCost(p.getCost());
            part.setMaintenanceVO(m);
            return part;
        }).toList();

        m.setParts(parts);

        return maintenanceRepo.save(m);
    }

    @Override
    public List<MaintenanceVO> getByVehicle(Long vehicleId) {
        return maintenanceRepo.findByVehicleId(vehicleId);
    }

    @Override
    @Transactional
    public void delete(Long maintenanceId) {
        maintenanceRepo.deleteById(maintenanceId);
    }
}

