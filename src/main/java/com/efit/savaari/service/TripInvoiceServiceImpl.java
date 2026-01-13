package com.efit.savaari.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.dto.TripInvoiceItemResponseDTO;
import com.efit.savaari.dto.TripInvoiceResponseDTO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TripInvoiceItemVO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.repo.CustomerRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TripInvoiceRepo;
import com.efit.savaari.repo.TripRepo;
import com.efit.savaari.repo.TvehicleRepo;

@Service
@Transactional
public class TripInvoiceServiceImpl implements TripInvoiceService {

    @Autowired private TripInvoiceRepo invoiceRepo;
    @Autowired private TvehicleRepo tVehicleRepo;
    @Autowired private TdriverRepo tDriverRepo;
    @Autowired private TripRepo tripRepo;
    @Autowired private CustomerRepo customerRepo;
	@Autowired
	PaginationService paginationService;

//    @Override
//    public TripInvoiceVO saveOrUpdate(TripInvoiceDTO dto) {
//
//        TripInvoiceVO invoice;
//
//        /* ===== CREATE / UPDATE ===== */
//        if (dto.getInvoiceId() != null) {
//            invoice = invoiceRepo.findById(dto.getInvoiceId())
//                    .orElseThrow(() -> new RuntimeException("Invalid Invoice ID"));
//        } else {
//            invoice = new TripInvoiceVO();
//        }
//
//        /* ===== CUSTOMER ===== */
//        if (dto.getCustomerId() != null) {
//            invoice.setCustomer(
//                customerRepo.findById(dto.getCustomerId())
//                    .orElseThrow(() -> new RuntimeException("Invalid Customer"))
//            );
//        }
//
//        /* ===== VEHICLE ===== */
//        if (dto.getVehicleNumber() != null && dto.getOrgId() != null) {
//            VehicleVO vehicle = vehicleRepo
//                    .findByOrgIdAndVehicleNumber(dto.getOrgId(), dto.getVehicleNumber())
//                    .orElseThrow(() -> new RuntimeException("Invalid Vehicle Number"));
//            invoice.setVehicle(vehicle);
//        }
//
//        /* ===== DRIVER ===== */
//        if (dto.getDriverNumber() != null && dto.getOrgId() != null) {
//            DriverVO driver = driverRepo.findAll().stream()
//                    .filter(d ->
//                        d.getDriverNumber().equals(dto.getDriverNumber()) &&
//                        d.getOrgId().equals(dto.getOrgId()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Invalid Driver Number"));
//            invoice.setDriver(driver);
//        }
//
//        /* ===== TRIP ===== */
//        if (dto.getTripId() != null) {
//            invoice.setTrip(
//                tripRepo.findById(dto.getTripId())
//                    .orElseThrow(() -> new RuntimeException("Invalid Trip"))
//            );
//        }
//
//        mapInvoiceDTOtoVO(dto, invoice);
//
//        return invoiceRepo.save(invoice);
//    }
//
//    /* ================= DTO → VO ================= */
//    private void mapInvoiceDTOtoVO(TripInvoiceDTO dto, TripInvoiceVO invoice) {
//
//        invoice.setTripDetails(dto.getTripDetails());
//        invoice.setIssueDate(LocalDate.parse(dto.getIssueDate()));
//        invoice.setDueDate(LocalDate.parse(dto.getDueDate()));
//        invoice.setStatus(dto.getStatus());
//        invoice.setPaymentMethod(dto.getPaymentMethod());
//
//        if (dto.getPaymentDate() != null && !dto.getPaymentDate().isEmpty()) {
//            invoice.setPaymentDate(LocalDate.parse(dto.getPaymentDate()));
//        }
//
//        invoice.setSubtotal(dto.getSubtotal());
//        invoice.setTaxRate(dto.getTaxRate());
//        invoice.setTaxAmount(dto.getTaxAmount());
//        invoice.setDiscount(dto.getDiscount());
//        invoice.setTotalAmount(dto.getTotalAmount());
//        invoice.setAmountPaid(dto.getAmountPaid());
//        invoice.setBalanceDue(dto.getBalanceDue());
//        invoice.setNotes(dto.getNotes());
//
//        invoice.getItems().clear();
//
//        dto.getItems().forEach(i -> {
//            TripInvoiceItemVO item = new TripInvoiceItemVO();
//            item.setItemCode(i.getId());
//            item.setDescription(i.getDescription());
//            item.setQuantity(i.getQuantity());
//            item.setUnit(i.getUnit());
//            item.setRate(i.getRate());
//            item.setAmount(i.getAmount());
//            item.setInvoice(invoice);
//            invoice.getItems().add(item);
//        });
//    }
    
//    
//    @Override
//    public Map<String, Object> createUpdateTripInvoice(TripInvoiceDTO dto) {
//
//        TripInvoiceVO invoice;
//   		String message;
//
//		if (dto.getId() != null) {
//			invoice = invoiceRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Invalid TripInvoice ID"));
//			invoice.setUpdatedBy(dto.getCreatedBy());
//			message = "TripInvoice Updated Successfully";
//		} else {
//			invoice = new TripInvoiceVO();
//			invoice.setCreatedBy(dto.getCreatedBy());
//			invoice.setUpdatedBy(dto.getCreatedBy());
//			message = "TripInvoice Created Successfully";
//		}
//
////        /* ===== CUSTOMER ===== */
////        if (dto.getCustomerId() != null) {
////            invoice.setCustomer(
////                customerRepo.findById(dto.getCustomerId())
////                    .orElseThrow(() -> new RuntimeException("Invalid Customer"))
////            );
////        }
//
//        /* ===== VEHICLE ===== */
//        if (dto.getVehicleNumber() != null && dto.getOrgId() != null) {
//            TvehicleVO vehicle = tVehicleRepo
//                    .findByOrgIdAndVehicleNumber(dto.getOrgId(), dto.getVehicleNumber())
//                    .orElseThrow(() -> new RuntimeException("Invalid TVehicle Number"));
//            invoice.setVehicle(vehicle);
//        }
//
//        /* ===== DRIVER ===== */
//        if (dto.getDriverNumber() != null && dto.getOrgId() != null) {
//            TdriverVO driver = tDriverRepo.findAll().stream()
//                    .filter(d ->
//                        d.getPhone().equals(dto.getDriverNumber()) &&
//                        d.getOrgId().equals(dto.getOrgId()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Invalid Driver Number"));
//            invoice.setDriver(driver);
//        }
//
//        /* ===== TRIP ===== */
//        if (dto.getTripId() != null) {
//            invoice.setTrip(
//                tripRepo.findById(dto.getTripId())
//                    .orElseThrow(() -> new RuntimeException("Invalid Trip"))
//            );
//        }
//
//        mapInvoiceDTOtoVO(dto, invoice);
//
//         invoiceRepo.save(invoice);
//        
//
//		TripInvoiceResponseDTO responseDTO = mapToTripInvoiceResponseDTO(invoice);
//
//		Map<String, Object> response = new HashMap<>();
//		response.put("tripInvoice", responseDTO);
//		response.put("message", message);
//
//		return response;
//    }
//
//    /* ================= DTO → VO ================= */
//    private void mapInvoiceDTOtoVO(TripInvoiceDTO dto, TripInvoiceVO invoice) {
//
//        invoice.setTripDetails(dto.getTripDetails());
//        invoice.setIssueDate(dto.getIssueDate());
//        invoice.setDueDate(dto.getDueDate());
//        invoice.setStatus(dto.getStatus());
//        invoice.setPaymentMethod(dto.getPaymentMethod());
//
//        if (dto.getPaymentDate() != null) {
//            invoice.setPaymentDate(dto.getPaymentDate());
//        }
//
//        invoice.setSubtotal(dto.getSubtotal());
//        invoice.setTaxRate(dto.getTaxRate());
//        invoice.setTaxAmount(dto.getTaxAmount());
//        invoice.setDiscount(dto.getDiscount());
//        invoice.setTotalAmount(dto.getTotalAmount());
//        invoice.setAmountPaid(dto.getAmountPaid());
//        invoice.setBalanceDue(dto.getBalanceDue());
//        invoice.setNotes(dto.getNotes());
//        invoice.setOrgId(dto.getOrgId());
//
//
//        // ✅ SAFE ITEM RESET
//        invoice.setItems(new ArrayList<>());
//
//        dto.getItems().forEach(i -> {
//            TripInvoiceItemVO item = new TripInvoiceItemVO();
//            item.setItemCode(i.getId());
//            item.setDescription(i.getDescription());
//            item.setQuantity(i.getQuantity());
//            item.setUnit(i.getUnit());
//            item.setRate(i.getRate());
//            item.setAmount(i.getAmount());
//            item.setInvoice(invoice);
//            invoice.getItems().add(item);
//        });
//    }
//
//    
//    private TripInvoiceResponseDTO mapToTripInvoiceResponseDTO(TripInvoiceVO invoice) {
//
//        TripInvoiceResponseDTO dto = new TripInvoiceResponseDTO();
//
//        dto.setId(invoice.getInvoiceId());
//        dto.setOrgId(invoice.getOrgId());
//
//        /* ===== CUSTOMER ===== */
//            dto.setCustomer(invoice.getCustomer());
//        
//
//        /* ===== VEHICLE ===== */
//        if (invoice.getVehicle() != null) {
//            dto.setVehicleNumber(invoice.getVehicle().getVehicleNumber());
//        }
//
//        /* ===== DRIVER ===== */
//        if (invoice.getDriver() != null) {
//            dto.setDriverNumber(invoice.getDriver().getPhone());
//        }
//
//        /* ===== TRIP ===== */
//        if (invoice.getTrip() != null) {
//            dto.setTripId(invoice.getTrip().getId());
//            dto.setTrip(
//                invoice.getTrip().getSource() + " → " + invoice.getTrip().getDestination()
//            );
//        }
//
//        /* ===== INVOICE DETAILS ===== */
//        dto.setTripDetails(invoice.getTripDetails());
//        dto.setIssueDate(invoice.getIssueDate());
//        dto.setDueDate( invoice.getDueDate());
//
//        dto.setStatus(invoice.getStatus());
//        dto.setPaymentMethod(invoice.getPaymentMethod());
//        dto.setPaymentDate( invoice.getPaymentDate());
//
//        dto.setSubtotal(invoice.getSubtotal());
//        dto.setTaxRate(invoice.getTaxRate());
//        dto.setTaxAmount(invoice.getTaxAmount());
//        dto.setDiscount(invoice.getDiscount());
//        dto.setTotalAmount(invoice.getTotalAmount());
//        dto.setAmountPaid(invoice.getAmountPaid());
//        dto.setBalanceDue(invoice.getBalanceDue());
//
//        dto.setCreatedBy(invoice.getCreatedBy());
//        dto.setNotes(invoice.getNotes());
//
//        /* ===== ITEMS ===== */
//        if (invoice.getItems() != null && !invoice.getItems().isEmpty()) {
//
//            List<TripInvoiceItemResponseDTO> itemList =
//                    invoice.getItems().stream().map(i -> {
//
//                        TripInvoiceItemResponseDTO itemDto =
//                                new TripInvoiceItemResponseDTO();
//
//                        itemDto.setItemCode(i.getItemCode());
//                        itemDto.setDescription(i.getDescription());
//                        itemDto.setQuantity(i.getQuantity());
//                        itemDto.setUnit(i.getUnit());
//                        itemDto.setRate(i.getRate());
//                        itemDto.setAmount(i.getAmount());
//
//                        return itemDto;
//
//                    }).collect(java.util.stream.Collectors.toList());
//
//            dto.setItems(itemList);
//        }
//
//        return dto;
//    }

	
	@Override
	public Map<String, Object> createUpdateTripInvoice(TripInvoiceDTO dto) {

	    TripInvoiceVO invoice;
	    String message;

	    if (dto.getId() != null) {
	        invoice = invoiceRepo.findById(dto.getId())
	                .orElseThrow(() -> new RuntimeException("Invalid TripInvoice ID"));
	        invoice.setUpdatedBy(dto.getCreatedBy());
	        message = "TripInvoice Updated Successfully";
	    } else {
	        invoice = new TripInvoiceVO();
	        invoice.setCreatedBy(dto.getCreatedBy());
	        invoice.setUpdatedBy(dto.getCreatedBy());
	        message = "TripInvoice Created Successfully";
	    }

	    /* ===== CUSTOMER ===== */
//	    if (dto.getCustomerId() != null) {
//	        CustomerVO customer = customerRepo.findById(dto.getCustomerId())
//	                .orElseThrow(() -> new RuntimeException("Invalid Customer"));
//	        invoice.setCustomer(customer);
//	    }

	    /* ===== VEHICLE ===== */
	    /* ===== VEHICLE ===== */
	    if (dto.getVehicleId() != null && !dto.getVehicleId().isEmpty()) {

	        Long vehicleId = Long.parseLong(dto.getVehicleId()); // ✅ convert

	        TvehicleVO vehicle = tVehicleRepo.findById(vehicleId)
	                .orElseThrow(() -> new RuntimeException("Invalid Vehicle ID"));

	        invoice.setVehicle(vehicle);
	    }

	    /* ===== DRIVER ===== */
	    if (dto.getDriverId() != null && !dto.getDriverId().isEmpty()) {

	        Long driverId = Long.parseLong(dto.getDriverId()); // ✅ convert

	        TdriverVO driver = tDriverRepo.findById(driverId)
	                .orElseThrow(() -> new RuntimeException("Invalid Driver ID"));

	        invoice.setDriver(driver);
	    }


	    /* ===== TRIP ===== */
	    if (dto.getTripId() != null) {
	        Long tripId = Long.parseLong(dto.getTripId()); // ✅ convert

	        invoice.setTrip(tripRepo.findById(tripId)
	                .orElseThrow(() -> new RuntimeException("Invalid Trip")));
	    }

	    mapInvoiceDTOtoVO(dto, invoice);

	    invoiceRepo.save(invoice);

	    TripInvoiceResponseDTO responseDTO = mapToTripInvoiceResponseDTO(invoice);

	    Map<String, Object> response = new HashMap<>();
	    response.put("tripInvoice", responseDTO);
	    response.put("message", message);

	    return response;
	}

