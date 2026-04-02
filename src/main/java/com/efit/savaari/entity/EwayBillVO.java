package com.efit.savaari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ewaybill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EwayBillVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ewaybillgen")
	@SequenceGenerator(name = "ewaybillgen", sequenceName = "ewaybillseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "ewaybillid")
	private Long id;

	@Column(name="supplyType")
	private String supplyType;

	@Column(name="subSupplyType")
	private String subSupplyType;

	@Column(name="subSupplyDesc")
	private String subSupplyDesc;

	@Column(name="docType")
	private String docType;

	@Column(name="docNo")
	private String docNo;

	@Column(name="docDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String docDate;

	@Column(name="fromGstin")
	private String fromGstin;

	@Column(name="fromTrdName")
	private String fromTrdName;

	@Column(name="fromAddr1")
	private String fromAddr1;

	@Column(name="fromAddr2")
	private String fromAddr2;

	@Column(name="fromPlace")
	private String fromPlace;

	@Column(name="fromPincode")
	private int fromPincode;

	@Column(name="actFromStateCode")
	private int actFromStateCode;

	@Column(name="fromStateCode")
	private int fromStateCode;

	@Column(name="toGstin")
	private String toGstin;

	@Column(name="toTrdName")
	private String toTrdName;

	@Column(name="toAddr1")
	private String toAddr1;

	@Column(name="toAddr2")
	private String toAddr2;

	@Column(name="toPlace")
	private String toPlace;

	@Column(name="toPincode")
	private int toPincode;

	@Column(name="actToStateCode")
	private int actToStateCode;

	@Column(name="toStateCode")
	private int toStateCode;

	@Column(name="transactionType")
	private int transactionType;

	@Column(name="otherValue")
	private String otherValue;

	@Column(name="totalValue")
	private double totalValue;

	@Column(name="cgstValue")
	private double cgstValue;

	@Column(name="sgstValue")
	private double sgstValue;

	@Column(name="igstValue")
	private double igstValue;

	@Column(name="cessValue")
	private double cessValue;

	@Column(name="cessNonAdvolValue")
	private double cessNonAdvolValue;

	@Column(name="totInvValue")
	private double totInvValue;

	@Column(name="transporterId")
	private String transporterId;

	@Column(name="transporterName")
	private String transporterName;

	@Column(name="transDocNo")
	private String transDocNo;

	@Column(name="transMode")
	private String transMode;

	@Column(name="transDistance")
	private String transDistance;

	@Column(name="transDocDate")
	private String transDocDate;

	@Column(name="vehicleNo")
	private String vehicleNo;

	@Column(name="vehicleType")
	private String vehicleType;
	
	@Column(name = "active")
	private boolean active = true;

	@Column(name = "cancel")
	private boolean cancel=false;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedby")
	private String updatedBy;

	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branch")
	private String branch;

	@OneToMany(mappedBy = "ewayBillVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<EwayBillDetailsVO> ewayBillDetailsVO;

}
