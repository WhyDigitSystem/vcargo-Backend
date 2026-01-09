package com.efit.savaari.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.efit.savaari.entity.MaintenancePartVO;
import com.efit.savaari.entity.TvehicleVO;
import com.efit.savaari.entity.UserVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDTO {

	private Long id;
	private String title;
	private String type; // preventive / corrective
	private String status; // pending / completed
	private String priority; // low / medium / high
	private LocalDate scheduledDate;
	private LocalDate completedDate;
	private BigDecimal odometerReading;
	private BigDecimal estimatedCost;
	private String serviceCenter;
	private String mechanic;
	private String description;
	private String notes;
	private String createdBy;
	private String branchCode;
	private String branchName;
	private Long orgId;
    private Long user;
	private Long vehicleId;
    private List<MaintenancePartDTO> parts;
}