	private void mapInvoiceDTOtoVO(TripInvoiceDTO dto, TripInvoiceVO invoice) {

	    invoice.setTripDetails(dto.getTripDetails());
	    invoice.setIssueDate(dto.getIssueDate());
	    invoice.setDueDate(dto.getDueDate());
	    invoice.setStatus(dto.getStatus());
	    invoice.setPaymentMethod(dto.getPaymentMethod());
	    invoice.setPaymentDate(dto.getPaymentDate());
	    invoice.setCustomer(dto.getCustomer());

	    invoice.setSubtotal(dto.getSubtotal());
	    invoice.setTaxRate(dto.getTaxRate());
	    invoice.setTaxAmount(dto.getTaxAmount());
	    invoice.setDiscount(dto.getDiscount());
	    invoice.setTotalAmount(dto.getTotalAmount());
	    invoice.setAmountPaid(dto.getAmountPaid());
	    invoice.setBalanceDue(dto.getBalanceDue());
	    invoice.setNotes(dto.getNotes());
	    invoice.setOrgId(dto.getOrgId());

	    // ✅ IMPORTANT — do not replace list
	 // ✅ If list is null, create it
	    if (invoice.getItems() == null) {
	        invoice.setItems(new ArrayList<>());
	    } else {
	        invoice.getItems().clear(); // safe now
	    }

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

	
	private TripInvoiceResponseDTO mapToTripInvoiceResponseDTO(TripInvoiceVO invoice) {

	    TripInvoiceResponseDTO dto = new TripInvoiceResponseDTO();

	    dto.setId(invoice.getInvoiceId());
	    dto.setOrgId(invoice.getOrgId());
	    dto.setCustomer(invoice.getCustomer());


//	    if (invoice.getCustomer() != null)
//	        dto.setCustomer(invoice.getCustomer().getCustomerName());

	    if (invoice.getVehicle() != null)
	        dto.setVehicleNumber(invoice.getVehicle().getVehicleNumber());
	        dto.setVehicleId(invoice.getVehicle().getId());

	    if (invoice.getDriver() != null)
	        dto.setDriverNumber(invoice.getDriver().getPhone());
           dto.setDriverId(invoice.getDriver().getId());

	    if (invoice.getTrip() != null) {
	        dto.setTripId(invoice.getTrip().getId());
	        dto.setTrip(invoice.getTrip().getSource() + " → " + invoice.getTrip().getDestination());
	    }

	    dto.setTripDetails(invoice.getTripDetails());
	    dto.setIssueDate(invoice.getIssueDate());
	    dto.setDueDate(invoice.getDueDate());
	    dto.setStatus(invoice.getStatus());
	    dto.setPaymentMethod(invoice.getPaymentMethod());
	    dto.setPaymentDate(invoice.getPaymentDate());

	    dto.setSubtotal(invoice.getSubtotal());
	    dto.setTaxRate(invoice.getTaxRate());
	    dto.setTaxAmount(invoice.getTaxAmount());
	    dto.setDiscount(invoice.getDiscount());
	    dto.setTotalAmount(invoice.getTotalAmount());
	    dto.setAmountPaid(invoice.getAmountPaid());
	    dto.setBalanceDue(invoice.getBalanceDue());

	    dto.setCreatedBy(invoice.getCreatedBy());
	    dto.setNotes(invoice.getNotes());

	    List<TripInvoiceItemResponseDTO> items =
	            invoice.getItems().stream().map(i -> {

	                TripInvoiceItemResponseDTO itemDto = new TripInvoiceItemResponseDTO();
	                itemDto.setId(i.getId());                 // ✅ DB ID
	                itemDto.setItemCode(i.getItemCode());
	                itemDto.setDescription(i.getDescription());
	                itemDto.setQuantity(i.getQuantity());
	                itemDto.setUnit(i.getUnit());
	                itemDto.setRate(i.getRate());
	                itemDto.setAmount(i.getAmount());
	                return itemDto;

	            }).collect(Collectors.toList());

	    dto.setItems(items);

	    return dto;
	}

   
    @Override
	public TripInvoiceResponseDTO getTripInvoiceById(Long id) {
    	TripInvoiceVO tripInvoiceVO = invoiceRepo.findById(id).orElseThrow();
		TripInvoiceResponseDTO tripInvoiceResponseDTO = mapToTripInvoiceResponseDTO(tripInvoiceVO);
		return tripInvoiceResponseDTO;
	}
    
	@Override
	public Map<String, Object> getAllTripInvoiceByOrgId(Long orgId, int page, int count) {
		Pageable pageable = PageRequest.of(page - 1, count);
		Page<TripInvoiceVO> quotePage = invoiceRepo.getTripInvoiceByOrgId(orgId, pageable);

		Page<TripInvoiceResponseDTO> dtoPage = quotePage.map(this::mapToTripInvoiceResponseDTO);
		return paginationService.buildResponse(dtoPage);
	}

//    @Override
//    public List<TripInvoiceVO> getAll() {
//        return invoiceRepo.findAll();
//    }
    
}
