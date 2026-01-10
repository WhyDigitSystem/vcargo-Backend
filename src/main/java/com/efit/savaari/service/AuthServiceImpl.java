package com.efit.savaari.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.efit.savaari.dto.RolesResponsibilityDTO;
import com.efit.savaari.dto.ScreensDTO;
import com.efit.savaari.dto.SignUpFormDTO;
import com.efit.savaari.dto.UserListDTO;
import com.efit.savaari.dto.UserResponseDTO;
import com.efit.savaari.dto.UserStatus;
import com.efit.savaari.entity.EmailOtpEntity;
import com.efit.savaari.entity.IndustryVO;
import com.efit.savaari.entity.ResponsibilityVO;
import com.efit.savaari.entity.RolesResponsibilityVO;
import com.efit.savaari.entity.RolesVO;
import com.efit.savaari.entity.ScreensVO;
import com.efit.savaari.entity.TokenVO;
import com.efit.savaari.entity.UserVO;
import com.efit.savaari.entity.VendorVO;
import com.efit.savaari.exception.ApplicationException;
import com.efit.savaari.repo.CompanyRepo;
import com.efit.savaari.repo.EmailOtpRepo;
import com.efit.savaari.repo.IndustryRepo;
import com.efit.savaari.repo.ResponsibilitiesRepo;
import com.efit.savaari.repo.RolesRepo;
import com.efit.savaari.repo.RolesResponsibilityRepo;
import com.efit.savaari.repo.ScreensRepo;
import com.efit.savaari.repo.TokenRepo;
import com.efit.savaari.repo.UserActionRepo;
import com.efit.savaari.repo.UserBranchAccessRepo;
import com.efit.savaari.repo.UserLoginRolesRepo;
import com.efit.savaari.repo.UserRepo;
import com.efit.savaari.repo.VendorRepo;
import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.security.TokenProvider;
import com.efit.savaari.util.CryptoUtils;

@Service
public class AuthServiceImpl implements AuthService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserActionRepo userActionRepo;

	@Autowired
	UserLoginRolesRepo loginRolesRepo;

	@Autowired
	UserBranchAccessRepo branchAccessRepo;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	UserService userService;

	@Autowired
	ScreensRepo screenRepo;

	@Autowired
	ResponsibilitiesRepo responsibilityRepo;

	@Autowired
	RolesRepo rolesRepo;

	@Autowired
	RolesResponsibilityRepo rolesResponsibilityRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	OtpService otpService;

	@Autowired
	EmailOtpRepo emailOtpRepo;

	@Autowired
	OtpAsyncService otpAsyncService; // <-- This must exist

	@Autowired
	PaginationService paginationService;

	@Autowired
	IndustryRepo industryRepo;

	@Autowired
	VendorRepo vendorRepo;

