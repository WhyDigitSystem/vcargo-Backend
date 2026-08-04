package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efit.savaari.dto.EwayBillNonIRNDTO;
import com.efit.savaari.responseDTO.EInvoiceGetToketDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface EInvoiceService {

//	Map<String, Object> createEWayBill(List<String> irn) throws JsonProcessingException;

//	Map<String, Object> generateToken(EInvoiceGetToketDTO eInvoiceGetToketDTO) throws JsonProcessingException, Exception;

	Map<String, Object> generateToken(List<EInvoiceGetToketDTO> eInvoiceGetToketDTO1) throws Exception;



}
