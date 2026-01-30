package com.efit.savaari.service;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.efit.savaari.dto.ConsentDTO;
import com.efit.savaari.dto.TraqoTripRequest;
import com.efit.savaari.responseDTO.ConsentResponse;
import com.efit.savaari.responseDTO.TraqoErrorResponse;
import com.efit.savaari.responseDTO.TraqoTripResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TraqoService {

	private final RestTemplate restTemplate;

	public TraqoService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Object checkConsent(ConsentDTO request) {

		String url = "https://dashboard.traqo.in/api/v3/check_consent/";

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("whydigit", "whydigit@123");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<ConsentDTO> entity = new HttpEntity<>(request, headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class,
					request);

			String body = response.getBody();
			System.out.println("Traqo RAW => [" + body + "]");

			// ✅ 1. Empty body check
			if (body == null || body.trim().isEmpty()) {
				return new TraqoErrorResponse("EMPTY_RESPONSE");
			}

			body = body.trim();

			// ✅ 2. Must be JSON
			if (!body.startsWith("{")) {
				return new TraqoErrorResponse("INVALID_RESPONSE");
			}

			// ✅ 3. Error JSON
			if (body.contains("\"error\"")) {
				return new ObjectMapper().readValue(body, TraqoErrorResponse.class);
			}

			// ✅ 4. Success JSON
			return new ObjectMapper().readValue(body, ConsentResponse.class);

		} catch (HttpClientErrorException | HttpServerErrorException ex) {

			System.out.println("Traqo STATUS CODE => " + ex.getStatusCode());
			System.out.println("Traqo ERROR BODY => " + ex.getResponseBodyAsString());
			System.out.println("Traqo HEADERS => " + ex.getResponseHeaders());

			String errorBody = ex.getResponseBodyAsString();

			if (errorBody != null && errorBody.contains("Mobile Not Registered")) {
				return new TraqoErrorResponse("Mobile Not Registered For Tracking");
			}

			return new TraqoErrorResponse("TRAQO_API_ERROR");

		} catch (Exception e) {
			e.printStackTrace();
			return new TraqoErrorResponse("TRAQO_API_DOWN");
		}
	}

	private static final String URL = "https://dashboard.traqo.in/api/v3/trip/create/";

	public TraqoTripResponse createTrip(TraqoTripRequest request) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBasicAuth("whydigit", "whydigit@123");

        HttpEntity<TraqoTripRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<TraqoTripResponse> response =
                restTemplate.postForEntity(URL, entity, TraqoTripResponse.class);

        return response.getBody();
	}

}