//	@Override
//	@Transactional
//	public void signup(SignUpFormDTO signUpRequest) throws ApplicationException {
//
//	    String methodName = "signup()";
//	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//	    if (signUpRequest == null || StringUtils.isBlank(signUpRequest.getEmail())) {
//	        throw new ApplicationContextException("Invalid signup details");
//	    }
//
//	    String email = signUpRequest.getEmail();
//
//	    Optional<UserVO> existingUserOpt = userRepo.findByEmail(email);
//
//	    UserVO userVO;
//
//	    // ---------------------------------------------------
//	    // 1️⃣ USER EXISTS → UPDATE
//	    // ---------------------------------------------------
//	    if (existingUserOpt.isPresent()) {
//	        userVO = existingUserOpt.get();
//
//	        userVO.setUserName(signUpRequest.getUserName());
//	        userVO.setMobileNo(signUpRequest.getMobileNo());
////	        userVO.setOrgId(signUpRequest.getOrgId());
////	        userVO.setBranch(signUpRequest.getBranch());
////	        userVO.setBranchCode(signUpRequest.getBranchCode());
//	        userVO.setType(signUpRequest.getType());
//	        userVO.setOrganizationName(signUpRequest.getOrganizationName());
//	        userVO.setCreatedby(signUpRequest.getCreatedby());
//
//	        // password update allowed?
//	        if (!StringUtils.isBlank(signUpRequest.getPassword())) {
//	            try {
//	                userVO.setPassword(
//	                        encoder.encode(CryptoUtils.getDecrypt(signUpRequest.getPassword()))
//	                );
//	            } catch (Exception e) {
//	                throw new ApplicationContextException("Unable to encode password");
//	            }
//	        }
//			    userVO.setActive(false);
//			
//	        userVO.setStatus(UserStatus.PENDING);
//
//	    }
//	    // ---------------------------------------------------
//	    // 2️⃣ USER DOES NOT EXIST → INSERT NEW
//	    // ---------------------------------------------------
//	    else {
//	        userVO = new UserVO();
//
//	        userVO.setUserName(signUpRequest.getUserName());
//	        try {
//	            userVO.setPassword(
//	                    encoder.encode(CryptoUtils.getDecrypt(signUpRequest.getPassword()))
//	            );
//	        } catch (Exception e) {
//	            throw new ApplicationContextException("Unable to encode password");
//	        }
//
//	        userVO.setEmail(signUpRequest.getEmail());
//	        userVO.setMobileNo(signUpRequest.getMobileNo());
////	        userVO.setOrgId(signUpRequest.getOrgId());
////	        userVO.setBranch(signUpRequest.getBranch());
////	        userVO.setBranchCode(signUpRequest.getBranchCode());
//	        userVO.setType(signUpRequest.getType());
//	        userVO.setOrganizationName(signUpRequest.getOrganizationName());
//	        userVO.setCreatedby(signUpRequest.getCreatedby());
//			userVO.setActive(false);
//			        
//	        userVO.setStatus(UserStatus.PENDING);
//	    }
//
//	    // ---------------------------------------------------
//	    // Save new or updated user
//	    // ---------------------------------------------------
//	    userRepo.save(userVO);
//
//	    // ---------------------------------------------------
//	    // Send OTP to verify email
//	    // ---------------------------------------------------
//	    Long generatedId = userVO.getId();  // <-- AUTO GENERATED PRIMARY KEY
//	    userVO.setOrgId(generatedId);
//	    userRepo.save(userVO);
//	    
//	    userService.createUserAction(
//	            userVO.getUserName(),
//	            userVO.getId(),
//	            UserConstants.USER_ACTION_ADD_ACCOUNT
//	    );
//	    
//	    otpService.sendOtp(email);
//
//	    // audit trail
//	   
//
//	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//	}

	@Override
	@Transactional
	public ResponseDTO signup(SignUpFormDTO signUpRequest) {

		String methodName = "signup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);

		ResponseDTO response = new ResponseDTO();

		if (signUpRequest == null || StringUtils.isBlank(signUpRequest.getEmail())) {
			throw new ApplicationContextException("Invalid signup details");
		}

		String email = signUpRequest.getEmail();

		Optional<UserVO> existingUserOpt = userRepo.findByEmail(email);
		UserVO userVO;

		// USER EXISTS → UPDATE
		if (existingUserOpt.isPresent()) {

			userVO = existingUserOpt.get();
			userVO.setUserName(signUpRequest.getUserName());
			userVO.setMobileNo(signUpRequest.getMobileNo());
			userVO.setType(signUpRequest.getType());
			userVO.setOrganizationName(signUpRequest.getOrganizationName());
			userVO.setCreatedby(signUpRequest.getCreatedby());
			userVO.setActive(false);
			userVO.setStatus(UserStatus.PENDING);

			if (!StringUtils.isBlank(signUpRequest.getPassword())) {
				try {
					userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(signUpRequest.getPassword())));
				} catch (Exception e) {
					throw new ApplicationContextException("Unable to encode password");
				}
			}
		}

		// NEW USER
		else {
			userVO = new UserVO();

			userVO.setUserName(signUpRequest.getUserName());
			userVO.setEmail(email); 
			userVO.setMobileNo(signUpRequest.getMobileNo());
			userVO.setType(signUpRequest.getType());
			userVO.setOrganizationName(signUpRequest.getOrganizationName());
			userVO.setCreatedby(signUpRequest.getCreatedby());
			userVO.setActive(false);
			userVO.setStatus(UserStatus.PENDING);

			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(signUpRequest.getPassword())));
			} catch (Exception e) {
				throw new ApplicationContextException("Unable to encode password");
			}
		}

		IndustryVO existingIndustry = industryRepo.findByEmail(email);
		VendorVO existingVendor = vendorRepo.findByPrimaryEmail(email);

		if (existingIndustry != null) {
			throw new ApplicationContextException("Email Already Exists In Industry Details");
		}

		if (existingVendor != null) {
			throw new ApplicationContextException("Email Already Exists In Vendor Details");
		}

		if (userVO.getType() == null || StringUtils.isBlank(userVO.getType())) {
			throw new ApplicationContextException("Invalid Type Details");
		}

//		else if (userVO.getType().equalsIgnoreCase("Industry")) {
//			IndustryVO industryVO = new IndustryVO();
//			industryVO.setIndustryType(userVO.getType());
//			industryVO.setCreatedBy(userVO.getCreatedby());
//			industryVO.setEmail(userVO.getEmail());
//			industryVO.setUserName(userVO.getUserName());
//			industryVO.setUserPassword(userVO.getPassword());
//			industryVO.setPhoneNumber(userVO.getMobileNo());
//			industryVO.setIndustryName(userVO.getOrganizationName());
//			industryRepo.save(industryVO);
//		}

		else if (userVO.getType().equalsIgnoreCase("Transporter")) {

			VendorVO vendorVO = new VendorVO();
			vendorVO.setBranch(userVO.getBranch());
			vendorVO.setBranchCode(userVO.getBranchCode());
			vendorVO.setCreatedBy(userVO.getCreatedby());
			vendorVO.setPrimaryEmail(userVO.getEmail());
			vendorVO.setUserName(userVO.getUserName());
			vendorVO.setUserPassword(userVO.getPassword());
			vendorVO.setOrganization(userVO.getOrganizationName());
			vendorVO.setVendorType(userVO.getType());
			vendorRepo.save(vendorVO);
			userVO.setVendorId(vendorVO.getId());
			userVO.setOrgId(vendorVO.getId());
		}
		userRepo.save(userVO);

		// Audit log
		userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_ADD_ACCOUNT);

		// -------------------------------------------
		// RETURN RESPONSE FIRST (fast API response)
		// -------------------------------------------
		response.setStatusFlag(ResponseDTO.OK);
		response.setStatus(true);
		response.addObject1("message", "User saved successfully.");
