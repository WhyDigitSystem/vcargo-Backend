package com.efit.savaari.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.EscalationDashboardDTO;
import com.efit.savaari.dto.FuelDashboardDTO;
import com.efit.savaari.dto.MaintenanceDashboardDTO;
import com.efit.savaari.dto.TDriverDashboardDTO;
import com.efit.savaari.dto.TripDashboardDTO;
import com.efit.savaari.dto.TyreDashboardDTO;
import com.efit.savaari.entity.FuelVO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.entity.TdriverVO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.TripVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.TyreMasterVO;
import com.efit.savaari.repo.DriverStatusCountProjection;
import com.efit.savaari.repo.FuelRepo;
import com.efit.savaari.repo.MaintenanceRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TripInvoiceRepo;
import com.efit.savaari.repo.TripRepo;
import com.efit.savaari.repo.TvehicleRepo;
import com.efit.savaari.repo.TyreMasterRepo;
import com.efit.savaari.repo.VehicleRepo;
import com.efit.savaari.responseDTO.InvoiceDashboardDTO;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DashBoardServiceImpl.class);

	
	 @Autowired 
	 private TripRepo tripRepo;
	 @Autowired 
	 private MaintenanceRepo maintenanceRepo;
	 @Autowired 
	 private FuelRepo fuelRepo;
	 @Autowired
	 private TripInvoiceRepo tripInvoiceRepo;
	 @Autowired 
	 private TyreMasterRepo tyreRepo;
	 
	 @Autowired
	 TdriverRepo tdriverRepo;
	 @Autowired
	 private VehicleRepo vehiclesRepo;
	 
	 @Autowired
	 TvehicleRepo tVehiclesrepo;
	 
	 @Autowired
	 TdriverRepo tDriverRepo;

