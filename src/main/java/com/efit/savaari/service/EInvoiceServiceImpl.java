package com.efit.savaari.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
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

import com.efit.savaari.dto.GenerateTokenDTO;
import com.efit.savaari.dto.PayloadDTO;
import com.efit.savaari.entity.HeaderDetailsVO;
import com.efit.savaari.repo.HeaderDetailsRepo;
import com.efit.savaari.responseDTO.EInvoiceGetToketDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EInvoiceServiceImpl implements EInvoiceService {

	@Autowired
	HeaderDetailsRepo headerDetailsRepo;
	
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

	
}
