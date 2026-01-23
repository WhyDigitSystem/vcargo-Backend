package com.efit.savaari.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tdriver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdriverVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tdrivergen")
    @SequenceGenerator(name = "tdrivergen", sequenceName = "tdriverseq", initialValue = 1000000001, allocationSize = 1)
    @Column(name = "tdriverid")
    private Long id;

    @Column(name = "name", length = 150)
    private String name;
    
    @ManyToOne
	@JoinColumn(name = "userid")
    private UserVO user;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "licensenumber", length = 50)
    private String licenseNumber;

    @Column(name = "licenseexpiry")
    private LocalDate licenseExpiry;

    @Column(name = "aadharnumber", length = 20)
    private String aadharNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "experience", length = 50)
    private String experience;

    @Column(name = "salary", length = 50)
    private String salary;

    @Column(name = "assignedvehicle", length = 30)
    private String assignedVehicle;

    @Column(name = "currentlocation", length = 100)
    private String currentLocation;

    @Column(name = "bloodgroup", length = 10)
    private String bloodGroup;

    @Column(name = "emergencycontact", length = 20)
    private String emergencyContact;

    @Column(name = "performance", length = 20)
    private String performance;

    @Column(name = "joineddate")
    private LocalDate joinedDate;

    @Column(name = "lasttrip")
    private LocalDate lastTrip;

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

    @Column(name = "branchcode")
    private String branchCode;

    @Column(name = "branchname")
    private String branchName;

    @OneToMany(mappedBy = "tdriverVO", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TdriverDocumentsVO> tdriverDocumentsVO;

    @Embedded
    private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