//	    @Override
//	    public Map<String, Object> getDashboardData(Long orgId) {
//
//	        Map<String, Object> map = new HashMap<>();
//
//	        map.put("trips", tripRepo.findByOrgId(orgId));
//	        map.put("maintenance", maintenanceRepo.findByOrgId(orgId));
//	        map.put("fuel", fuelRepo.findByOrgId(orgId));
////	        map.put("invoices", invoiceRepo.findByOrgId(orgId));
//	        map.put("tyres", tyreRepo.findByOrgId(orgId));
//
//	        return map;
//	    }



	 @Override
	 public Map<String, Object> getDashboardData(Long orgId, String type) {

	     Map<String, Object> map = new HashMap<>();

	     LocalDate[] range = getDateRange(type);

	     map.put("trips", getTrips(orgId, range));
	     map.put("maintenance", getMaintenance(orgId, range));
	     map.put("fuel", getFuel(orgId, range));
	     map.put("tyres", getTyres(orgId, range));
	     map.put("invoices", getTripInvoices(orgId, range));

	     return map;
	 }

	 private List<InvoiceDashboardDTO> getTripInvoices(Long orgId, LocalDate[] range) {

		    List<TripInvoiceVO> invoices;

		    if (range == null) {

		        invoices = tripInvoiceRepo.findByOrgId(orgId);

		    } else {

		        String[] dateRange = getDateTimeRange(range);

		        invoices = tripInvoiceRepo.findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		                orgId,
		                dateRange[0],
		                dateRange[1]);
		    }

		    return invoices.stream().map(i -> {

		        InvoiceDashboardDTO d = new InvoiceDashboardDTO();

		        d.setInvoiceId(i.getInvoiceId());
		        d.setVehicleNo(i.getVehicle() != null ? i.getVehicle().getVehicleNumber() : "");
		        d.setDriverName(i.getDriver() != null ? i.getDriver().getName() : "");
		        d.setCustomerName(i.getCustomer() != null ? i.getCustomer().getCustomerName() : "");
		        d.setTripDetails(i.getTripDetails());
		        d.setIssueDate(i.getIssueDate());
		        d.setDueDate(i.getDueDate());
		        d.setSubtotal(i.getSubtotal());
		        d.setTaxAmount(i.getTaxAmount());
		        d.setDiscount(i.getDiscount());
		        d.setTotalAmount(i.getTotalAmount());
		        d.setAmountPaid(i.getAmountPaid());
		        d.setBalanceDue(i.getBalanceDue());
		        d.setPaymentMethod(i.getPaymentMethod());
		        d.setPaymentDate(i.getPaymentDate());
		        d.setStatus(i.getStatus());

		        return d;

		    }).collect(Collectors.toList());
		}
	 
	 private List<TripDashboardDTO> getTrips(Long orgId, LocalDate[] range) {

		    List<TripVO> trips;

		    if (range == null) {

		        trips = tripRepo.findByOrgId(orgId);

		    } else {

		        String[] dateRange = getDateTimeRange(range);

		        trips = tripRepo.findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		                orgId,
		                dateRange[0],
		                dateRange[1]);
		    }

		    return trips.stream().map(t -> {

		        TripDashboardDTO d = new TripDashboardDTO();

		        d.setId(t.getId());
		        d.setVehicleNo(t.getVehicle() != null ? t.getVehicle().getVehicleNumber() : "");
		        d.setDriverName(t.getDriver() != null ? t.getDriver().getName() : "");
		        d.setRoute(t.getSource());
		        d.setStatus(t.getStatus());

		        return d;

		    }).collect(Collectors.toList());
		}

	 private List<MaintenanceDashboardDTO> getMaintenance(Long orgId, LocalDate[] range) {

		    List<MaintenanceVO> maintenance;

		    if (range == null) {

		        maintenance = maintenanceRepo.findByOrgId(orgId);

		    } else {

		        String[] dateRange = getDateTimeRange(range);

		        maintenance = maintenanceRepo.findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		                orgId,
		                dateRange[0],
		                dateRange[1]);
		    }

		    return maintenance.stream().map(m -> {

		        MaintenanceDashboardDTO d = new MaintenanceDashboardDTO();

		        d.setId(m.getId());
		        d.setVehicleNo(m.getVehicle() != null ? m.getVehicle().getVehicleNumber() : "");
		        d.setDescription(m.getDescription());
		        d.setCompletedDate(m.getCompletedDate());
		        d.setTotalCost(m.getTotalCost());
		        d.setPriority(m.getPriority());
		        d.setType(m.getType());
		        d.setStatus(m.getStatus());

		        return d;

		    }).collect(Collectors.toList());
		}

	 
	 private List<FuelDashboardDTO> getFuel(Long orgId, LocalDate[] range) {

		    List<FuelVO> fuels;

		    if (range == null) {

		        fuels = fuelRepo.findByOrgId(orgId);

		    } else {

		        String[] dateRange = getDateTimeRange(range);

		        fuels = fuelRepo.findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		                orgId,
		                dateRange[0],
		                dateRange[1]);
		    }

		    return fuels.stream().map(f -> {

		        FuelDashboardDTO d = new FuelDashboardDTO();

		        d.setId(String.valueOf(f.getId()));
		        d.setVehicle(f.getVehicle() != null ? f.getVehicle().getVehicleNumber() : "");
		        d.setStation(f.getStation());
		        d.setQuantity(f.getQuantity());
		        d.setTotal(f.getCost());

		        if (f.getCost() != null && f.getQuantity() != null) {
		            d.setRate(f.getCost().divide(f.getQuantity()));
		        }

		        d.setDate(f.getDate() != null ? f.getDate().toString() : "");
		        d.setDriver(f.getDriver() != null ? f.getDriver().getName() : "");

		        return d;

		    }).collect(Collectors.toList());
		}

	 
	 private List<TyreDashboardDTO> getTyres(Long orgId, LocalDate[] range) {

		    List<TyreMasterVO> tyres;

		    if (range == null) {

		        tyres = tyreRepo.findByOrgId(orgId);

		    } else {

		        String[] dateRange = getDateTimeRange(range);

		        tyres = tyreRepo.findByOrgIdAndCreatedUpdatedDateCreatedonBetween(
		                orgId,
		                dateRange[0],
		                dateRange[1]);
		    }

		    return tyres.stream().map(t -> {

		        TyreDashboardDTO d = new TyreDashboardDTO();

		        d.setId(t.getId());
		        d.setVehicle(t.getVehicle() != null ? t.getVehicle().getVehicleNumber() : "");
		        d.setVehicleNumber(t.getVehicle() != null ? t.getVehicle().getVehicleNumber() : "");
		        d.setBrand(t.getBrand());
		        d.setPosition(t.getPosition());
		        d.setStatus(t.getStatus());
		        d.setDepth(t.getTreadDepth());
		        d.setInstalledDate(t.getPurchaseDate());

		        return d;

		    }).collect(Collectors.toList());
		}


	 private String[] getDateTimeRange(LocalDate[] range) {

		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");

		    String fromDate = range[0]
		            .atStartOfDay()
		            .format(formatter);

		    String toDate = range[1]
		            .atTime(23, 59, 59)
		            .format(formatter);

		    return new String[] { fromDate, toDate };
		}
	 
	 private LocalDate[] getDateRange(String type) {

		    if (type == null || type.isBlank()) {
		        return null;
		    }

		    LocalDate today = LocalDate.now();

		    switch (type.toLowerCase()) {

		        case "today":
		            return new LocalDate[] { today, today };

		        case "yesterday":
		            LocalDate yesterday = today.minusDays(1);
		            return new LocalDate[] { yesterday, yesterday };

		        case "week":
		            return new LocalDate[] {
		                today.minusDays(6),   // Last 7 days including today
		                today
		            };
		            
		        case "month":
		            return new LocalDate[] {
		                today.withDayOfMonth(1),
		                today
		            };

//		        case "year":
//		            return new LocalDate[] {
//		                today.withDayOfYear(1),
//		                today
//		            };
		            

		        default:
		            return null;
		    }
		}
	 
	 //Trips , driver DashBoard
	 
	 @Override
	 public Map<String, Object> getAllDashBoardDetails(Long orgId, String type) {

	     LocalDate[] range = getDateRange(type);

	     String fromDate = null;
	     String toDate = null;

	     if (range != null) {
	         fromDate = range[0].toString();
	         toDate = range[1].toString();
	     }

	     Map<String, Object> dashboard = new HashMap<>();

	     dashboard.put("activeVehicle",
	             vehiclesRepo.getActiveVehicleCount(orgId, fromDate, toDate));

	     dashboard.put("maintenanceVehicleCount",
	             vehiclesRepo.getMaintenanceVehicleCount(orgId, fromDate, toDate));

	     dashboard.put("upcomingMaintenanceVehicle",
	             vehiclesRepo.getUpcomingMaintenanceVehicle(orgId, fromDate, toDate));

	     dashboard.put("maintenanceCost",
	             vehiclesRepo.getMaintenanceCost(orgId, fromDate, toDate));

	     dashboard.put("totalTyresPurchased",
	             tyreRepo.getTyresPurchased(orgId, fromDate, toDate));

	     dashboard.put("totalTripCount",
	             tripRepo.getTotalCount(orgId, fromDate, toDate));

	     dashboard.put("totalFuelAmount",
	             fuelRepo.getTotalFuelAmount(orgId, fromDate, toDate));

	     dashboard.put("onTripDriverCount",
	             tripRepo.getOnTripDriverCount(orgId, fromDate, toDate));

	     dashboard.put("totalFuel",
	             fuelRepo.getTotalFuel(orgId, fromDate, toDate));

	     dashboard.put("tDriver",
	             getDriver(orgId, fromDate, toDate));

	     return dashboard;
	 }

	 private TDriverDashboardDTO getDriver(Long orgId, String fromDate, String toDate) {

		    DriverStatusCountProjection p =
		            tdriverRepo.getDriverStatusCounts(orgId,  fromDate,  toDate);

		    TDriverDashboardDTO d = new TDriverDashboardDTO();

		    if (p != null) {
		        d.setActiveDriver(p.getActiveCount() == null ? 0L : p.getActiveCount());
		        d.setInActiveDriver(p.getInactiveCount() == null ? 0L : p.getInactiveCount());
		        d.setLeaveDriver(p.getLeaveCount() == null ? 0L : p.getLeaveCount());
		    }

		    return d;
		}

	 @Override
	 public Map<String, Object> getAllDashBoardVehicleDetails(Long orgId, String type) {

	     LocalDate[] range = getDateRange(type);

	     List<Object[]> result;

	     if (range == null) {
	         result = tVehiclesrepo.getAllDashBoardVehicleDetails(
	                 orgId, null, null);
	     } else {

	         String fromDate = range[0].toString();
	         String toDate = range[1].toString();

	         result = tVehiclesrepo.getAllDashBoardVehicleDetails(
	                 orgId, fromDate, toDate);
	     }

	     Map<String, Object> map = new HashMap<>();

	     if (result == null || result.isEmpty()) {
	         map.put("maintenanceVehicles", 0);
	         map.put("onTripVehicles", 0);
	         map.put("activeVehicles", 0);
	         return map;
	     }

	     Object[] row = result.get(0);

	     map.put("maintenanceVehicles",
	             row[0] == null ? 0 : ((Number) row[0]).longValue());

	     map.put("onTripVehicles",
	             row[1] == null ? 0 : ((Number) row[1]).longValue());

	     map.put("activeVehicles",
	             row[2] == null ? 0 : ((Number) row[2]).longValue());

	     return map;
	 }
	 
