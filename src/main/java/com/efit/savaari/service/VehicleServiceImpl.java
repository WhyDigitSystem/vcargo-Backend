package com.efit.savaari.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.efit.savaari.dto.DriverDTO;
import com.efit.savaari.dto.VehicleDTO;
import com.efit.savaari.dto.VehicleTypeDTO;
import com.efit.savaari.entity.DriverVO;
import com.efit.savaari.entity.VehicleTypeVO;
import com.efit.savaari.entity.VehicleVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.DriverRepo;
import com.efit.savaari.repo.VehicleRepo;
import com.efit.savaari.repo.VehicleTypeRepo;

@Service
public class VehicleServiceImpl implements VehicleService {
	
  public static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

  @Autowired
  VehicleRepo vehicleRepo;
  
  @Autowired
  PaginationService paginationService;
  
  @Autowired
  DriverRepo driverRepo;
  
  @Autowired
  VehicleTypeRepo vehicleTypeRepo;
  

	
	
// Vehicle Service Start here
	
  @Override
  @Transactional
  public Map<String, Object> createUpdateVehicle(VehicleDTO dto) throws Exception {

      VehicleVO vehicleVO;
      String message = null;
      


      // CREATE flow
      if (ObjectUtils.isEmpty(dto.getId())) {

          // Validation: duplicate vehicle number
          if (vehicleRepo.existsByVehicleNumberAndOrgId(dto.getVehicleNumber(), dto.getOrgId())) {
              String errorMessage = String.format(
                      "This Vehicle Number: %s Already Exists in This Organization",
                      dto.getVehicleNumber());
              throw new ApplicationException(errorMessage);
          }

          // Create new vehicle
          vehicleVO = new VehicleVO();
          vehicleVO.setCreatedBy(dto.getCreatedBy());
          vehicleVO.setUpdatedBy(dto.getCreatedBy());

          message = "Vehicle Created Successfully";

      } else {

          // UPDATE flow
          vehicleVO = vehicleRepo.findById(dto.getId())
                  .orElseThrow(() -> new ApplicationException(
                          "Vehicle not found with id: " + dto.getId()));

          vehicleVO.setUpdatedBy(dto.getCreatedBy());

          // Check if vehicle number changed → validate duplicate
          if (!vehicleVO.getVehicleNumber().equalsIgnoreCase(dto.getVehicleNumber())) {

              if (vehicleRepo.existsByVehicleNumberAndOrgId(dto.getVehicleNumber(), dto.getOrgId())) {
                  String errorMessage = String.format(
                          "This Vehicle Number: %s Already Exists in This Organization",
                          dto.getVehicleNumber());
                  throw new ApplicationException(errorMessage);
              }

              vehicleVO.setVehicleNumber(dto.getVehicleNumber().toUpperCase());
          }

          message = "Vehicle Updated Successfully";
      }

      // COPY DTO → VO fields (Corrected mapping)
      getVehicleVOFromDTO(vehicleVO, dto);

      // SAVE
      vehicleRepo.save(vehicleVO);

      // RESPONSE (same format as branch)
      Map<String, Object> response = new HashMap<>();
      response.put("message", message);
      response.put("vehicleVO", vehicleVO);

      return response;
  }

  private void getVehicleVOFromDTO(VehicleVO vehicleVO, VehicleDTO dto) {

      vehicleVO.setVehicleNumber(dto.getVehicleNumber().toUpperCase());
      vehicleVO.setVehicleType(dto.getVehicleType().toUpperCase());
//      vehicleVO.setStatus(dto.getStatus().toUpperCase());
      vehicleVO.setBranch(dto.getBranch().toUpperCase());
      vehicleVO.setBranchCode(dto.getBranchCode().toUpperCase());
      vehicleVO.setOrgId(dto.getOrgId());
      vehicleVO.setActive(dto.isActive());

  }


