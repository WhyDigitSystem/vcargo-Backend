package com.efit.savaari.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndentsDTO {

	private Long id;
	private String status;
	private String customer;
	private String origin;
	private String destination;
	private String vechicleType;
	private String weight;
	private String route;
	private boolean pitStop;
	private String numberOfVechicles;
	private String customerRate;
	private String vendorRateVehicles;
	private String extraInfo;
	private LocalDate placementDate;
	private String orderType;
	private String dockerNo;
	private String tripType;
	private String materialType;
	private int overTimeHours;
	private int vendorRank;
	private String createdBy;
	private Long orgId;
	
	private String originPoc;
	private String destinationPoc;
	
	List<TimeLineDTO> timeLineDTO;
	List<VendorResponseDTO> vendorResponseDTO;
	List<TripsLinkedDTO> tripsLinkedDTO;
	List<TripsDocumentsDTO> tripsDocumentsDTO;
    List<IndentsPitstopDTO> indentsPitstopDTO;
    List<IndentsParticipantsDTO> indentsParticipantsDTO;


	
	
}
