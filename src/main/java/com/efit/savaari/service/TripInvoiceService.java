package com.efit.savaari.service;

import java.util.List;

import com.efit.savaari.dto.TripInvoiceDTO;
import com.efit.savaari.entity.TripInvoiceVO;

public interface TripInvoiceService {

    /** Create or Update Invoice */
	TripInvoiceVO saveOrUpdate(TripInvoiceDTO dto);

    /** Get Invoice by ID */
    TripInvoiceVO getById(Long invoiceId);

    /** Get All Invoices */
    List<TripInvoiceVO> getAll();
}
