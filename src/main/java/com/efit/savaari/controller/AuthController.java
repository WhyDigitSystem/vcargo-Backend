package com.efit.savaari.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.common.AuthConstant;
import com.efit.savaari.common.CommonConstant;
import com.efit.savaari.common.UserConstants;
import com.efit.savaari.dto.ChangePasswordFormDTO;
import com.efit.savaari.dto.LoginFormDTO;
import com.efit.savaari.dto.RefreshTokenDTO;
import com.efit.savaari.dto.ResetPasswordDTO;
import com.efit.savaari.dto.ResetPasswordFormDTO;
import com.efit.savaari.dto.ResponsibilityDTO;
import com.efit.savaari.dto.RolesDTO;
import com.efit.savaari.dto.SignUpFormDTO;
import com.efit.savaari.dto.UserResponseDTO;
import com.efit.savaari.entity.ResponsibilityVO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.AuthService;
import com.efit.savaari.service.OtpService;

@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthService authService;
	
	@Autowired
	OtpService otpService;

	@PutMapping("/signup")
	public ResponseEntity<ResponseDTO> signup(@Valid @RequestBody SignUpFormDTO signUpRequest) {
	    
	    String methodName = "signup()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

	    ResponseDTO responseDTO;

	    try {
	        // ⛔ WRONG earlier — you ignored the return value
	        // 👍 FIXED:
	        ResponseDTO serviceResponse = authService.signup(signUpRequest);

	        responseDTO = createServiceResponse(serviceResponse.getParamObjectsMap());
	    } 
	    catch (Exception e) {

	        String errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME,
	                     methodName, signUpRequest.getEmail(), errorMsg);

	        responseDTO = createServiceResponseError(
	                new HashMap<>(),
	                UserConstants.SIGNUP_REGISTERED_FAILED_MESSAGE,
	                errorMsg
	        );
	    }

	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginFormDTO loginRequest,HttpServletRequest request) {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserResponseDTO userResponseDTO = null;
		try {
			userResponseDTO = authService.login(loginRequest,request);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, loginRequest.getUserName(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGIN_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userResponseDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_LOGIN_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/logout")
	public ResponseEntity<ResponseDTO> logout(@RequestParam String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.logout(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGOUT_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_LOGOUT_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.changePassword(changePasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					changePasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.CHANGE_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.CHANGE_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.resetPassword(resetPasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					resetPasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.RESET_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.RESET_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRefreshToken")
	public ResponseEntity<ResponseDTO> getRefreshToken(@RequestParam String userName, @RequestParam String tokenId) {
		String methodName = "getRefreshToken()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();
		try {
			refreshTokenDTO = authService.getRefreshToken(userName, tokenId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, AuthConstant.REFRESH_TOKEN_SUCCESS_MESSAGE);
			responseObjectsMap.put(CommonConstant.REFRESH_TOKEN, refreshTokenDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, AuthConstant.REFRESH_TOKEN_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateResponsibility")
	public ResponseEntity<ResponseDTO> createUpdateResponsibility(@RequestBody ResponsibilityDTO responsibilityDTO) {
		String methodName = "createUpdateResponsibility()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> responsibile = authService.createUpdateResponsibilities(responsibilityDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responsibile.get("message") );
			responseObjectsMap.put("responsibilityVO", responsibile.get("responsibilityVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getResponsibilityForRolesByOrgId")
	public ResponseEntity<ResponseDTO> getResponsibilityForRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getResponsibilityForRolesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> responsibilityVO = new ArrayList<>();
		try {
			responsibilityVO = authService.getResponsibilityForRolesByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Responsibility Founded");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Responsibility not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Responsibility not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateRoles")
	public ResponseEntity<ResponseDTO> createUpdateRoles(@RequestBody RolesDTO rolesDTO) {
		String methodName = "createUpdateResponsibility()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> roles = authService.createUpdateRoles(rolesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, roles.get("message") );
			responseObjectsMap.put("rolesVO", roles.get("rolesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allRolesByOrgId")
	public ResponseEntity<ResponseDTO> getAllRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RolesVO> rolesVO = new ArrayList<>();
		try {
			rolesVO = authService.getAllRoles(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allActiveRolesByOrgId")
	public ResponseEntity<ResponseDTO> getAllActiveRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllActiveRolesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RolesVO> rolesVO = new ArrayList<>();
		try {
			rolesVO = authService.getAllActiveRoles(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/rolesById")
	public ResponseEntity<ResponseDTO> getRolesById(@RequestParam Long id) {
		String methodName = "getRolesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RolesVO rolesVO = new RolesVO();
		try {
			rolesVO = authService.getRolesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/allActiveResponsibilityByOrgId")
	public ResponseEntity<ResponseDTO> getAllActiveResponsibilityByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllActiveResponsibilityByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ResponsibilityVO> resposResponsibilityVO = new ArrayList<>();
		try {
			resposResponsibilityVO = authService.getAllActiveResponsibility(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("resposResponsibilityVO", resposResponsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allResponsibilityByOrgId")
	public ResponseEntity<ResponseDTO> getAllResponsibilityByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllResponsibilityByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ResponsibilityVO> responsibilityVO = new ArrayList<>();
		try {
			responsibilityVO = authService.getAllResponsibility(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/responsibilityById")
	public ResponseEntity<ResponseDTO> getResponsibilityById(@RequestParam Long id) {
		String methodName = "getResponsibilityById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ResponsibilityVO responsibilityVO = new ResponsibilityVO();
		try {
			responsibilityVO = authService.getResponsibilityById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allUsersByOrgId")
	public ResponseEntity<ResponseDTO> getAllUsersByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllUsersByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UserVO> userVO = new ArrayList<>();
		try {
			userVO = authService.getAllUsersByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Users information get successfully");
			responseObjectsMap.put("userVO", userVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Users information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getUserById")
	public ResponseEntity<ResponseDTO> getUserById(@RequestParam Long userId) {
		String methodName = "getUserById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserVO userVO = null;
		try {
			userVO = authService.getUserById(userId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, userId, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.GET_USER_INFORMATION_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					UserConstants.GET_USER_INFORMATION_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getUserByUserName")
	public ResponseEntity<ResponseDTO> getUserByUserName(@RequestParam String userName) {
		String methodName = "getUserByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserVO userVO = null;
		try {
			userVO = authService.getUserByUserName(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_ID, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.GET_USER_INFORMATION_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					UserConstants.GET_USER_INFORMATION_FAILED_MESSAGE, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	
	
	@GetMapping("/getAllUsersList")
	public ResponseEntity<ResponseDTO> getUsersByOrgId(
	        @RequestParam(required = false) String branchCode,
	        @RequestParam(defaultValue = "") String search,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int count
	) {
	    String methodName = "getAllUsersList()";
	    LOGGER.debug("Starting {}", methodName);

	    Map<String, Object> responseMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> userVO = authService.getAllUsersList( branchCode, search, page, count);
	        responseMap.put("message", "Users retrieved successfully");
	        responseMap.put("userVO", userVO);
	        responseDTO = createServiceResponse(responseMap);
	    } catch (Exception e) {
	        LOGGER.error("Error in {}: {}", methodName, e.getMessage());
	        responseDTO = createServiceResponseError(responseMap, "Error fetching users", e.getMessage());
	    }

	    LOGGER.debug("Ending {}", methodName);
	    return ResponseEntity.ok(responseDTO);
	}
	
	
	//ForgotPassword
    
//	@PostMapping("/forgot-password")
//	public ResponseEntity<ResponseDTO> sendOtp(@RequestParam String email) {
//
//	    try {
//	        ResponseDTO res = new ResponseDTO();
//
//	        // Step 1: Check user exists
//	        authService.checkUserExists(email);
//
//	        // Step 2: Send OTP
//	        otpService.sendOtp(email);
//
//	        // Success response
//	        res.setStatusFlag(ResponseDTO.OK);
//	        res.setStatus(true);
//	        res.addObject1("message", "OTP sent to email.");
//	        res.addObject1("email", email);
//
//	        return ResponseEntity.ok(res);
//
//	    } catch (Exception e) {
//
//	        ResponseDTO error = new ResponseDTO();
//	        error.setStatus(false);
//	        error.setStatusFlag(ResponseDTO.ERROR);
//	        error.addObject1("message", e.getMessage());
//
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//	    }
//	}
	
	
	@PostMapping("/forgot-password")
	public ResponseEntity<ResponseDTO> sendOtp(@RequestParam String email) {
	    String methodName = "sendOtp()";
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        // Step 1: Check user
	        authService.checkUserExists(email);

	        // Step 2: Send OTP
	        otpService.sendOtp(email);

	        // Success Response
	        responseObjectsMap.put("message", "OTP sent to email.");
	        responseObjectsMap.put("email", email);

	        responseDTO = createServiceResponse(responseObjectsMap);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}

	
	@PostMapping("/verify-reset-otp")
	public ResponseEntity<ResponseDTO> verifyOtp(@RequestParam String email,
	                                             @RequestParam String otp) {

	    String methodName = "verifyOtp()";
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {

	        boolean ok = otpService.verifyOtp(email, otp);

	        if (!ok) {
	            responseObjectsMap.put("verified", false);
	            responseObjectsMap.put("message", "Invalid or expired OTP");

	            responseDTO = createServiceResponseError(responseObjectsMap, "Invalid OTP", "Invalid or expired OTP");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
	        }

	        // Success
	        responseObjectsMap.put("verified", true);
	        responseObjectsMap.put("email", email);
	        responseObjectsMap.put("message", "OTP verified successfully.");

	        responseDTO = createServiceResponse(responseObjectsMap);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ResponseDTO> resetPassword(@RequestBody ResetPasswordDTO req) {

	    String methodName = "resetPassword()";
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO;

	    try {
	        Map<String, Object> result = authService.resetPassword(req);

	        responseObjectsMap.put("message", result.get("message"));
	        responseObjectsMap.put("email", result.get("email"));

	        responseDTO = createServiceResponse(responseObjectsMap);
	        return ResponseEntity.ok(responseDTO);

	    } catch (Exception e) {

	        responseDTO = createServiceResponseError(responseObjectsMap, e.getMessage(), e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
	    }
	}

    
    
//    @PostMapping("/verify-reset-otp")
//    public ResponseEntity<ResponseDTO> verifyOtp(@RequestParam String email,
//                                                 @RequestParam String otp) {
//
//        try {
//            boolean ok = otpService.verifyOtp(email, otp);
//
//            ResponseDTO res = new ResponseDTO();
//
//            if (!ok) {
//                res.setStatusFlag(ResponseDTO.ERROR);
//                res.setStatus(false);
//                res.addObject1("message", "Invalid or expired OTP");
//                res.addObject1("verified", false);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//            }
//
//            res.setStatusFlag(ResponseDTO.OK);
//            res.setStatus(true);
//            res.addObject1("message", "OTP verified successfully.");
//            res.addObject1("verified", true);
//            res.addObject1("email", email);
//
//            return ResponseEntity.ok(res);
//
//        } catch (Exception e) {
//
//            ResponseDTO error = new ResponseDTO();
//            error.setStatus(false);
//            error.setStatusFlag(ResponseDTO.ERROR);
//            error.addObject1("message", e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }

    
//    @PostMapping("/reset-password")
//    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody ResetPasswordDTO req) {
//
//        try {
//            ResponseDTO res = authService.resetPassword(req);
//            return ResponseEntity.ok(res);
//
//        } catch (Exception e) {
//
//            ResponseDTO error = new ResponseDTO();
//            error.setStatus(false);                     // ❗ your DTO has this
//            error.setStatusFlag("ERROR");               // ❗ your DTO has this
//
//            Map<String, Object> err = new HashMap<>();
//            err.put("message", e.getMessage());         // put message inside map
//            error.setParamObjectsMap(err);              // ❗ your DTO has this
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }


    @PutMapping("/createApprovalUserList")
	public ResponseEntity<ResponseDTO> createApprovalUserList( @RequestParam Long id,
			@RequestParam String userName, @RequestParam String action, @RequestParam String actionBy) {
		String methodName = "createApprovalUserList()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> result = authService.createApprovalUserList( id, userName, action,
					actionBy);

			// ✅ Correct keys from the returned map
			responseObjectsMap.put("userVO", result.get("userVO"));
			responseObjectsMap.put("message", result.get("message"));

			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
    
}