		@Override
		public VehicleVO getVehicleById(Long id) throws ApplicationException {

			return vehicleRepo.findById(id)
                    .orElseThrow(() -> new ApplicationException("vehicle not found"));
		}
		
		
		@Override
		public Map<String, Object> getAllVehicle(String branchCode, Long orgId ,String search, int page, int count) {

			if (search != null) {
		        search = search.trim();
		        if (search.isEmpty()) {
		            search = null;
		        }
		    }
			
		    Pageable pageable = PageRequest.of(page - 1, count, Sort.by("vehiclenumber").ascending());
		    Page<VehicleVO> vehiclePage = vehicleRepo.getAllVehicle(branchCode,orgId, search, pageable);

		    // return paginated response
		    return paginationService.buildResponse(vehiclePage);

		}
		
		// End Vehicle Service here
		
		// Driver Service start here
	
		
		@Override
		@Transactional
		public Map<String, Object> createUpdateDriver(DriverDTO dto) throws Exception {

		    DriverVO driverVO;
		    String message = null;

		    // CREATE
		    if (ObjectUtils.isEmpty(dto.getId())) {

		        if (driverRepo.existsByDriverNumberAndOrgId(dto.getDriverNumber(), dto.getOrgId())) {
		            throw new ApplicationException(
		                "This Driver Number: " + dto.getDriverNumber() + " Already Exists in This Organization"
		            );
		        }

		        driverVO = new DriverVO();
		        driverVO.setCreatedBy(dto.getCreatedBy());
		        driverVO.setUpdatedBy(dto.getCreatedBy());
		   

		        message = "Driver Created Successfully";

		    } else {

		        driverVO = driverRepo.findById(dto.getId())
		                .orElseThrow(() -> new ApplicationException("Driver not found with id: " + dto.getId()));

		        driverVO.setUpdatedBy(dto.getCreatedBy());

		        if (!driverVO.getDriverNumber().equalsIgnoreCase(dto.getDriverNumber())) {

		            if (driverRepo.existsByDriverNumberAndOrgId(dto.getDriverNumber(), dto.getOrgId())) {
		                throw new ApplicationException(
		                    "This Driver Number: " + dto.getDriverNumber() + " Already Exists in This Organization"
		                );
		            }

		            driverVO.setDriverNumber(dto.getDriverNumber());
		        }

		        message = "Driver Updated Successfully";
		    }

		    // Copy DTO → VO
		    getDriverVOFromDTO(driverVO, dto);

		    // Save
		    driverRepo.save(driverVO);

		    Map<String, Object> response = new HashMap<>();
		    response.put("message", message);
		    response.put("driverVO", driverVO);

		    return response;
		}

		private void getDriverVOFromDTO(DriverVO driverVO, DriverDTO dto) {
		    driverVO.setDriverName(dto.getDriverName().toUpperCase());
		    driverVO.setDriverNumber(dto.getDriverNumber());
		    driverVO.setBranch(dto.getBranch().toUpperCase());
		    driverVO.setBranchCode(dto.getBranchCode().toUpperCase());
		    driverVO.setOrgId(dto.getOrgId());
		    driverVO.setActive(dto.isActive());

		}
		
		@Override
		public DriverVO getDriverById(Long id) throws ApplicationException {

			return driverRepo.findById(id)
                    .orElseThrow(() -> new ApplicationException("driver not found"));
		}
		
		@Override
		public Map<String, Object> getAllDriver(String branchCode,Long orgId, String search, int page, int count) {

			if (search != null) {
		        search = search.trim();
		        if (search.isEmpty()) {
		            search = null;
		        }
		    }
			
		    Pageable pageable = PageRequest.of(page - 1, count, Sort.by("drivernumber").ascending());
		    Page<DriverVO> driverPage = driverRepo.getAllDriver(branchCode,orgId, search, pageable);

		    // return paginated response
		    return paginationService.buildResponse(driverPage);

		}
		
// Vehicle Type Service Start here
		
