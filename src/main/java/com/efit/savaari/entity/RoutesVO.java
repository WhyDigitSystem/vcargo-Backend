package com.efit.savaari.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efit.savaari.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routesgen")
	@SequenceGenerator(name = "routesgen", sequenceName = "routesseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "routesid")
	private Long id;
	
	@Column(name = "customer")
	private String customer;
	@Column(name = "origin")
	private String origin;
	@Column(name = "destination")
	private String destination;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "mileage")
	private String mileage;
	
	@Column(name = "fuelrate")
	private int fuelRate;
	@Column(name = "showpumps")
	private boolean showpumps;
	@Column(name = "route")
	private String route;
	@Column(name = "tat")
	private String tat;
	
	@Column(name = "orgid")
	private long orgId;
//	@Column(name = "branchid")
//	private String branchId;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branchname")
	private String branchName;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "active")
	private boolean active;
	@Column(name = "remarks")
	private String remarks;
	
	
    @OneToMany(mappedBy = "routesVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<RoutesPitstopVO> routesPitStopVO;
    
    @OneToMany(mappedBy = "routesVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<RoutesPetrolPumpsVO> routesPetrolPumpsVO;
    

    @OneToMany(mappedBy = "routesVO",cascade = CascadeType.ALL)
   	@JsonManagedReference
   	private List<RoutesDetailsVO> routesDetailsVO;
    
//    @JsonGetter("active")
//	public String getActive() {
//		return active ? "Active" : "In-Active";
//	}
//	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
