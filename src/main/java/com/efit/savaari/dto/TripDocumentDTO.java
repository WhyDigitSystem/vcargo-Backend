package com.efit.savaari.dto;

import lombok.Data;

@Data
public class TripDocumentDTO {
    private String documentType;
    private byte[] document;
}
