package com.efit.savaari.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentgen")
	@SequenceGenerator(name = "documentgen", sequenceName = "documentseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "documentid")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private String type;
	@Column(name = "issuedate")
	private LocalDate issueDate;
	@Column(name = "expirydate")
	private LocalDate expiryDate;
	@Column(name = "associatedwith")
	private String associatedWith;
	@Column(name = "associationtype")
	private String associationType;
	@Column(name = "description")
	private String description;
	@Column(name = "tags")
	private String tags;
	@Column(name = "documentno")
	private String documentNo;
	@Column(name = "status")
	private String status;
	@Column(name = "varifiedstatus")
	private String varifiedStatus;

	@Lob
	@Column(name = "fileupload")
	private byte[] fileUpload;

	@Column(name = "screencode", length = 5)
	private String screenCode = "DO";

	@Column(name = "screenname", length = 25)
	private String screenName = "DOCUMENT";

	@Column(name = "branchcode", length = 20)
	private String branchCode;

	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	
	@Column(name = "userid")
	private Long userId;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
