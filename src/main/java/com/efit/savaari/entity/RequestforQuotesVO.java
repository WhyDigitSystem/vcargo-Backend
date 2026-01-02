package com.efit.savaari.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requestforquotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestforQuotesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requestforquotesgen")
	@SequenceGenerator(name = "requestforquotesgen", sequenceName = "requestforquotesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "requestforquotesid")
	private Long id;

	@Column(name = "rfqdetails", length = 255)
	private String rfqDetails;

	@Column(name = "natureofcontract", length = 75)
	private String natureOfContract;

	@Column(name = "material", length = 255)
	private String material;

	@Column(name = "contractstartdate", length = 75)
	private LocalDate contractStartDate;

	@Column(name = "contractduration", length = 255)
	private String contractDuration;

	@Column(name = "termsandconditions", length = 75)
	private String termsAndConditions;
	
	@Column(name = "vendortags", length = 75)
	private String vendorTags;
	
	@Lob
	@Column(name = "documents")
	private byte[] documents;
	
	@Column(name = "numtransporter", length = 75)
	private String numTransporter;

	@Column(name = "additionalcharges", length = 255)
	private String additionalCharges;

	
	
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel=false;
	
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode", length = 5)
	private String screenCode = "RQ";

	@Column(name = "screenname", length = 25)
	private String screenName = "REQUESTFORQUOTES";
	
	
    @OneToMany(mappedBy = "requestforQuotesVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<LineItemsVO> lineItemsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}

