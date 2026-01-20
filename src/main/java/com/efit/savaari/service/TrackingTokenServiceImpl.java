package com.efit.savaari.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
public class TrackingTokenServiceImpl  implements TrackingTokenService{

	@Autowired
	private TrackingTokenRepo trackingTokenRepo;
	
//	@Override
//	public TrackingTokenResponse getTokenFromMasters() {
//
//	    RestTemplate restTemplate = new RestTemplate();
//
//	    String url = "https://api-platform.mastersindia.co/api/v2/token-auth/";
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//
//	    TrackingDetailsRequest request = new TrackingDetailsRequest();
//	    request.setUsername("info@whydigit.com");
//	    request.setPassword("Masters@12345");
//
//	    HttpEntity<TrackingDetailsRequest> entity =
//	            new HttpEntity<>(request, headers);
//
//	    ResponseEntity<TrackingTokenResponse> response =
//	            restTemplate.postForEntity(url, entity, TrackingTokenResponse.class);
//
//	    TrackingTokenResponse res = response.getBody();
//
//	    // ✅ SAVE INTO trackingtoken TABLE
//	    TrackingTokenVO vo = new TrackingTokenVO();
//	    vo.setToken(res.getToken());
//	    vo.setRefreshToken(res.getRefresh_token());
//	    // commonDate will auto set if you set in constructor / listener
//
//	    trackingTokenRepo.save(vo);
//
//	    return res;
//	}
//
//	
//	@Override
//	public Map<String, Object> getToken() {
//
//	    TrackingTokenVO tokenVO = trackingTokenRepo.findTopByOrderByIdDesc();
//
//	    Map<String, Object> map = new HashMap<>();
//	    map.put("token", tokenVO.getToken());
//	    map.put("refreshToken", tokenVO.getRefreshToken());
//
//	    return map;
//	}
	
	@Override
	public Map<String, Object> getToken() {

	    // ✅ 1. Check latest token from DB
	    TrackingTokenVO tokenVO = trackingTokenRepo.findTopByOrderByIdDesc();

	    if (tokenVO != null && tokenVO.getToken() != null) {

	        Map<String, Object> map = new HashMap<>();
	        map.put("token", tokenVO.getToken());
	        map.put("refreshToken", tokenVO.getRefreshToken());
	        map.put("source", "DB");

	        return map;
	    }

	    // ✅ 2. If not found → call MastersIndia API
	    RestTemplate restTemplate = new RestTemplate();

	    String url = "https://api-platform.mastersindia.co/api/v2/token-auth/";

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    TrackingDetailsRequest request = new TrackingDetailsRequest();
	    request.setUsername("info@whydigit.com");
	    request.setPassword("Masters@12345");

	    HttpEntity<TrackingDetailsRequest> entity =
	            new HttpEntity<>(request, headers);

	    ResponseEntity<TrackingTokenResponse> response =
	            restTemplate.postForEntity(url, entity, TrackingTokenResponse.class);

	    TrackingTokenResponse res = response.getBody();

	    if (res == null || res.getToken() == null) {
	        throw new RuntimeException("MastersIndia token API failed");
	    }

	    // ✅ 3. Save into DB
	    TrackingTokenVO vo = new TrackingTokenVO();
	    vo.setToken(res.getToken());
	    vo.setRefreshToken(res.getRefresh_token());

	    trackingTokenRepo.save(vo);

	    // ✅ 4. Return response
	    Map<String, Object> map = new HashMap<>();
	    map.put("token", res.getToken());
	    map.put("refreshToken", res.getRefresh_token());
	    map.put("source", "API");

	    return map;
	}

	
	 @Override
	    public Map<String, Object> callFastagApi(FastagRequestDTO dto) {

	        String token;

	        // ✅ 1. Token from DTO or DB
	        if (dto.getToken() != null && !dto.getToken().isEmpty()) {
	            token = dto.getToken();
	        } else {
	            TrackingTokenVO latest =
	                    trackingTokenRepo.findTopByOrderByIdDesc();

	            if (latest == null) {
	                throw new RuntimeException(
	                        "Token not found. Please generate token first.");
	            }
	            token = latest.getToken();
	        }

	        // ✅ 2. FASTAG API URL
	        String url =
	          "https://api-platform.mastersindia.co/api/v2/sbt/FASTAG/";

	        // ✅ 3. Headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        headers.set("Subid", dto.getSubid());
	        headers.set("Productid", dto.getProductid());
	        headers.set("Mode", dto.getMode());

	        // MastersIndia expects JWT
	        headers.set("Authorization", "JWT " + token);

	        // ✅ 4. Body
	        Map<String, String> body = new HashMap<>();
	        body.put("vehiclenumber", dto.getVehiclenumber());

	        HttpEntity<Map<String, String>> entity =
	                new HttpEntity<>(body, headers);

	        RestTemplate restTemplate = new RestTemplate();

	        // ✅ 5. Call API
	        ResponseEntity<JsonNode> response =
	                restTemplate.postForEntity(url, entity, JsonNode.class);

	        JsonNode root = response.getBody();

	        if (root == null) {
	            throw new RuntimeException("Empty FASTAG response");
	        }

	        // ✅ 6. FLATTEN RESPONSE
	        Map<String, Object> finalResponse = new HashMap<>();

	        JsonNode responseArr =
	                root.path("data").path("response");

	        if (responseArr.isArray() && responseArr.size() > 0) {

	            JsonNode mainResp = responseArr.get(0).path("response");

	            String result = mainResp.path("result").asText();
	            finalResponse.put("status", result);

	            JsonNode vehicleNode = mainResp.path("vehicle");
	            JsonNode txnList = vehicleNode.path("vehltxnList");

	            if (!txnList.isMissingNode() && !txnList.isNull()) {

	                finalResponse.put("totalTransactions",
	                        txnList.path("totalTagsInMsg").asInt());

	                JsonNode txns = txnList.path("txn");

	                List<Map<String, Object>> txnArray = new ArrayList<>();

	                if (txns.isArray()) {
	                    for (JsonNode txn : txns) {

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
	                }

	                finalResponse.put("transactions", txnArray);
	            }
	        }

	        return finalResponse;
	    }
}

