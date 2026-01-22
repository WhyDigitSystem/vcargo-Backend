package com.efit.savaari.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.FuelDashboardDTO;
import com.efit.savaari.dto.MaintenanceDashboardDTO;
import com.efit.savaari.dto.TDriverDashboardDTO;
import com.efit.savaari.dto.TripDashboardDTO;
import com.efit.savaari.dto.TyreDashboardDTO;
import com.efit.savaari.repo.DriverStatusCountProjection;
import com.efit.savaari.repo.FuelRepo;
import com.efit.savaari.repo.MaintenanceRepo;
import com.efit.savaari.repo.TdriverRepo;
import com.efit.savaari.repo.TripRepo;
import com.efit.savaari.repo.TyreMasterRepo;
import com.efit.savaari.repo.VehicleRepo;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	public static final Logger LOGGER = LoggerFactory.getLogger(DashBoardServiceImpl.class);

	
	 @Autowired 
	 private TripRepo tripRepo;
	 @Autowired 
	 private MaintenanceRepo maintenanceRepo;
	 @Autowired 
	 private FuelRepo fuelRepo;
//	 @Autowired
//	 private InvoiceRepo invoiceRepo;
	 @Autowired 
	 private TyreMasterRepo tyreRepo;
	 
	 @Autowired
	 TdriverRepo tdriverRepo;
	 @Autowired
	 private VehicleRepo vehiclesRepo;

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
	 public Map<String, Object> getDashboardData(Long orgId) {

	     Map<String, Object> map = new HashMap<>();

	     map.put("trips", getTrips(orgId));
	     map.put("maintenance", getMaintenance(orgId));
	     map.put("fuel", getFuel(orgId));
	     map.put("tyres", getTyres(orgId));

	     return map;
	 }

	 private List<TripDashboardDTO> getTrips(Long orgId) {

		    return tripRepo.findByOrgId(orgId).stream().map(t -> {
		        TripDashboardDTO d = new TripDashboardDTO();
		        d.setId(t.getId());
		        
		        if (t.getVehicle() != null) {
		            d.setVehicleNo(t.getVehicle().getVehicleNumber());
		        } else {
		            d.setVehicleNo("");
		        }
		        
		        if (t.getDriver() != null) {
		            d.setDriverName(t.getDriver().getName());
		        } else {
		            d.setDriverName("");
		        }
		        
		        d.setRoute(t.getSource());
		        d.setStatus(t.getStatus());
		        return d;
		    }).collect(java.util.stream.Collectors.toList());
		}

	 private List<MaintenanceDashboardDTO> getMaintenance(Long orgId) {

		    return maintenanceRepo.findByOrgId(orgId).stream().map(m -> {
		        MaintenanceDashboardDTO d = new MaintenanceDashboardDTO();
		        d.setId(m.getId());
		        if (m.getVehicle() != null) {
		            d.setVehicleNo(m.getVehicle().getVehicleNumber());
		        } else {
		            d.setVehicleNo("");
		        }
		        d.setDescription(m.getDescription());
		        d.setCompletedDate(m.getCompletedDate());
		        d.setTotalCost(m.getTotalCost());
		        d.setPriority(m.getPriority());

		        d.setType(m.getType());
		        d.setStatus(m.getStatus());
		        return d;
		    }).collect(java.util.stream.Collectors.toList());
		}

	 
	 private List<FuelDashboardDTO> getFuel(Long orgId) {

		    return fuelRepo.findByOrgId(orgId).stream().map(f -> {

		        FuelDashboardDTO d = new FuelDashboardDTO();

		        // ID
		        d.setId(String.valueOf(f.getId()));

		        // Vehicle Number
		        if (f.getVehicle() != null) {
		            d.setVehicle(f.getVehicle().getVehicleNumber());
		        } else {
		            d.setVehicle("");
		        }

		        // Station
		        d.setStation(f.getStation());

		        // Quantity
		        if (f.getQuantity() != null) {
		            d.setQuantity(f.getQuantity());
		        }

		        // Cost
		        if (f.getCost() != null) {
		            d.setTotal(f.getCost());
		        }

		        
		        if (f.getCost() != null) {
		        	BigDecimal rate = f.getCost().divide(f.getQuantity());
		        	d.setRate(rate);		        }
		        // Date
		        if (f.getDate() != null) {
		            d.setDate(f.getDate().toString()); // later you can format
		        }

		        // Driver Name
		        if (f.getDriver() != null) {
		            d.setDriver(f.getDriver().getName());
		        } else {
		            d.setDriver("");
		        }

		        return d;

		    }).collect(java.util.stream.Collectors.toList());
		}


	 
	 private List<TyreDashboardDTO> getTyres(Long orgId) {

		    return tyreRepo.findByOrgId(orgId).stream().map(t -> {
		        TyreDashboardDTO d = new TyreDashboardDTO();
		        d.setId(t.getId());
		        if (t.getVehicle() != null) {
		            d.setVehicle(t.getVehicle().getVehicleNumber());
		        } else {
		            d.setVehicle("");
		        }
		        d.setBrand(t.getBrand());
		        d.setPosition(t.getPosition());
		        d.setStatus(t.getStatus());
		        d.setDepth(t.getTreadDepth());
		        d.setInstalledDate(t.getPurchaseDate());


		        if (t.getVehicle() != null) {
		            d.setVehicleNumber(t.getVehicle().getVehicleNumber());
		        }
		        return d;
		    }).collect(java.util.stream.Collectors.toList());
		}


	 
	 //Trips , driver DashBoard
	 
	 @Override
	    public Map<String, Object> getAllDashBoardDetails(Long orgId) {

	        Map<String, Object> dashboard = new HashMap<>();

	        // ✅ Vehicle Count
	        Number vehicleCount = vehiclesRepo.getActiveVehicleCount(orgId);
	        dashboard.put("activeVechicle", vehicleCount == null ? 0L : vehicleCount.longValue());
	        
	        Number maintenanceVehicleCount = vehiclesRepo.getMaintenanceVehicleCount(orgId);
	        dashboard.put("maintenanceVehicleCount", maintenanceVehicleCount == null ? 0L : maintenanceVehicleCount.longValue());

	        Number upcomingMaintenanceVehicle = vehiclesRepo.getUpcomingMaintenanceVehicle(orgId);
	        dashboard.put("upcomingMaintenanceVehicle", upcomingMaintenanceVehicle == null ? 0L : upcomingMaintenanceVehicle.longValue());
	        
	        BigDecimal maintenanceCost = vehiclesRepo.getmaintenanceCost(orgId);
	        dashboard.put("maintenanceCost", maintenanceCost == null ? 0L : maintenanceCost.longValue());
	        
	        
	        Number totalTyresPurchased = tyreRepo.getTyresPurchased(orgId);
	        dashboard.put("totalTyresPurchased", totalTyresPurchased == null ? 0L : totalTyresPurchased.longValue());
	        
	        Number totalTripCount = tripRepo.getTotalCount(orgId);
	        dashboard.put("totalTripCount", totalTripCount == null ? 0L : totalTripCount.longValue());
	        
	        BigDecimal totalFuelAmount = fuelRepo.gettotalFuelAmount(orgId);
	        dashboard.put("totalFuelAmount", totalFuelAmount == null ? 0L : totalFuelAmount.longValue());
	        
	        Number onTripDriverCount = tripRepo.getOnTripDriverCount(orgId);
	        dashboard.put("onTripDriverCount", onTripDriverCount == null ? 0L : onTripDriverCount.longValue());
	        
	        // ✅ Total Fuel Cost
	        BigDecimal totalFuel = fuelRepo.getTotalFuel(orgId);
	        dashboard.put("totalFuel", totalFuel == null ? BigDecimal.ZERO : totalFuel);

	        // ✅ Driver Status Counts
	        dashboard.put("tDriver", getDriver(orgId));

	        return dashboard;
	    }

	 private TDriverDashboardDTO getDriver(Long orgId) {

		    DriverStatusCountProjection p =
		            tdriverRepo.getDriverStatusCounts(orgId);

		    TDriverDashboardDTO d = new TDriverDashboardDTO();

		    if (p != null) {
		        d.setActiveDriver(p.getActiveCount() == null ? 0L : p.getActiveCount());
		        d.setInActiveDriver(p.getInactiveCount() == null ? 0L : p.getInactiveCount());
		        d.setLeaveDriver(p.getLeaveCount() == null ? 0L : p.getLeaveCount());
		    }

		    return d;
		}


}

