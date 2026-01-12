package com.efit.savaari.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.dto.ResponseDTO;
import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.entity.TripInvoiceVO;
import com.efit.savaari.service.TripInvoiceService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/tripinvoice")
public class TripInvoiceController extends BaseController {

    @Autowired
    private TripInvoiceService tripInvoiceService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveOrUpdate(@RequestBody TripInvoiceDTO dto) {

        TripInvoiceVO invoice = tripInvoiceService.saveOrUpdate(dto);

        Map<String, Object> map = new HashMap<>();
        map.put("message", dto.getInvoiceId() == null
                ? "Invoice created successfully"
                : "Invoice updated successfully");
        map.put("invoice", invoice);

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @GetMapping("/getById")
    public ResponseEntity<ResponseDTO> getById(@RequestParam Long invoiceId) {

        TripInvoiceVO invoice = tripInvoiceService.getById(invoiceId);

        Map<String, Object> map = new HashMap<>();
        map.put("invoice", invoice);

        return ResponseEntity.ok(createServiceResponse(map));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {

        List<TripInvoiceVO> list = tripInvoiceService.getAll();

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);

        return ResponseEntity.ok(createServiceResponse(map));
    }
}