		@Override
		@Transactional
		public Map<String, Object> createUpdateVehicleType(VehicleTypeDTO dto) throws Exception {

		    VehicleTypeVO vehicleTypeVO;
		    String message = null;

		    // CREATE
		    if (ObjectUtils.isEmpty(dto.getId())) {

		        if (vehicleTypeRepo.existsByVehicleTypeAndOrgId(dto.getVehicleType(), dto.getOrgId())) {
		            throw new ApplicationException(
		                "This Vehicle Type: " + dto.getVehicleType() + " Already Exists in This Organization"
		            );
		        }

		        vehicleTypeVO = new VehicleTypeVO();
		        vehicleTypeVO.setCreatedBy(dto.getCreatedBy());
		        vehicleTypeVO.setUpdatedBy(dto.getCreatedBy());
		        vehicleTypeVO.setActive(true);
		        vehicleTypeVO.setCancel(false);

		        message = "Vehicle Type Created Successfully";

		    } else {

		        vehicleTypeVO = vehicleTypeRepo.findById(dto.getId())
		                .orElseThrow(() -> new ApplicationException("Vehicle Type not found with id: " + dto.getId()));

		        vehicleTypeVO.setUpdatedBy(dto.getCreatedBy());

		        if (!vehicleTypeVO.getVehicleType().equalsIgnoreCase(dto.getVehicleType())) {

		            if (vehicleTypeRepo.existsByVehicleTypeAndOrgId(dto.getVehicleType(), dto.getOrgId())) {
		                throw new ApplicationException(
		                    "This Vehicle Type: " + dto.getVehicleType() + " Already Exists in This Organization"
		                );
		            }

		            vehicleTypeVO.setVehicleType(dto.getVehicleType());
		        }

		        message = "Vehicle Type Updated Successfully";
		    }

		    // Copy DTO → VO
		    getVehicleTypeVOFromDTO(vehicleTypeVO, dto);

		    // Save
		    vehicleTypeRepo.save(vehicleTypeVO);

		    Map<String, Object> response = new HashMap<>();
		    response.put("message", message);
		    response.put("vehicleTypeVO", vehicleTypeVO);

		    return response;
		}

		
		// Copy DTO to VO
		    private void getVehicleTypeVOFromDTO(VehicleTypeVO vo, VehicleTypeDTO dto) {

		        vo.setVehicleType(dto.getVehicleType().toUpperCase());
		        vo.setStatus(dto.getStatus().toUpperCase());
		        vo.setMileage(dto.getMileage());

		        // Dimensions
		        vo.setUnit(dto.getUnit());
		        vo.setHight(dto.getHight());
		        vo.setWidth(dto.getWidth());
		        vo.setLength(dto.getLength());
		        vo.setVehicleSqftCapacity(dto.getVehicleSqftCapacity());
		        vo.setVehicleTonnageCapacity(dto.getVehicleTonnageCapacity());

		        // Common
		        vo.setBranch(dto.getBranch().toUpperCase());
		        vo.setBranchCode(dto.getBranchCode().toUpperCase());
		        vo.setOrgId(dto.getOrgId());
		    
		    }
		    
		    // End vehicle Type create update
		    
		    
		    // Get Vehicle Type by ID
			
			@Override
			public VehicleTypeVO getVehicleTypeById(Long id) throws ApplicationException {

				return vehicleTypeRepo.findById(id)
	                    .orElseThrow(() -> new ApplicationException("Vehicle Type not found"));
			}
			
			//end Get Vehicle Type by ID
			
			// Get All Vehicle Type with pagination
			@Override
			public Map<String, Object> getAllVehicleType(String branchCode,Long orgId, String search, int page, int count) {

			    // Clean search text
			    if (search != null) {
			        search = search.trim();
			        if (search.isEmpty()) {
			            search = null;
			        }
			    }

			    // Correct sorting field
			    Pageable pageable = PageRequest.of(page - 1, count, Sort.by("vehicleType").ascending());

			    Page<VehicleTypeVO> vehicleTypePage =
			            vehicleTypeRepo.getAllVehicleType(branchCode,orgId, search, pageable);

			    // Build pagination response
			    return paginationService.buildResponse(vehicleTypePage);
			}

			
		}