//	 @Override
//	 public List<MaintenanceResponseDTO> getMaintenanceScheduleForDashBoard(
//	         Long orgId,
//	         String vehicleNumber,
//	         String type) throws ApplicationException {
//
//	     LocalDate today = LocalDate.now();
//
//	     List<MaintenanceVO> maintenanceList;
//
//	     switch (type.toUpperCase()) {
//
//	         case "WEEK":
//	             maintenanceList = maintenanceRepo.findWeekSchedule(
//	                     orgId, vehicleNumber, today, today.plusDays(7));
//	             break;
//
//	         case "MONTH":
//	             maintenanceList = maintenanceRepo.findMonthSchedule(
//	                     orgId, vehicleNumber, today, today.plusMonths(1));
//	             break;
//
//	         case "EXPIRED":
//	             maintenanceList = maintenanceRepo.findExpiredSchedule(
//	                     orgId, vehicleNumber, today);
//	             break;
//
//	         default:
//	             throw new ApplicationException("Invalid type. Use WEEK, MONTH or EXPIRED.");
//	     }
//
//	     return maintenanceList.stream()
//	             .map(this::mapToMaintenanceResponseDTO)
//	             .collect(Collectors.toList());
//	 }
//	 
//	 private MaintenanceResponseDTO mapToMaintenanceResponseDTO(MaintenanceVO vo) {
//
//	        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();
//
//	        dto.setId(vo.getId());
//	        dto.setTitle(vo.getTitle());
//	        dto.setType(vo.getType());
//	        dto.setStatus(vo.getStatus());
//	        dto.setPriority(vo.getPriority());
//	        dto.setScheduledDate(vo.getScheduledDate());
//	        dto.setCompletedDate(vo.getCompletedDate());
//	        dto.setOdometerReading(vo.getOdometerReading());
//	        dto.setEstimatedCost(vo.getEstimatedCost());
//	        dto.setTotalCost(vo.getTotalCost());
//	        dto.setTotalQty(vo.getTotalqty());
//	        dto.setServiceCenter(vo.getServiceCenter());
//	        dto.setMechanic(vo.getMechanic());
//	        dto.setDescription(vo.getDescription());
//	        dto.setNotes(vo.getNotes());
//	        dto.setActive(vo.isActive());
//	        dto.setNextServiceMonth(vo.getNextServiceMonth());
//
//	        dto.setCreatedBy(vo.getCreatedBy());
//	        dto.setBranchCode(vo.getBranchCode());
//	        dto.setBranchName(vo.getBranchName());
//	        dto.setOrgId(vo.getOrgId());
//
//	        if (vo.getVehicle() != null) {
//	            dto.setVehicleId(vo.getVehicle().getId());
//	            dto.setVehicle(vo.getVehicle().getVehicleNumber());
//	        }
//
//	        if (vo.getUser() != null) {
//	            dto.setUser(vo.getUser().getId());
//	        }
//
//	        if (vo.getParts() != null) {
//	        	dto.setParts(
//	        		    vo.getParts().stream()
//	        		        .map(p -> new MaintenancePartResponseDTO(
//	        		                p.getId(),   // ✅ use correct ID field
//	        		                p.getName(),
//	        		                p.getQuantity(),
//	        		                p.getCost()
//	        		        ))
//	        		        .collect(Collectors.toList()) // safer than toList() for Java < 16
//	        		);
//	        }
//
//	        return dto;
//	    }
//	 
//	 @Override
//	 public List<TvehicleResponseDTO> getInsuranceExpiryForDashBoard(
//	         Long orgId,
//	         String vehicleNumber,
//	         String type) throws ApplicationException {
//
//	     LocalDate today = LocalDate.now();
//
//	     List<TvehicleVO> vehicleList;
//
//	     switch (type.toUpperCase()) {
//
//	         case "WEEK":
//	             vehicleList = tVehiclesrepo.findInsuranceExpiryWeek(
//	                     orgId,
//	                     vehicleNumber,
//	                     today,
//	                     today.plusDays(7));
//	             break;
//
//	         case "MONTH":
//	             vehicleList = tVehiclesrepo.findInsuranceExpiryMonth(
//	                     orgId,
//	                     vehicleNumber,
//	                     today,
//	                     today.plusMonths(1));
//	             break;
//
//	         case "EXPIRED":
//	             vehicleList = tVehiclesrepo.findInsuranceExpired(
//	                     orgId,
//	                     vehicleNumber,
//	                     today);
//	             break;
//
//	         default:
//	             throw new ApplicationException("Invalid type. Use WEEK, MONTH or EXPIRED.");
//	     }
//
//	     List<TvehicleResponseDTO> response = new ArrayList<>();
//
//	     for (TvehicleVO vehicle : vehicleList) {
//	         response.add(mapToVehicleResponseDTO(vehicle));
//	     }
//
//	     return response;
//	 }
//	 
//
//	 public TvehicleResponseDTO mapToVehicleResponseDTO(TvehicleVO vehicle) {
//
//			TvehicleResponseDTO dto = new TvehicleResponseDTO();
//
//			dto.setId(vehicle.getId());
//			dto.setVehicleNumber(vehicle.getVehicleNumber());
//			dto.setType(vehicle.getType());
//			dto.setModel(vehicle.getModel());
//			dto.setCapacity(vehicle.getCapacity());
//			dto.setRegistrationType(vehicle.getRegistrationType());
//
//			if (vehicle.getUser() != null) {
//				dto.setUser(vehicle.getUser().getId());
//			}
//
//			dto.setDriver(vehicle.getDriver());
//			dto.setDriverPhone(vehicle.getDriverPhone());
//			dto.setCurrentLocation(vehicle.getCurrentLocation());
//
//			dto.setFuelEfficiency(vehicle.getFuelEfficiency());
//			dto.setMaintenanceRequired(vehicle.isMaintenanceRequired());
//
//			dto.setYear(vehicle.getYear());
//			dto.setChassisNumber(vehicle.getChassisNumber());
//			dto.setEngineNumber(vehicle.getEngineNumber());
//			dto.setPermitType(vehicle.getPermitType());
//			dto.setOwnerName(vehicle.getOwnerName());
//
//			dto.setInsuranceExpiry(vehicle.getInsuranceExpiry());
//			dto.setFitnessExpiry(vehicle.getFitnessExpiry());
//			dto.setLastService(vehicle.getLastService());
//			dto.setNextService(vehicle.getNextService());
//
//			dto.setActive(vehicle.getActive());
//			dto.setCancel(vehicle.isCancel());
//
//			dto.setOrgId(vehicle.getOrgId());
//			dto.setBranchCode(vehicle.getBranchCode());
//			dto.setBranchName(vehicle.getBranchName());
//
//			if (vehicle.getDocuments() == null) {
//				dto.setDocuments(null);
//			} else {
//				dto.setDocuments(vehicle.getDocuments().stream().map(doc -> {
//					TvehicleDocumentResponseDTO d = new TvehicleDocumentResponseDTO();
//					d.setId(doc.getId());
//					d.setDocumentType(doc.getDocumentType());
//					d.setFileName(doc.getFileName());
//
//					String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/transaction/files")
//							.toUriString();
//
//					d.setFilePath(baseUrl + doc.getFilePath());
//					d.setFileType(doc.getFileType());
//					d.setFileSize(doc.getFileSize());
//					d.setUploadedOn(doc.getUploadedOn());
//					return d;
//				}).toList());
//			}
//
//			return dto;
//		}

	 
//	 @Override
//	 public Map<String, Object> getDashboardAlerts(Long orgId) {
//
//	     Map<String, Object> response = new HashMap<>();
//
//	     response.put("maintenanceDue",
//	             maintenanceRepo.findMaintenance(orgId));
//
//	     response.put("insuranceExpiry",
//	             tVehiclesrepo.findInsuranceExpiry(orgId));
//
//	     response.put("fitnessExpiry",
//	             tVehiclesrepo.findFitnessExpiry(orgId));
//
////	     response.put("permitExpiry",
////	             tVehiclesrepo.findPermitExpiry(orgId));
//
//	     response.put("pucExpiry",
//	             tVehiclesrepo.findPucExpiry(orgId));
//
//	     response.put("driverLicenseExpiry",
//	             tDriverRepo.findDriverLicenseExpiry(orgId));
//
//	     return response;
//	 }
	 
	 @Override
	 public Map<String, Object> getEscalationDashboard(Long orgId) {

	     List<EscalationDashboardDTO> escalations = new ArrayList<>();

	     escalations.addAll(getMaintenance(orgId));

	     escalations.addAll(getInsurance(orgId));

	     escalations.addAll(getFitness(orgId));

//	     escalations.addAll(getPermit(orgId));

	     escalations.addAll(getPuc(orgId));

	     escalations.addAll(getDriverLicense(orgId));

	     Map<String, Object> summary = new HashMap<>();

	     summary.put("critical",
	             escalations.stream()
	                     .filter(e -> "Critical".equals(e.getSeverity()))
	                     .count());

	     summary.put("high",
	             escalations.stream()
	                     .filter(e -> "High".equals(e.getSeverity()))
	                     .count());

	     summary.put("medium",
	             escalations.stream()
	                     .filter(e -> "Medium".equals(e.getSeverity()))
	                     .count());

	     summary.put("low",
	             escalations.stream()
	                     .filter(e -> "Low".equals(e.getSeverity()))
	                     .count());

	     summary.put("total", escalations.size());

	     escalations.sort(
	    		    Comparator.comparing(
	    		        EscalationDashboardDTO::getDueDate,
	    		        Comparator.nullsLast(Comparator.naturalOrder())
	    		    )
	    		);
	     Map<String, Object> response = new HashMap<>();
	     response.put("summary", summary);
	     response.put("escalations", escalations);

	     return response;
	 }
	 
	 private List<EscalationDashboardDTO> getMaintenance(Long orgId) {

		    List<MaintenanceVO> list = maintenanceRepo.getMaintenanceDashboard(orgId);

		    List<EscalationDashboardDTO> response = new ArrayList<>();

		    for (MaintenanceVO m : list) {

		        EscalationDashboardDTO dto = new EscalationDashboardDTO();

		        dto.setType("Vehicle Maintenance Due");
		        dto.setVehicleNumber(m.getVehicle() != null ? m.getVehicle().getVehicleNumber() : "");
		        dto.setDriver(m.getMechanic());
		        dto.setDueDate(m.getScheduledDate());
		        dto.setStatus(m.getStatus());

		        setSeverityAndDays(dto, m.getScheduledDate());

		        response.add(dto);
		    }

		    return response;
		}
	 
	 
	 private List<EscalationDashboardDTO> getInsurance(Long orgId) {

		    List<TvehicleVO> list = tVehiclesrepo.getInsuranceDashboard(orgId);

		    List<EscalationDashboardDTO> response = new ArrayList<>();

		    for (TvehicleVO v : list) {

		        EscalationDashboardDTO dto = new EscalationDashboardDTO();

		        dto.setType("Insurance Expiry");
		        dto.setVehicleNumber(v.getVehicleNumber());
		        dto.setDriver(v.getDriver());
		        dto.setDueDate(v.getInsuranceExpiry());
		        dto.setStatus(v.getActive());

		        setSeverityAndDays(dto, v.getInsuranceExpiry());

		        response.add(dto);
		    }

		    return response;
		}
	 
	 private List<EscalationDashboardDTO> getFitness(Long orgId) {

		    List<TvehicleVO> list = tVehiclesrepo.getFitnessDashboard(orgId);

		    List<EscalationDashboardDTO> response = new ArrayList<>();

		    for (TvehicleVO v : list) {

		        EscalationDashboardDTO dto = new EscalationDashboardDTO();

		        dto.setType("Fitness Expiry");
		        dto.setVehicleNumber(v.getVehicleNumber());
		        dto.setDriver(v.getDriver());
		        dto.setDueDate(v.getFitnessExpiry());
		        dto.setStatus(v.getActive());

		        setSeverityAndDays(dto, v.getFitnessExpiry());

		        response.add(dto);
		    }

		    return response;
		}
	 
