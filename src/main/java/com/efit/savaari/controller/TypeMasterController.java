package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.TypeMasterDTO;
import com.efit.savaari.entity.TypeMasterVO;
import com.efit.savaari.service.TypeMasterService;

@RestController
@RequestMapping("/api/type")
public class TypeMasterController extends BaseController {

    @Autowired
    TypeMasterService typeMasterService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveType(@RequestBody TypeMasterDTO dto) {

        String methodName = "saveType()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

        String errorMsg = null;
        Map<String, Object> map = new HashMap<>();
        ResponseDTO responseDTO;

        try {
            TypeMasterVO type = typeMasterService.saveOrUpdateType(dto);

            map.put(CommonConstant.STRING_MESSAGE,
                    dto.getTypeId() == null ? "Type created successfully" : "Type updated successfully");
            map.put("type", type);

            responseDTO = createServiceResponse(map);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(map, "Type save failed", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/byVehicle")
    public ResponseEntity<ResponseDTO> getByVehicle(@RequestParam Long vehicleId) {

        List<TypeMasterVO> list = typeMasterService.getByVehicle(vehicleId);

        Map<String, Object> map = new HashMap<>();
        map.put("typeList", list);

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @PutMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam Long typeId) {

        typeMasterService.deleteType(typeId);

        return ResponseEntity.ok(createServiceResponse(new HashMap<>()));
    }
}
