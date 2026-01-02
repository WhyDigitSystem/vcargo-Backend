package com.efit.savaari.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicletype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicletypegen")
	@SequenceGenerator(name = "vehicletypegen", sequenceName = "vehicletypeseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vehicletypeid")
	private Long id;

	@Column(name = "vehicletype")
	private String vehicleType;
	
	@Column(name = "mileage")
	private Double mileage;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "hight")
	private Double hight;
	
	@Column(name = "width")
	private Double width;
	
	@Column(name = "length")
	private Double length;
	
	@Column(name = "vehiclesqftcapacity")
	private Double vehicleSqftCapacity;
	
	@Column(name = "vehicletonnagecapacity")
	private Double vehicleTonnageCapacity;
	
	//common fields
	
		@Column(name = "branch")
		private String branch;
		
		@Column(name = "branchcode")
		private String branchCode;
		
		@Column(name = "active")
		private boolean active = true;
		
		@Column(name = "createdby")
		private String createdBy;
		
		@Column(name = "modifiedby")
		private String updatedBy;
		
		@Column(name = "orgid")
		private Long orgId;
		
		@Column(name = "cancel")
		private boolean cancel;
		
		@Column(name = "screenname")
		private String screenName="VECHICLETYPE";
		
		@Column(name = "screencode")
		private String screenCode ="VTC";
		
		
		@JsonGetter("active")
		public String getActive() {
			return active ? "Active" : "In-Active";
		}

		// Optionally, if you want to control serialization for 'cancel' field similarly
		@JsonGetter("cancel")
		public String getCancel() {
			return cancel ? "T" : "F";
		}

		private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}