//	 private List<EscalationDashboardDTO> getPermit(Long orgId) {
//
//		    List<TvehicleVO> list = tVehiclesrepo.getPermitDashboard(orgId);
//
//		    List<EscalationDashboardDTO> response = new ArrayList<>();
//
//		    for (TvehicleVO v : list) {
//
//		        EscalationDashboardDTO dto = new EscalationDashboardDTO();
//
//		        dto.setType("Permit Expiry");
//		        dto.setVehicleNumber(v.getVehicleNumber());
//		        dto.setDriver(v.getDriver());
//		        dto.setDueDate(v.getPermitExpiry());
//		        dto.setStatus(v.getActive());
//
//		        setSeverityAndDays(dto, v.getPermitExpiry());
//
//		        response.add(dto);
//		    }
//
//		    return response;
//		}
	 
	 private List<EscalationDashboardDTO> getPuc(Long orgId) {

		    List<TvehicleVO> list = tVehiclesrepo.getPucDashboard(orgId);

		    List<EscalationDashboardDTO> response = new ArrayList<>();

		    for (TvehicleVO v : list) {

		        EscalationDashboardDTO dto = new EscalationDashboardDTO();

		        dto.setType("PUC Expiry");
		        dto.setVehicleNumber(v.getVehicleNumber());
		        dto.setDriver(v.getDriver());
		        dto.setDueDate(v.getPucExpiry());
		        dto.setStatus(v.getActive());

		        setSeverityAndDays(dto, v.getPucExpiry());

		        response.add(dto);
		    }

		    return response;
		}
	 
	 private List<EscalationDashboardDTO> getDriverLicense(Long orgId) {

		    List<TdriverVO> list = tDriverRepo.getDriverDashboard(orgId);

		    List<EscalationDashboardDTO> response = new ArrayList<>();

		    for (TdriverVO d : list) {

		        EscalationDashboardDTO dto = new EscalationDashboardDTO();

		        dto.setType("Driver License Expiry");
		        dto.setVehicleNumber(d.getAssignedVehicle());
		        dto.setDriver(d.getName());
		        dto.setDueDate(d.getLicenseExpiry());
		        dto.setStatus(d.getStatus());

		        setSeverityAndDays(dto, d.getLicenseExpiry());

		        response.add(dto);
		    }

		    return response;
		}
	 
	 private void setSeverityAndDays(EscalationDashboardDTO dto, LocalDate dueDate) {

		    if (dueDate == null) {
		        dto.setSeverity("Unknown");
		        dto.setDays(0L);
		        return;
		    }

		    long days = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

		    dto.setDays(Math.abs(days));

		    if (days < 0) {
		        dto.setSeverity("Critical");
		    } else if (days <= 7) {
		        dto.setSeverity("High");
		    } else if (days <= 30) {
		        dto.setSeverity("Medium");
		    } else {
		        dto.setSeverity("Low");
		    }
		}
	 
}