//	    response.addObject("userId", generatedId);

		// -------------------------------------------
		// OTP SEND IN BACKGROUND (non-blocking)
		// -------------------------------------------
		otpAsyncService.sendOtpAsync(email);
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);

		return response;
	}

//	@Override
//	public void signup(SignUpFormDTO signUpRequest) {
//		String methodName = "signup()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		if (ObjectUtils.isEmpty(signUpRequest) 
//				|| StringUtils.isBlank(signUpRequest.getUserName())) {
//			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_REGISTER_INFORMATION);
//		}
//
//		UserVO userVO = getUserVOFromSignUpFormDTO(signUpRequest);
//		userRepo.save(userVO);
//		userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//	}

//	private UserVO getUserVOFromSignUpFormDTO(SignUpFormDTO signUpFormDTO) {
//
//		UserVO userVO = new UserVO();
//
//		userVO.setUserName(signUpFormDTO.getUserName());
//		if (ObjectUtils.isEmpty(userVO.getId())) {
//			try {
//				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(signUpFormDTO.getPassword())));
//			} catch (Exception e) {
//				LOGGER.error(e.getMessage());
//				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
//			}
//		}
//
//		userVO.setEmail(signUpFormDTO.getEmail());
//		userVO.setMobileNo(signUpFormDTO.getMobileNo());
////		userVO.setActive(signUpFormDTO.isActive());
//		userVO.setOrgId(signUpFormDTO.getOrgId());
//		userVO.setBranch(signUpFormDTO.getBranch());
//		userVO.setBranchCode(signUpFormDTO.getBranchCode());
//		userVO.setStatus(signUpFormDTO.getStatus());
//		userVO.setSelectType(signUpFormDTO.getSelectType());
//		userVO.setTransportName(signUpFormDTO.getTransportName());
//		userVO.setCreatedby(signUpFormDTO.getCreatedby());
//
//		// Active status logic
//		if (signUpFormDTO.getStatus() == UserStatus.APPROVED) {
//		    userVO.setActive(true);
//		} else {
//		    userVO.setActive(false);
//		}
//
//
//		return userVO;
//	}

	@Override
	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(loginRequest) || StringUtils.isBlank(loginRequest.getUserName())
				|| StringUtils.isBlank(loginRequest.getPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGIN_INFORMATION);
		}

		UserVO userVO = userRepo.findByUserName(loginRequest.getUserName());

		if (userVO == null) {
			throw new ApplicationException("Invalid login credentials. Please check your email and password.");
		}

		// Email OTP check
		Optional<EmailOtpEntity> emailOtpEntity = emailOtpRepo.findByEmailAndVerified(userVO.getEmail(), true);

		if (!emailOtpEntity.isPresent()) {
			throw new ApplicationException("Your account is not verified.");
		}

		// Check Email must match
		if (!loginRequest.getUserName().equalsIgnoreCase(userVO.getEmail())) {
			throw new ApplicationException("Invalid login credentials. Please check your email");
		}

		// Check Password must match
		boolean passwordMatch = compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(),
				userVO.getPassword());

		if (!passwordMatch) {
			throw new ApplicationException("Invalid login credentials. Please check your Password.");
		}

		if (ObjectUtils.isNotEmpty(userVO)) {

			if ("In-Active".equalsIgnoreCase(userVO.getActive()) && userVO.getStatus() == UserStatus.APPROVED) {
				throw new ApplicationException("Your account is Currently In-Active");
			}

//			if ("In-Active".equalsIgnoreCase(userVO.getActive()) 
//			        && userVO.getStatus() == UserStatus.APPROVED) {
//			    throw new ApplicationException("Your account is awaiting admin approval.");
//			}

			if ("In-Active".equalsIgnoreCase(userVO.getActive())) {
				if (userVO.getStatus() == UserStatus.PENDING) {
					throw new ApplicationException("Your account is awaiting admin approval.");
				} else if (userVO.getStatus() == UserStatus.REJECTED) {
					throw new ApplicationException("Your account has been rejected by admin.");
				}
			}

			if (compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(), userVO.getPassword())) {
				updateUserLoginInformation(userVO, request);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(
					UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND_AND_ASKING_SIGNUP);
		}
		UserResponseDTO userResponseDTO = mapUserVOToDTO(userVO);

		List<Map<String, Object>> roleVOList = new ArrayList<>();

		// Iterate through UserLoginRolesVO to fetch roles and responsibilities
