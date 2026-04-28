package com.efit.savaari.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.efit.savaari.entity.CommentsVO;

@Service
public class CommentSyncService {

	@Autowired
	private RestTemplate restTemplate;

	@Async("taskExecutor")
	public void sendToServerB(CommentsVO commentsVO) {

		try {
			System.out.println("🚀 Sending A → B for ID: " + commentsVO.getId());

			Map<String, Object> body = new HashMap<>();

			body.put("comment", commentsVO.getComments());
			body.put("sourceId", commentsVO.getId());
			body.put("sourceOrgId", commentsVO.getOrgId());
			body.put("sourceTicketId", commentsVO.getTicketId());
			body.put("sourceUserName", commentsVO.getUserName());
			body.put("application", "VCARGO");
			body.put("ticketId", commentsVO.getTicketId());

//			String url = "http://localhost:8061/api/ticket/createComments";
			
			String url = "http://139.5.190.244:8061/api/ticket/createComments";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

			System.out.println("➡️ A → B Payload: " + body);

			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			System.out.println("✅ A → B Response: " + response.getBody());

		} catch (Exception e) {
			System.out.println("❌ ERROR A → B");
			e.printStackTrace();
		}
	}
}
