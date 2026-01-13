package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.dto.TripInvoiceResponseDTO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.TripInvoiceService;


@RestController
@RequestMapping("/api/tripinvoice")
public class TripInvoiceController extends BaseController {

    @Autowired
    private TripInvoiceService tripInvoiceService;

//    @PostMapping("/save")
//    public ResponseEntity<ResponseDTO> saveOrUpdate(@RequestBody TripInvoiceDTO dto) {
//
//        TripInvoiceVO invoice = tripInvoiceService.saveOrUpdate(dto);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("message", dto.getInvoiceId() == null
//                ? "Invoice created successfully"
//                : "Invoice updated successfully");
//        map.put("invoice", invoice);
//
//        return ResponseEntity.ok(createServiceResponse(map));
//    }
//    
//    
    @PutMapping("/createUpdateTripInvoice")
	public ResponseEntity<ResponseDTO> createUpdateTripInvoice(@RequestBody TripInvoiceDTO tripInvoiceDTO) {

		String methodName = "createUpdateTripInvoice()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseMap = new HashMap<>();

		try {
			Map<String, Object> tripInvoice = tripInvoiceService.createUpdateTripInvoice(tripInvoiceDTO);
			responseMap.put("message", tripInvoice.get("message"));
			responseMap.put("tripInvoice", tripInvoice.get("tripInvoice"));

			ResponseDTO responseDTO = createServiceResponse(responseMap);
			return ResponseEntity.ok(responseDTO);
		} catch (Exception e) {

			LOGGER.error("{} - Unexpected Error: {}", methodName, e.getMessage(), e);

			ResponseDTO errorDTO = createServiceResponseError(responseMap, "Unexpected Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
		}
	}

    @GetMapping("/getTripInvoiceById")
    public ResponseEntity<ResponseDTO> getTripInvoiceById(@RequestParam Long invoiceId) {

        String methodName = "getTripInvoiceById()";
        LOGGER.debug("Starting {}", methodName);

        Map<String, Object> responseMap = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            TripInvoiceResponseDTO invoice =
                    tripInvoiceService.getTripInvoiceById(invoiceId);

            responseMap.put("message", "TripInvoice details retrieved successfully");
            responseMap.put("invoice", invoice);
            responseDTO = createServiceResponse(responseMap);

        } catch (Exception e) {
            LOGGER.error("Error in {}: {}", methodName, e.getMessage(), e);
            responseDTO = createServiceResponseError(
                    responseMap,
                    "Error fetching invoice",
                    e.getMessage()
            );
        }

        LOGGER.debug("Ending {}", methodName);
        return ResponseEntity.ok(responseDTO);
    }


//    @GetMapping("/getById")
//    public ResponseEntity<ResponseDTO> getById(@RequestParam Long invoiceId) {
//
//        TripInvoiceVO invoice = tripInvoiceService.getById(invoiceId);
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("invoice", invoice);
//
//        return ResponseEntity.ok(createServiceResponse(map));
//    }

//    @GetMapping("/getAll")
//    public ResponseEntity<ResponseDTO> getAll() {
//
//        List<TripInvoiceVO> list = tripInvoiceService.getAll();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("list", list);
//
//        return ResponseEntity.ok(createServiceResponse(map));
//    }
//    
    
    @GetMapping("/getAllTripInvoiceByOrgId")
   	public ResponseEntity<ResponseDTO> getAllTripInvoiceByOrgId(@RequestParam Long orgId,
   			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int count) {
   		String methodName = "getAllTripInvoiceByOrgId()";
   		LOGGER.debug("Starting {}", methodName);

   		Map<String, Object> responseMap = new HashMap<>();
   		ResponseDTO responseDTO;

   		try {
   			Map<String, Object> tripInvoice = tripInvoiceService.getAllTripInvoiceByOrgId(orgId, page, count);
   			responseMap.put("message", "TripInvoice Details retrieved successfully");
   			responseMap.put("tripInvoice", tripInvoice);
   			responseDTO = createServiceResponse(responseMap);
   		} catch (Exception e) {
   			LOGGER.error("Error in {}: {}", methodName, e.getMessage());
   			responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
   		}

   		LOGGER.debug("Ending {}", methodName);
   		return ResponseEntity.ok(responseDTO);
   	}

}