//		for (UserLoginRolesVO loginRolesVO : userVO.getRoleAccessVO()) {
//			Map<String, Object> roleMap = new HashMap<>();
//			roleMap.put("role", loginRolesVO.getRole());
//			roleMap.put("roleId", loginRolesVO.getRoleId());
//			roleMap.put("startDate", loginRolesVO.getStartDate());
//			roleMap.put("endDate", loginRolesVO.getEndDate());
//			// Initialize the list for responsibilities under this role
//			List<Map<String, Object>> responsibilityVOList = new ArrayList<>();
//
//			// Fetch the RolesVO using loginRolesVO.getRoleId()
//			RolesVO rolesVO = rolesRepo.findById(loginRolesVO.getRoleId()).orElse(null);
//			if (rolesVO != null && rolesVO.getRolesReposibilitiesVO() != null) {
//				for (RolesResponsibilityVO rolesResponsibilityVO : rolesVO.getRolesReposibilitiesVO()) {
//					Map<String, Object> responsibilityMap = new HashMap<>();
//					responsibilityMap.put("responsibilityName", rolesResponsibilityVO.getResponsibility());
//
//					ResponsibilityVO responsibilityVO = responsibilityRepo
//							.findById(rolesResponsibilityVO.getResponsibilityId()).orElse(null);
//					if ((loginRolesVO.getStartDate() == null || !loginRolesVO.getStartDate().isAfter(LocalDate.now())) &&
//						    (loginRolesVO.getEndDate() == null || !loginRolesVO.getEndDate().isBefore(LocalDate.now()))) {
//						if (responsibilityVO != null && responsibilityVO.getScreensVO() != null) {
//							List<String> screensList = new ArrayList<>();
//							for (ScreensVO screenVO : responsibilityVO.getScreensVO()) {
//								screensList.add(screenVO.getScreenName());
//							}
//							responsibilityMap.put("screensVO", screensList);
//						}
//						responsibilityVOList.add(responsibilityMap);
//					} else {
//						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//						String startDateFormatted = loginRolesVO.getStartDate().format(formatter);
//						String endDateFormatted = loginRolesVO.getEndDate().format(formatter);
//
//						responsibilityMap.put("screensVO", null);
//						responsibilityMap.put("expiredMessage",
//							"Your Role " + loginRolesVO.getRole() + " is valid only from " + startDateFormatted + " to " + endDateFormatted + ".");
//
//						responsibilityVOList.add(responsibilityMap);
//					}
//
//				}
//			}
//
//			// Add the responsibilities list to the role map
//			roleMap.put("responsibilityVO", responsibilityVOList);
//
//			// Add the role map to the roleVOList
//			roleVOList.add(roleMap);
//		}

