package com.efit.savaari.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {
	
	public Map<String, Object> buildResponse(Page<?> pageData) {

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageData.getContent());
        response.put("currentPage", pageData.getNumber()+1);
        response.put("totalCount", pageData.getTotalElements());
        response.put("totalPages", pageData.getTotalPages());
        response.put("pageSize", pageData.getSize());
        response.put("isFirst", pageData.isFirst());
        response.put("isLast", pageData.isLast());

        return response;
    }

}