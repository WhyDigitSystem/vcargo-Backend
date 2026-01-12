package com.efit.savaari.service;



import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.entity.*;
import com.efit.savaari.repo.*;
import com.efit.savaari.service.TripInvoiceService;

@Service
@Transactional
public class TripInvoiceServiceImpl implements TripInvoiceService {

    @Autowired private TripInvoiceRepo invoiceRepo;
    @Autowired private VehicleRepo vehicleRepo;
    @Autowired private DriverRepo driverRepo;
    @Autowired private TripRepo tripRepo;
    @Autowired private CustomerRepo customerRepo;

    /* ================= CREATE / UPDATE ================= */

    @Override
    public TripInvoiceVO saveOrUpdate(TripInvoiceDTO dto) {

        TripInvoiceVO invoice = dto.getInvoiceId() != null
                ? invoiceRepo.findById(dto.getInvoiceId())
                    .orElseThrow(() -> new RuntimeException("Invoice not found"))
                : new TripInvoiceVO();

        invoice.setCustomer(customerRepo.findById(dto.getCustomerId()).orElse(null));
        invoice.setVehicle(vehicleRepo.findById(dto.getVehicleId()).orElse(null));
        invoice.setDriver(driverRepo.findById(dto.getDriverId()).orElse(null));
        invoice.setTrip(
                dto.getTripId() != null
                        ? tripRepo.findById(dto.getTripId()).orElse(null)
                        : null
        );

        invoice.setTripDetails(dto.getTripDetails());
        invoice.setIssueDate(LocalDate.parse(dto.getIssueDate()));
        invoice.setDueDate(LocalDate.parse(dto.getDueDate()));
        invoice.setStatus(dto.getStatus());
        invoice.setPaymentMethod(dto.getPaymentMethod());

        invoice.setPaymentDate(
                dto.getPaymentDate() != null && !dto.getPaymentDate().isEmpty()
                        ? LocalDate.parse(dto.getPaymentDate())
                        : null
        );

        invoice.setSubtotal(dto.getSubtotal());
        invoice.setTaxRate(dto.getTaxRate());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setDiscount(dto.getDiscount());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setAmountPaid(dto.getAmountPaid());
        invoice.setBalanceDue(dto.getBalanceDue());
        invoice.setNotes(dto.getNotes());

        /* ===== Items ===== */
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

        return invoiceRepo.save(invoice);
    }

    /* ================= GET BY ID ================= */

    @Override
    public TripInvoiceVO getById(Long invoiceId) {
        return invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + invoiceId));
    }

    /* ================= GET ALL ================= */

    @Override
    public List<TripInvoiceVO> getAll() {
        return invoiceRepo.findAll();
    }
}

