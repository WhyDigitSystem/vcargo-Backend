package com.efit.savaari.service;

import java.util.List;
import java.util.Map;

import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.dto.TripInvoiceResponseDTO;
import com.efit.savaari.entity.TripInvoiceVO;

public interface TripInvoiceService {

    /** Create or Update Invoice */
//	TripInvoiceVO saveOrUpdate(TripInvoiceDTO dto);

    /** Get Invoice by ID */
//    TripInvoiceVO getById(Long invoiceId);

    /** Get All Invoices */
//    List<TripInvoiceVO> getAll();

	Map<String, Object> createUpdateTripInvoice(TripInvoiceDTO tripInvoiceDTO);

	TripInvoiceResponseDTO getTripInvoiceById(Long invoiceId);

	Map<String, Object> getAllTripInvoiceByOrgId(Long orgId, int page, int count);
}