//		userResponseDTO.setRoleVO(roleVOList);
//        userResponseDTO.setScreensVO(roleVOList);
		TokenVO tokenVO = tokenProvider.createToken(userVO.getId(), loginRequest.getUserName());
		userResponseDTO.setToken(tokenVO.getToken());
		userResponseDTO.setTokenId(tokenVO.getId());
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userResponseDTO;
	}

	/**
	 * @param encryptedPassword -> Data from user;
	 * @param encodedPassword   ->Data from DB;
	 * @return
	 */
	private boolean compareEncodedPasswordWithEncryptedPassword(String encryptedPassword, String encodedPassword) {
		boolean userStatus = false;
		try {
			userStatus = encoder.matches(CryptoUtils.getDecrypt(encryptedPassword), encodedPassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		return userStatus;
	}

//	@Override
//	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException {
//	    String methodName = "login()";
//	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//
//	    if (ObjectUtils.isEmpty(loginRequest) || StringUtils.isBlank(loginRequest.getUserName())
//	            || StringUtils.isBlank(loginRequest.getPassword())) {
//	        throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGIN_INFORMATION);
//	    }
//
//	    UserVO userVO = userRepo.findByUserNameOrEmailOrMobileNo(
//	        loginRequest.getUserName(), loginRequest.getUserName(), loginRequest.getUserName());
//
//	    if (ObjectUtils.isNotEmpty(userVO)) {
//	        if ("In-Active".equalsIgnoreCase(userVO.getActive())) {
//	            throw new ApplicationException("Your account is In-Active, Please Contact Administrator");
//	        }
//	        if (compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(), userVO.getPassword())) {
//	            updateUserLoginInformation(userVO, request);
//	        } else {
//	            throw new ApplicationContextException(UserConstants.ERRROR_MSG_PASSWORD_MISMATCH);
//	        }
//	    } else {
//	        throw new ApplicationContextException(
//	            UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND_AND_ASKING_SIGNUP);
//	    }
//
//	    UserResponseDTO userResponseDTO = mapUserVOToDTO(userVO);
//	    List<Map<String, Object>> roleVOList = new ArrayList<>();
//
//	    for (UserLoginRolesVO loginRolesVO : userVO.getRoleAccessVO()) {
//	        Map<String, Object> roleMap = new HashMap<>();
//	        roleMap.put("role", loginRolesVO.getRole());
//	        roleMap.put("roleId", loginRolesVO.getRoleId());
//	        roleMap.put("startDate", loginRolesVO.getStartDate());
//	        roleMap.put("endDate", loginRolesVO.getEndDate());
//
//	        List<Map<String, Object>> responsibilityVOList = new ArrayList<>();
//
//	        RolesVO rolesVO = rolesRepo.findById(loginRolesVO.getRoleId()).orElse(null);
//	        if (rolesVO != null && rolesVO.getRolesReposibilitiesVO() != null) {
//	            for (RolesResponsibilityVO rolesResponsibilityVO : rolesVO.getRolesReposibilitiesVO()) {
//	                Map<String, Object> responsibilityMap = new HashMap<>();
//	                ResponsibilityVO responsibilityVO = responsibilityRepo
//	                        .findById(rolesResponsibilityVO.getResponsibilityId()).orElse(null);
//
//	                responsibilityMap.put("responsibilityName", rolesResponsibilityVO.getResponsibility());
//
//	                // Check if the role is currently valid
//	                if ((loginRolesVO.getStartDate() == null || !loginRolesVO.getStartDate().isAfter(LocalDate.now())) &&
//	                        (loginRolesVO.getEndDate() == null || !loginRolesVO.getEndDate().isBefore(LocalDate.now()))) {
//
//	                    if (responsibilityVO != null && responsibilityVO.getScreensVO() != null) {
//	                        List<String> screensList = new ArrayList<>();
//	                        for (ScreensVO screenVO : responsibilityVO.getScreensVO()) {
//	                            screensList.add(screenVO.getScreenName());
//	                        }
//	                        responsibilityMap.put("screensVO", screensList);
//	                    } else {
//	                        responsibilityMap.put("screensVO", new ArrayList<>());
//	                    }
//
//	                } else {
//	                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//	                    String startDateFormatted = loginRolesVO.getStartDate().format(formatter);
//	                    String endDateFormatted = loginRolesVO.getEndDate().format(formatter);
//
//	                    responsibilityMap.put("screensVO", null);
//	                    responsibilityMap.put("expiredMessage", 
//	                        "Your Role " + loginRolesVO.getRole() + " is valid only from " + startDateFormatted + " to " + endDateFormatted + ".");
//	                }
//
//	                responsibilityVOList.add(responsibilityMap);
//	            }
//	        }
//
//	        roleMap.put("responsibilityVO", responsibilityVOList);
//	        roleVOList.add(roleMap);
//	    }
//
//	    userResponseDTO.setRoleVO(roleVOList);
//
//	    TokenVO tokenVO = tokenProvider.createToken(userVO.getId(), loginRequest.getUserName());
//	    userResponseDTO.setToken(tokenVO.getToken());
//	    userResponseDTO.setTokenId(tokenVO.getId());
//
//	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//	    return userResponseDTO;
//	}
//
//	/**
//	 * @param encryptedPassword -> Data from user;
//	 * @param encodedPassword   -> Data from DB;
//	 * @return true if matched
//	 */
//	private boolean compareEncodedPasswordWithEncryptedPassword(String encryptedPassword, String encodedPassword) {
//	    boolean userStatus = false;
//	    try {
//	        userStatus = encoder.matches(CryptoUtils.getDecrypt(encryptedPassword), encodedPassword);
//	    } catch (Exception e) {
//	        LOGGER.error(e.getMessage());
//	        throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
//	    }
//	    return userStatus;
//	}

	@Override
	public void logout(String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isBlank(userName)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGOUT_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(userName);
		if (ObjectUtils.isNotEmpty(userVO)) {
			updateUserLogOutInformation(userVO);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void changePassword(ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(changePasswordRequest) || StringUtils.isBlank(changePasswordRequest.getUserName())
				|| StringUtils.isBlank(changePasswordRequest.getOldPassword())
				|| StringUtils.isBlank(changePasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_CHANGE_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(changePasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			if (compareEncodedPasswordWithEncryptedPassword(changePasswordRequest.getOldPassword(),
					userVO.getPassword())) {
				try {
					userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(changePasswordRequest.getNewPassword())));
				} catch (Exception e) {
					throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
				}
				userRepo.save(userVO);
				userService.createUserAction(userVO.getUserName(), userVO.getId(),
						UserConstants.USER_ACTION_TYPE_CHANGE_PASSWORD);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_OLD_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(resetPasswordRequest) || StringUtils.isBlank(resetPasswordRequest.getUserName())
				|| StringUtils.isBlank(resetPasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_RESET_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(resetPasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(resetPasswordRequest.getNewPassword())));
			} catch (Exception e) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(),
					UserConstants.USER_ACTION_TYPE_RESET_PASSWORD);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException {
		UserVO userVO = userRepo.findByUserName(userName);
		RefreshTokenDTO refreshTokenDTO = null;
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		TokenVO tokenVO = tokenRepo.findById(tokenId).orElseThrow(() -> new ApplicationException("Invalid Token Id."));
		if (tokenVO.getExpDate().compareTo(new Date()) > 0) {
			tokenVO = tokenProvider.createRefreshToken(tokenVO, userVO);
			refreshTokenDTO = RefreshTokenDTO.builder().token(tokenVO.getToken()).tokenId(tokenVO.getId()).build();
		} else {
			tokenRepo.delete(tokenVO);
			throw new ApplicationException(AuthConstant.REFRESH_TOKEN_EXPIRED_MESSAGE);
		}
		return refreshTokenDTO;
	}

	/**
	 * @param userVO
	 * @param request
	 */
	private void updateUserLoginInformation(UserVO userVO, HttpServletRequest request) {
		try {
			userRepo.save(userVO);
			userService.createUserLoginAction(userVO.getUserName(), userVO.getId(),
					UserConstants.USER_ACTION_TYPE_LOGIN, request);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	private void updateUserLogOutInformation(UserVO userVO) {
		try {
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getId(), UserConstants.USER_ACTION_TYPE_LOGOUT);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	public static UserResponseDTO mapUserVOToDTO(UserVO userVO) {
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUsersId(userVO.getId());
		userDTO.setBranch(userVO.getBranch());
		userDTO.setBranchCode(userVO.getBranchCode());
		userDTO.setOrgId(userVO.getOrgId());
		userDTO.setActive(userVO.isActive());
		userDTO.setEmail(userVO.getEmail());
		userDTO.setUserName(userVO.getUserName());
		userDTO.setMobileNo(userVO.getMobileNo());
		userDTO.setEmail(userVO.getEmail());
		userDTO.setType(userVO.getType());
		userDTO.setOrganizationName(userVO.getOrganizationName());
		userDTO.setStatus(userVO.getStatus());
		userDTO.setCreatedby(userVO.getCreatedby());

		// userDTO.setIsActive(userVO.getIsActive());
		userDTO.setCommonDate(userVO.getCommonDate());

//		List<UserLoginRolesVO> loginRolesVOs = userVO.getRoleAccessVO();
		return userDTO;
	}

	@Override
	public Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO)
			throws ApplicationException {

		ResponsibilityVO responsibilityVO = new ResponsibilityVO();
		String message;
		// Check if the responsibilityDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(responsibilityDTO.getId())) {

			// Validate if responsibility already exists by responsibility name
			if (responsibilityRepo.existsByResponsibilityAndOrgId(responsibilityDTO.getResponsibility(),
					responsibilityDTO.getOrgId())) {
				throw new ApplicationException("Responsibility Name already exists");
			}

			responsibilityVO.setCreatedBy(responsibilityDTO.getCreatedBy());
			responsibilityVO.setUpdatedBy(responsibilityDTO.getCreatedBy());
			// Set the values from responsibilityDTO to responsibilityVO
			mapResponsibilityDtoToResponsibilityVo(responsibilityDTO, responsibilityVO);
			message = "Responsibilites Created successfully";

		} else {

			// Retrieve the existing ResponsibilityVO from the repository
			responsibilityVO = responsibilityRepo.findById(responsibilityDTO.getId())
					.orElseThrow(() -> new ApplicationException("Responsibility not found"));

			// Validate and update unique fields if changed
			if (!responsibilityVO.getResponsibility().equalsIgnoreCase(responsibilityDTO.getResponsibility())) {
				if (responsibilityRepo.existsByResponsibilityAndOrgId(responsibilityDTO.getResponsibility(),
						responsibilityDTO.getOrgId())) {
					throw new ApplicationException("Responsibility Name already exists");
				}
				responsibilityVO.setResponsibility(responsibilityDTO.getResponsibility());
			}

			List<ScreensVO> screensVOs = screenRepo.findByResponsibilityVO(responsibilityVO);
			screenRepo.deleteAll(screensVOs);

			responsibilityVO.setUpdatedBy(responsibilityDTO.getCreatedBy());
			// Update the remaining fields from responsibilityDTO to responsibilityVO
			mapResponsibilityDtoToResponsibilityVo(responsibilityDTO, responsibilityVO);
			message = "Responsibilites Updated successfully";
		}

		responsibilityRepo.save(responsibilityVO);
		Map<String, Object> response = new HashMap<>();
		response.put("responsibilityVO", responsibilityVO);
		response.put("message", message);
		return response;
	}

	// Helper method to map ResponsibilityDTO to ResponsibilityVO
	private void mapResponsibilityDtoToResponsibilityVo(ResponsibilityDTO responsibilityDTO,
			ResponsibilityVO responsibilityVO) {
		responsibilityVO.setResponsibility(responsibilityDTO.getResponsibility().toUpperCase());
		responsibilityVO.setOrgId(responsibilityDTO.getOrgId());
		responsibilityVO.setActive(responsibilityDTO.isActive());
		if (responsibilityDTO.getScreensDTO() != null) {
			List<ScreensVO> screensVOList = new ArrayList<>();
			for (ScreensDTO screensDTO : responsibilityDTO.getScreensDTO()) {
				ScreensVO screensVO = new ScreensVO();
				screensVO.setScreenName(screensDTO.getScreenName().toUpperCase());
				screensVO.setOrgId(responsibilityDTO.getOrgId());
				screensVO.setResponsibilityVO(responsibilityVO);
				screensVOList.add(screensVO);
			}
			responsibilityVO.setScreensVO(screensVOList);
		}
	}

	@Override
	public List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId) {
		Set<Object[]> activeResponsibility = responsibilityRepo.findActiveByOrgId(orgId);
		return getActiveResponsibile(activeResponsibility);
	}

	private List<Map<String, Object>> getActiveResponsibile(Set<Object[]> activeResponsibility) {
		List<Map<String, Object>> getResponse = new ArrayList<>();
		for (Object[] response : activeResponsibility) {
			Map<String, Object> res = new HashMap<>();
			res.put("responsibilityId", response[0] != null ? response[0].toString() : "");
			res.put("responsibility", response[1] != null ? response[1].toString() : "");
			getResponse.add(res);
		}
		return getResponse;
	}

	@Override
	public Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException {
		RolesVO rolesVO = new RolesVO();
		String message;

		// Check if the rolesDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(rolesDTO.getId())) {

			// Validate if role already exists
			if (rolesRepo.existsByRoleAndOrgId(rolesDTO.getRole(), rolesDTO.getOrgId())) {
				throw new ApplicationException("Role already exists");
			}

			rolesVO.setCreatedBy(rolesDTO.getCreatedBy());
			rolesVO.setUpdatedBy(rolesDTO.getCreatedBy());
			// Set the values from rolesDTO to rolesVO
			mapRolesDtoToRolesVo(rolesDTO, rolesVO);
			message = "Roles Created successfully";

		} else {

			// Retrieve the existing RolesVO from the repository
			rolesVO = rolesRepo.findById(rolesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Role not found"));

			// Validate and update unique fields if changed
			if (!rolesVO.getRole().equalsIgnoreCase(rolesDTO.getRole())) {
				if (rolesRepo.existsByRoleAndOrgId(rolesDTO.getRole(), rolesDTO.getOrgId())) {
					throw new ApplicationException("Role already exists");
				}
				rolesVO.setRole(rolesDTO.getRole().toUpperCase());
			}

			List<RolesResponsibilityVO> rolesResponsibilityVOs = rolesResponsibilityRepo.findByRolesVO(rolesVO);
			rolesResponsibilityRepo.deleteAll(rolesResponsibilityVOs);

			rolesVO.setUpdatedBy(rolesDTO.getCreatedBy());
			// Update the remaining fields from rolesDTO to rolesVO
			mapRolesDtoToRolesVo(rolesDTO, rolesVO);
			message = "Roles Updated successfully";
		}

		rolesRepo.save(rolesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("rolesVO", rolesVO);
		response.put("message", message);
		return response;
	}

	// Helper method to map RolesDTO to RolesVO
	private void mapRolesDtoToRolesVo(RolesDTO rolesDTO, RolesVO rolesVO) {
		rolesVO.setRole(rolesDTO.getRole().toUpperCase());
		rolesVO.setOrgId(rolesDTO.getOrgId());
		rolesVO.setActive(rolesDTO.isActive());
		if (rolesDTO.getRolesResponsibilityDTO() != null) {
			List<RolesResponsibilityVO> rolesResponsibilityVOList = new ArrayList<>();
			for (RolesResponsibilityDTO rolesResponsibilityDTO : rolesDTO.getRolesResponsibilityDTO()) {
				RolesResponsibilityVO rolesResponsibilityVO = new RolesResponsibilityVO();
				rolesResponsibilityVO.setResponsibility(rolesResponsibilityDTO.getResponsibility().toUpperCase());
				rolesResponsibilityVO.setResponsibilityId(rolesResponsibilityDTO.getResponsibilityId());
				rolesResponsibilityVO.setOrgId(rolesDTO.getOrgId());
				rolesResponsibilityVO.setRolesVO(rolesVO);
				rolesResponsibilityVOList.add(rolesResponsibilityVO);
			}
			rolesVO.setRolesReposibilitiesVO(rolesResponsibilityVOList);
		}
	}

	@Override
	public List<RolesVO> getAllRoles(Long orgId) {

		return rolesRepo.findRolesByOrgId(orgId);
	}

	@Override
	public List<RolesVO> getAllActiveRoles(Long orgId) {

		return rolesRepo.findRolesByOrgId(orgId);
	}

	@Override
	public RolesVO getRolesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Roles Id");
		}

		RolesVO rolesVO = rolesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Role not found for Id: " + id));

		return rolesVO;
	}

	@Override
	public ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Responsibility Id");
		}

		ResponsibilityVO responsibilityVO = responsibilityRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Responsibility not found for Id: " + id));

		return responsibilityVO;
	}

	@Override
	public List<ResponsibilityVO> getAllResponsibility(Long orgId) {

		return responsibilityRepo.findAllResponsibilityByOrgId(orgId);
	}

	@Override
	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId) {
		// TODO Auto-generated method stub
		return responsibilityRepo.findAllActiveResponsibilityByOrgId(orgId);
	}

	@Override
	public List<UserVO> getAllUsersByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return userRepo.findAllByOrgId(orgId);
	}

	@Override
	public UserVO getUserById(Long usersId) {
		String methodName = "getUserById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(usersId)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_ID);
		}
		UserVO userVO = userRepo.getUserById(usersId);
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userVO;
	}

	@Override
	public UserVO getUserByUserName(String userName) {
		String methodName = "getUserByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isNotEmpty(userName)) {
			UserVO userVO = userRepo.findByUserName(userName);
			if (ObjectUtils.isEmpty(userVO)) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return userVO;
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_NAME);
		}
	}

	@Override
	public Map<String, Object> getAllUsersList(String branchCode, String search, int page, int count) {

		if (search != null) {
			search = search.trim();
			if (search.isEmpty()) {
				search = null;
			}
		}

		Pageable pageable = PageRequest.of(page - 1, count, Sort.by("userName").ascending());
		Page<Map<String, Object>> userPage = userRepo.getAllUsersList(branchCode, search, pageable);

		Page<UserListDTO> dtoPage = userPage.map(row -> {

			UserListDTO dto = new UserListDTO();

			dto.setUserId(row.get("userid") != null ? ((Number) row.get("userid")).longValue() : null);
			dto.setUserName((String) row.get("username"));
			dto.setEmail((String) row.get("email"));
			dto.setMobileNo((String) row.get("mobileno"));
			dto.setPassword((String) row.get("password"));
			dto.setType((String) row.get("type"));
			dto.setOrgId(row.get("orgid") != null ? ((Number) row.get("orgid")).longValue() : null);
			dto.setOrganizationName((String) row.get("organizationname"));
			dto.setBranch((String) row.get("branch"));
			dto.setBranchCode((String) row.get("branchcode"));
			dto.setActive(row.get("active") != null && ((Boolean) row.get("active")));

			if (row.get("status") != null) {
				int code = ((Number) row.get("status")).intValue();
				dto.setStatus(UserStatus.values()[code]);
			}

			dto.setCreatedBy((String) row.get("createdby"));
			dto.setModifiedBy((String) row.get("modifiedby"));
			dto.setCreatedOn((String) row.get("createdon"));
			dto.setModifiedOn((String) row.get("modifiedon"));

			return dto;
		});

		// return paginated response
		return paginationService.buildResponse(dtoPage);
//
//	    Map<String, Object> result = new HashMap<>();
//	    result.put("totalCount", userPage.getTotalElements());
//	    result.put("totalPages", userPage.getTotalPages());
//	    result.put("currentPage", page);
//	    result.put("users", dtoList);

//	    return paginationService.buildResponse(userPage);
	}

	@Override
	public void checkUserExists(String email) throws ApplicationException {
		Optional<UserVO> existing = userRepo.findByEmail(email);
		if (!existing.isPresent()) {
			throw new ApplicationException("User email does not exist.");
		}
	}

