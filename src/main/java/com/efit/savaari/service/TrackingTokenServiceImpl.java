package com.efit.savaari.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.efit.savaari.dto.FastagRequestDTO;
import com.efit.savaari.dto.TrackingDetailsRequest;
import com.efit.savaari.entity.TrackingTokenVO;
import com.efit.savaari.repo.TrackingTokenRepo;
import com.efit.savaari.responseDTO.TrackingTokenResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TrackingTokenServiceImpl  implements TrackingTokenService{
	
	private static final Logger LOGGER =
	        LoggerFactory.getLogger(TrackingTokenServiceImpl.class);


	@Autowired
	private TrackingTokenRepo trackingTokenRepo;
	
	 @Autowired
	 private RestTemplate restTemplate;

	    // ================= TOKEN API =================

	    @Override
	    public Map<String, Object> getToken() {

	        TrackingTokenVO tokenVO =
	                trackingTokenRepo.findTopByOrderByIdDesc();

	        // ✅ if exists and not expired → use DB
	        if (tokenVO != null && !isTokenExpired(tokenVO.getTokenExpiry())) {

	            Map<String, Object> map = new HashMap<>();
	            map.put("token", tokenVO.getToken());
	            map.put("refreshToken", tokenVO.getRefreshToken());
	            map.put("expiry", tokenVO.getTokenExpiry());
	            map.put("source", "DB");
	            return map;
	        }

	        // ❗ expired or not exists → create new
	        return createAndSaveNewToken();
	    }

	    
	    @Override
	    public void autoRefreshIfExpired() {

	        TrackingTokenVO latest =
	                trackingTokenRepo.findTopByOrderByIdDesc();

	        if (latest == null) {
	            LOGGER.info("No token found, creating new token");
	            createAndSaveNewToken();
	            return;
	        }

	        if (isTokenExpired(latest.getTokenExpiry())) {
	            LOGGER.info("Token expired / near expiry, refreshing...");
	            createAndSaveNewToken();
	        } else {
	            LOGGER.info("Token still valid till {}", latest.getTokenExpiry());
	        }
	    }

	    private Map<String, Object> createAndSaveNewToken() {

	    	 TrackingTokenVO cred =
	    	            trackingTokenRepo.findTopByOrderByIdDesc();
	    	 
	    	  if (cred == null || cred.getUsername() == null) {
	    	        throw new RuntimeException("Credentials not found in trackingtoken table");
	    	    }
	    	  
	        String url =
	            "https://api-platform.mastersindia.co/api/v2/token-auth/";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        TrackingDetailsRequest request = new TrackingDetailsRequest();
//	        request.setUsername("info@whydigit.com");
//	        request.setPassword("Masters@12345");
	        request.setUsername(cred.getUsername());
	        request.setPassword(cred.getPassword());

	        HttpEntity<TrackingDetailsRequest> entity =
	                new HttpEntity<>(request, headers);

	        ResponseEntity<TrackingTokenResponse> response =
	                restTemplate.postForEntity(url, entity,
	                        TrackingTokenResponse.class);

	        TrackingTokenResponse res = response.getBody();

	        if (res == null || res.getToken() == null) {
	            throw new RuntimeException("Token API failed");
	        }

	        // ✅ extract expiry
	        Long exp = getExpiryFromToken(res.getToken());
	        LocalDateTime expiryTime = convertToDate(exp);

	        // ✅ save DB
	        TrackingTokenVO vo = new TrackingTokenVO();
	        vo.setUsername(cred.getUsername());
	        vo.setPassword(cred.getPassword());
	        vo.setToken(res.getToken());
	        vo.setRefreshToken(res.getRefresh_token());
	        vo.setTokenExpiry(expiryTime);

	        trackingTokenRepo.save(vo);

	        Map<String, Object> map = new HashMap<>();
	        map.put("token", res.getToken());
	        map.put("refreshToken", res.getRefresh_token());
	        map.put("expiry", expiryTime);
	        map.put("source", "API");

	        return map;
	    }

	    // ================= FASTAG API =================

	    @Override
	    public Map<String, Object> callFastagApi(FastagRequestDTO dto) {

	        // ✅ always get valid token automatically
	        Map<String, Object> tokenMap = getToken();
	        String token = (String) tokenMap.get("token");

	        String url =
	            "https://api-platform.mastersindia.co/api/v2/sbt/FASTAG/";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        headers.set("Subid", dto.getSubid());
	        headers.set("Productid", dto.getProductid());
	        headers.set("Mode", dto.getMode());
	        headers.set("Authorization", "JWT " + token);

	        Map<String, String> body = new HashMap<>();
	        body.put("vehiclenumber", dto.getVehiclenumber());

	        HttpEntity<Map<String, String>> entity =
	                new HttpEntity<>(body, headers);

	        ResponseEntity<JsonNode> response =
	                restTemplate.postForEntity(url, entity, JsonNode.class);

	        JsonNode root = response.getBody();

	        if (root == null) {
	            throw new RuntimeException("Empty FASTAG response");
	        }

	        // ===== flatten response =====

	        Map<String, Object> finalResponse = new HashMap<>();

	        JsonNode responseArr =
	                root.path("data").path("response");

	        if (responseArr.isArray() && responseArr.size() > 0) {

	            JsonNode mainResp = responseArr.get(0).path("response");

	            finalResponse.put("status",
	                    mainResp.path("result").asText());

	            JsonNode txnList =
	                    mainResp.path("vehicle").path("vehltxnList");

	            if (!txnList.isNull()) {

	                finalResponse.put("totalTransactions",
	                        txnList.path("totalTagsInMsg").asInt());

	                List<Map<String, Object>> txnArray =
	                        new ArrayList<>();

	                for (JsonNode txn : txnList.path("txn")) {

	                    Map<String, Object> t = new HashMap<>();
	                    t.put("readerReadTime",
	                            txn.path("readerReadTime").asText());
	                    t.put("tollPlazaName",
	                            txn.path("tollPlazaName").asText());
	                    t.put("laneDirection",
	                            txn.path("laneDirection").asText());
	                    t.put("vehicleType",
	                            txn.path("vehicleType").asText());
	                    t.put("vehicleRegNo",
	                            txn.path("vehicleRegNo").asText());

	                    txnArray.add(t);
	                }

	                finalResponse.put("transactions", txnArray);
	            }
	        }

	        return finalResponse;
	    }

	    // ================= JWT UTILS =================

	    private Long getExpiryFromToken(String token) {
	        try {
	            String[] parts = token.split("\\.");
	            String payload = new String(
	                    Base64.getUrlDecoder().decode(parts[1]));
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode json = mapper.readTree(payload);
	            return json.get("exp").asLong();
	        } catch (Exception e) {
	            throw new RuntimeException("Invalid JWT token");
	        }
	    }

	    private LocalDateTime convertToDate(long expSeconds) {
	        return Instant.ofEpochSecond(expSeconds)
	                .atZone(ZoneId.systemDefault())
	                .toLocalDateTime();
	    }

	    private boolean isTokenExpired(LocalDateTime expiry) {
	        return expiry == null ||
	                LocalDateTime.now().isAfter(expiry);
	    }


	    @Override
	    public Map<String, Object> getTokenDetails() {

	        TrackingTokenVO cred =
	                trackingTokenRepo.findTopByOrderByIdDesc();

	        if (cred == null) {
	            throw new RuntimeException("No token found in database");
	        }

	        Map<String, Object> map = new HashMap<>();
	        map.put("token", cred.getToken());
	        map.put("refreshToken", cred.getRefreshToken());
	        map.put("expiry", cred.getTokenExpiry());

	        return map;   // ✅ IMPORTANT
	    }

}

