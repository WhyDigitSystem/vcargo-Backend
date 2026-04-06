package com.efit.savaari.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.efit.savaari.dto.EwayBillNonIRNDTO;
import com.efit.savaari.dto.EwayBillPayLoadDTO;
import com.efit.savaari.dto.EwayBillResponseDTO;
import com.efit.savaari.dto.GenerateTokenDTO;
import com.efit.savaari.dto.PayloadDTO;
import com.efit.savaari.entity.EwayBillDetailsVO;
import com.efit.savaari.entity.EwayBillDirectVO;
import com.efit.savaari.entity.EwayBillResponseVO;
import com.efit.savaari.entity.EwayBillVO;
import com.efit.savaari.entity.EwayResponseVO;
import com.efit.savaari.entity.HeaderDetailsVO;
import com.efit.savaari.repo.EwayBillDirectRepo;
import com.efit.savaari.repo.EwayBillRepo;
import com.efit.savaari.repo.EwayBillResponseRepo;
import com.efit.savaari.repo.EwayHeadersRepo;
import com.efit.savaari.repo.EwayResponseRepo;
import com.efit.savaari.repo.HeaderDetailsRepo;
import com.efit.savaari.responseDTO.EInvoiceGetToketDTO;
import com.efit.savaari.responseDTO.ItemListDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EInvoiceServiceImpl implements EInvoiceService {

	@Autowired
	HeaderDetailsRepo headerDetailsRepo;

	@Autowired
	EwayBillResponseRepo ewayBillResponseRepo;

	@Autowired
	EwayResponseRepo ewayResponseRepo;

	@Autowired
	EwayBillDirectRepo ewayBillDirectRepo;

	@Autowired
	EwayHeadersRepo ewayHeadersRepo;

	@Autowired
	EwayBillRepo ewayBillRepo;

	static byte[] appKey1 = null;

	@Value("${public.key.path}")
	private String publicKeyPath;

	private PublicKey publicKey;

	@Override
	public Map<String, Object> generateToken(List<EInvoiceGetToketDTO> eInvoiceGetToketDTO1) throws Exception {

		Map<String, Object> token = new HashMap<>();

		for (EInvoiceGetToketDTO eInvoiceGetToketDTO : eInvoiceGetToketDTO1) {

			// Convert the byte array to a Base64 string
			String appKey2 = "LAz2aeV0irbbTrjtl3uKAAXeVJig91kjbracM3DWfO8=";
			System.out.println("AppKey: " + appKey2);

			// Convert hex string to byte array
//        byte[] apk = hexStringToByteArray(hexString);
			publicKey = loadPublicKey(publicKeyPath);
			HeaderDetailsVO headerDetailsVO = headerDetailsRepo.findByUserName(eInvoiceGetToketDTO.getUserName());

			String appKey = appKey2;
			String gstin = headerDetailsVO.getGstin();
			String clientId = headerDetailsVO.getClientId();
			String clientSecret = headerDetailsVO.getClientSecret();
			GenerateTokenDTO generateTokenDTO = new GenerateTokenDTO();
			generateTokenDTO.setUserName(eInvoiceGetToketDTO.getUserName());
			generateTokenDTO.setPassword(eInvoiceGetToketDTO.getPassword());
			generateTokenDTO.setAppKey(appKey);
			generateTokenDTO.setForceRefreshAccessToken(true);
			ObjectMapper objectMapper = new ObjectMapper();
			String payload = objectMapper.writeValueAsString(generateTokenDTO);

			// Encode the payload into Base64
			String base64Payload = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));

			// Encrypt the Base64 encoded payload using RSA (for sending the payload)
			String encryptedPayload = encryptWithRSA(base64Payload.getBytes(StandardCharsets.UTF_8), publicKey);
			System.out.println("Encrypted Payload " + encryptedPayload);
			PayloadDTO payloadDTO = new PayloadDTO();
			payloadDTO.setData(encryptedPayload);

			// SandBox API
			String url = "https://einv1api.gstsandbox.nic.in/eivital/v1.04/auth";
			// Live API
//			 String url = "https://api.einvoice1.gst.gov.in/eivital/v1.04/auth";
			HttpHeaders headers = new HttpHeaders();
			headers.set("client_id", clientId);
			headers.set("client_secret", clientSecret);
			headers.set("gstin", gstin);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<PayloadDTO> request = new HttpEntity<>(payloadDTO, headers);
			RestTemplate restTemplate = new RestTemplate();
			try {
				ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

				System.out.println("Raw Response: " + response.getBody());

				JsonNode jsonNode = objectMapper.readTree(response.getBody());
				String encryptedSek = jsonNode.get("Data").get("Sek").asText();

				ObjectMapper objectMapper1 = new ObjectMapper();
				Map<String, Object> mp = objectMapper1.readValue(response.getBody(),
						new TypeReference<Map<String, Object>>() {
						});
				if (mp.get("Data") != null) {

					Map<String, Object> dataMap = (Map<String, Object>) mp.get("Data");

					String ClientId = (String) dataMap.get("ClientId");
					String UserName = (String) dataMap.get("UserName");
					String AuthToken = (String) dataMap.get("AuthToken");
					String Sek1 = (String) dataMap.get("Sek");
					System.out.println("Encrypted Sek: " + Sek1);

					String TokenExpiry = (String) dataMap.get("TokenExpiry");

					byte[] decodedBytes = Base64.getDecoder().decode(appKey);
					SecretKeySpec secretKey = new SecretKeySpec(decodedBytes, "AES");
//	            System.out.println("AES Key: " + bytesToHex(secretKey.getEncoded()));
					byte[] decryptedSekBytes = decryptWithAppKey(encryptedSek, appKey);

					// Convert the decrypted SEK byte array to a human-readable hex format
					String base64DecryptedSek = bytesToBase64(decryptedSekBytes);
					System.out.println("Decrypted SEK (Base64): " + base64DecryptedSek);
					headerDetailsVO.setSek(base64DecryptedSek);
					headerDetailsVO.setAuthtoken(AuthToken);
					headerDetailsVO.setTokenExpiry(TokenExpiry);
					headerDetailsRepo.save(headerDetailsVO);
					token.put("ClientId", ClientId);
					token.put("UserName", UserName);
					token.put("AuthToken", AuthToken);
					token.put("Sek", base64DecryptedSek);
					token.put("TokenExpiry", TokenExpiry);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// Return the encrypted data and encrypted AES key
		Map<String, Object> response = new HashMap<>();
		response.put("TokenDetails", token);
		System.out.println("Decrypted Response: " + response);
		return response;
	}

	public static byte[] decryptWithAppKey(String encryptedSek, String appKey) throws Exception {
		// Decode the AppKey (Base64) and the encrypted SEK (Base64)
		byte[] appKeyBytes = Base64.getDecoder().decode(appKey);
		byte[] encryptedSekBytes = Base64.getDecoder().decode(encryptedSek);

		// Initialize the AES cipher for decryption with the AppKey
		SecretKeySpec secretKey = new SecretKeySpec(appKeyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // AES ECB mode with padding
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		// Decrypt the SEK and return the raw byte array
		return cipher.doFinal(encryptedSekBytes);
	}

	// Utility to convert byte array to Base64
	public static String bytesToBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	// RSA Encryption for the AES key and data
	private static String encryptWithRSA(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedBytes = cipher.doFinal(data);
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	// Load RSA Public Key from File
	private static PublicKey loadPublicKey(String filePath) throws Exception {
		String key = new String(Files.readAllBytes(Paths.get(filePath)));
		key = key.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s",
				""); // Remove new lines and spaces
		byte[] decodedKey = Base64.getDecoder().decode(key);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(spec);
	}

	@Scheduled(fixedRate = 2000)
	public void processTokenAutomation() throws Exception {
		System.out.println("Running Token Automation service every 1 Sec...");
		// Replace with actual branchCode

		List<Object[]> getTokenDetails = headerDetailsRepo.getAutomationTokenDetails();
		if (getTokenDetails != null) {

			int length = getTokenDetails.size();
			System.out.println("Length of the list: " + length);
			// Extract docIds from the list
			List<EInvoiceGetToketDTO> docIds = new ArrayList<>();
			for (Object[] record : getTokenDetails) {
				if (record != null && record.length > 0) {
					EInvoiceGetToketDTO dto = new EInvoiceGetToketDTO();
					String userName = record[0].toString(); // Assuming docId is the first column
					HeaderDetailsVO details = headerDetailsRepo.findByUserName(userName);
					dto.setUserName(details.getUserName());
					dto.setPassword(details.getPwd());
					docIds.add(dto);
				}
			}
			// Call the service method with the collected docIds
			if (!docIds.isEmpty()) {

				generateToken(docIds);

			} else {
				System.out.println("No docIds found to process.");
			}
		} else {
			System.out.println("List is null.");
		}

	}
//
//	@Scheduled(fixedRate = 2000)
//	public void processEWayBill() throws JsonProcessingException {
//		System.out.println("Running E-Way service every 1 Sec...");
//		// Replace with actual branchCode
//
//		List<Object[]> getPendingEwayDetails = ewayBillRepo.getPendingEwayDetails();
//		if (getPendingEwayDetails != null) {
//
//			int length = getPendingEwayDetails.size();
//			System.out.println("Length of the list: " + length);
//			// Extract docIds from the list
//			List<String> docIds = new ArrayList<>();
//			for (Object[] record : getPendingEwayDetails) {
//				if (record != null && record.length > 0) {
//					String docId = record[0].toString(); // Assuming docId is the first column
//					docIds.add(docId);
//				}
//			}
//			// Call the service method with the collected docIds
//			if (!docIds.isEmpty()) {
//				System.out.println(" Process Success.");
//				createEWayBill(docIds);
//
//			} else {
//				System.out.println("No docIds found to process.");
//			}
//		} else {
//			System.out.println("List is null.");
//		}
//
//	}

	@Override
	public EwayBillNonIRNDTO generateEwayBillByNonIRN(String docIds) {
		EwayBillNonIRNDTO ewayBillNonIRNDTO = new EwayBillNonIRNDTO();

		String docId = docIds;
		EwayBillVO ewayBillVO = ewayBillRepo.findByDocNo(docId);

		ewayBillNonIRNDTO.setSupplyType(ewayBillVO.getSupplyType());
		ewayBillNonIRNDTO.setSubSupplyType(ewayBillVO.getSubSupplyType());
		ewayBillNonIRNDTO.setSubSupplyDesc(ewayBillVO.getSubSupplyDesc());
		ewayBillNonIRNDTO.setDocType(ewayBillVO.getDocType());
		ewayBillNonIRNDTO.setDocNo(ewayBillVO.getDocNo());
		ewayBillNonIRNDTO.setDocDate(ewayBillVO.getDocDate());

		ewayBillNonIRNDTO.setFromGstin(ewayBillVO.getFromGstin());
		ewayBillNonIRNDTO.setFromTrdName(ewayBillVO.getFromTrdName());
		ewayBillNonIRNDTO.setFromAddr1(ewayBillVO.getFromAddr1());
		ewayBillNonIRNDTO.setFromAddr2(ewayBillVO.getFromAddr2());
		ewayBillNonIRNDTO.setFromPlace(ewayBillVO.getFromPlace());
		ewayBillNonIRNDTO.setFromPincode(ewayBillVO.getFromPincode());
		ewayBillNonIRNDTO.setActFromStateCode(ewayBillVO.getActFromStateCode());
		ewayBillNonIRNDTO.setFromStateCode(ewayBillVO.getFromStateCode());

		ewayBillNonIRNDTO.setToGstin(ewayBillVO.getToGstin());
		ewayBillNonIRNDTO.setToTrdName(ewayBillVO.getToTrdName());
		ewayBillNonIRNDTO.setToAddr1(ewayBillVO.getToAddr1());
		ewayBillNonIRNDTO.setToAddr2(ewayBillVO.getToAddr2());
		ewayBillNonIRNDTO.setToPlace(ewayBillVO.getToPlace());
		ewayBillNonIRNDTO.setToPincode(ewayBillVO.getToPincode());
		ewayBillNonIRNDTO.setActToStateCode(ewayBillVO.getActToStateCode());
		ewayBillNonIRNDTO.setToStateCode(ewayBillVO.getToStateCode());

		ewayBillNonIRNDTO.setTransactionType(ewayBillVO.getTransactionType());
		ewayBillNonIRNDTO.setOtherValue(ewayBillVO.getOtherValue());

		ewayBillNonIRNDTO.setCgstValue(ewayBillVO.getCgstValue());
		ewayBillNonIRNDTO.setSgstValue(ewayBillVO.getSgstValue());
		ewayBillNonIRNDTO.setIgstValue(ewayBillVO.getIgstValue());
		ewayBillNonIRNDTO.setCessValue(ewayBillVO.getCessValue());
		ewayBillNonIRNDTO.setCessNonAdvolValue(ewayBillVO.getCessNonAdvolValue());
		ewayBillNonIRNDTO.setTotInvValue(ewayBillVO.getTotInvValue());

		ewayBillNonIRNDTO.setTransporterId(ewayBillVO.getTransporterId());
		ewayBillNonIRNDTO.setTransporterName(ewayBillVO.getTransporterName());
		ewayBillNonIRNDTO.setTransDocNo(ewayBillVO.getTransDocNo());
		ewayBillNonIRNDTO.setTransMode(ewayBillVO.getTransMode());
		ewayBillNonIRNDTO.setTransDistance(ewayBillVO.getTransDistance());
		ewayBillNonIRNDTO.setTransDocDate(ewayBillVO.getTransDocDate());
		ewayBillNonIRNDTO.setVehicleNo(ewayBillVO.getVehicleNo());
		ewayBillNonIRNDTO.setVehicleType(ewayBillVO.getVehicleType());
		List<ItemListDTO> itemListDTOs = new ArrayList<>();

		for (EwayBillDetailsVO detailVO : ewayBillVO.getEwayBillDetailsVO()) {

			ItemListDTO itemDTO = new ItemListDTO();

			itemDTO.setProductName(detailVO.getProductName());
			itemDTO.setProductDesc(detailVO.getProductDesc());
			itemDTO.setHsnCode(detailVO.getHsnCode());
			itemDTO.setQuantity(detailVO.getQuantity());
			itemDTO.setQtyUnit(detailVO.getQtyUnit());
			itemDTO.setCgstRate(detailVO.getCgstRate());
			itemDTO.setSgstRate(detailVO.getSgstRate());
			itemDTO.setIgstRate(detailVO.getIgstRate());
			itemDTO.setCessRate(detailVO.getCessRate());
			itemDTO.setCessNonadvol(detailVO.getCessNonadvol());
			itemDTO.setTaxableAmount(detailVO.getTaxableAmount());

			itemListDTOs.add(itemDTO);
		}

		ewayBillNonIRNDTO.setItemList(itemListDTOs);

		return ewayBillNonIRNDTO;
	}

	private String formatDate(String dateString) {
		try {
			// Parse the incoming date string (adjust format if necessary)
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date date = inputFormat.parse(dateString);

			// Define the desired output format
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

			// Return the formatted date
			return outputFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null; // In case of parsing error, you can return a default or error value
		}
	}

	@Override
	public Map<String, Object> createEWayBillNonIRN(List<String> docId) throws JsonProcessingException {
		String message = null;
		for (String docid : docId) {
			String gstin = "";
			String clientId = "";
			String clientSecret = "";
			String authToken = "";
			String sek = "";
			List<EwayBillDirectVO> ewayBillDirectVOs = ewayBillDirectRepo.getDocidDetails(docId);
			List<EwayBillDirectVO> updatedEwayBillDirectVOs = new ArrayList<>();
			Set<Object[]> headerDetails = ewayBillResponseRepo.getEwayHeaderDetails(docid);
			if (!headerDetails.isEmpty()) {
				Object[] firstRow = headerDetails.iterator().next(); // Get the first row
				gstin = firstRow[1].toString();
				clientId = firstRow[2].toString();
				clientSecret = firstRow[3].toString();
				authToken = firstRow[4].toString();
				sek = firstRow[5].toString();
			}

			EwayBillResponseDTO ewayBillResponseDTO = new EwayBillResponseDTO();
			EwayBillPayLoadDTO payloadDTO = new EwayBillPayLoadDTO();

//			EwayBillNonIRNDTO billNonIRNDTO = generateEwayBillByNonIRN(docid);

			Object eWayPaload = generateEwayBillByNonIRN(docid);

			// Convert object to JSON string
			ObjectMapper objectMapper = new ObjectMapper();
			String name = objectMapper.writeValueAsString(eWayPaload);
			String encryptedName = encryptBySymmetricKey1(name, sek);
			System.out.println("Encrypted Name: " + encryptedName);
			payloadDTO.setData(encryptedName);
			payloadDTO.setAction("GENEWAYBILL");
			// Sandbox
			String url = "https://ewb1api.gstsandbox.nic.in/ewaybillapi/v1.03/ewayapi";

			// Live
//		    String url = "https://api.ewaybillgst.gov.in/v1.03/ewayapi";
			HttpHeaders headers = new HttpHeaders();
			headers.set("client_id", clientId);
			headers.set("client_secret", clientSecret);
			headers.set("gstin", gstin);
			headers.set("authtoken", authToken);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<EwayBillPayLoadDTO> request = new HttpEntity<>(payloadDTO, headers);
			RestTemplate restTemplate = new RestTemplate();
			try {
				ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

				System.out.println("Raw Response: " + response.getBody());
				for (EwayBillDirectVO eBillDirectVO : ewayBillDirectVOs) {
					eBillDirectVO.setEapicall("T");
					updatedEwayBillDirectVOs.add(eBillDirectVO); // ✅ Add to a separate list
				}
				ewayBillDirectRepo.saveAll(updatedEwayBillDirectVOs);
				EwayBillResponseVO ewayBillResponseVO = new EwayBillResponseVO();
				ewayBillResponseVO.setDocid(docid);
				ewayBillResponseVO.setResponse(response.getBody());
				ObjectMapper objectMapper12 = new ObjectMapper();
				Map<String, Object> mp12 = objectMapper12.readValue(response.getBody(),
						new TypeReference<Map<String, Object>>() {
						});

				String status = (String) mp12.get("status");
				if ("1".equals(status)) {
					String datas = mp12.get("data").toString();
					byte[] dt = datas.getBytes(StandardCharsets.UTF_8);
					ewayBillResponseDTO.setData(dt);
					if (ewayBillResponseDTO.getData() != null) {
						String decryptedText = decryptBySymmetricKey(datas, sek);
						ObjectMapper objectMapper3 = new ObjectMapper();
						Map<String, Object> decryptedMap = objectMapper3.readValue(decryptedText, Map.class);
						System.out.println("Decrypted Data " + decryptedMap);
						EwayResponseVO ewayResponseVO1 = new EwayResponseVO();
						ewayResponseVO1.setDocid(docid);
						ewayResponseVO1.setEwbdate(
								decryptedMap.get("ewayBillDate") != null ? decryptedMap.get("ewayBillDate").toString()
										: "");
						ewayResponseVO1.setEwbno(
								decryptedMap.get("ewayBillNo") != null ? decryptedMap.get("ewayBillNo").toString()
										: "");
						ewayResponseVO1.setEwvalidtill(
								decryptedMap.get("validUpto") != null ? decryptedMap.get("validUpto").toString() : "");
						ewayResponseVO1.setAlert(
								decryptedMap.get("alert") != null ? decryptedMap.get("alert").toString() : "");
						ewayResponseVO1.setType("Direct");
						ewayResponseRepo.save(ewayResponseVO1);
						ewayBillResponseVO.setDocid(docid);
						ewayBillResponseVO.setIserror("N");
						ewayBillResponseVO.setMessage("E-Way Generated");
						ewayBillResponseRepo.save(ewayBillResponseVO);

						for (EwayBillDirectVO eBillDirectVO : ewayBillDirectVOs) {
							eBillDirectVO.setEwbno(ewayResponseVO1.getEwbno());
							eBillDirectVO.setEwbdate(ewayResponseVO1.getEwbdate());
							eBillDirectVO.setEwbvalidtill(ewayResponseVO1.getEwvalidtill());
							eBillDirectVO.setEwaystatus("T");
							updatedEwayBillDirectVOs.add(eBillDirectVO);
						}
						ewayBillDirectRepo.saveAll(updatedEwayBillDirectVOs);
					}
				} else {

					for (EwayBillDirectVO eBillDirectVO : ewayBillDirectVOs) {
						eBillDirectVO.setEwaystatus("F");
						updatedEwayBillDirectVOs.add(eBillDirectVO); // ✅ Add to a separate list
					}
					ewayBillDirectRepo.saveAll(updatedEwayBillDirectVOs);
					// Handle error response
					String encodedError = (String) mp12.get("error");
					if (encodedError != null) {
						byte[] decodedErrorBytes = Base64.getDecoder().decode(encodedError);
						String decodedError = new String(decodedErrorBytes, StandardCharsets.UTF_8);
						System.out.println("API Error: " + decodedError);
						ewayBillResponseVO.setIserror("Y");
						ewayBillResponseVO.setDocid(docid);
						ObjectMapper objectMapper25 = new ObjectMapper();
						JsonNode errorJson = objectMapper25.readTree(decodedError);
						String rawErrorCodes = errorJson.get("errorCodes").asText(); // "604,640,688,751,"
						ewayBillResponseVO.setMessage("ErrorCode: " + rawErrorCodes);
						// Remove the trailing comma (if any)
						String cleanedErrorCodes = rawErrorCodes.replaceAll(",$", "");
						ewayBillResponseRepo.save(ewayBillResponseVO);

					} else {
						System.out.println("Unknown error or unexpected response: " + response.getBody());
					}
				}
				message = "EwayBill Generated Successfully";

			} catch (Exception e) {
				e.printStackTrace();
				return null; // Handle errors properly based on your business logic
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return response;
	}

	public String decryptBySymmetricKey(String encryptedText, String decryptedSek) throws Exception {
		// Decode the AES key from Base64
		byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
		SecretKey aesKey = new SecretKeySpec(sekByte, "AES");

		// Initialize AES cipher for decryption
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);

		// Decode and decrypt the encrypted text
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

		// Convert decrypted bytes to a string and return
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public String encryptBySymmetricKey1(String textToEncrypt, String decryptedSek) {
		try {
			if (decryptedSek == null || decryptedSek.isEmpty()) {
				throw new IllegalArgumentException("Secret key (SEK) is empty or null");
			}

			byte[] sekByte = Base64.getDecoder().decode(decryptedSek);
			SecretKey aesKey = new SecretKeySpec(sekByte, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);

			byte[] encryptedBytes = cipher.doFinal(textToEncrypt.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return "Encryption error: " + e.getMessage();
		}
	}

	@Scheduled(fixedRate = 2000)
	public void processEWayBillNonIRN() throws JsonProcessingException {
		System.out.println("Running E-Way service every 1 Sec...");
		// Replace with actual branchCode

		List<Object[]> getPendingEwayDetails = ewayBillResponseRepo.getPendingEwayNonIRNDetails();
		if (getPendingEwayDetails != null) {

			int length = getPendingEwayDetails.size();
			System.out.println("Length of the list: " + length);
			// Extract docIds from the list
			List<String> docIds = new ArrayList<>();
			for (Object[] record : getPendingEwayDetails) {
				if (record != null && record.length > 0) {
					String docId = record[0].toString();
					docIds.add(docId);
				}
			}
			// Call the service method with the collected docIds
			if (!docIds.isEmpty()) {
				System.out.println(" Process Success.");
				createEWayBillNonIRN(docIds);

			} else {
				System.out.println("No docIds found to process.");
			}
		} else {
			System.out.println("List is null.");
		}

	}

//	@Override
//	public Map<String, Object> createEWayBill(List<String> docId) throws JsonProcessingException {
//		String message = null;
//		for (String irn : docId) {
//
//			List<EInvoiceVO> eInvoiceVOs = eInvoiceRepo.getIrnDetails(irn);
//			List<EInvoiceVO> updatedEInvoiceVOs = new ArrayList<>();
//
//			String userName = "";
//			String gstin = "";
//			String clientId = "";
//			String clientSecret = "";
//			String authToken = "";
//			String sek = "";
//
//			Set<Object[]> headerDetails = eInvoiceRepo.getEwayHeaderDetails(irn);
//			if (!headerDetails.isEmpty()) {
//				Object[] firstRow = headerDetails.iterator().next(); // Get the first row
//
//				userName = firstRow[0].toString();
//				gstin = firstRow[1].toString();
//				clientId = firstRow[2].toString();
//				clientSecret = firstRow[3].toString();
//				authToken = firstRow[4].toString();
//				System.out.println("Auth Token :" + authToken);
//				sek = firstRow[5].toString();
//				System.out.println("SEK  :" + sek);
//
//			}
//
//			EwayBillResponseDTO ewayBillResponseDTO = new EwayBillResponseDTO();
//
//			PayloadDTO payloadDTO = new PayloadDTO();
//			EwayBillDTO dto = getEWayBillByDocIdnew(irn);
//			Object eWayPaload = getEWayBillByDocIdnew(irn);
//
//			// Convert object to JSON string
//			ObjectMapper objectMapper = new ObjectMapper();
//			String name = objectMapper.writeValueAsString(eWayPaload);
//			String encryptedName = encryptBySymmetricKey1(name, sek);
//			payloadDTO.setData(encryptedName);
//
//			// SandBox API
//			String url = "https://einv1api.gstsandbox.nic.in/eiewb/v1.03/ewaybill";
//			// Live API
////			String url ="https://api.einvoice1.gst.gov.in/eiewb/v1.03/ewaybill";
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("client_id", clientId);
//			headers.set("client_secret", clientSecret);
//			headers.set("gstin", gstin);
//			headers.set("user_name", userName);
//			headers.set("authtoken", authToken);
//			System.out.println("TEST tOKEN :" + authToken);
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<PayloadDTO> request = new HttpEntity<>(payloadDTO, headers);
//			RestTemplate restTemplate = new RestTemplate();
//			try {
//				ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//
//				System.out.println("Raw Response: " + response.getBody());
//				EwayBillResponseVO ewayBillResponseVO = new EwayBillResponseVO();
//				ewayBillResponseVO.setDocid(irn);
//				ewayBillResponseVO.setResponse(response.getBody());
//				ObjectMapper objectMapper5 = new ObjectMapper();
//				Map<String, Object> mp1 = objectMapper5.readValue(response.getBody(),
//						new TypeReference<Map<String, Object>>() {
//						});
//				if (mp1.get("Status").equals(0)) {
//					ewayBillResponseVO.setIserror("Y");
//					Object errorDetailsObj = mp1.get("ErrorDetails");
//					if (errorDetailsObj instanceof List) {
//						List<?> errorDetailsList = (List<?>) errorDetailsObj;
//						if (!errorDetailsList.isEmpty() && errorDetailsList.get(0) instanceof Map) {
//							Map<?, ?> firstError = (Map<?, ?>) errorDetailsList.get(0);
//							Object errorCode = firstError.get("ErrorCode");
//							Object errorMessage = firstError.get("ErrorMessage");
//							if (errorCode != null) {
//								ewayBillResponseVO.setMessage("ErrorCode: " + errorCode.toString());
//								ewayBillResponseVO.setErrordetails(errorMessage.toString());
//							}
//						}
//					}
//
//				} else {
//					ewayBillResponseVO.setIserror("N");
//					ewayBillResponseVO.setMessage("E-Way Generated");
//				}
//				for (EInvoiceVO eInvoiceVO : eInvoiceVOs) {
//					eInvoiceVO.setEapicall("T");
//					updatedEInvoiceVOs.add(eInvoiceVO);
//				}
//				eInvoiceRepo.saveAll(updatedEInvoiceVOs);
//				ewayBillResponseRepo.save(ewayBillResponseVO);
//				// Convert JSON response to a Map
//				ObjectMapper objectMapper1 = new ObjectMapper();
//				Map<String, Object> mp = objectMapper1.readValue(response.getBody(),
//						new TypeReference<Map<String, Object>>() {
//						});
//
//				// Map to InvoiceResponse object
//				ewayBillResponseDTO
//						.setStatus(mp.get("Status") != null ? Integer.parseInt(mp.get("Status").toString()) : 0);
//
//				// Convert Data field if present
//				if (mp.get("Data") != null) {
//					String datas = mp.get("Data").toString();
//					byte[] dt = datas.getBytes(StandardCharsets.UTF_8);
//					ewayBillResponseDTO.setData(dt);
//					if (ewayBillResponseDTO.getData() != null) {
//						String decryptedText = decryptBySymmetricKey(datas, sek);
//						ObjectMapper objectMapper3 = new ObjectMapper();
//						Map<String, Object> decryptedMap = objectMapper3.readValue(decryptedText, Map.class);
//						System.out.println("Decrypted Data " + decryptedMap);
//						if (decryptedMap != null) {
//							EwayResponseVO ewayResponseVO1 = new EwayResponseVO();
//							ewayResponseVO1.setEwbdate(
//									decryptedMap.get("EwbDt") != null ? decryptedMap.get("EwbDt").toString() : "");
//							ewayResponseVO1.setEwbno(
//									decryptedMap.get("EwbNo") != null ? decryptedMap.get("EwbNo").toString() : "");
//							ewayResponseVO1.setEwvalidtill(decryptedMap.get("EwbValidTill") != null
//									? decryptedMap.get("EwbValidTill").toString()
//									: "");
//							ewayResponseVO1.setRemarks(
//									decryptedMap.get("Remarks") != null ? decryptedMap.get("Remarks").toString() : "");
//							if (mp.get("InfoDtls") != null) {
//								List<Map<String, Object>> infoDtlsList = (List<Map<String, Object>>) mp.get("InfoDtls");
//								if (!infoDtlsList.isEmpty()) {
//									Object desc = infoDtlsList.get(0).get("Desc");
//									ewayResponseVO1.setAlert(desc != null ? desc.toString() : "");
//								}
//							}
//							ewayResponseVO1.setIrn(dto.getIrn());
//
//							for (EInvoiceVO eInvoiceVO1 : eInvoiceVOs) {
//								eInvoiceVO1.setEwbno(
//										decryptedMap.get("EwbNo") != null ? decryptedMap.get("EwbNo").toString() : "");
//								eInvoiceVO1.setEwbdate(
//										decryptedMap.get("EwbDt") != null ? decryptedMap.get("EwbDt").toString() : "");
//								eInvoiceVO1.setEwbvalidtill(decryptedMap.get("EwbValidTill") != null
//										? decryptedMap.get("EwbValidTill").toString()
//										: "");
//								ewayResponseVO1.setDocid(eInvoiceVO1.getDocid());
//								ewayResponseVO1.setType("IRN");
//								eInvoiceVO1.setEwaystatus("T");
//								updatedEInvoiceVOs.add(eInvoiceVO1);
//								;
//							}
//							eInvoiceRepo.saveAll(updatedEInvoiceVOs);
//
//							ewayResponseRepo.save(ewayResponseVO1);
//						}
//
//					} else {
//						for (EInvoiceVO eInvoiceVO : eInvoiceVOs) {
//							eInvoiceVO.setEwaystatus("F");
//							updatedEInvoiceVOs.add(eInvoiceVO);
//						}
//						eInvoiceRepo.saveAll(updatedEInvoiceVOs);
//					}
//				}
//				message = "EwayBill Genaretd Successfully";
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null; // Handle errors properly based on your business logic
//			}
//		}
//		Map<String, Object> response = new HashMap<>();
//		response.put("message", message);
//		return response;
//	}

}
