package com.efit.savaari.responseDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdriverDocumentResponseDTO {

	private Long id;
	private String documentType; 
	private String fileName;
	private String filePath;
	private String fileType;
	private Long fileSize;
	private LocalDateTime uploadedOn;

}
