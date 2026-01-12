package com.efit.savaari.service;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.TripInvoiceItemVO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.VehicleVO;
import com.efit.savaari.repo.CustomerRepo;
import com.efit.savaari.repo.DriverRepo;
import com.efit.savaari.repo.TripInvoiceRepo;
import com.efit.savaari.repo.TripRepo;
import com.efit.savaari.repo.VehicleRepo;

@Service
@Transactional
public class TripInvoiceServiceImpl implements TripInvoiceService {

    @Autowired private TripInvoiceRepo invoiceRepo;
    @Autowired private VehicleRepo vehicleRepo;
    @Autowired private DriverRepo driverRepo;
    @Autowired private TripRepo tripRepo;
    @Autowired private CustomerRepo customerRepo;

    @Override
    public TripInvoiceVO saveOrUpdate(TripInvoiceDTO dto) {

        TripInvoiceVO invoice;

        /* ===== CREATE / UPDATE ===== */
        if (dto.getInvoiceId() != null) {
            invoice = invoiceRepo.findById(dto.getInvoiceId())
                    .orElseThrow(() -> new RuntimeException("Invalid Invoice ID"));
        } else {
            invoice = new TripInvoiceVO();
        }

        /* ===== CUSTOMER ===== */
        if (dto.getCustomerId() != null) {
            invoice.setCustomer(
                customerRepo.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Invalid Customer"))
            );
        }

        /* ===== VEHICLE ===== */
        if (dto.getVehicleNumber() != null && dto.getOrgId() != null) {
            VehicleVO vehicle = vehicleRepo
                    .findByOrgIdAndVehicleNumber(dto.getOrgId(), dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException("Invalid Vehicle Number"));
            invoice.setVehicle(vehicle);
        }

        /* ===== DRIVER ===== */
        if (dto.getDriverNumber() != null && dto.getOrgId() != null) {
            DriverVO driver = driverRepo.findAll().stream()
                    .filter(d ->
                        d.getDriverNumber().equals(dto.getDriverNumber()) &&
                        d.getOrgId().equals(dto.getOrgId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Invalid Driver Number"));
            invoice.setDriver(driver);
        }

        /* ===== TRIP ===== */
        if (dto.getTripId() != null) {
            invoice.setTrip(
                tripRepo.findById(dto.getTripId())
                    .orElseThrow(() -> new RuntimeException("Invalid Trip"))
            );
        }

        mapInvoiceDTOtoVO(dto, invoice);

        return invoiceRepo.save(invoice);
    }

    @Override
    public TripInvoiceVO getById(Long id) {
        return invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public List<TripInvoiceVO> getAll() {
        return invoiceRepo.findAll();
    }

    /* ================= DTO → VO ================= */
    private void mapInvoiceDTOtoVO(TripInvoiceDTO dto, TripInvoiceVO invoice) {

        invoice.setTripDetails(dto.getTripDetails());
        invoice.setIssueDate(LocalDate.parse(dto.getIssueDate()));
        invoice.setDueDate(LocalDate.parse(dto.getDueDate()));
        invoice.setStatus(dto.getStatus());
        invoice.setPaymentMethod(dto.getPaymentMethod());

        if (dto.getPaymentDate() != null && !dto.getPaymentDate().isEmpty()) {
            invoice.setPaymentDate(LocalDate.parse(dto.getPaymentDate()));
        }

        invoice.setSubtotal(dto.getSubtotal());
        invoice.setTaxRate(dto.getTaxRate());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setDiscount(dto.getDiscount());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setAmountPaid(dto.getAmountPaid());
        invoice.setBalanceDue(dto.getBalanceDue());
        invoice.setNotes(dto.getNotes());

        invoice.getItems().clear();

        dto.getItems().forEach(i -> {
            TripInvoiceItemVO item = new TripInvoiceItemVO();
            item.setItemCode(i.getId());
            item.setDescription(i.getDescription());
            item.setQuantity(i.getQuantity());
            item.setUnit(i.getUnit());
            item.setRate(i.getRate());
            item.setAmount(i.getAmount());
            item.setInvoice(invoice);
            invoice.getItems().add(item);
        });
    }
}
