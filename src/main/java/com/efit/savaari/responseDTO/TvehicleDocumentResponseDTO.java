package com.efit.savaari.responseDTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TvehicleDocumentResponseDTO {

    private Long id;
    private String documentType;   // RC, INSURANCE, FC, etc.
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedOn;
}