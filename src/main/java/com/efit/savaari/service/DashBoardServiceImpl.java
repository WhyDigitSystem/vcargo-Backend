package com.efit.savaari.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelDashboardDTO;
import com.efit.savaari.dto.MaintenanceDashboardDTO;
import com.efit.savaari.dto.TDriverDashboardDTO;
import com.efit.savaari.dto.TripDashboardDTO;
import com.efit.savaari.dto.TyreDashboardDTO;
import com.efit.savaari.entity.FuelVO;
import com.efit.savaari.entity.MaintenanceVO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.entity.TripVO;
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
}