//	@Override
//	@Transactional
//	public ResponseDTO resetPassword(ResetPasswordDTO req) throws ApplicationException {
//
//	    ResponseDTO res = new ResponseDTO();
//
//	    Optional<UserVO> optUser = userRepo.findByEmail(req.getEmail());
//
//	    if (!optUser.isPresent()) {
//	        res.setStatusFlag(ResponseDTO.ERROR);
//	        res.setStatus(false);
//	        res.addObject1("message","Email not found.");
//	        return res;
//	    }
//
//	    UserVO user = optUser.get();
//
//	    // encrypt
//	    try {
//	        String decrypted = CryptoUtils.getDecrypt(req.getNewPassword());
//	        user.setPassword(encoder.encode(decrypted));
//	    } catch (Exception e) {
//	        throw new ApplicationException("Unable to encode new password");
//	    }
//
//	    userRepo.save(user);
//
//	    res.setStatusFlag(ResponseDTO.OK);
//	    res.setStatus(true);
//	    res.addObject1("message","Password reset successfully.");
//	    res.addObject1("email",req.getEmail());
//
//	    return res;
//	}
//
//

	@Transactional
	public Map<String, Object> resetPassword(ResetPasswordDTO req) throws ApplicationException {

		Map<String, Object> result = new HashMap<>();

		Optional<UserVO> optUser = userRepo.findByEmail(req.getEmail());

		if (!optUser.isPresent()) {
			throw new ApplicationException("Email not found.");
		}

		UserVO user = optUser.get();

		// decrypt & encrypt new password
		try {
			String decrypted = CryptoUtils.getDecrypt(req.getNewPassword());
			user.setPassword(encoder.encode(decrypted));
		} catch (Exception e) {
			throw new ApplicationException("Unable to encode new password.");
		}

		userRepo.save(user);

		// Build response map
		result.put("message", "Password reset successfully.");
		result.put("email", req.getEmail());

		return result;
	}

	@Override
	public Map<String, Object> createApprovalUserList(Long id, String userName, String action, String actionBy)
			throws Exception {

		Map<String, Object> response = new HashMap<>();
		String message = "";

		// 1️⃣ Fetch user
		UserVO userVO = userRepo.findByIdAndUserName(id, userName);

		if (userVO == null) {
			throw new ApplicationException("User record not found for the given details.");
		}

		// 2️⃣ Check if already approved or rejected
		UserStatus currentStatus = userVO.getStatus();
		if (currentStatus == UserStatus.APPROVED || currentStatus == UserStatus.REJECTED) {
			throw new ApplicationException("This user is already " + currentStatus + ".");
		}

		// 3️⃣ Process action
		UserStatus statusToSet;

		if ("APPROVED".equalsIgnoreCase(action)) {
			statusToSet = UserStatus.APPROVED;
			userVO.setActive(true);
			message = "Approved Successfully";

		} else if ("REJECTED".equalsIgnoreCase(action)) {
			statusToSet = UserStatus.REJECTED;
			userVO.setActive(false);
			message = "Rejected Successfully";

		} else {
			throw new ApplicationException("Invalid action: must be APPROVED or REJECTED.");
		}

		// 4️⃣ Update details
		userVO.setStatus(statusToSet);
		userVO.setApproveBy(actionBy);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
		userVO.setApproveOn(LocalDateTime.now().format(formatter).toUpperCase());

		userRepo.save(userVO);

		// 5️⃣ Response
		response.put("userVO", userVO);
		response.put("message", message);

		return response;
	}

}
