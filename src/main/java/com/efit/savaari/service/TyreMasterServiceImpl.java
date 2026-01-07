package com.efit.savaari.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TyreMasterDTO;
import com.efit.savaari.entity.TyreMasterVO;
import com.efit.savaari.repo.TyreMasterRepo;

@Service
public class TyreMasterServiceImpl implements TyreMasterService {

    @Autowired
    private TyreMasterRepo typeMasterRepo;

    @Override
    @Transactional
    public TyreMasterVO saveOrUpdateType(TyreMasterDTO dto) {

        TyreMasterVO type;

        if (dto.getTyreId() != null) {
            type = typeMasterRepo.findById(dto.getTyreId())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
        } else {
            type = new TyreMasterVO();
        }

        type.setSerialNumber(dto.getSerialNumber());
        type.setBrand(dto.getBrand());
        type.setModel(dto.getModel());
        type.setSize(dto.getSize());
        type.setVehicleId(dto.getVehicleId());
        type.setPosition(dto.getPosition());
        type.setStatus(dto.getStatus());
        type.setPurchaseDate(LocalDate.parse(dto.getPurchaseDate()));
        type.setPurchaseCost(dto.getPurchaseCost());
        type.setOdometerReading(dto.getOdometerReading());
        type.setTreadDepth(dto.getTreadDepth());
        type.setRecommendedPressure(dto.getRecommendedPressure());
        type.setPressure(dto.getPressure());
        type.setNotes(dto.getNotes());

        return typeMasterRepo.save(type);
    }

    @Override
    public List<TyreMasterVO> getByVehicle(Long vehicleId) {
        return typeMasterRepo.findByVehicleId(vehicleId);
    }

    @Override
    public List<TyreMasterVO> getByStatus(String status) {
        return typeMasterRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public void deleteType(Long typeId) {
        typeMasterRepo.deleteById(typeId);
    }
}
