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

//	@Async("taskExecutor")
//	public void sendToServerB(CommentsVO commentsVO) {
//
//		System.out.println("🚀 Async Thread: " + Thread.currentThread().getName());
//
//		try {
//			Map<String, Object> body = new HashMap<>();
//
//			body.put("comment", commentsVO.getComments());
////			body.put("commentName", commentsVO.getUserName());
//			body.put("sourceId", commentsVO.getId());
//			body.put("sourceOrgId", commentsVO.getOrgId());
//			body.put("sourceTicketId", commentsVO.getOrgId());
//			body.put("sourceUserName", commentsVO.getUserName());
//			body.put("application", "VCARGO"); 
//
//			String url = "http://localhost:8061/api/ticket/createComments";
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//
//			System.out.println("🔥 Sending to Server B: " + body);
//
//			ResponseEntity<String> res = restTemplate.postForEntity(url, request, String.class);
//
//			System.out.println("✅ Response from Server B: " + res.getBody());
//
//		} catch (Exception e) {
//			System.err.println("❌ ERROR calling Server B");
//			e.printStackTrace();
//		}
//	}
	
	@Async("taskExecutor")
	public void sendToServerB(CommentsVO commentsVO) {

	    try {
	        Map<String, Object> body = new HashMap<>();

	        body.put("comment", commentsVO.getComments());
	        body.put("commentName", commentsVO.getUserName());
	        body.put("ticketId", commentsVO.getTicketId());
	        body.put("sourceId", commentsVO.getId());
	        body.put("sourceOrgId", commentsVO.getOrgId());
	        body.put("sourceTicketId", commentsVO.getTicketId());
	        body.put("sourceUserName", commentsVO.getUserName());

	        body.put("application", "VCARGO"); 

	        String url = "http://localhost:8061/api/ticket/createComments";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<Map<String, Object>> request =
	                new HttpEntity<>(body, headers);

	        System.out.println("➡️ A → B : " + body);

	        restTemplate.postForEntity(url, request, String.class);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
